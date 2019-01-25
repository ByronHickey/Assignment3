/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manufacturer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author c0006557
 */
public class Manufacturer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String name;
        Scanner kb = new Scanner(System.in);
        
        System.out.println("Enter a manufacuter name");
        name = kb.nextLine();
        
        String sql1 = "SELECT MANUFACTURER_ID FROM MANUFACTURER WHERE NAME = ?";
        String sql2 = "SELECT PRODUCT_ID, DESCRIPTION FROM PRODUCT WHERE"
                + " MANUFACTURER_ID = ?";
        
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String jdbc = "jdbc:derby://localhost:1527/sample";
            
            Connection conn = DriverManager.getConnection(jdbc, "app", "app");
            
            PreparedStatement pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1,name);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("manufacturer_id");
                PreparedStatement pstmt2 = conn.prepareStatement(sql2);
                pstmt2.setInt(1, id);
                ResultSet rs2 = pstmt2.executeQuery();
                List<String> products = new ArrayList<>();
                while(rs2.next()){
                    products.add(String.format("%s: %s",
                            rs2.getString("description"),
                            rs2.getString("product_id")));
                }
                products.sort(null);
                for(String s : products)
                    System.out.println(s);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Manufacturer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Manufacturer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
