package authentication;

import java.util.ArrayList;

public class AuthenticationService implements IAuthenticationService {
    private ArrayList<User> users;

    // TODO Now: Add a constructor to initialize the users list with the default user
    public AuthenticationService() {
        this.users = new ArrayList<>();
        // Adding a default user for demonstration purposes
        //users.add(new User("defaultUser", "defaultPassword"));
    }

    // TODO Now: Implement the signUp method
    @Override
    public User signUp(String username, String password, String userType) {
        // Check if the username is already taken
        if (isUsernameTaken(username)) {
            System.out.println("Username is already taken. Please choose another one.");
            return null; // Return null indicating sign-up failure
        }

        // Create a new user and add it to the users list
        User newUser = new User(username, password, userType);
        users.add(newUser);

        System.out.println("Sign-up successful!");
        return newUser; // Return the new user indicating successful sign-up
    }

    // TODO Now: Implement the logIn method
    @Override
    public User logIn(String username, String password) {
        // Iterate through the users list to find a match
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Login successful!");
                return user; // Return the user indicating successful login
            }
        }

        System.out.println("Invalid username or password. Please try again.");
        return null; // Return null indicating login failure
    }

    public boolean usernameCheck(String username) {
        // Check if the username is already taken
        if (isUsernameTaken(username)) {
            System.out.println("Username is already taken. Please choose another one.");
            return false; // Return null indicating sign-up failure
        }
        return true;
    }

    // Helper method to check if a username is already taken
    private boolean isUsernameTaken(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
