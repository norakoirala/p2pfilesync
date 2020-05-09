package peertopeer;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
                    this.bootstrap = new Socket("25.124.176.158",4321);
                    System.out.println("Bootstrap Node Connected!");
                    try {
                    	//System.out.println("Receiving...");
                    	
                    	//initialize components
                        int bufferSize = bootstrap.getReceiveBufferSize(); 
                        InputStream in = bootstrap.getInputStream();
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
                    try {
                    	//System.out.println("Receiving...");
                    	
                    	//initialize components
                        int bufferSize = a.getReceiveBufferSize(); 
                        InputStream in = a.getInputStream();
                        DataInputStream clientData = new DataInputStream(in);
                    	long time = clientData.readLong();
               
                        
                        //Displays file name
                        String fileName = clientData.readUTF();
                        System.out.println("Recieving File: " + fileName);
                        
                        //Makes directory + creates file
                        File dir = new File("File");
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
