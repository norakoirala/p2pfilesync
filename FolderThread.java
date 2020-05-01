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
public class FolderThread extends Thread {
     
    sNode s;
   FolderThread () {
      // The compiler creates the byte code equivalent of super ();
   }
 
   public FolderThread (sNode sn) {
      // The compiler creates the byte code equivalent of super ();
    
      this.s = sn;
   }
   
   

   
   public void run () {
       try{
           s.listen();
       } catch ( Exception e) {
           
       }
   }
}
