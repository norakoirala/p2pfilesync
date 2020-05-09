package peertopeer;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;

/**
 * Sender sends the file to a client
 * @author RJ
 * @author Nora
 */
public class Sender {
	
	sNode n; //node sending the file
	ArrayList<Socket> connections; //list of all connections to send to 
    
	/**
	 * Overloaded constructor
	 * @param s - the node sending the file
	 */
    public Sender(sNode s){
        this.n = s;  
        connections = new ArrayList<Socket>();
    }
    
    /**
     * Sends file to client socket
     * @param socket - client socket
     * @param filename - file to send
     * @throws Exception
     */
    public void send(Socket socket, String filename) throws Exception {
    	try {
        	System.out.println("Sending File: " + filename + " to" + socket);
        	
        	//initialize components
        	final File myFile = new File("FileDrop/" + filename); //sdcard/DCIM.JPG 

    		FileInputStream fis = new FileInputStream(myFile);  
    		BufferedInputStream bis = new BufferedInputStream(fis);
    		DataInputStream dis = new DataInputStream(bis);
    		OutputStream os = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            
            //names file, sends it + confirmation
           
            dos.writeUTF("File");
//            dos.writeInt(1);
           
            dos.writeLong(myFile.lastModified());
            dos.writeUTF(myFile.getName()); 
            dos.writeLong(myFile.length());
    
            int read;
            while((read = bis.read()) > -1) { 
                dos.write(read);
            }
			dos.writeByte(-1);
			dos.flush();
			System.out.println(filename + " sent to " + socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used for drag and drop of files 
     * @param socket
     * @throws Exception
     */
    public void watch (ServerSocket socket) throws Exception {
		WatchService watcher = FileSystems.getDefault().newWatchService();
		new File("FileDrop").mkdir();
		Path dir = Paths.get("FileDrop");
		          
		try {
			WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		} catch (IOException x) {
			System.err.println(x);
		}

		ServerSocket server = null;
		String line = null;
        OutputStream out = null;
        
        for (;;) {
        	ListenerThread lt = new ListenerThread(connections,socket);
        	lt.start();
    
                
		    // wait for key to be signaled
		    WatchKey key;
		    try {
		        key = watcher.take();
		    } catch (InterruptedException x) {
		        return;
		    }

		    for (WatchEvent<?> event: key.pollEvents()) {
		        WatchEvent.Kind<?> kind = event.kind();
		   
		        // This key is registered only
		        // for ENTRY_CREATE events,
		        // but an OVERFLOW event can
		        // occur regardless if events
		        // are lost or discarded.
		        if (kind == OVERFLOW) {
		            continue;
		        }

		        // The filename is the
		        // context of the event.
		        WatchEvent<Path> ev = (WatchEvent<Path>)event;
		        Path filename = ev.context();
		        System.out.println(filename);
		        // Verify that the new
		        //  file is a text file.
		        try {
		            // Resolve the filename against the directory.
		            // If the filename is "test" and the directory is "foo",
		            // the resolved name is "test/foo".
		            Path child = dir.resolve(filename);
		           
		        } catch (Exception x) {
		            System.err.println(x);
		            continue;
		        }
		         
		        System.out.println(connections.toString());
		        System.out.println(ev.kind());
		        this.connections = lt.connections;
		        lt.kill();
		        if(StandardWatchEventKinds.ENTRY_CREATE == ev.kind() || StandardWatchEventKinds.ENTRY_MODIFY == ev.kind() ) {         
		        	String s = filename.toString();
           
           
            
	            try{
	              
	                 System.out.println("Entered try");
                  
	                SendThread thread = new SendThread(connections,s,n);
	                thread.start();
	                System.out.println("Accepted");
	   
	              
	
	            } catch (Exception e) {
	               e.printStackTrace();
	             }   

		       }
		       
		    }

		    boolean valid = key.reset();
		    if (!valid) {
		        break;
		    }
		}
    }
    
    /**
     * Tests the connection of the sender
     * @return - the sender server 
     * @throws Exception
     */
    public ServerSocket sender() throws Exception {
        ServerSocket server = null;
       
        try {
            System.out.println("beep1");
            server = new ServerSocket(4321); 
        }
        catch (IOException e) { 
        	e.getStackTrace();
            System.exit(-1);
        }
    
        return server;
    }
  
}