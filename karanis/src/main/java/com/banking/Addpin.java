package com.banking;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Addpin {
    
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
    
}
