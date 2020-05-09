package peertopeer;

import java.io.DataInputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * RecieverThread is the thread that Receiver runs on
 * @author RJ
 * @author Nora
 */
public class ReceiverThread extends Thread {
	
     sNode s; //the node that receives the file
     ArrayList<String> IPList;
     ArrayList<Socket> incoming = new ArrayList<>();
     Socket bootstrap = null;
     
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
     public ReceiverThread (sNode sn) throws Exception {
      // The compiler creates the byte code equivalent of super ();
    	 this.s = sn;
     }
     
     /**
      * Method runs the receive thread
      */
     public void run () {
    	 while(true){
    		 try{      
                if(bootstrap == null && !s.master)  {
                    System.out.println("Bootstrap Node Connecting...");
                    this.bootstrap = new Socket("25.150.162.62",4321);
                    System.out.println("Bootstrap Node Connected!");
                }
                 
                if(bootstrap != null ) {
                	try{
                    	System.out.println("Connection established\n" + bootstrap.toString());
                    	DataInputStream dis = new DataInputStream(bootstrap.getInputStream());
                    	String check = dis.readUTF();
                    	if (check.equals("IP")) {
    						String ip = dis.readUTF();    
    						System.out.println("New obtained " + ip.substring(1,ip.length()));
    						int i = ip.indexOf(":");
    						if (i != -1) {incoming.add(new Socket(ip.substring(1, i), 4321)); }
                    	}
                	} catch (Exception e) {
                		break;
                	}
                }
                
                for(Socket a : incoming) {
                	System.out.println("Confirmed connection non bootstrap "  + a.toString());
                }
                
                sleep(15000);
    		 } catch ( Exception e) {
		       e.printStackTrace();
    		 } 
    	 }
     }
     
     
//     public int findColon(String s) {
//		for(int i = 0; i < s.length(); i++){
//		    if(s.charAt(i) == ':') return i; 
//		}
//		return -1; 
//    }
//      
}
