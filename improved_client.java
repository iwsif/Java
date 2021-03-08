import java.util.*;
import java.net.*;
import java.io.*;



public class client {
    public static void main(String[] args) {
    try
    {    
        System.out.println("Welcome");
        System.out.println("Opening sockets...");
        System.out.println("Starting connection...");
        
        //Thread thread = Thread.currentThread();

        Socket socket = new Socket("localhost",10000);

        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        
        String question1 = in.readUTF();
        System.out.println(question1);    
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        out.writeUTF(name);

        String question2 = in.readUTF();
        System.out.println(question2);
        String surname = scanner.nextLine();
        out.writeUTF(surname);

        String question3 = in.readUTF();
        System.out.println(question3);
        String gender = scanner.nextLine();
        out.writeUTF(gender);

        String ask_username = in.readUTF();
        System.out.println(ask_username);
        String username = scanner.nextLine();
        out.writeUTF(username);

        String ask_password = in.readUTF();
        System.out.println(ask_password);
        String password = scanner.nextLine();
        out.writeUTF(password);

        String key = in.readUTF();
        System.out.println(key);

        String mymessage;

        while(true)
        {
            mymessage = scanner.nextLine();
            if (mymessage == "quit")
            {
                scanner.close();
                out.flush();
                socket.close();
                break;
            }
            out.writeUTF(mymessage);
        }

    }
    catch(Exception e)
    {
        System.out.println(e);
    }  
        
    }
}
