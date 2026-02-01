
package com.banking;

import java.sql.*;
import java.util.Scanner;


public class Operations {

    private Jdbc jdbc;
    private Scanner nerd;
    int userpinID;

    public Operations(Jdbc jdbc) {
        this.jdbc = jdbc;
        this.nerd = new Scanner(System.in);
    }

    public int verifycheck(int id){
        String query="SELECT id FROM pin WHERE pin= ? ";
           int userpinID =-1;

        int pin;
        System.out.println("Please enter your bank account pin: ");
        pin=nerd.nextInt();

        try (
            Connection conn=jdbc.getConnection();
            PreparedStatement ptsmt=conn.prepareStatement(query)){
                ptsmt.setInt(1, pin);              
                
            ResultSet rs=ptsmt.executeQuery();
               if(rs.next()){
                userpinID=rs.getInt(id);
            }


        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return userpinID;

    }
 

      
    public double showBalance(int id){  
        double balance=0.0;
            if(userpinID !=id){return -1;}

        String sql = "SELECT balance FROM users WHERE id=?";     
        
        try (
                    Connection conn = jdbc.getConnection();
                    PreparedStatement pstmt = conn.prepareStatement(sql)
            ) {
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    balance = rs.getDouble("balance");
                } else {
                    System.out.println("Account not found");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.println("Current balance: " + balance);
            return balance;
        }

    public void deposit(int id) {    

        if(userpinID !=id){
            return;
        }

        System.out.print("Enter the amount you want to deposit: ");
        double amount = nerd.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }

        String sql = "UPDATE users SET balance = balance + ? WHERE id = ?";

        try (
                Connection conn = jdbc.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("Deposit successful: " + amount);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void withdraw(int id) {
      

        if(userpinID !=id){
            return;
        }
        System.out.print("Enter the amount you want to withdraw: ");
        double amount = nerd.nextDouble();
        double balance = showBalance(id);

        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }

        if (amount > balance) {
            System.out.println("Insufficient funds.");
            return;
        }

        String sql = "UPDATE users SET balance = balance - ? WHERE id = ?";

        try (
                Connection conn = jdbc.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("Withdrawal successful: " + amount);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
