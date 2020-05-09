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
    	 try {
 			if(bootstrap == null && !s.master)  {
				System.out.println("Bootstrap Node Connecting...");
				this.bootstrap = new Socket("25.124.176.158",4321);
				System.out.println("Bootstrap Node:" + bootstrap + "Connected!");
			        	//setting up streams to accept files
			            BufferedInputStream bis = new BufferedInputStream(bootstrap.getInputStream());
			            DataInputStream dis = new DataInputStream(bis);
			            
			            //Makes directory to put files in
			            File dir = new File("FileDrop");
			            if(!dir.exists()) dir.mkdir();
			            
			           
			 
			            
			            //Receiving number of files
			            String file = dis.readUTF();
			            System.out.println(file);
			            if (file.equals("File")) {
				            int numFiles = dis.readInt();
				            System.out.println(numFiles);
				            if(numFiles > 0) {
				            	 System.out.println("Receiving " + numFiles + " files...");
						            
						            //Writing each file
						            for (int i = 0; i < numFiles; i++) {
						            	//receiving metadata
						            	String f = dis.readUTF();
						        
						            		long time = dis.readLong();
							            	String name = dis.readUTF();
							            	long length = dis.readLong();
							            	
							            	//creating file
							            	File tmp = new File("FileDrop/" + name);
							            	System.out.println("Receiving File #" + (i+1) + ": " + name);
							            	
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
							                    bos.flush();
							                    System.out.println("Received File #" + (i+1) + ": " + name);
							                }
						            	}
			            }
			    

					            	
					            
			            } 
		            }

	    	 } catch (Exception e) {
	    		 e.printStackTrace();
    	 }
    	 
    	 System.out.println("hello");

    	 while(true){
    		 try{                  
                if(bootstrap != null ) {
                	try{
                    	System.out.println("Connection established\n" + bootstrap.toString());
                    	BufferedInputStream bis = new BufferedInputStream(bootstrap.getInputStream());
                    	DataInputStream dis = new DataInputStream(bis);
                    	String check = dis.readUTF();
                    	if (check.equals("IP")) {
    						String ip = dis.readUTF();    
    						System.out.println("New obtained " + ip.substring(1,ip.length()));
    						int i = ip.indexOf(":");
    						if (i != -1) {incoming.add(new Socket(ip.substring(1, i), 4321)); }
                    	} else if (check.equals("File")) {
                        	//initialize components
                            	long time = dis.readLong();
                            	   
                               
                                //Displays file name
                                String fileName = dis.readUTF();
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
                                	int bufferSize = (int) dis.readLong();
                                    OutputStream output = new FileOutputStream("FileDrop/" + fileName);
                                    byte[] buffer = new byte[bufferSize];
                                    int read;
                                    while((read = dis.read(buffer)) != 1){
                                        output.write(buffer, 0, read);
                                    }
                                    output.flush();
                                    System.out.println("Received File: " + fileName);
                                    tmp.setLastModified(time);
                                }
                    	}
                	} catch (Exception e) {
                		break;
                	}
                }
                
                for(Socket a : incoming) {
                	System.out.println("Confirmed connection non bootstrap "  + a.toString());
                	try {        	
                    	//initialize components
                    	System.out.println("incoming from: " + a);
                        int bufferSize = a.getReceiveBufferSize(); 
                        InputStream in = a.getInputStream();
                        DataInputStream clientData = new DataInputStream(in);
                        String s = clientData.readUTF();
                        
                        System.out.println("Here accepting file: " + s);
                        
                        if(s.equals("File")) {
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

                sleep(500);
    		 } catch (Exception e) {
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
