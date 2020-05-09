package peertopeer;

import java.net.Socket;
import java.util.ArrayList;

/**
 * Send Thread is the thread that send runs on 
 * @author RJ
 * @author Nora
 */
 public class SendThread extends Thread {
	String fileName; //the file being sent
	sNode s; 
	ArrayList<Socket> connections; //the list of all the connections on the network
	
	/**
	 * Default constructor
	 */
	SendThread () {
      // The compiler creates the byte code equivalent of super ();
	}
	
	/**
	 * Overloaded constructor
	 * @param name
	 */
	SendThread (String name) {
      super (name); // Pass name to Thread superclass
	}
   
	/**
	 * Overloaded constructor
	 * @param cn - the list of connections 
	 * @param st - the file name 
	 * @param sn
	 */
	public SendThread (ArrayList<Socket> cn, String st ,sNode sn) {
		// The compiler creates the byte code equivalent of super ();
		this.fileName = st;
		this.s = sn;
		this.connections = cn;
            
	}
	/**
	 * Method runs the thread to send files
	 */
	public void run () {
		try{
			for(Socket s : connections){ //sends file to connections
				this.s.outgoing.send(s,fileName); 
				System.out.println("Sending to... " +  s);
			}
       } catch ( Exception e) {
           e.printStackTrace();
       }
   }
	
}