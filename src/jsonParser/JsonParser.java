package jsonParser;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jsonParser.annotations.JsonIgnore;
import jsonParser.annotations.JsonOrder;
import jsonParser.exceptions.CircularReferenceException;
import jsonParser.exceptions.JsonParsingException;

public class JsonParser {

                            /*  User Visible Part  */
    public static String toJson(Object obj) throws IllegalAccessException {
        Set<Object> visited = Collections.newSetFromMap(new IdentityHashMap<>());

        return toJson(obj, visited, 0);
    }
    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        try {
            Parser p = new Parser(jsonString);
            @SuppressWarnings("unchecked")
            T obj = (T) p.parseValueAs(clazz);
            return obj;
        } catch (Exception e) {
                throw new JsonParsingException("Failed to parse JSON: " + e.getMessage(), e);
        }
    }
    public static <T> T fromJson(String jsonString, Class<T> clazz,
                                Class<?> mapKeyType, Class<?> mapValueType, Class<?> collectionElementType) {
        try {
            Parser p = new Parser(jsonString, mapKeyType, mapValueType, collectionElementType);
            @SuppressWarnings("unchecked")
            T obj = (T) p.parseValueAs(clazz);
            return obj;
        } catch (Exception e) {
            throw new JsonParsingException("Failed to parse JSON: " + e.getMessage(), e);
        }
    }
    public static <T> T fromJson(String jsonString, Class<T> clazz, Class<?> collectionElementType) {
        try {
            Parser p = new Parser(jsonString, null, null, collectionElementType);
            @SuppressWarnings("unchecked")
            T obj = (T) p.parseValueAs(clazz);
            return obj;
        } catch (Exception e) {
            throw new JsonParsingException("Failed to parse JSON: " + e.getMessage(), e);
        }
    }

                            /*  Private Parts  */

    private static class Parser {
        private final String input;
        private int pos;
        private final int len;
        private static final Set<Class<?>> NUMERIC_TYPES = Set.of(
            byte.class, Byte.class,
            short.class, Short.class,
            int.class, Integer.class,
            long.class, Long.class,
            float.class, Float.class,
            double.class, Double.class,
            Number.class
        );
        private final Class<?> mapKeyType;
        private final Class<?> mapValueType;
        private final Class<?> collectionElementType;

        Parser(String input) {
            this(input, null, null, null);
        }
        Parser(String input, Class<?> mapKeyType, Class<?> mapValueType, Class<?> collectionElementType) {
            this.input = ((input == null) ? "" : input);
            this.pos = 0;
            this.len = this.input.length();
            this.mapKeyType = mapKeyType;
            this.mapValueType = mapValueType;
            this.collectionElementType = collectionElementType;
        }

        private void skipWhiteSpace() {
            while (pos < len && Character.isWhitespace(input.charAt(pos)))
                pos++;
        }
        private char peek() {
            return ((pos < len) ? input.charAt(pos) : '\0');
        }
        private char next() {
            return ((pos < len) ? input.charAt(pos++) : '\0');
        }
        private boolean startsWith(String s) {
            if (pos + s.length() > len) 
                return false;

            return input.startsWith(s, pos);
        }
        private void expect(char expected) {
            skipWhiteSpace();
            if (pos >= len) {
                throw new IllegalArgumentException("Unexpected end of input");
            }
            
            char actual = input.charAt(pos);
            if (actual != expected) {
                String charInfo = String.format("'%c' (ASCII: %d)", actual, (int) actual);
                throw new IllegalArgumentException(
                    "Unexpected Json character at index " + pos + ": " + charInfo + ". Expected: '" + expected + "'"
                );
            }
            pos++;
        }
        private boolean isDigit(char c) {
            return (c >= '0' && c <= '9');
        }
        private boolean isJsonTerminator(char c) {
            return c == ' ' || c == '\t' || c == '\n' || c == '\r' || 
                c == ',' || c == '}' || c == ']' || c == '\0';
        }
        private boolean isNumericType(Class<?> target) {
            return target != null && NUMERIC_TYPES.contains(target);
        }
        private void skipValue() {
            skipWhiteSpace();
            char c = peek();
            
            if (c == '\"') {
                skipString();
            } else if (c == '{') {
                skipObject();
            } else if (c == '[') {
                skipArray();
            } else if (c == 't' || c == 'f') {
                skipBoolean();
            } else if (c == 'n') {
                skipNull();
            } else if (isDigit(c) || c == '-') {
                skipNumber();
            } else {
                throw new IllegalArgumentException("Unexpected character while skipping value: " + c);
            }
        }
        private void skipString() {
            expect('"');
            while (pos < len) {
                char ch = input.charAt(pos);
                if (ch == '"') {
                    pos++; // consume closing quote
                    return;
                }
                if (ch == '\\') {
                    pos++;
                    if (pos >= len)
                        throw new IllegalArgumentException("Unterminated escape sequence at pos " + pos);
                    char esc = input.charAt(pos);
                    if (esc == 'u') {
                        // ensure 4 hex digits exist
                        if (pos + 4 >= len)
                            throw new IllegalArgumentException("Incomplete \\u escape at pos " + pos);
                        // validate hex digits
                        for (int j = 1; j <= 4; j++) {
                            char hex = input.charAt(pos + j);
                            if (! ( (hex >= '0' && hex <= '9') ||
                                    (hex >= 'a' && hex <= 'f') ||
                                    (hex >= 'A' && hex <= 'F') ) ) {
                                throw new IllegalArgumentException("Invalid hex digit in \\u escape at pos " + (pos + j));
                            }
                        }
                        pos += 5; // move past 'u' and 4 hex digits (we are at 'u' index)
                    }
                    else {
                        // single-char escape like \", \\, \n, \t, etc.
                        pos++;
                    }
                }
                else {
                    pos++;
                }
            }
            throw new IllegalArgumentException("Unterminated string at position " + pos);
        }
        private void skipObject() {
            expect('{');
            skipWhiteSpace();
            if (peek() == '}') {
                next();
                return;
            }
            
            while (true) {
                skipString(); // Key
                expect(':');
                skipValue(); // Value
                
                skipWhiteSpace();
                char c = next();
                if (c == '}') break;
                if (c != ',') {
                    throw new IllegalArgumentException("Expected ',' or '}' in object");
                }
                skipWhiteSpace();
            }
        }
        private void skipArray() {
            expect('[');
            skipWhiteSpace();
            if (peek() == ']') {
                next();
                return;
            }
            
            while (true) {
                skipValue(); // Array element
                
                skipWhiteSpace();
                char c = next();
                if (c == ']') break;
                if (c != ',') {
                    throw new IllegalArgumentException("Expected ',' or ']' in array");
                }
                skipWhiteSpace();
            }
        }
        private void skipBoolean() {
            if (startsWith("true")) {
                pos += 4;
            } else if (startsWith("false")) {
                pos += 5;
            } else {
                throw new IllegalArgumentException("Expected boolean value");
            }
        }
        private void skipNull() {
            if (startsWith("null")) {
                pos += 4;
            } else {
                throw new IllegalArgumentException("Expected null value");
            }
        }
        private void skipNumber() {
            if (peek() == '-') next();
            
            if (peek() == '0') {
                next();
            } else {
                while (isDigit(peek())) next();
            }
            
            if (peek() == '.') {
                next();
                while (isDigit(peek())) next();
            }
            
            if (peek() == 'e' || peek() == 'E') {
                next();
                if (peek() == '+' || peek() == '-') next();
                while (isDigit(peek())) next();
            }
        }
        private Field findField(Class<?> targetClass, String fieldName) {
            return findField(targetClass, fieldName, false);
        }
        private Field findField(Class<?> targetClass, String fieldName, boolean caseSensitive) {
            Class<?> currentClass = targetClass;
            String searchName = caseSensitive ? fieldName : fieldName.toLowerCase();
            
            while (currentClass != null && currentClass != Object.class) {
                for (Field field : currentClass.getDeclaredFields()) {
                    String currentFieldName = caseSensitive ? field.getName() : field.getName().toLowerCase();
                    
                    if (currentFieldName.equals(searchName)) {
                        return field;
                    }
                }
                
                currentClass = currentClass.getSuperclass();
            }
            
            return null;
        }
        private Object createInstance(Class<?> clazz) {
            if (clazz == null)
                throw new JsonParsingException("Cannot create instance: target class is null");

            try {
                Constructor<?> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                return constructor.newInstance();
            } catch (NoSuchMethodException e) {
                throw new JsonParsingException("Class " + clazz.getName() + 
                                            " does not have a no-argument constructor", e);
            } catch (InstantiationException e) {
                throw new JsonParsingException("Cannot instantiate abstract class or interface: " + 
                                            clazz.getName(), e);
            } catch (IllegalAccessException e) {
                throw new JsonParsingException("No access to constructor of " + clazz.getName() + 
                                            " (is it private?)", e);
            } catch (InvocationTargetException e) {
                throw new JsonParsingException("Constructor of " + clazz.getName() + 
                                            " threw an exception: " + e.getTargetException().getMessage(), 
                                            e.getTargetException());
            }
        }

        Object parseValueAs(Class<?> target) {
            skipWhiteSpace();
            char c = peek();

            if (isDigit(c) || c == '-') {
                String numberStr = parseNumberText();
                return convertNumberTextToTarget(numberStr, target);
            }
            else if (c == 't' || c == 'f') {
                Boolean bool = parseBoolean();
                return bool;
            }
            else if (c == 'n') {
                parseNull();
                return null;
            }
            else if (c == '\"') {
                String str = parseString();
                return convertStringToTarget(str, target);
            }
            else if (c == '[') {
                if (target == null) {
                    return parseArray(ArrayList.class);
                } else if (target != null && Map.class.isAssignableFrom(target)) {
                    return parseMapFromPairs(target);
                } else {
                    return parseArray(target);
                }
            }
            else if (c == '{') {
                if (target == null) {
                    return parseMap(HashMap.class);
                }
                else if (target != null && Map.class.isAssignableFrom(target)) {
                    return parseMap(target);
                }
                else {
                    return parseObjectAs(target);
                }
            }
            else {
                throw new IllegalArgumentException(
                    "Unexpected character: '" + c + "' (ASCII: " + (int)c + ") at position " + pos +
                    ". Expected one of: \", {, [, t, f, n, digit, or -"
                );
            }
        }

        private Object parseObjectAs(Class<?> target) {
            expect('{');
            skipWhiteSpace();

            if (target == null)
                throw new JsonParsingException("Target class cannot be null when parsing an object");
            
            // Handle empty object case
            if (peek() == '}') {
                next();
                try {
                    return target.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new IllegalArgumentException(
                        "Cannot create instance of " + target.getName() + 
                        ". No-argument constructor required for empty objects.", e);
                }
            }
            
            Object instance = createInstance(target);

            while (true) {
                String key = parseString();
                expect(':');

                Field field = findField(target, key);
                if (field != null) {
                    try {
                        Object value = parseValueAs(field.getType());
                        field.setAccessible(true);

                        if (value == null && field.getType().isPrimitive()) 
                            throw new JsonParsingException("Cannot set primitive " + field.getType().getName() +
                                " to null for field " + field.getName());

                        field.set(instance, value);
                    } catch (IllegalAccessException e) {
                        System.out.println("Couldn\'t access field: " + field.getName());
                    } finally {
                        field.setAccessible(false);
                    }
                } else {
                    skipValue();
                }

                skipWhiteSpace();

                if (peek() == '}') {
                    next();
                    break;
                }
                else if (peek() == ',') {
                    next();
                    skipWhiteSpace();
                }
                else {
                    throw new IllegalArgumentException("Expected ',' or '}' after key-value pair");
                }
            }

            return instance;
        }
        @SuppressWarnings("unchecked")
        private Object parseMapFromPairs(Class<?> target) {
            expect('[');
            skipWhiteSpace();

            Map<Object, Object> map;
            try {
                if (target == Map.class || target == Object.class)
                    map = new HashMap<>();
                else
                    map = (Map<Object, Object>) target.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new JsonParsingException("Failed to create Map instance", e);
            }

            // empty array -> return empty map
            if (peek() == ']') {
                next();
                return map;
            }

            while (true) {
                skipWhiteSpace();
                expect('{');
                skipWhiteSpace();

                Object key = null;
                Object value = null;

                // parse fields inside the pair object
                while (true) {
                    String fieldName = parseString();
                    expect(':');
                    skipWhiteSpace();

                    if ("key".equals(fieldName)) {
                        key = parseValueAs(this.mapKeyType);
                    } else if ("value".equals(fieldName)) {
                        value = parseValueAs(this.mapValueType);
                    } else {
                        // ignore unknown fields inside pair object
                        skipValue();
                    }

                    skipWhiteSpace();
                    if (peek() == '}') {
                        next();
                        break;
                    } else if (peek() == ',') {
                        next();
                        skipWhiteSpace();
                    } else {
                        throw new IllegalArgumentException("Expected ',' or '}' inside pair object");
                    }
                }

                map.put(key, value);

                skipWhiteSpace();
                if (peek() == ']') {
                    next();
                    break;
                } else if (peek() == ',') {
                    next();
                    skipWhiteSpace();
                    continue;
                } else {
                    throw new IllegalArgumentException("Expected ',' or ']' after pair element");
                }
            }

            return map;
        }
        @SuppressWarnings("unchecked")
        private Object parseMap(Class<?> target) {
            expect('{');
            skipWhiteSpace();
            
            Map<Object, Object> map;
            try {
                if (target == Map.class || target == Object.class)
                    map = new HashMap<>();
                else
                    map = (Map<Object, Object>) target.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new JsonParsingException("Failed to create Map instance", e);
            }

            while (peek() != '}') {
                String keyStr = parseString();
                expect(':');

                // parse value using provided mapValueType (may be null)
                Object value = parseValueAs(null);

                map.put(keyStr, value);

                skipWhiteSpace();
                if (peek() == ',') {
                    next();
                    skipWhiteSpace();
                }
            }

            expect('}');
            return map;
        }
        @SuppressWarnings("unchecked")
        private Object parseArray(Class<?> target) {
            expect('[');
            skipWhiteSpace();

            if (peek() == ']') {
                next();
                if (target.isArray()) {
                    Class<?> componentType = target.getComponentType();
                    return Array.newInstance(componentType, 0);
                }
                else if (List.class.isAssignableFrom(target)) {
                    try {
                        return target.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        return new ArrayList<>();
                    }
                }
                else if (Set.class.isAssignableFrom(target)) {
                    try {
                        return target.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        return new java.util.LinkedHashSet<>();
                    }
                }
                else {
                    throw new IllegalArgumentException("Expected array or list type at position " 
                            + pos);
                }
            }

            if (target.isArray()) {
                Class<?> componentType = target.getComponentType();
                List<Object> list = new ArrayList<>();

                while (true) {
                    list.add(parseValueAs(componentType));
                    skipWhiteSpace();

                    if (peek() == ',') {
                        next();
                        skipWhiteSpace();
                    }
                    else if (peek() == ']') {
                        next();
                        break;
                    }
                    else {
                        throw new IllegalArgumentException("Expected ',' or ']' in array");
                    }
                }
                return convertListToArray(list, componentType);
            }
            else if (List.class.isAssignableFrom(target)) {
                List<Object> list;
                try {
                    list = (List<Object>)target.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    list = new ArrayList<>();
                }

                while (true) {
                    // add support for Type tokens in the future versions
                    list.add(parseValueAs(this.collectionElementType));
                    skipWhiteSpace();

                    if (peek() == ',') {
                        next();
                        skipWhiteSpace();
                    }
                    else if (peek() == ']') {
                        next();
                        break;
                    }
                    else {
                        throw new IllegalArgumentException("Expected ',' or ']' in array");
                    }
                }
                return list;
            }
            else if (Set.class.isAssignableFrom(target)) {
                Set<Object> set;
                try {
                    set = (Set<Object>) target.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    set = new LinkedHashSet<>();
                }

                while (true) {
                    set.add(parseValueAs(this.collectionElementType));   // elements parsed with target==null (no generics)
                    skipWhiteSpace();

                    if (peek() == ',') {
                        next();
                        skipWhiteSpace();
                    }
                    else if (peek() == ']') {
                        next();
                        break;
                    }
                    else {
                        throw new IllegalArgumentException("Expected ',' or ']' in array");
                    }
                }
                return set;
            }
            else {
                throw new IllegalArgumentException("Expected array or list type at position " 
                            + pos + ", but found: " + target.getName());
            }
        }
        private String parseString() {
            expect('\"');
            StringBuilder sb = new StringBuilder();

            while (true) {
                char c = next();
                if (c == '\"')
                    break;

                if (c == '\\') {
                    if (pos >= len)
                        throw new IllegalArgumentException("Unterminated escape");
                    
                    c = next();
                    switch (c) {
                        case '\"':
                            sb.append("\"");
                            break;
                        case '\\':
                            sb.append("\\");
                            break;
                        case '/': 
                            sb.append("/");
                            break;
                        case 'b':
                            sb.append("\b");
                            break;
                        case 'n':
                            sb.append("\n");
                            break;
                        case 'f':
                            sb.append("\f");
                            break;
                        case 'r':
                            sb.append("\r");
                            break;
                        case 't':
                            sb.append("\t");
                            break;
                        case 'u':
                            StringBuilder hex = new StringBuilder();
                            for (int i = 0; i < 4; i++)
                                hex.append(next());
                            try {
                                int codePoint = Integer.parseInt(hex.toString(), 16);
                                sb.append((char)codePoint);
                            } catch (NumberFormatException e) {
                                throw new IllegalArgumentException("Invalid Unicode escape: \\u" + hex.toString(), e);
                            }
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid escape sequence: \\" + c);
                    }
                }
                else 
                    sb.append(c);
                if (pos >= len)
                    throw new IllegalArgumentException("Unterminated string");
            }
            return sb.toString();
        }
        private String parseNumberText() {
            StringBuilder sb = new StringBuilder();

            if (peek() == '-')
                sb.append(next());

            if (peek() == '0') {
                sb.append(next());
                if (isDigit(peek()))
                    throw new IllegalArgumentException("Leading zeros are not allowed in JSON numbers");
            }
            else if (isDigit(peek())) {
                while (isDigit(peek()))
                    sb.append(next());
            }
            else {
                throw new IllegalArgumentException("Expected digit but found: " + peek());
            }

            if (peek() == '.') {
                sb.append(next());
                
                if (!isDigit(peek()))
                    throw new IllegalArgumentException("Expected digit after decimal point");
                while (isDigit(peek()))
                    sb.append(next());
            }

            if (peek() == 'e' || peek() == 'E') {
                sb.append(next());

                if (peek() == '+' || peek() == '-')
                    sb.append(next());

                if (!isDigit(peek()))
                    throw new IllegalArgumentException("Expected digit in exponent");

                while (isDigit(peek()))
                    sb.append(next());
            }

            return sb.toString();
        }
        private boolean parseBoolean() {
            skipWhiteSpace();
            
            if (pos + 4 <= len && startsWith("true")) {
                if (pos + 4 == len || isJsonTerminator(input.charAt(pos + 4))) {
                    pos += 4; 
                    return Boolean.TRUE;
                }
            }
            if (pos + 5 <= len && startsWith("false")) {
                if (pos + 5 == len || isJsonTerminator(input.charAt(pos + 5))) {
                    pos += 5;
                    return Boolean.FALSE;
                }
            }

            throw new IllegalArgumentException("Expected boolean value (true or false) at position " + pos);
        }
        private Object parseNull() {
            skipWhiteSpace();

            if (pos + 4 <= len && startsWith("null")) {
                if (pos + 4 == len || isJsonTerminator(input.charAt(pos + 4))) {
                    pos += 4;
                    return null;
                }
            }
            throw new IllegalArgumentException("Expected null value at position " + pos);
        }
        private Object convertNumberTextToTarget(String text, Class<?> target) {
            try {
                if (target == null) {
                    if (text.contains(".") || text.toLowerCase().contains("e")) {
                        return Double.parseDouble(text);
                    }
                    else {
                        try {
                            return Integer.parseInt(text);
                        } catch (NumberFormatException e) {
                            return Long.parseLong(text);
                        }
                    }
                }

                if (target == Integer.class || target == int.class) {
                    return Integer.parseInt(text);
                }
                else if (target == Long.class || target == long.class) {
                    return Long.parseLong(text);
                }
                else if (target == Double.class || target == double.class) {
                    return Double.parseDouble(text);
                }
                else if (target == Float.class || target == float.class) {
                    return Float.parseFloat(text);
                }
                else if (target == Short.class || target == short.class) {
                    return Short.parseShort(text);
                }
                else if (target == Byte.class || target == byte.class) {
                    return Byte.parseByte(text);
                }
                else if (target == Number.class) {
                    if (text.contains(".") || text.toLowerCase().contains("e")) {
                        return Double.parseDouble(text);
                    }
                    else {
                        try {
                            return Integer.parseInt(text);
                        } catch (NumberFormatException e) {
                            return Long.parseLong(text);
                        }
                    }
                }
                else if (target == BigDecimal.class) {
                    return new BigDecimal(text);
                }
                else if (target == BigInteger.class) {
                    return new BigInteger(text);
                }
                else {
                    throw new IllegalArgumentException("Unsupported number type: " + target.getName());
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                    "Cannot convert '" + text + "' to " + target.getName() + 
                    ": " + e.getMessage(), e);
            }
        }
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private Object convertStringToTarget(String s, Class<?> target) {
            if (target == null || target == String.class) {
                return s;
            }
            else if (target.isEnum()) {
                // This is the safe way to handle enum conversion without warnings
                Class<? extends Enum<?>> enumType = (Class<? extends Enum<?>>) target;
                return Enum.valueOf((Class) enumType, s); // Safe cast to raw Class
            }
            else if (target == Character.class || target == char.class) {
                if (s.length() != 1) {
                    throw new IllegalArgumentException("Cannot convert string to character: string must have exactly one character");
                }
                return s.charAt(0);
            }
            else if (isNumericType(target)) {
                return convertNumberTextToTarget(s, target);
            }
            else if (target == Boolean.class || target == boolean.class) {
                if ("true".equalsIgnoreCase(s)) {
                    return Boolean.TRUE;
                } 
                else if ("false".equalsIgnoreCase(s)) {
                    return Boolean.FALSE;
                } 
                else {
                    throw new IllegalArgumentException("Cannot convert string to boolean: expected 'true' or 'false' but got '" + s + "'");
                }
            }   // add supporting for annotation @JsonDateFormat
            else if (target == LocalDate.class) {
                return LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
            }
            else if (target ==  LocalTime.class) {
                return LocalTime.parse(s, DateTimeFormatter.ISO_LOCAL_TIME);
            }
            else if (target == LocalDateTime.class) {
                return LocalDateTime.parse(s, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }
            else {
                throw new IllegalArgumentException(
                    "Cannot convert '" + s + "' to " + target.getName());
            }
        }
        private Object convertListToArray(List<Object> list, Class<?> componentType) {
            Object array = Array.newInstance(componentType, list.size());
            for (int i = 0; i < list.size(); i++) {
                Array.set(array, i, list.get(i));
            }
            return array;
        }
    }

    private static String toJson(Object obj, Set<Object> visited, int depth) throws IllegalAccessException {
        if (obj == null)
            return "null";
        
        StringBuilder sb = new StringBuilder();

        if (obj instanceof Enum<?>) {
            return "\"" + ((Enum<?>) obj).name() + "\"";
        }
        else if (obj instanceof LocalDate) {
            return "\"" + ((LocalDate) obj).format(DateTimeFormatter.ISO_LOCAL_DATE) + "\"";
        }
        else if (obj instanceof LocalTime) {
            return "\"" + ((LocalTime) obj).format(DateTimeFormatter.ISO_LOCAL_TIME) + "\"";
        }
        else if (obj instanceof LocalDateTime) {
            return "\"" + ((LocalDateTime) obj).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "\"";
        }
        else if (obj instanceof Number || obj instanceof Boolean) {
            return obj.toString();
        }
        else if (obj instanceof String) {
            escapeString((String)obj, sb);
            return "\"" + sb.toString() + "\"";
        }
        else if (obj instanceof Map<?, ?>) {
            serializeMap((Map<?, ?>)obj, sb, visited, depth);
            return sb.toString();
        }
        else if (obj instanceof Collection<?>) {
            serializeIterable((Collection<?>)obj, sb, visited, depth);
            return sb.toString();
        }
        else if (obj.getClass().isArray()) {
            serializeArray(obj, sb, visited, depth);
            return sb.toString();
        }
        else {
            serializeObject(obj, sb, visited, depth);
            return sb.toString();
        }
    }
    private static void serializeArray(Object array, StringBuilder sb, Set<Object> visited, int depth) throws IllegalAccessException {
        if (array == null) {
            sb.append("null");
            return;
        }
        if (visited.contains(array))
            throw new CircularReferenceException(
                "Circular reference detected in " + array.getClass().getName() +
                "@" + System.identityHashCode(array)
            );
        
        visited.add(array);

        try {
            sb.append("[");
            final int length =  Array.getLength(array);
            for (int i = 0; i < length; i++) {
                Object element = Array.get(array, i);

                indentation(depth+1, sb, false);

                if (element == null) {
                    sb.append("null");
                }
                else if (element instanceof String) {
                    sb.append("\"");
                    escapeString((String)element, sb);
                    sb.append("\"");
                }
                else if (element instanceof Enum<?> || element instanceof Number || element instanceof Boolean) {
                    sb.append(((element instanceof Enum<?>) ? ("\"" + element + "\"") : element));
                }
                else {
                    sb.append(JsonParser.toJson(element, visited, depth+1));
                }

                if (i < length-1)
                    sb.append(",");
            }
            
            indentation(depth, sb, false);
            sb.append("]");
        } finally {
            visited.remove(array);
        }
    }
    private static void serializeIterable(Iterable<?> iterable, StringBuilder sb, Set<Object> visited, int depth) throws IllegalAccessException{
        if (iterable == null){
            sb.append("null");
            return;
        }
        if (visited.contains(iterable))
            throw new CircularReferenceException(
                "Circular reference detected in " + iterable.getClass().getName() + 
                "@" + System.identityHashCode(iterable)
            );        
        
        visited.add(iterable);

        try {
            sb.append("[");
            Iterator<?> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                Object element = iterator.next();

                indentation(depth+1, sb, false);

                if (element == null) {
                    sb.append("null");
                }
                else if (element instanceof String) {
                    sb.append("\"");
                    escapeString((String)element, sb);
                    sb.append("\"");
                }
                else if (element instanceof Enum<?> || element instanceof Number || element instanceof Boolean) {
                    sb.append(((element instanceof Enum<?>) ? ("\"" + element + "\"") : element));
                }
                else {
                    sb.append(JsonParser.toJson(element, visited, depth+1));
                }
                
                if (iterator.hasNext())
                    sb.append(",");
            }

            indentation(depth, sb, false);
            sb.append("]");
        } finally {
            visited.remove(iterable);
        }
    }
    private static void serializeMap(Map<?, ?> map, StringBuilder sb, Set<Object> visited, int depth) throws IllegalAccessException {
        if (map == null) {
            sb.append("null");
            return;
        }
        if (visited.contains(map))
            throw new CircularReferenceException(
                "Circular reference detected in " + map.getClass().getName() + 
                "@" + System.identityHashCode(map)
            );

        visited.add(map);
        try {
            // serialize as array of {"key": <keyJson>, "value": <valueJson>}
            sb.append("[");
            Iterator<? extends Map.Entry<?, ?>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<?, ?> entry = it.next();

                indentation(depth+1, sb, false);
                sb.append("{");

                // key
                indentation(depth+2, sb, false);
                sb.append("\"key\": ");
                Object key = entry.getKey();
                if (key == null) {
                    sb.append("null");
                } else {
                    sb.append(JsonParser.toJson(key, visited, depth+2));
                }
                sb.append(",");

                // value
                indentation(depth+2, sb, false);
                sb.append("\"value\": ");
                Object value = entry.getValue();
                if (value == null) {
                    sb.append("null");
                } else {
                    sb.append(JsonParser.toJson(value, visited, depth+2));
                }

                indentation(depth+1, sb, false);
                sb.append("}");

                if (it.hasNext())
                    sb.append(",");
            }

            if (!map.isEmpty())
                indentation(depth, sb, false);
            sb.append("]");
        } finally {
            visited.remove(map);
        }
    }
    private static void serializeObject(Object obj, StringBuilder sb, Set<Object> visited, int depth) throws IllegalAccessException {
        if (obj == null){
            sb.append("null");
            return;
        }
        if (visited.contains(obj))
            throw new CircularReferenceException(
                "Circular reference detected in " + obj.getClass().getName() + 
                "@" + System.identityHashCode(obj)
            );        

        visited.add(obj);
        try {
            sb.append("{");
            Class<?> clazz = obj.getClass();
            Field[] fields = clazz.getDeclaredFields();
            Arrays.sort(fields, (f1, f2) -> {
                JsonOrder order1 = f1.getAnnotation(JsonOrder.class);
                JsonOrder order2 = f2.getAnnotation(JsonOrder.class);

                if (order1 != null && order2 != null)
                    return Integer.compare(order1.value(), order2.value());
                else if (order1 != null)
                    return -1;
                else if (order2 != null)
                    return 1;
                else
                return 0;
            });

            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                if (f.getDeclaringClass().getName().startsWith("java."))
                    continue;
                if (Modifier.isStatic(f.getModifiers()))
                    continue;
                if (f.isAnnotationPresent(JsonIgnore.class))
                    continue;
                
                f.setAccessible(true);
                Object value = f.get(obj);

                indentation(depth+1, sb, false);
                sb.append("\"").append(f.getName()).append("\": ");

                if (value == null) {
                    sb.append("null");
                }
                else if (value instanceof Number || value instanceof Boolean) {
                    sb.append(value);
                }
                else if (value instanceof String) {
                    sb.append("\"");
                    escapeString((String)value, sb);
                    sb.append("\"");
                }
                else if (value instanceof Enum<?>) {
                    sb.append("\"").append((Enum<?>)value).append("\"");
                }
                else if (value instanceof LocalDate) {
                    LocalDate date = (LocalDate)value;
                    sb.append("\"").append(date.format(DateTimeFormatter.ISO_LOCAL_DATE)).append("\"");
                }
                else if (value instanceof LocalTime) {
                    LocalTime time = (LocalTime)value;
                    sb.append("\"").append(time.format(DateTimeFormatter.ISO_LOCAL_TIME)).append("\"");
                }
                else if (value instanceof LocalDateTime) {
                    LocalDateTime dateTime = (LocalDateTime)value;
                    sb.append("\"").append(dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append("\"");
                }
                else if (value instanceof Map<?, ?>) {
                    serializeMap((Map<?, ?>)value, sb, visited, depth+1);
                }
                else if (value instanceof Collection<?>) {
                    serializeIterable((Collection<?>)value, sb, visited, depth+1);
                }
                else if (value.getClass().isArray()) {
                    serializeArray(value, sb, visited, depth+1);
                }
                else if (value instanceof Object) {
                    serializeObject(value, sb, visited, depth+1);
                }
                else {
                    sb.append(value);
                }
                
                f.setAccessible(false);
                sb.append(",");
            }

            if (sb.charAt(sb.length()-1) == ',')
                sb.deleteCharAt(sb.length()-1);
            indentation(depth, sb, false);
            sb.append("}");
        } finally {
            visited.remove(obj);
        }
    }
    private static void escapeString(String s, StringBuilder sb) {
        if (s == null)
            throw new IllegalArgumentException("cannot escape null string object");
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\"')
                sb.append("\\\"");
            else if (c == '\\')
                sb.append("\\\\");
            else if (c == '/')
                sb.append("\\/");
            else if (c == '\b')
                sb.append("\\b");
            else if (c == '\f')
                sb.append("\\f");
            else if (c == '\n')
                sb.append("\\n");
            else if (c == '\r')
                sb.append("\\r");
            else if (c == '\t')
                sb.append("\\t");
            else if (c < 0x20)
                sb.append(String.format("\\u%04x", (int)c));
            else
                sb.append(c);
        }
    }
    private static void indentation(int depth, StringBuilder sb, boolean useTabs) {
        if (depth < 0)
            throw new IllegalArgumentException("Depth cannot be negative");
        
        String indent = useTabs ? "\t" : "    ";
        sb.append("\n");
        for (int i = 0; i < depth; i++) {
            sb.append(indent);
        }
    }
}
