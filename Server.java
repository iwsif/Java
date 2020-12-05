import java.net.*;
import java.util.*;
import java.io.*;
import java.security.*;
import javax.crypto.*;

public class Server {
    public static void main(String[] args) {
        try
        {
            System.out.println("Welcome this is a chat program");
            System.out.println("Starting..");
        
            Scanner scanner = new Scanner(System.in);
            System.out.println("Type the port that you want to start the server...");
            int port = scanner.nextInt();

            System.out.println("Server is starting...");
            
            ServerSocket server = new ServerSocket(port);
            Socket socket = new Socket();
            socket = server.accept();

            System.out.println(String.format("Server is listening on %d",port));
            
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            
            //Thread thread = Thread.currentThread();
            out.writeUTF("Type your name:");
            String name = in.readUTF();
            //thread.sleep(5000);
            
            out.writeUTF("Type your surname:");
            String surname = in.readUTF();
            //thread.sleep(5000);

            out.writeUTF("Type your gender:"); 
            String gender = in.readUTF();           
            //thread.sleep(5000);

            User first_user = new User(name,surname,gender);
            first_user.get_inputStream(in);
            first_user.get_outputStream(out);
            first_user.create_account(first_user);
            first_user.create_key();
            first_user.encrypt();
            Key key= first_user.get_the_key();
            out.writeUTF("Use this to decrypt the data " + key.toString());
            //thread.sleep(5000);

            first_user.create_entry(first_user);
            Mythread mythread = new Mythread();
            mythread.get_input_stream(socket);
            mythread.get_the_user(first_user);
            mythread.start_thread();

            while(true)
            {
                mythread.run();
            }
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}

class User {
    private String name;
    private String surname;
    private String gender;
    private String username;
    private String password;
    DataOutputStream out;
    DataInputStream in;
    Integer code;
    Hashtable table = new Hashtable();

    Key mykey;

    User(String in_name,String in_surname,String in_gender) {
        name = in_name;
        surname = in_surname;
        gender = in_gender;
    }
    public String get_name(User user) {
        return name;
    }

    public void get_outputStream(DataOutputStream send_message) {
        out = send_message;
    }
    public void get_inputStream(DataInputStream read_message) {
        in = read_message;
    }
    public void create_account(User user) {
        try
        {
            out.writeUTF("Enter a username:");
            username = in.readUTF();
            out.writeUTF("Enter a password:");
            password = in.readUTF();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public void create_entry(User user) {
        SecureRandom random = new SecureRandom();
        Integer code = random.hashCode();
        table.put(user,code);
    }
    
    public boolean user_exists(User user) {
        if(table.get(user)!= null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void create_key()  {        
        try
        {
            SecureRandom random = new SecureRandom();
       
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(random);
            Key key = keygen.generateKey();
            mykey = key;
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public Key get_the_key() {
        return mykey;
    }
    
    public void encrypt() {
        byte[] name = username.getBytes();
        byte[] pass = password.getBytes();

        try
        {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE,mykey);
            cipher.update(name); 
            cipher.update(pass);
        } 
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}

class Mythread implements Runnable {
    private String user_message;
    private Boolean quit = false;
    User user;
    DataInputStream in;
    DataOutputStream out;
    Thread thread = new Thread();

    public void get_input_stream(Socket socket) {
        try
        {
            in = new DataInputStream(socket.getInputStream());
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public void start_thread() {
        thread.start();
    }

    public void get_the_user(User in_user) {
        user = in_user;
    }
    public void run() {
        try
        {
            user_message = in.readUTF();
            System.out.println("User " + user.get_name(user) + "says:" + user_message);
            if(!user.user_exists(user))
            {
                System.out.println("Violetion detected by " + user.get_name(user));
            }
            if (user_message == "quit")
            {
                quit = true;
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public boolean quit() {
        return quit;
    }

}
