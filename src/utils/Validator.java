package utils;

import java.util.ArrayList;
import java.util.List;

import core.Factory;
import core.User;
import exceptions.InvalidEmailException;

public class Validator {
    /**
     * Validates a sent email and password.
     * 
     * @param email the email to validate
     * @param password the password to check
     * @param factory the factory you want to check for the user in
     * 
     * @return -1 if the user is not found,
     *         0 if the email is found but the password is incorrect,
     *         1 if both email and password are correct.
     * 
     * @throws InvalidEmailException if the email format is incorrect
     */
    public static int validateEmail(String email, String password, Factory factory) throws InvalidEmailException{
        if (!email.matches("^[a-zA-Z0-9_]+@gmail\\.com$")) {
            throw new InvalidEmailException("Invalid Email Format.");
        }
        
        List<User> users = new ArrayList<>(factory.getUsersList());

        if (users.isEmpty()) {
            return -1;
        }

        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                return 1;
            } else if (u.getEmail().equals(email)) {
                return 0;
            }
        }

        return -1;
    }
}
