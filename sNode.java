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

}
