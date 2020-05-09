package peertopeer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Listener thread scans for new connections
 * @author RJ
 * @author Nora
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
		this.server = s;
		this.connections = c;
	}
	

	/**
	 * Method runs thread to scan the network for new connections 
	 */
	public void run () {
		Socket tmp = null; 
		//while there are no files changes, accept new connections 
		while(!fileChange){
			try {
				//System.out.println("Attempting to add");
				connections.add(tmp = server.accept());
		        System.out.println("Added  new connection: " + tmp); //verifying new connection
		        
		        BufferedOutputStream os = new BufferedOutputStream(tmp.getOutputStream());
	            DataOutputStream dos = new DataOutputStream(os);
	        	
		       
	            //getting all the files from one folder 
	        	File dir = new File("FileDrop/");
	    		File[] fileList = dir.listFiles();
	    		
	    		//adding # of files to output stream
	    		int numFiles = fileList.length;
	    		if(numFiles > 0) {
	    			dos.writeUTF("File");
		    		dos.writeInt(numFiles);
		    		System.out.println("Sending " + numFiles + " files...");
		    		
		    		//sending the files one by one
		    		for(int i = 0; i < numFiles; i++) {
		    			File f = fileList[i];
		    			//adding meta data for each file
		    			long time = f.lastModified();
		    			dos.writeLong(time);
		    			long length = f.length();
		    			dos.writeLong(length);
		    			String name = f.getName();
		    			dos.writeUTF(name);
		    			
		    			//set up streams
		        		FileInputStream fis = new FileInputStream(f);  
		        		BufferedInputStream bis = new BufferedInputStream(fis);
		        		System.out.println("Sending File #" + (i+1) + ": " + name);
		    			
		        		//write files
		                int read;
		                while((read = bis.read()) != -1) { 
		                    dos.write(read);
		                }
		                System.out.println("Sent File #" + (i+1) + ": " + name);
		    		}
		    		
		    		dos.flush();
	    		} 
	    		
			} catch (Exception e) {
				System.out.println("Couldnt add ");
			}//adds new connection to list of connections 
			
			if(connections.size() > 1){
				for(Socket c : connections) {
					try {
						System.out.println("Sending peers to " + c.toString());
						DataOutputStream dos = new DataOutputStream(c.getOutputStream());
						for(Socket co : connections) {
							if(c == co ) continue;
							dos.writeUTF("IP");
							dos.writeUTF(co.getRemoteSocketAddress().toString());
						}
						dos.flush();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}          
			} 
		}
      }
	/**
	 *  Method switches boolean to stop scanning the network because a file was addded, removed, updated
	 */
	public void kill(){
       this.fileChange = true;
	}
	
}
