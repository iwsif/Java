import java.util.*;
import java.net.*;
import java.io.*;
import java.security.*;


public class server {
    public static void main(String[] args) {
        System.out.println("Welcome to my chat program");
        Scanner scanner = new Scanner(System.in);
        String first_message = "Welcome type in your name:";
        String second_message = "Type in your surname:";
        try 
        {
            ServerSocket server = new ServerSocket(10000);
            Socket socket = new Socket();
            System.out.println("Listening on port 10000!!");
                
            socket = server.accept();
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            out.writeUTF(first_message);
            String name = in.readUTF();
            out.writeUTF(second_message);
            String surname =  in.readUTF();
            User user = new User(name,surname);
            System.out.println(user.get_full_name() + " connected!!");
            User_code code = new User_code();
            code.generate_code();
            String users_code = code.hash_to_string();
            code.insert_new_user(user);
            out.writeUTF("Your auth code is " + users_code);
            while(true)
                {
                    System.out.println(user.get_full_name() + " says" + ":");
                    System.out.println(in.readUTF() + "\n"); 

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

    User(String in_name,String in_surname) {
        name = in_name;
        surname = in_surname;
    }
    
    String get_full_name() {
        return name + " " + surname;
    }
}

class User_code  {
    Hashtable users_table = new Hashtable();
    SecureRandom random = new SecureRandom();
    private String code;
    private Integer hash;

    public void insert_new_user(User new_user) {
        users_table.put(new_user,code);
    }

    public void generate_code() {
        random.generateSeed(1);
        hash = random.hashCode();
    }
    
    public String hash_to_string() {
        code = hash.toString();
        return code;
    }

    public boolean auth_user(String code) {
        if (users_table.contains(code)) 
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
}
