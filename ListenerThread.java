/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peertopeer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author RJ
 */
public class ListenerThread extends Thread {
     
    ArrayList<Socket> connections;
    ServerSocket server;
    boolean detected = false;
   ListenerThread () {
      // The compiler creates the byte code equivalent of super ();
   }
 
   public ListenerThread (ArrayList<Socket> c, ServerSocket s) {
      // The compiler creates the byte code equivalent of super ();
    this.server =s;
     this.connections = c;
   }
   
   

   
   public void run () {
       try{
          
           while(!detected){
            Socket tmp;
            connections.add(tmp = server.accept());
            System.out.println("Added  new connection: " + tmp);
           
         
           }
           
           
       } catch ( Exception e) {
           
       }
   }
   
   public void kill(){
   
       this.detected = true;
   }
}
