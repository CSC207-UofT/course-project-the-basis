package UseCase.Accounts;

import Entity.User;
import Gateway.UserList;

import java.util.Scanner;

public class ValidateSignup {

    private final User user;
    private final UserList list;

    public ValidateSignup(User user, UserList list1) {
        this.user = user;
        this.list = list1;
    }

    /**
     * Checks if username is already taken when signing up
     */
    public String isTaken() {
        while (!(list.readWithUsername(this.user.getUsername()).isEmpty())) {
            System.out.println("This username is already taken, please try another one.");
            Scanner potUser = new Scanner(System.in);
            String username = potUser.nextLine();
            user.setUsername(username);
        }
        return user.getUsername();
    }

}