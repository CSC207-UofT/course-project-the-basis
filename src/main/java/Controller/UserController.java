package Controller;

import UseCase.Accounts.CurrentUser;
import UseCase.Accounts.UserHistory;
import UseCase.Accounts.UserLogIn;
import UseCase.Accounts.UserSignUp;

import java.io.IOException;
import java.util.Scanner;

public class UserController {

    private int choice;
    private final CurrentUser curr = new CurrentUser();
    private final UserHistory userHistory = new UserHistory();

    /**
     * Sets user choice
     * @param choice2 user choice
     */
    public void setChoice(int choice2) {
        choice = choice2;
    }

    /**
     * @return current user
     */
    public CurrentUser getCurr() {
        return curr;
    }

    /**
     * Handles users request
     * @return if valid option selected
     */
    public boolean handleUser() {
        if (choice == 1) {
            UserLogIn login = new UserLogIn();
            curr.setCurrentUser(login.logIn());
            userHistory.setCurr(curr);
            System.out.println("You have successfully logged in!");
            return true;
        } else if (choice == 2) {
            UserSignUp signup = new UserSignUp();
            curr.setCurrentUser(signup.signUp());
            userHistory.setCurr(curr);
            System.out.println("You have successfully signed up!");
            return true;
        }
        else{
            System.out.println("Invalid Option, Please Try Again!");
            Scanner input = new Scanner(System.in);
            choice = input.nextInt();
            return handleUser();
        }
    }
}
