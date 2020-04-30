 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 */
public class Receiver {
    Socket clientConnection;
    static int counter = 0;
    public Receiver(){
        
    }
    
    
    
    
    public Socket client() {
        try{
           System.out.println("beep");
           clientConnection = new Socket("25.124.176.85", 4321);
           System.out.println("beep");

          } catch (UnknownHostException e) {
            System.out.println("Unknown host: kq6py");
            System.exit(1);
          } catch  (IOException e) {
            System.out.println(e.toString());
            System.exit(1);
          }
     
      return clientConnection;

        
    }
    

    

    
public void acceptFile(Socket socket, String filename) throws Exception {
     
        InputStream in;
        int bufferSize=0;

        try {
              
            bufferSize=socket.getReceiveBufferSize();
            in=socket.getInputStream();
            DataInputStream clientData = new DataInputStream(in);
            String x;
            System.out.println(x = clientData.readUTF());
         
            File dir = new File("JavaP2P");
            if(!dir.exists()) dir.mkdir();
            File tmp = new File("JavaP2P/"+x);
            
            if(tmp.exists()) {
                       
                dir = new File("JavaP2P/oldfiles");
           	dir.mkdir();
             
            //    Files.move(Paths.get("JavaP2P/"+x),Paths.get("JavaP2P/oldfiles/"+counter+x+));
                System.out.println("Enter duplicate file name");
                Scanner input = new Scanner(System.in);
                x=input.nextLine()+x;
              
            } 
                           
            OutputStream output = new FileOutputStream("JavaP2P/"+x);
            byte[] buffer = new byte[bufferSize];
            int read;
            System.out.println("Hello");
            while((read = clientData.read(buffer)) != 1){
                output.write(buffer, 0, read);
                    System.out.println(read);
            }
          
           output.flush();
            

        } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
       

      System.out.println("Received");
    
    }

        
}


