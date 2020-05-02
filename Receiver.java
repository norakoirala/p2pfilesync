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
 *
 * @author RJ
 * @author Nora
 * Receiver receives the files from a client 
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
     * @param filename - the name of the file being received 
     * @throws Exception
     */
    public void acceptFile(Socket socket, String filename) throws Exception {
        InputStream in; 
        int bufferSize = 0; 

        try {
        	//initialize components
            bufferSize = socket.getReceiveBufferSize(); 
            in = socket.getInputStream();
            DataInputStream clientData = new DataInputStream(in);
            
            //Displays file name
            String fileName = clientData.readUTF();
            System.out.println("File: " + fileName);
            
            //Makes directory + creates file
            File dir = new File("JavaP2P");
            if(!dir.exists()) dir.mkdir();
            File tmp = new File("JavaP2P/" + fileName);
            
            //Handles duplicate files 
            if(tmp.exists()) {
            	//TODO implement the function that chooses the most recently modified file 
                System.out.println("Enter duplicate file name");
                Scanner input = new Scanner(System.in);
                fileName = input.nextLine() + fileName;
                input.close();
            } 
              
            //writes to file + sends confirmation message
            OutputStream output = new FileOutputStream("JavaP2P/" + fileName);
            byte[] buffer = new byte[bufferSize];
            int read;
            while((read = clientData.read(buffer)) != 1){
                output.write(buffer, 0, read);
                //System.out.println(read);
            }
            output.close();
            System.out.println("Received");

        } catch (IOException e) {
        	e.printStackTrace();
        }
    } 
    
}