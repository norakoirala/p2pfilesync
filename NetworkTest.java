/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networktest;

/**
 *
 * @author RJ
 */
import java.net.*; 
import java.io.*; 
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
public class NetworkTest {
    
    /**
     * @param args the command line arguments
     */
    static Socket socket = null;
    static Scanner input = new Scanner(System.in);
    static int count = 0;
    public static void main(String[] args) throws Exception{
     

            client();
           
            acceptFile(socket, "s1.pdf");
       
        
    }
    
    public static void client() throws Exception {
   
      
 
        
 
           
        try{
           System.out.println("beep");
           socket = new Socket("25.124.176.85", 4321);
           System.out.println("beep");

          } catch (UnknownHostException e) {
            System.out.println("Unknown host: kq6py");
            System.exit(1);
          } catch  (IOException e) {
            System.out.println(e.toString());
            System.exit(1);
          }
      

        
    }
 
    public static void acceptFile(Socket socket, String filename) throws Exception {
        
        long bufLen = 0;
        int bytesRead;
        InputStream in;
        int bufferSize=0;
        int current = 0;
        int byteread;

        try {
              
          //     mutex.acquire();
            bufferSize=socket.getReceiveBufferSize();
            in=socket.getInputStream();
            DataInputStream clientData = new DataInputStream(in);
            String x;
            System.out.println(x = clientData.readUTF());
         
            File dir = new File("folder1");
           	dir.mkdir();
                   File tmp = new File("folder1/"+x);
                   if(tmp.exists()) {
                       
                dir = new File("folder1/oldfiles");
           	dir.mkdir();
               Files.move(Paths.get("folder1/"+x),Paths.get("folder1/oldfiles/"+x));
                       System.out.println("Detected new file, saving new one as 'filename(number)'");
                   } 
                           
            OutputStream output = new FileOutputStream("folder1/"+x);
            byte[] buffer = new byte[bufferSize];
            int read;
            while((read = clientData.read(buffer)) != 1){
                output.write(buffer, 0, read);
                    System.out.println(read);
            }
            count++;
           output.flush();
            

        } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
        
   /*   System.out.println("Sending message");
        DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
        
           dOut.writeByte(2);
            dOut.writeUTF("RECEIVED");
            dOut.flush(); // Send off the data

   */System.out.println("end?");
    
    }

    
    
public static void server(){
        ServerSocket server = null;
        Socket client = null;
            PrintWriter out = null;
        BufferedReader in = null;
        String line = null;
        
        try{
    server = new ServerSocket(4321); 
     System.out.println("beep1");
  } catch (IOException e) {
    System.out.println("Could not listen on port 4321");
    System.exit(-1);
  }
        
          try{
                   System.out.println("beep1");
    client = server.accept();
         System.out.println("beep1");
  } catch (IOException e) {
    System.out.println("Accept failed: 4321");
    System.exit(-1);
  }



  try{
   in = new BufferedReader(new InputStreamReader(
                           client.getInputStream()));
   out = new PrintWriter(client.getOutputStream(), 
                         true);
  } catch (IOException e) {
    System.out.println("Read failed");
    System.exit(-1);
  }
  
   while(true){
      try{
        line = in.readLine();
//Send data back to client
        out.println(line);
      } catch (IOException e) {
        System.out.println("Read failed");
        System.exit(-1);
      }
    }
  
  
}
}



    
       /*    
            
            // Send the second message
            dOut.writeByte(2);
            dOut.writeUTF("Guess what??");
            dOut.flush(); // Send off the data

            // Send the third message
            dOut.writeByte(3);
            dOut.writeUTF(" I LOVE YOU ! ");
            dOut.writeUTF("<3<3");
            dOut.flush(); // Send off the data

         
    
               // Send the exit message
            dOut.writeByte(-1);
            dOut.flush();
*/      
             