package peertopeer;

import java.net.Socket;

/**
 *
 * @author RJ
 * @author Nora
 */
public class FolderThread extends Thread {
	
	sNode s; //
    
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
