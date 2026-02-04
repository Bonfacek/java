package com.banking;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Addpin {
    Scanner nerd=new Scanner(System.in);
    private Jdbc jdbc;
    public int id;
    public int userpinID;

   
    public Addpin(Jdbc jdbc){
        this.jdbc=jdbc;
    }



     public int verify(String username, String password) {
             String sql = "SELECT id FROM users WHERE username=? AND password=?";

             try(Connection conn = this.jdbc.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                 pstmt.setString(1, username);
                 pstmt.setString(2, password);
                 ResultSet rs = pstmt.executeQuery();
                 if (rs.next()) {
                     id=rs.getInt("id");
                 } else {
                     return -1;
                 }
             } catch (SQLException ex) {
                 ex.printStackTrace();
                 return -1;
             }
        return id;
       }


    public void addUserPin(){
        int setpin;
        System.out.print("Set the bank account pin you will be using: ");
        setpin=nerd.nextInt();
        String pinquery="UPDATE pin SET pin= ? WHERE id= ?";
        try(
         Connection conn = this.jdbc.getConnection();
         PreparedStatement psmt = conn.prepareStatement(pinquery)){
          psmt.setInt(1,setpin);
          psmt.setInt(2, id);
          psmt.executeUpdate();
        }catch(SQLException e){
            e.getErrorCode();
            e.printStackTrace();
        }

    }



public int verifycheck(){
        String query="SELECT id FROM pin WHERE pin=?";
        

        int pin;
        System.out.print("Please enter your bank account pin: ");
        pin=nerd.nextInt();

        try (
            Connection conn=jdbc.getConnection();
            PreparedStatement ptsmt=conn.prepareStatement(query)){
                ptsmt.setInt(1, pin);              
                
            ResultSet rs=ptsmt.executeQuery();
               if(rs.next()){
                userpinID=rs.getInt("id");
            }


        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return userpinID;
    }
public int checked(){
     verifycheck();
        if(userpinID != id){
            return -1;
        }else{
            return id;
        }

    }   
}
