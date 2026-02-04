
package com.banking;

import java.sql.*;
import java.util.Scanner;



public class Operations {

    private Jdbc jdbc;
    private Scanner nerd;
    private Addpin addpin;
    public double balance;
   

    public Operations(Jdbc jdbc, Addpin addpin) {
        this.addpin=addpin;
        this.jdbc = jdbc;
        this.nerd = new Scanner(System.in);
    }
    
   public double balance(int id){
    double checkedbalance=0.0;
    String balanceCheck="SELECT balance FROM users WHERE id=?"; 
        try(Connection conn= jdbc.getConnection();
            PreparedStatement stm= conn.prepareStatement(balanceCheck);){

            stm.setInt(1, id);
            ResultSet rs=stm.executeQuery();
            if(rs.next()){
            checkedbalance=rs.getDouble("balance");}
        }catch(SQLException e){
            e.getErrorCode();
            e.printStackTrace();

        }

     return checkedbalance;

   }
      
    public double showBalance(int id){  
        addpin.checked();

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
        System.out.print("Enter the amount you want to deposit: ");
        double amount = nerd.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid amount, Can't deposit zero money.");
            return;
        }

         addpin.checked(); 

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
             
        System.out.print("Enter the amount you want to withdraw: ");
        double amount = nerd.nextDouble();
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }


        balance=balance(id);

        if (amount > balance) {
            System.out.println("Insufficient funds in your account.");
            return;
        }

         addpin.checked();
         

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
