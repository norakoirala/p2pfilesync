package peertopeer;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Scanner;

/**
 * PeerToPeer runs the main method, has hashing function
 * @author RJ
 * @author Nora
 */
public class PeerToPeer {

    /**
     * Main Method to run program
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
		// TODO code application logic here
		     
		Scanner in = new Scanner(System.in);
		sNode n1 = null;
		System.out.println("Are you the bootstrap node? (Y/N)");
		String s = in.nextLine();
		if (s.charAt(0) == 'Y') {
			n1 = new sNode(true);
		} else if (s.charAt(0) == 'N') {
			n1 = new sNode(false);
		} else {
			System.out.println("Invalid input!");
		}
	
		
		
		System.out.println("Start Sending (S) or Start Receiving (R)?");
		s = in.nextLine();
		if (s.charAt(0) == 'S') {
			new FolderThread(n1).start();
		} else if (s.charAt(0) == 'R') {
			new ReceiverThread(n1).start(); 
		} else {
			System.out.println("Invalid input!");
		}
    }
    
    /**
     * Method takes in input and returns the encrypted message
     * @param input - the string to be hashed using SHA-1
     * @return - the hashed string 
     * @throws Exception
     */
    public static String encrypt(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-1"); //implementing SHA-1
        byte[] messageDigest = md.digest(input.getBytes()); //using SHA-1 to convert message to bytes
        BigInteger no = new BigInteger(1, messageDigest); //converting byes into bigInteger
        String hashtext = no.toString(16); //converting bigInteger to String
        
        while (hashtext.length() < 32) { 
            hashtext = "0" + hashtext; //converting to 32 bit message
        }
        
        return hashtext; 
    }
    
}