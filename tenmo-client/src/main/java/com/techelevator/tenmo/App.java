package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;

import java.util.Scanner;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final AccountService accountService = new AccountService();
    private final UserService userService = new UserService();
    private final TransferService transferService = new TransferService();
    private AuthenticatedUser currentUser;
    private Scanner userInput;
    private String choice;




    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                handleListUsers();
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
        User user = currentUser.getUser();
        Account account = accountService.getAccountBalance(user.getId());
        System.out.println("Your current account balance is: $" + account.getBalance());
    }

	private void viewTransferHistory() {
        Transfer[] transfers = transferService.viewTransfers();
        User[] users = userService.listUsers();
        if (transfers != null) {
            consoleService.printTransfers(transfers);
        } else {
            consoleService.printErrorMessage();
        }
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
        Transfer transfer = new Transfer();
        Account account = accountService.getAccountId(currentUser.getUser().getId());
        double amountTosend = 0;
        int recievingAddress = -1;
        int user = currentUser.getUser().getId();
        while (recievingAddress != 0) {
            System.out.println("--------------------------------------------");
            recievingAddress = consoleService.promptForMenuSelection("Enter ID of user you are sending to (0 to cancel): ");
            amountTosend = consoleService.promptForInt("Enter amount: ");
            if (recievingAddress != 0) {
                if (recievingAddress != user) {
                    if (amountTosend <= account.getBalance()) {
                        if (amountTosend > 0) {
                            if (account.getUserId() == user) {
                                user = account.getAccountId();
                                transfer.setTransferTypeId(2);
                                transfer.setAmount(amountTosend);
                                transfer.setAccountTo(recievingAddress);
                                transfer.setAccountFrom(user);
                                transfer.setTransferStatusId(1);
                                transferService.createTransfer(transfer);
                                System.out.println("Sending!");
                                break;
                            }
                        } else {
                            System.out.println("Insufficient funds.");
                        }
                    } else {
                        System.out.println("Insufficient funds.");
                    }
                } else {
                    System.out.println("Error!");
                }
            }
        }
    }

    public void handleListUsers() {
        User[] users = userService.listUsers();
        if (users != null) {
            consoleService.printUsers(users);
        } else {
            consoleService.printErrorMessage();
        }
    }


	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

}
