package com.banking;

import java.util.Scanner;

public class Bankstm {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Jdbc jdbc = new Jdbc();
        AddUsers users = new AddUsers(jdbc);
        Verification verifier = new Verification(jdbc, users);
        Operations oppis = new Operations(jdbc);
        System.out.println("\n*****************************************");
        System.out.println("Welcome to the Karanis Banking program");
        System.out.println("******************************************");
        System.out.println("--- User Login ---");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        int id = verifier.verify(username, password);

        while(id > 0) {
            System.out.println("\n1. Show Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.println("********************************");
            System.out.print("Enter your choice of operation (1-4): ");

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (Exception var11) {
                System.out.println("Invalid input! Please enter a number (1-4)");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    loadingDots();
                    oppis.showBalance(id);
                    break;
                case 2:
                    loadingDots();
                    oppis.deposit(id);
                    break;
                case 3:
                    loadingDots();
                    oppis.withdraw(id);
                    break;
                case 4:
                    System.out.println("Thanks for using our banking services!");
                    return;
                default:
                    System.out.println("INVALID OPERATION!!!");
            }
        }

        scanner.close();
    }

    static void pause(int ms) {
        try {
            Thread.sleep((long)ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    static void loadingDots() {
        System.out.print("Processing");

        for(int i = 0; i < 5; ++i) {
            try {
                Thread.sleep(1000L);
                System.out.print(".");
            } catch (InterruptedException var2) {
            }
        }

        System.out.println();
    }
}
