package socketPrototype;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import protos.KeyValueProtos.KeyValuePair;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;


public class Runner {
	
	public static final int PORT = 8080;
	
	private static KeyValuePair generateKeyValue() {
		KeyValuePair.Builder kvpb = KeyValuePair.newBuilder();
		Random r = new Random();

		kvpb.setKey("k-" + r.nextInt(1000000000));
		kvpb.setValue("v-" + r.nextInt(1000000000));
		
		return kvpb.build();
	}
	
	
	public static void main(String args[]) throws Exception{
		
		String[] env =  {"PYTHONPATH=src/python"};
		Process p = Runtime.getRuntime().exec("python runner.py");
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        
	    ServerSocket server = new ServerSocket(PORT);
	    System.out.println("Waiting for connection on port " + PORT);
	    Socket socket = server.accept();
	    System.out.println("OutputThread: got connection on port " + PORT);

        OutputStream out = socket.getOutputStream();
	    final CodedOutputStream cout = CodedOutputStream.newInstance(out);
	            
        System.out.println("Setting up");
        for(int i=0; i < 1; i++){
        	// For each kvp write the size as int and then the kvp
	        KeyValuePair kvp = generateKeyValue();
	        int size = kvp.getSerializedSize();
	        cout.writeInt32NoTag(size);
	        //cout.flush();
	        System.out.println("Wrote size: " + size);    	
	        kvp.writeTo(cout);
	        System.out.println("Wrote kvp: (" + kvp.getKey() + ":" + kvp.getValue() + ")");
	    }
	    System.out.println("Cleaning up CThread");
        cout.writeRawLittleEndian32(-1);
        cout.flush();
            	
        // Some Printing
        String line;
        while ((line = input.readLine()) != null) {
        	System.err.println("Python: '"+line);
		}
        while ((line = err.readLine()) != null) {
        	System.err.println("Python Error: "+line);
		}
	}

}
