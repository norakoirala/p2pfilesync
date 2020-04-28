/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peertopeer;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 *
 * @author RJ
 */
public class PeerToPeer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        sNode n1 = new sNode(1);
        sNode n2 = new sNode(2);
        MyThread server = new MyThread();
        MyThread client = new MyThread();
        n1.sender();
        n2.client();
        n1.send(n1.sender.accept(),"s.pdf");
        client.run(n2,2);
        System.out.println("hello");
       // server.run(n1,1);
    
        
       
        
        
    }
    
    public static String encrypt(String input) throws Exception {
    
           MessageDigest md = MessageDigest.getInstance("SHA-1"); 
        byte[] messageDigest = md.digest(input.getBytes());
        BigInteger no = new BigInteger(1, messageDigest); 
         String hashtext = no.toString(16); 
          while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
           return hashtext; 
       
    }
    
    
    }
    
    

