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
 public class MyThread extends Thread {
   MyThread () {
      // The compiler creates the byte code equivalent of super ();
   }
   MyThread (String name) {
      super (name); // Pass name to Thread superclass
   }
   public void run () {
     
   }
    public void run(sNode s, int control) throws Exception {
      if (control == 1) {
              s.send(s.sender.accept(), "s.pdf");
      }
      if(control == 2) {
          s.acceptFile(s.client(),"s.pdf");
      } 
   }
    
  
}