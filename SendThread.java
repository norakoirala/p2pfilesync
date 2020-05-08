package peertopeer;

import java.net.Socket;
import java.util.ArrayList;

/**
 * Send Thread is the thread that send runs on 
 * @author RJ
 * @author Nora
 */
 public class SendThread extends Thread {
	sNode s; 
	String fn; //file name
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
	public SendThread (ArrayList<Socket> cn, String fn, sNode sn) {
		// The compiler creates the byte code equivalent of super ();
		this.s = sn;
		this.fn = fn;
		this.connections = cn;
	}
	/**
	 * Method runs the thread to send files
	 */
	public void run () {
		try{
			
			for(Socket socket : connections){ //sends file to connections
				s.join(socket);
//				System.out.println("Sending to: " +  s);
//				this.s.outgoing.sendFile(s, fn);
			}
       } catch ( Exception e) {
           e.printStackTrace();
       }
   }
	
}