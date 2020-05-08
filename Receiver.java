package peertopeer;

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
		   clientConnection = new Socket("25.124.176.158", 4321);
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
        	//System.out.println("Receiving...");
        	
        	//initialize components
            int bufferSize = socket.getReceiveBufferSize(); 
            InputStream in = socket.getInputStream();
            DataInputStream clientData = new DataInputStream(in);
        	long time = clientData.readLong();
   
            
            //Displays file name
            String fileName = clientData.readUTF();
            System.out.println("Recieving File: " + fileName);
            
            //Makes directory + creates file
            File dir = new File("FileDrop");
            if(!dir.exists()) dir.mkdir();
            File tmp = new File("FileDrop/" + fileName);
            
            System.out.println("File in dir: " + tmp.lastModified() + "\nFile coming in : " + time);
            //Handles duplicate files 
            if(tmp.exists() && (tmp.lastModified() >= time)) {
    			System.out.println("Most recent version of:" + fileName + " already exists!");
            } else {
                //Receives file + confirmation 
                tmp.setLastModified(time);
                OutputStream output = new FileOutputStream("FileDrop/" + fileName, false);
                byte[] buffer = new byte[bufferSize];
                int read;
                System.out.println("I got to here");
                while((read = clientData.read(buffer)) > -1){
                	System.out.println("In the looooop!");
                    output.write(buffer, 0, read);
                }
                System.out.println("Out of the loop.");
                output.flush();
                System.out.println("Recieved file: " + fileName);
            }
            
            
        } catch (IOException e) {
        	e.printStackTrace();
        }
    } 
    
}
