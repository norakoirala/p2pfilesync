/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peertopeer;

import java.net.Socket;

/**
 *
 * @author RJ
 */
 public class SendThread extends Thread {
    Socket socket;
    String fileName;
    sNode s;
   SendThread () {
      // The compiler creates the byte code equivalent of super ();
   }
   SendThread (String name) {
      super (name); // Pass name to Thread superclass
   }
   
   public SendThread (Socket so, String st ,sNode sn) {
      // The compiler creates the byte code equivalent of super ();
      this.socket = so;
      this.fileName = st;
      this.s = sn;
   }
   
   

   
   public void run () {
       try{
            System.out.println("Thread");
           this.s.outgoing.send(socket,fileName);
       } catch ( Exception e) {
           
       }
   }
    
  
}