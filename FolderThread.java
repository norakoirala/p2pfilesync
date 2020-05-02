package peertopeer;

/**
 *
 * @author RJ
 * @author Nora
 * FolderThread scans for changes in the file folder
 */
public class FolderThread extends Thread {
	
	sNode s; //the node detecting file changes 
    
	/*
	 * Default Constructor
	 */
	FolderThread () {
      // The compiler creates the byte code equivalent of super ();
	}
	
	/**
	 * Overloaded Constructor
	 * @param sn - node that the folder is on 
	 */
	public FolderThread (sNode sn) {
      // The compiler creates the byte code equivalent of super ();
      this.s = sn;
	}
	
	/*
	 * Method runs to check for new changes to the folder 
	 */
	public void run () {
       try {
           s.listen();
       } catch (Exception e) { 
    	   e.getStackTrace();
       }
	}
	
}
