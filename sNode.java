package peertopeer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * sNode is representation of a node
 * @author RJ
 * @author Nora
 */
public class sNode {
	
    Sender outgoing; 
    Receiver incoming;
    Socket cl;
    int nodeNum;
    Socket bootstrap;
    ArrayList<Socket> incomingConnections;
     ArrayList<String> IPList;
    boolean master;
    
    /**
     * Overloaded constructor
     * @param n - number for the node
     */
 
    public sNode(boolean m){
        this.master = m;
    }
    
    
    /**
     * Method sends files
     * @throws Exception
     */
    
   
    public void listen() throws Exception {
		outgoing = new Sender(this);
		outgoing.watch(outgoing.sender());
    }
    
    /**
     * Method receives files
     * @throws Exception
     */
    
    public void establish(){
        System.out.println("E");
        incoming = new Receiver();
        this.cl = incoming.client();
    }

    public void receive(Socket s) throws Exception{
    	this.incoming = new Receiver();
        System.out.println("R: " + s);
        incoming.acceptFile(cl);
    }
    

    
}
