/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peertopeer;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
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
import java.util.HashMap;



/**
 *
 * @author RJ
 */
public class sNode {
    HashMap<String,sNode> fingerTable;
    Socket clientConnection;
    ServerSocket sender;
    int nodeNum;
    
    
    public sNode(int n){
        this.nodeNum = n;
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
    
    
    public ServerSocket sender() throws Exception {
    
        ServerSocket server = null;
        String line = null;
       
        try{
           
            System.out.println("beep1");
            server = new ServerSocket(4321); 
        }
        catch (IOException e) {
             
            System.out.println(e);
            System.exit(-1);
        }
      sender = server;
     return server;
     
    }
    
    public void watch (ServerSocket socket) throws Exception {
     
     
             WatchService watcher = FileSystems.getDefault().newWatchService();
           new File("subfolder").mkdir();
          Path dir = Paths.get("subfolder");
          
          try {
                WatchKey key = dir.register(watcher,
                                       ENTRY_CREATE,
                                       ENTRY_DELETE,
                                       ENTRY_MODIFY);
            } catch (IOException x) {
                System.err.println(x);
            }
          
          
          
          System.out.println("hello");
         
        
        ServerSocket server = null;
     

        String line = null;
        OutputStream out = null;

          for (;;) {

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
                    System.out.println("tick");
        System.out.println(ev.kind());
        if(StandardWatchEventKinds.ENTRY_CREATE == ev.kind()) {
           String s = filename.toString();
            
            MyThread thread = new MyThread();
            
            try{
                
                System.out.println("beep1");
                System.out.println("beep1");
               // thread.run(socket.accept(),s);

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
                Files.move(Paths.get("JavaP2P/"+x),Paths.get("JavaP2P/oldfiles/"+x));
                System.out.println("Detected new file, saving new one as 'filename(number)'");
                
            } 
                           
            OutputStream output = new FileOutputStream("JavaP2P/"+x);
            byte[] buffer = new byte[bufferSize];
            int read;
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


   public void send(Socket socket, String filename) throws Exception {
   System.out.println("sending");
   final File myFile= new File(filename); //sdcard/DCIM.JPG
   
        byte[] mybytearray = new byte[(int)myFile.length()];
        FileInputStream fis = new FileInputStream(myFile);  
        BufferedInputStream bis = new BufferedInputStream(fis);
        DataInputStream dis = new DataInputStream(bis);
        OutputStream os;
        
        try {

            os = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(myFile.getName());     
            dos.writeLong(mybytearray.length);
            int read;
            while((read = dis.read(mybytearray)) != -1){
                dos.write(mybytearray, 0, read);
            }

          dos.writeByte(-1);
          dos.flush();


        } catch (IOException e) {

            e.printStackTrace();
        }
    


        System.out.println("Finished sending");
}



}
