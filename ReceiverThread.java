package peertopeer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Flushable;
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
	
     sNode s;
     ArrayList<String> IPList;
     ArrayList<Socket> incoming = new ArrayList<>();
     Socket bootstrap;
     
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
    	 this.bootstrap = sn.bootstrap;
    	 if(s.master) {
    		 incoming.add(new Socket("25.124.176.158",4321));
    	 }
     }
     
     /**
      * Method runs the receive thread
      */
     public void run () {
    	 System.out.println("Scanning for new connections...");
   
    	 while(true){
    		 try{ 
    			 //checks disconnection
    	 			if(bootstrap == null && !s.master)  {
    	 				//connects to the bootstrap node
    					System.out.println("Bootstrap Node Connecting...");
    					this.bootstrap = new Socket("25.124.176.158",4321);
    					this.s.bootstrap = bootstrap;
    					System.out.println("Connected to Bootstrap Node: " + bootstrap);  
    					
    					//adds bootstrap to the list of connections
    					incoming.add(bootstrap);
    					System.out.println(incoming);
    					
    					//prepares to receive files
    		            BufferedInputStream bis = new BufferedInputStream(bootstrap.getInputStream());
    		            DataInputStream dis = new DataInputStream(bis);
    		          
    		            //Makes directory to put files in
    		            File dir = new File("FileDrop");
    		            if(!dir.exists()) dir.mkdir();
    		            String check = dis.readUTF();
    		            System.out.println(check);

    		            //accepts all files that bootstrap has
    		            if(check.equals("File")) {
    		            	
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
    			            	System.out.println("Receiving File #" + (i+1) + ": " + name);
    			            	
    			            	//checking duplicates
    			                if(tmp.exists() && (tmp.lastModified() <= time)) {
    		            			System.out.println("Updating file: " + name);
    		                    } 
    		                     
    		                    //Receives file + confirmation 
    		                    FileOutputStream fos = new FileOutputStream("FileDrop/" + name);
    		                    BufferedOutputStream bos = new BufferedOutputStream(fos);
    		                    //byte[] buffer = new byte[bufferSize];
    		                    int read = 0;
    		                    while(read < length) {
    		                        bos.write(bis.read());
    		                        read++;
    		                    }
    		                    
    		                    bos.flush();
    		                    System.out.println("Received File #" + (i+1) + ": " + name);
    		                    tmp.setLastModified(time);
    		            	} 
    		            }
    	            }
    		 } catch (Exception e) {
    			 System.out.println("No bootstrap detected!");
    			 System.exit(1);
    		 }
    			
               System.out.println("before loop");
               System.out.println(incoming.toString());
               if(incoming.size() > 1) {
                for(Socket a : incoming) {
                	try {        	
                    	//initialize components
                	       System.out.println("in loop");
                        BufferedInputStream bis = new BufferedInputStream(a.getInputStream());
                        DataInputStream dis = new DataInputStream(bis);
                        
                        System.out.println(dis.readAllBytes().length);
                        String check = dis.readUTF();
                        
                 
        
                        
                    	if (check.equals("IP")) { //receives IP from bootstrap 
    						String ip = dis.readUTF();    
    						if(ip != a.getInetAddress().toString())
    						System.out.println("New connection obtained " + ip.substring(1,ip.length()));
    						int i = ip.indexOf(":");
    						if (i != -1) {incoming.add(new Socket(ip.substring(1, i), 4321)); }
                    	} 
                    	

                        if(check.equals("File")) { //receives file from node
                        	//initialize components
                			int files = dis.readInt();
                        	long time = dis.readLong();
                        	long length = dis.readLong();
                        	 
                            //Displays file name
                            String fileName = dis.readUTF();
                            System.out.println("Recieving " + files + " File: " + fileName + " From: " + a);
                            
                            //Makes directory + creates file
                            File dir = new File("FileDrop");
                            if(!dir.exists()) dir.mkdir();
                            File tmp = new File("FileDrop/" + fileName);
                            
                            //Handles duplicate files 
                            if(tmp.exists() && (tmp.lastModified() <= time)) {
                    			System.out.println("Updating file " + fileName);
                            } 
                            
                            //Receives file + confirmation 
                            FileOutputStream output = new FileOutputStream("FileDrop/" + fileName);
                            BufferedOutputStream bos = new BufferedOutputStream(output);
                            int read = 0;
    	                    while(read < length) {
    	                        output.write(bis.read());
    	                        read++;
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
                System.out.println("Out of loop");
                try {
					sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		 }
     }
}
