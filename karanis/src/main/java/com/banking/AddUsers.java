
package com.banking;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AddUsers {
    private String username;
    private String password;
    private int accountpin;
    private Jdbc jdbc;
    private Addpin addpin;

    Scanner nerd;
    int id=0;


    public AddUsers(Jdbc jdbc,Addpin addpin) {
        this.nerd = new Scanner(System.in);
        this.addpin=addpin;
        this.jdbc = jdbc;
    }
   

    public int getAccountpin(){
        return accountpin;
    }
    public void setAccountpin(int accountpin){
        this.accountpin=accountpin;
    }
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void saveUser(){
    

        System.err.println("--- Registration ---");
        System.out.print("Enter username: ");
        this.username = this.nerd.nextLine();
        System.out.print("Enter password: ");
        this.password = this.nerd.nextLine();

           if (username.isEmpty() && password.isEmpty()){
              System.out.println("Username and password must not be empty.");}

        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (
            Connection conn = this.jdbc.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, this.username);
            pstmt.setString(2, this.password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
      addpin.addUserPin();
    System.out.println("User credentials registered successfully!");

        System.out.print("Do you want to continue to the main banking program? [Y/N]: ");
            if (nerd.next().toUpperCase().charAt(0) == 'Y') Bankstm.main(new String[0]);

    }

}