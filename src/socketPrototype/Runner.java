package socketPrototype;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import de.dima.protocoltest.KeyValueProtos.KeyValuePair;

public class Runner {
	
	private static KeyValuePair generateKeyValue() {
		KeyValuePair.Builder kvpb = KeyValuePair.newBuilder();
		Random r = new Random();

		kvpb.setKey("k-" + r.nextInt(1000000000));
		kvpb.setValue("v-" + r.nextInt(1000000000));
		
		return kvpb.build();
	}
	
	public static void main(String args[]) throws Exception{
        String fromClient;
        String toClient;
 
		//String[] env =  {"PYTHONPATH=src/python"};
		Process p = Runtime.getRuntime().exec("python runner.py");
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		
        ServerSocket server = new ServerSocket(8080);
        System.out.println("wait for connection on port 8080");
 
        boolean run = true;
        while(run) {
            Socket client = server.accept();
            System.out.println("got connection on port 8080");
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            OutputStream out = client.getOutputStream();
            DataOutputStream dout = new DataOutputStream(out);
 
            
            for(int i=0; i < 1; i++){
            	// For each kvp write the size as int and then the kvp
            	KeyValuePair kvp = generateKeyValue();
            	dout.writeInt(kvp.getSerializedSize());
            	dout.flush();
            	kvp.writeTo(out);
            	out.flush();
            }
            
            // This code is the copy pasted one, one would need a second thread to read the data from the in
            // Thread from here
            fromClient = in.readLine();
            System.out.println("received: " + fromClient);
 
            if(fromClient.equals("Hello")) {
                toClient = "olleH";
                System.out.println("send olleH");
                out.println(toClient);
                fromClient = in.readLine();
                System.out.println("received: " + fromClient);
 
                if(fromClient.equals("Bye")) {
                    toClient = "eyB";
                    System.out.println("send eyB");
                    out.println(toClient);
                    client.close();
                    run = false;
                    System.out.println("socket closed");
                }
            }
            
            // Some Printing
            String line;
			while ((line = input.readLine()) != null) {
				System.err.println("Python: '"+line);
			}
			while ((line = err.readLine()) != null) {
				System.err.println("Python Error: "+line);
			}
        }
        
        System.exit(0);
	}

}
