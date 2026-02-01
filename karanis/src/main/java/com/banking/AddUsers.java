
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

    Scanner nerd;
    int id=0;

    public int getInt(Verification verification){
        id=verification.verify(username, password);
        return id;
    }
    public AddUsers(Jdbc jdbc) {
        this.nerd = new Scanner(System.in);
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
        int id;

        System.err.print("--- Registration ---");
        System.out.println("Enter username: ");
        this.username = this.nerd.nextLine();
        System.out.print("Enter password: ");
        this.password = this.nerd.nextLine();
        System.out.print("Set the bank account pin you will be using: ");
        this.accountpin=this.nerd.nextInt();

        String pinquery="UPDATE pin SET pin= ? WHERE id= ?";
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (
            Connection conn = this.jdbc.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            PreparedStatement psmt = conn.prepareStatement(pinquery);
        ) {
            pstmt.setString(1, this.username);
            pstmt.setString(2, this.password);
            psmt.setInt(1,this.accountpin);
            pstmt.executeUpdate();
            if (username.isEmpty() && password.isEmpty()){
            System.out.println("Username and password must not be empty.");}
            else {
            System.out.println("User registered successfully!");}

            System.out.print("Do you want to continue to the main banking program? [Y/N]: ");
            if (nerd.next().toUpperCase().charAt(0) == 'Y') Bankstm.main(new String[0]);

        } catch (SQLException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }

    }

}