package peertopeer;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Tester {
	
	public static void main(String[] args) {
			
		File dir = new File("JavaP2P/");
		
		String[] list = dir.list();
		
		for(int i = 0; i < list.length; i++) {
			System.out.println(list[i]);
		}
		
			
	}
	
}
