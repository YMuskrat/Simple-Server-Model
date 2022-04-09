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

public class Server {

	public static void main (String[] args) throws Exception {

		ServerSocket ss = new ServerSocket(80);
		while(true){
			System.out.println("Server waiting...");
			Socket connectionFromClient = ss.accept();
			System.out.println("Server got a connection from a client whose port is: " + connectionFromClient.getPort());
			
			try{
				
				InputStream in = connectionFromClient.getInputStream();
				OutputStream out = connectionFromClient.getOutputStream();
                
				String errorMessage = "NOT FOUND\n";

				BufferedReader headerReader = new BufferedReader(new InputStreamReader(in));
				BufferedWriter headerWriter = new BufferedWriter(new OutputStreamWriter(out));

				String header = headerReader.readLine();
				StringTokenizer strk = new StringTokenizer(header);
				
				String command = strk.nextToken();
				
				if(command.equals("OV")) {
					try{
						String os = System.getProperty("os.name");
						header = os +"\n";
						headerWriter.write(header, 0, header.length());
						headerWriter.flush();
					}catch(Exception ex) {
						headerWriter.write(errorMessage, 0, errorMessage.length());
						headerWriter.flush();

					}finally {
						connectionFromClient.close();
					}
				}else if(command.equals("S")) {

					Robot robot = new Robot();
					Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                    BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
					ImageIO.write(screenFullImage, "PNG", connectionFromClient.getOutputStream());
					header ="Screenshot taken" +"\n";
                    headerWriter.write(header, 0, header.length());
					headerWriter.flush();
				}
				
				else if(command.equals("r")) {
					
					Runtime.getRuntime().exec("shutdown -r -t 15");
					header =" Succefful System reboot" +"\n";
                    headerWriter.write(header, 0, header.length());
					headerWriter.flush();
				}
				
				else {

					System.out.println("Connection got from an incompatible client");

				}
			}catch(Exception e) {
			}

		}
	}
}

