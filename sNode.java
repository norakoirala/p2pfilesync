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
    Sender outgoing;
    Receiver incoming;
    int nodeNum;
    
    
    public sNode(int n){
        this.nodeNum = n;
    }
    
    public void listen() throws Exception {
        outgoing = new Sender(this);
        outgoing.watch(outgoing.sender());
    }
    
    public void receive() throws Exception{
        incoming = new Receiver();
        Socket cl = incoming.client();
        System.out.println("Broken?");
        incoming.acceptFile(cl,"s.pdf");
        
    }

}
