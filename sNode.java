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
    int nodeNum;
    
    /**
     * Overloaded constructor
     * @param n - number for the node
     */
    public sNode(int n){
        this.nodeNum = n;
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
    public void receive() throws Exception{
        incoming = new Receiver();
        Socket cl = incoming.client();
        System.out.println("Broken?");
        incoming.acceptFile(cl,"s.pdf");
    }
    
}
