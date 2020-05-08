package peertopeer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Receiver receives the files from a client 
 * @author RJ
 * @author Nora
 */
public class Receiver {
	
    Socket clientConnection; //the client that gets connected 
    
    /*
     * Default Constructor
     */
    public Receiver(){
        
    }
    
    /**
     * Method connects to the client
     * @return - the client socket 
     */
    public Socket client() {
		try {
		   System.out.println("Establishing connection...");
		   clientConnection = new Socket("25.14.231.10", 4321);
		   System.out.println("Connection Established!");
		} catch (UnknownHostException e) {
		    System.out.println("Unknown host: kq6py");
		    System.exit(1);
		} catch  (IOException e) {
		    System.out.println(e.toString());
		    System.exit(1);
		} 
     
		return clientConnection;
    }
 
    /**
     * Method ensures that file 
     * @param socket - the socket receiving the file
     * @throws Exception
     */
    public void acceptFile(Socket socket) throws Exception {
        try {
        	//setting up streams to accept files
            BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
            DataInputStream dis = new DataInputStream(bis);
            
            //Makes directory to put files in
            File dir = new File("FileDrop");
            if(!dir.exists()) dir.mkdir();
            
            //Receiving number of files
            int numFiles = dis.readInt();
            System.out.println("Receiving " + numFiles + " files...");
            
            //Writing each file
            for (int i = 0; i < numFiles; i++) {
            	//receiving metadata
            	long time = dis.readLong();
            	long length = dis.readLong();
            	String name = dis.readUTF();
            	
            	//creating file
            	File tmp = new File("FileDrop/" + name);
            	System.out.println("Receiving File #" + i + ": " + name);
            	
            	//checking duplicates
                if(tmp.exists() && (tmp.lastModified() >= time)) {
        			System.out.println("Most recent version of:" + name + " already exists!");
                } else {
                    //Receives file + confirmation 
                    tmp.setLastModified(time);
                    FileOutputStream fos = new FileOutputStream("FileDrop/" + name, false);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    //byte[] buffer = new byte[bufferSize];
                    int read = 0;
                    while(read < length) {
                        bos.write(bis.read());
                        read++;
                    }
                    bos.close();
                    System.out.println("Received File #" + i + ": " + name);
                }
                
                dis.close();
            }            
        } catch (IOException e) {
        	e.printStackTrace();
        }
    } 
    
}
