import java.net.*;
import java.io.*;
import java.util.StringTokenizer;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
 
import javax.imageio.ImageIO;

public class Client{
	public static void main(String[] args) throws Exception{
		String command = args[0];
		//String fileName = args[1];

		Socket connectionToServer = new Socket("localhost", 80);

		// I/O operations

		InputStream in = connectionToServer.getInputStream();
		OutputStream out = connectionToServer.getOutputStream();

		BufferedReader headerReader = new BufferedReader(new InputStreamReader(in));
		BufferedWriter headerWriter = new BufferedWriter(new OutputStreamWriter(out));
		DataInputStream dataIn = new DataInputStream(in);
		DataOutputStream dataOut = new DataOutputStream(out);

		if(command.equals("OV")){
			String header = "OV" +"\n";
			headerWriter.write(header, 0, header.length());
			headerWriter.flush();

			header = headerReader.readLine();
			System.out.println(header);

			if(header.equals("NOT FOUND")){
				System.out.println("We're extremely sorry, the file you specified is not available!");
			}

			

		}else if(command.equals("S")){
			String header = "S" +"\n";
			headerWriter.write(header, 0, header.length());
			headerWriter.flush();

			BufferedImage image = ImageIO.read(connectionToServer.getInputStream());
			File outputfile = new File("C:\\Users\\yassi\\Downloads\\image1.jpg");
            ImageIO.write(image, "jpg", outputfile);
			
			header = headerReader.readLine();
			System.out.println(header);
		}else if(command.equals("r")){
			String header = "r" +"\n";
			headerWriter.write(header, 0, header.length());
			headerWriter.flush();
			
			header = headerReader.readLine();
			System.out.println(header);

			//To do
		}
		connectionToServer.close();
	}
}
