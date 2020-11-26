import java.util.Scanner;
import java.io.*;
import java.net.*;


public class myclient {
    public static void main(String[] args) {
        try
        {  
           Scanner scanner = new Scanner(System.in);
           String name,surname;
           Socket socket = new Socket("localhost",10000);
           DataInputStream in = new DataInputStream(socket.getInputStream());
           DataOutputStream out = new DataOutputStream(socket.getOutputStream());
           String server_message = in.readUTF();
           System.out.println(server_message);
           out.writeUTF(scanner.nextLine());
           String server_message2 = in.readUTF();
           System.out.println(server_message2);
           out.writeUTF(scanner.nextLine());
           System.out.println(in.readUTF());
           while(true)
            {   
                System.out.print(">> ");
                String message = scanner.nextLine();
                out.writeUTF(message);                          
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}