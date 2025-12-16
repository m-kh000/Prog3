package utils;

import core.Factory;
import core.User;
import exceptions.EmptyFieldException;
import exceptions.InvalidDateFormatException;
import exceptions.InvalidEmailException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import jsonParser.JsonParser;

public class Validator {
    /**
     * Validates a sent email and password.
     * 
     * @param email the email to validate
     * @param password the password to check
     * @param factory the factory you want to check for the user in
     * 
     * @return json string of a {@code Response} object which contains the response message
     *         and the role of the user if found.
     *         If the user is not found then the role will be null. 
     * 
     * @throws InvalidEmailException if the email format is incorrect
     */
    public static String validateEmail(String email, String password, Factory factory) throws InvalidEmailException, EmptyFieldException{
        try {
            if (email == null || password == null || email.equals("") || password.equals("")) {
                throw new EmptyFieldException();
            }

            email = email.trim();
            password = password.trim();

            if (!email.matches("^[a-zA-Z0-9_]+@gmail\\.com$")) {
                throw new InvalidEmailException("Invalid Email Format.");
            }
            
            List<User> users = new ArrayList<>(factory.getUsers());

            if (users.isEmpty()) {
                return JsonParser.toJson(new Response("No users signedup", "signup"));
            }

            User foundUser = null;

            for (User u : users) {
                if (u.getEmail() != null && u.getEmail().trim().equals(email)) {
                    foundUser = u;
                    break;
                }
            }

            if (foundUser == null) {
                return JsonParser.toJson(new Response("No user with the provided email", "signup"));
            }

            if (foundUser.getPassword() == null || !foundUser.getPassword().equals(password)) {
                return JsonParser.toJson(new Response("Wrong password", null));
            }

            String role = foundUser.isManager() ? "Manager" : "Supervisor";
            return JsonParser.toJson(new Response("Welcome " + role, role));
        } catch (IllegalAccessException e) {
            FileUtils.log(e);
            return null;
        } 
    }

    /**
     * Validates a date String in the format of {@code DD-MM-YYYY}
     * 
     * @param date the string to validate
     * @return a new {@code LocalDate} object of the sent String if the validation completed
     * successfully
     * @throws InvalidDateFormatException if there was any problem with the format
     */
    public static LocalDate validateDate(String date) throws InvalidDateFormatException {
        if (date == null || date.trim().isEmpty()) {
            throw new InvalidDateFormatException("Date cannot be null or empty.");
        }

        date = date.trim();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu")
                                     .withResolverStyle(ResolverStyle.STRICT);
        
        try {
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            if (e.getMessage().contains("Invalid date")) {
                throw new InvalidDateFormatException("Invalid date.");
            } else if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                throw new InvalidDateFormatException("Must be exactly YYYY-MM-DD");
            } else {
                throw new InvalidDateFormatException("Invalid date format: " + e.getMessage());
            }
        }
    }

    public static class Response {
        private String message;
        private String role;

        public Response() {}

        public Response(String message, String role) {
            this.message = message;
            this.role = role;
        }

        public String getMessage() {
            return this.message;
        }
        public String getRole() {
            return this.role;
        }
    }
}
