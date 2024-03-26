package com.example.cab302javaproject;

import java.util.ArrayList;
import java.util.Scanner;

import authentication.*;
import calendar.*;

public class Authentication {
    private static final ArrayList<User> users = new ArrayList<>();

    private static AuthenticationService authService = new AuthenticationService(); // Proper initialization
    private static boolean isRunning = true;


    public static void main(String[] args) {
        //users.add(new User("test", "test")); // Add an empty list for ToDoItems
        while (isRunning) {
            showMenu();
        }
    }

    /**
     * Displays the main menu to the user.
     */
    public static void showMenu() {
        System.out.println("Welcome to the To-Do List Application!");
        System.out.println("1. Log in");
        System.out.println("2. Sign up");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
        // Ask for user choice
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        handleMenu(choice);
    }

    /**
     * Handles the user's choice, mapping the menu options to the corresponding methods.
     *
     * @param choice The user's choice.
     */
    public static void handleMenu(int choice) {
        switch (choice) {
            case 1:
                onLogIn();
                break;
            case 2:
                onSignUp();
                break;
            case 3:
                onExit();
                break;
            default:
                System.out.println("Invalid choice!");
                showMenu();
        }
    }

    /**
     * Handles the sign-up process.
     */
    public static void onSignUp() {
        boolean validUsername = false;
        String username = "";
        String password = "";
        // could make an option to escape this signup loop
        while (!validUsername) {
            System.out.print("Enter your username: ");
            Scanner scanner = new Scanner(System.in);
            username = scanner.nextLine();
            if (authService.usernameCheck(username) == true) {
                validUsername = true;
                System.out.print("Enter your password: ");
                password = scanner.nextLine();
            } else {
                System.out.println("This username is already taken!");
                // - If the user is null, show "The username is already taken!"
            }
        }

        User user = authService.signUp(username, password); //could change to only check password now as username is superflous

        // TODO Now: Show a message based on the result of the signUp method:
        if (user != null) {
            System.out.println("User " + user.getUsername() + " has been created successfully!");
            // - If the user is not null, show "User <username> has been created successfully!"
        } else {
            System.out.println("Sign-up failed. The username is already taken!");
            // - If the user is null, show "The username is already taken!"
        }
    }

    /**
     * Handles the log-in process, and the post-login operations.
     */
    public static void onLogIn() {
        //could check if username exists before making enter password
        System.out.print("Enter your username: ");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        User user = authService.logIn(username, password);
        if (user != null) {
            System.out.println("Welcome, " + user.getUsername() + "!");
            // Creates an instance of the CalendarList class with the logged-in user and call the run method
            CalendarList CalendarList = new CalendarList(user);
            CalendarList.run();
        } else {
            System.out.println("Login failed. Please check your username and password.");
        }
    }


    /**
     * Exits the application by setting the `isRunning` flag to false.
     */
    public static void onExit() {
        isRunning = false;
    }


}

