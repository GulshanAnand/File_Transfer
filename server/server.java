import java.net.InetAddress;
import java.io.*;
import java.net.*;

public class server {
    public static void main(String[] args) {
        try{
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("Server IP address: " + localhost.getHostAddress());

            ServerSocket ss = new ServerSocket(6666);
            System.out.println("Server is listening on port 6666...");
            Socket socket = ss.accept();


            DataInputStream din = new DataInputStream(socket.getInputStream());
            String str = din.readUTF();  
            System.out.println("Message = " + str);
            
            String filename = "myfile.pdf";
            File file = new File(filename);
            FileInputStream fileInputStream = new FileInputStream(file);
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
            dout.writeUTF(filename);
            dout.writeLong(file.length());
            dout.flush();

            int bytes = 0;

            // byte[] buffer = new byte[40*1024];
            // bytes = fileInputStream.read(buffer,0,buffer.length);
            // dout.write(buffer,0,bytes);


            byte[] buffer = new byte[4*1024];
            while((bytes = fileInputStream.read(buffer)) != -1){
                dout.write(buffer,0,bytes);
                dout.flush();
            }
            
            


            fileInputStream.close();
            din.close();
            ss.close();

        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
