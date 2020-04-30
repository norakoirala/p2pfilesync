/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peertopeer;

import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author RJ
 */
 public class SendThread extends Thread {
    Socket socket;
    String fileName;
    sNode s;
    ArrayList<Socket> connections;
   SendThread () {
      // The compiler creates the byte code equivalent of super ();
   }
   SendThread (String name) {
      super (name); // Pass name to Thread superclass
   }
   
   public SendThread (ArrayList<Socket> cn, String st ,sNode sn) {
      // The compiler creates the byte code equivalent of super ();
  
      this.fileName = st;
      this.s = sn;
      this.connections = cn;
   }
   
   

   
   public void run () {
       try{
            System.out.println("Thread");
            for(Socket s : connections){
                this.s.outgoing.send(s,fileName); 
                System.out.println("Sending to... " +  s);
            }
       } catch ( Exception e) {
           
       }
   }
    
  
}