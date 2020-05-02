package peertopeer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author RJ
 * @author Nora
 * Listener thread scans for new connections
 */
public class ListenerThread extends Thread {

	ArrayList<Socket> connections; //keeps a list of all the connections on the network
	ServerSocket server; //the node that is the server
	boolean fileChange = false; //boolean to check if any file changes have occured
    
	/**
	 * Default Constructor 
	 */
	ListenerThread () {
      // The compiler creates the byte code equivalent of super ();
	}
	
	/**
	 * Overloaded constructor
	 * @param c - the list of connections already on the network
	 * @param s - the server node 
	 */
	public ListenerThread (ArrayList<Socket> c, ServerSocket s) {
      // The compiler creates the byte code equivalent of super ();
		this.server =s;
		this.connections = c;
	}
	
	/*
	 * Method runs to scan the network for new connections 
	 */
	public void run () {
       try{
    	   
          //while there are no files changes, accept new connections 
           while(!fileChange){
            Socket tmp; 
            connections.add(tmp = server.accept()); //adds new connection to list of connections 
            System.out.println("Added  new connection: " + tmp); //verifying new connection
           }
           
       } catch ( Exception e) {
           
       }
	}
	
	/*
	 * Method switches boolean to stop scanning the network because a file was addded, removed, updated
	 */
	public void kill(){
       this.fileChange = true;
	}
}
