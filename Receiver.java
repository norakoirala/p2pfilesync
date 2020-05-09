package peertopeer;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

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
		   clientConnection = new Socket("192.168.1.10", 4321);
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
        	//initialize components
            int bufferSize = socket.getReceiveBufferSize(); 
            InputStream in = socket.getInputStream();
            DataInputStream clientData = new DataInputStream(in);
            
            if(clientData.readUTF().equals("File")) {
            	long time = clientData.readLong();
            	   
                
                //Displays file name
                String fileName = clientData.readUTF();
                System.out.println("Recieving File: " + fileName);
                
                //Makes directory + creates file
                File dir = new File("JavaP2P");
                if(!dir.exists()) dir.mkdir();
                File tmp = new File("JavaP2P/" + fileName);
                
                
                System.out.println("File in dir: " + tmp.lastModified() + "\nFile coming in : " + time);
                //Handles duplicate files 
                if(tmp.exists() && (tmp.lastModified() >= time)) {
        			System.out.println("Most recent version of:" + fileName + " already exists!");
                } else {
                    //Receives file + confirmation 
                    OutputStream output = new FileOutputStream("JavaP2P/" + fileName);
                    byte[] buffer = new byte[bufferSize];
                    int read;
                    while((read = clientData.read(buffer)) != 1){
                        output.write(buffer, 0, read);
                    }
                    output.flush();
                    System.out.println("Received File: " + fileName);
                    tmp.setLastModified(time);
                }
                
            }
        } catch (IOException e) {
        	e.printStackTrace();
        }
    } 
    
    
      public void acceptFiles(Socket socket) throws Exception {
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
            File dir = new File("JavaP2P");
            if(!dir.exists()) dir.mkdir();
            File tmp = new File("JavaP2P/" + fileName);
            
            
            System.out.println("File in dir: " + tmp.lastModified() + "\nFile coming in : " + time);
            //Handles duplicate files 
            if(tmp.exists() && (tmp.lastModified() >= time)) {
    			System.out.println("Most recent version of:" + fileName + " already exists!");
            } else {
                //Receives file + confirmation 
                OutputStream output = new FileOutputStream("JavaP2P/" + fileName);
                byte[] buffer = new byte[bufferSize];
                int read;
                while((read = clientData.read(buffer)) != 1){
                    output.write(buffer, 0, read);
                }
                output.flush();
                System.out.println("Received File: " + fileName);
                tmp.setLastModified(time);
            }
        } catch (IOException e) {
        	e.printStackTrace();
        }
    } 
    
}
