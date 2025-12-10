package utils;

import java.util.ArrayList;
import java.util.List;

import core.Factory;
import core.User;
import exceptions.InvalidEmailException;
import exceptions.InvalidPasswordException;
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
     * @throws InvalidPasswordException if the password is empty
     */
    public static String validateEmail(String email, String password, Factory factory) throws InvalidEmailException, InvalidPasswordException {
        try {
            if (email == null || password == null || factory == null) {
                return JsonParser.toJson(new Response("Invalid input parameters", null));
            }

            email = email.trim();
            password = password.trim();

            if (!email.matches("^[a-zA-Z0-9_]+@gmail\\.com$")) {
                throw new InvalidEmailException("Invalid Email Format.");
            }

            if (password.isEmpty()) {
                throw new InvalidPasswordException("Password cannot be empty.");
            }
            
            List<User> users = new ArrayList<>(factory.getUsersList());

            if (users.isEmpty()) {
                return JsonParser.toJson(new Response("No user with the provided email", null));
            }

            User foundUser = null;

            for (User u : users) {
                if (u.getEmail() != null && u.getEmail().trim().equals(email)) {
                    foundUser = u;
                    break;
                }
            }

            if (foundUser == null) {
                return JsonParser.toJson(new Response("No user with the provided email", null));
            }

            if (foundUser.getPassword() == null || !foundUser.getPassword().equals(password)) {
                return JsonParser.toJson(new Response("Wrong password", null));
            }

            String role = foundUser.isManager() ? "Manager" : "Supervisor";
            return JsonParser.toJson(new Response("Authentication successful", role));
        } catch (IllegalAccessException e) {
            return null;
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
