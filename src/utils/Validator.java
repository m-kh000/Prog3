package utils;

import exceptions.InvalidEmailException;

public class Validator {
    /**
     * Validates a sent email and password.
     * 
     * @param email the email to validate
     * @param password the password to check
     * 
     * @return -1 if the email is not found,
     *         0 if the email is found but the password is incorrect,
     *         1 if both email and password are correct.
     * 
     * @throws InvalidEmailException if the email format is incorrect
     */
    //TODO, add the email and password checking.
    public static int validateEmail(String email, String password) throws InvalidEmailException{
        if (!email.matches("^[a-zA-Z0-9_]+@gmail\\.com$")) {
            throw new InvalidEmailException("Invalid Email Format.");
        }
        return 0;
    }
}
