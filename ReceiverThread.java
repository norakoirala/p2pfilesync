package peertopeer;

/**
 * RecieverThread is the thread that Receiver runs on
 * @author RJ
 * @author Nora
 */
public class ReceiverThread extends Thread {
	
     sNode s; //the node that receives the file
     
     /**
      * Default constructor 
      */
     ReceiverThread () {
      // The compiler creates the byte code equivalent of super ();
     }
 
     /**
      * Overloaded constructor
      * @param sn - the node that receives the file
      */
     public ReceiverThread (sNode sn) {
      // The compiler creates the byte code equivalent of super ();
      this.s = sn;
      s.establish();
     }
     
     /**
      * Method runs the receive thread
      */
     public void run () {
       try {
          while(true){
            s.receive();
            sleep(15000);
          }
       } catch ( Exception e) {
           e.printStackTrace();
       }
     }
      
}
