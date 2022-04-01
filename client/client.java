import java.io.*;
import java.net.*;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        try{
            System.out.println("Enter IP of Server: ");
            String ip;
            Scanner sc = new Scanner(System.in);
            ip = sc.next();
            
            Socket socket = new Socket(ip,6666);
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
            dout.writeUTF("Hello Server");  
            dout.flush();

            DataInputStream din = new DataInputStream(socket.getInputStream());
            String filename = din.readUTF();
            long filesize = din.readLong();

            int bytes = 0;

            // byte[] buffer = new byte[40*1024];
            // bytes = din.read(buffer,0,buffer.length);
            // FileOutputStream fileOutputStream = new FileOutputStream(filename);
            // fileOutputStream.write(buffer,0,bytes);

            long size = filesize;
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            byte[] buffer = new byte[4*1024];
            while (size > 0 && (bytes = din.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
                fileOutputStream.write(buffer,0,bytes);
                size -= bytes;
            }

            sc.close();
            fileOutputStream.close();
            dout.close();
            socket.close();


            System.out.println("File received: " + filename);
            System.out.println("File size: " + ((int)((filesize/1024.0)*100))/100.0 + " KB");

        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
}
