package com.banking;


 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;

 public class Verification {
     private AddUsers addUsers;
     private Jdbc jdbc;

     public Verification(Jdbc jdbc, AddUsers addUsers) {
         this.jdbc = jdbc;
         this.addUsers = addUsers;
     }

     public int verify(String username, String password) {
         if (username.isEmpty() && password.isEmpty()) {
         System.out.println("Username and password must not be empty.");
         return -1;
         }

             String sql = "SELECT id FROM users WHERE username=? AND password=?";

             try {
                 Connection conn = this.jdbc.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql);
                 pstmt.setString(1, username);
                 pstmt.setString(2, password);
                 ResultSet rs = pstmt.executeQuery();
                 if (rs.next()) {
                     return rs.getInt("id");
                 } else {
                     System.out.println("Invalid username or password");
                     this.addUsers.saveUser();
                     return -1;
                 }
             } catch (SQLException ex) {
                 ex.printStackTrace();
                 return -1;
             }
       }
 }
