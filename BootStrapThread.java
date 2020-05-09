/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peertopeer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author RJ
 */
public class BootStrapThread extends Thread {
    
    sNode s;
    Socket bootstrap = null;
    public BootStrapThread(Socket bt) {
                bootstrap = bt;
                }
    public void run() {
     try {
           
        getPeers();
     } catch (Exception e) {
     
     }
    
    }
    
    
        
    public void getPeers() throws Exception{
        ArrayList<String> IPList;
        IPList = new ArrayList<String>();
       
          
            
            DataInputStream dis = new DataInputStream(bootstrap.getInputStream());
           
                try{
                 
                String ip = dis.readUTF();    
                   System.out.println(ip);
         //     IPList.add(ip.substring(1,findColon(ip)));
               System.out.println(IPList.get(IPList.size()-1));
                } catch (Exception e) {
                 
                }
            
    }
    
      
    
    
}
