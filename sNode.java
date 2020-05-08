package peertopeer;

import java.net.Socket;
import java.util.HashMap;

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
    
    /**
     * Overloaded constructor
     * @param n - number for the node
     */
    public sNode(int n){
        this.nodeNum = n;
    }
    
    public void join(Socket s) throws Exception {
    	outgoing = new Sender(this);
    	outgoing.join(s);
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
     * Method establishes connection
     */
    public void establish(){
        incoming = new Receiver();
        this.cl = incoming.client();
    }
    
    /**
     * Method receives files
     * @throws Exception
     */
    
    public void receive() throws Exception {
        incoming.acceptFile(cl);
    }


    
}
