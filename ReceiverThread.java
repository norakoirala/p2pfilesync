/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peertopeer;

/**
 *
 * @author RJ
 */

    public class ReceiverThread extends Thread {
     
    sNode s;
   ReceiverThread () {
      // The compiler creates the byte code equivalent of super ();
   }
 
   public ReceiverThread (sNode sn) {
      // The compiler creates the byte code equivalent of super ();
      this.s = sn;
   }
   
   

   
   public void run () {
       try{
          while(true){
            s.receive();
            sleep(15000);
       }
       } catch ( Exception e) {
           
       }
   }
}
