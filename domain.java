

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.*;
import java.net.*;
import java.io.*;


public class domain {
    public static void main(String[] args) {
        System.out.println("Welcome this is a program for extracting information from html pages");
        
        System.out.println("Type the domain name you want to search for...");
        Scanner scanner = new Scanner(System.in);
        String domain_name = scanner.nextLine();
        
        String []domain = new String[domain_name.length()]; 
        String domain_part1 = "";
        String domain_part2 = "";
        String new_domain = "";
        if (!domain_name.contains("https://") && domain_name.contains("www."))
        {
            domain_name = "https://" + domain_name;
            
            System.out.println("Domain_name:" + domain_name);
            System.out.println("Continue:");
            System.out.println("Type yes or no:");
            Scanner scanner2 = new Scanner(System.in);

            String answer2 = scanner2.nextLine();

            if (!answer2.equals("yes") || answer2.equals("no"))
            {
                System.out.println("Error you should type yes or no..");
                System.out.println("Exiting...");
                System.exit(0);
            }
            else if (answer2 .equals("yes"))
            {
                System.out.println("Starting...");
                Start_connection con = new Start_connection();
                con.get_domain(domain_name);
                con.start_connection(); 
                StringBuilder value = new StringBuilder();
                value = con.build_string();
                
                List <String> list = new ArrayList <String>();
                list = con.extract_links(value);
                                
                con.search_list(domain_name,list);
            }
            else 
            {
                System.out.println("Exiting...");
                System.exit(0);
            }
        }    

        else if (domain_name.contains("https://") && !domain_name.contains("www."))
        {
            domain = domain_name.split("//");
            for (int i=0;i<domain.length;i++)
            {
                domain_part1 = domain[0];
                domain_part2 = domain[1];
            }
            new_domain = domain_part1 + "//" + "www." + domain_part2;
        
            System.out.println("Domain_name " +new_domain);
            System.out.println("Continue?");
            System.out.println("Type yes or no:");

            String user_answer = scanner.nextLine();
                
            if (user_answer != "yes" || user_answer != "no")
            {
                System.out.println("Type yes or no...");
                
                System.out.println("Exiting..");
                System.exit(0);
            }
            else if (user_answer.contains("yes"))
            {
                Start_connection con = new Start_connection();

                con.get_domain(new_domain);
                con.start_connection();
                StringBuilder builder = new StringBuilder();
                builder = con.build_string();
                System.out.println(builder);
            }
            else
            {
                System.out.println("Exiting..");
                System.exit(0);
            }
        }
        else {
            System.out.println("Error reading the domain_name...");
            System.exit(0);
        }
    }
}

class Start_connection{
    private String domain_name;
    private int data;
    private List <Character> list = new ArrayList<Character>();
    private Pattern pattern;
    private String values;
    private List <String> list2 = new ArrayList<String>();
    private List <String> link_list = new ArrayList<String>();
    private String values2;
    private String values3;
    private Pattern pattern2;

    void get_domain(String domain) {
        domain_name  = domain;
    }

    void start_connection() {
        try
        {
            URL url = new URL(domain_name);
            URLConnection  con = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        

            while((data = reader.read()) != -1)
            {
                list.add((char)data);      
            }
    
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    StringBuilder build_string() {
        StringBuilder string =  new StringBuilder();
        for (int i=0;i<list.size();i++)
        {
            string.append(list.get(i));
        }
        return string;
    }

    List extract_links(StringBuilder string)
    {

        pattern = pattern.compile("(?:href=\")(.*?)\""); 
        String content = string.toString();
        Matcher matcher = pattern.matcher(content);
        Boolean result  = matcher.matches();
        String link;

        while(matcher.find())
        {
            values = matcher.group(1);
            list2.add(values);
        }
        return list2;
    }
    List search_list(String base_url,List list) {
        Pattern pattern = null;
        pattern = pattern.compile("(?:" + base_url + ")" + "(.*)");
        Matcher match = pattern.matcher(list.toString());
        
        List <String> list2 = new ArrayList<String>();

        while(match.find())
        {
            list2.add(match.group(0).toString());
        }
        
        return list2;
    }   
}