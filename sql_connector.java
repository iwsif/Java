import java.util.*;
import java.sql.*;
import java.util.concurrent.TimeUnit;




public class sql_connector {
    public static void main(String[] args) {
        try
        {
            System.out.println("Starting...");
            TimeUnit.SECONDS.sleep(1);

            Scanner scanner = new Scanner(System.in);

            System.out.print("Username:");
            String username = scanner.nextLine();
            
            System.out.print("Password:");
            String password = scanner.nextLine();
        
            System.out.print("Database_name:");
            String database_name = scanner.nextLine();

            if (username != "" && password != "" && database_name != "")
            {
                System.out.println("Connecting..");
                TimeUnit.SECONDS.sleep(1);

                String url = "jdbc:mysql://localhost:3306/" + database_name;
                Connection connection = DriverManager.getConnection(url,username,password);
                
                if (connection != null )
                {
                    System.out.println("Connected!!");
                    TimeUnit.SECONDS.sleep(1);

                    
                    for (int i=0;i<1;)    
                    {       
                        System.out.println("Type 1 to create a table");
                        System.out.println("Type 2 to insert values to table");
                        System.out.println("Type 3 to select from table");
                        System.out.println("Type 4 to drop table");
                        System.out.println("Type 5 to quit");
                        System.out.print("Choose:");
                        String answer = scanner.nextLine();

                        if (!answer.equals(""))
                        {
                            if (answer.equals("1"))
                            {
                                System.out.print("Table_name:");
                                String table_name = scanner.nextLine();
                                if (!table_name.equals(""))
                                {
                                    String to_insert = "";
                                    int counter = 0;
                                    
                                
                                    while(true)
                                    {
                                        System.out.print("Field and datatype to insert:");
                                        String field = scanner.nextLine();
                                        counter = counter + 1;

                                        System.out.print("Continue:");
                                        String to_continue = scanner.nextLine();

                                        if (to_continue.equals("no") && counter == 1)
                                        {
                                            to_insert = to_insert + field;
                                            break; 
                                        }
                                        else if (to_continue.equals("yes") && counter == 1)
                                        {
                                            if (to_insert.equals(""))
                                            {
                                                to_insert = to_insert + field;
                                            }
                                            else 
                                            {           
                                                to_insert = to_insert + "," + field; 
                                            }
                                            
                                        }
                                        else if (to_continue.equals("no") && counter > 1)
                                        {
                                            to_insert = to_insert + "," + field;
                                            break;                                                
                                        }
                                            
                                    }
                                    System.out.println("Executing query...");

                                    TimeUnit.SECONDS.sleep(1);
                                        
                                    String query = "CREATE TABLE " + table_name + "(" + to_insert + ")" + ";";
                                    PreparedStatement statement = connection.prepareStatement(query);                                        
                                    if (statement.executeUpdate() == 0 )
                                    {
                                        System.out.println("Created!!");
                                        TimeUnit.SECONDS.sleep(1);
                                        continue;
                                    }
                                    else
                                    {
                                        System.out.println("Failed to create table...");
                                        TimeUnit.SECONDS.sleep(1);
                                        continue;    
                                    }

                                }
                                else
                                {
                                    System.out.println("I need name for the table");
                                    TimeUnit.SECONDS.sleep(1);
                                    System.out.println("Returning to main menu..");
                                    TimeUnit.SECONDS.sleep(1);
                                    continue;
                                }
                            }
                            else if  (answer.equals("2"))
                            {
                                String val_to_insert = "";
                                System.out.println("Insert:");
                                int second_counter = 0;
                                int count = 0;
                                String in = "";

                                for (i=0;i<1;) 
                                {
                                    System.out.print("Type the fields that you want to insert value:");
                                    String tmp_in = scanner.nextLine();
                                    count = count + 1;
                                    System.out.print("Continue:");
                                    String an = scanner.nextLine();
                                    if (!an.equals(""))
                                    {
                                        if (an.equals("no") && count == 1)
                                        {
                                            in = in + tmp_in;
                                            break;
                                        }
                                        else if (an.equals("yes"))
                                        {
                                            if (in.equals(""))
                                            {
                                                in = in + tmp_in;
                                                continue;
                                            } 
                                            else
                                            {
                                                in = in + "," + tmp_in;
                                                continue;
                                            }
                                        }
                                        else if (an.equals("no") && count > 1)
                                        {
                                            in = in + "," + tmp_in;
                                            break;
                                        }
                                    }
                                    else
                                    {
                                        System.out.println("Answer yes or no");
                                        TimeUnit.SECONDS.sleep(1);
                                        continue;
                                    }
                                        
                                 }

                                for (i=0;i<1;)
                                {
                                    System.out.print("Value to enter:");
                                    String val = scanner.nextLine();
                                    second_counter = second_counter + 1;
                                    if (val != "")
                                    {
                                        System.out.print("Continue:");
                                        String user_answer = scanner.nextLine();
                                        if (user_answer.contains("no") && second_counter == 1)
                                        {
                                            val_to_insert = val_to_insert + '"' + val + '"';
                                            break;
                                        }
                                        else if (user_answer.contains("yes"))
                                        {
                                            if (val_to_insert.equals(""))
                                            {
                                                val_to_insert = val_to_insert + '"' + val + '"';
                                                
                                            }
                                            else
                                            {
                                                val_to_insert = '"' + val_to_insert +  '"' + "," + '"' + val + '"';
                                                
                                            }
                                        }
                                        else if (user_answer.equals("no") && second_counter > 1)
                                        {
                                            val_to_insert =  val_to_insert  + "," + '"' + val + '"';
                                            break;
                                        }
                                    }
                                }
                                System.out.print("Table name:");
                                String tb_name = scanner.nextLine();
                                        
    
                                if (!tb_name.equals(""))
                                {
                                        String insert_to_table = "insert into " + tb_name + "(" +  in  + ")" + "VALUES" + "(" + val_to_insert  + ")" +  ";" ;
                                        
                                        TimeUnit.SECONDS.sleep(2);
                                        PreparedStatement st = connection.prepareStatement(insert_to_table);
                                        if (st.executeUpdate() == 0)
                                        {
                                            System.out.println("Done!!");
                                            TimeUnit.SECONDS.sleep(1);
                                            continue;
                                        }
                                               
                                }
                            }
                            else if (answer.equals("3"))
                            {
                                    System.out.println("Executing select statement...");
                                    TimeUnit.SECONDS.sleep(1);
                                    System.out.print("Table name:");
                                    String name_for_table = scanner.nextLine();
                                    System.out.print("Fields and datatypes:");
                                    String user_input = "";
                                    int fourth_counter = 0;
                                    for (i=0;i<1;)
                                    {

                                        String input = scanner.nextLine();
                                        fourth_counter = fourth_counter + 1;
                                        System.out.print("Continue:");
                                        String _an_ = scanner.nextLine();
                                        if (_an_.equals("no") && fourth_counter == 1)
                                        {
                                            user_input = user_input + input;
                                            break;
                                        } 
                                        else if (_an_.equals("yes"))
                                        {
                                            if (!user_input.equals(""))
                                            {
                                                user_input = user_input + " " + input;
                                                continue;
                                            }
                                            else
                                            {
                                                user_input = user_input + input;
                                            }
                                        }
                                        else if (_an_.equals("no") && fourth_counter > 1)
                                        {
                                            user_input = user_input + " " + input;
                                            break;
                                        }

                                    }
                                    Statement select_statement = connection.createStatement();
                                    String select = "SELECT * FROM " + name_for_table;
                                    ResultSet result = select_statement.executeQuery(select);
                                    for (i=0;i<1;) 
                                    {
                                        if (result.next())
                                        {
                                            if (user_input.toLowerCase().contains("int"))
                                            {
                                                if (user_input.contains(" "))
                                                {
                                                    String new_input = user_input.replaceAll("i.*", "");
                                                    String inp2 = new_input.strip();
                                                    System.out.println(result.getInt(inp2));
                                                    TimeUnit.SECONDS.sleep(3);
                                                    System.out.println();
                                                }
                                        
                                            }
                                            else if (user_input.toLowerCase().contains("string"))
                                            {
                                                if (user_input.contains(" "))
                                                {
                                                    String new_input2 = user_input.replaceAll("s.*","");
                                                    String inp = new_input2.strip();
                                                
                                                    System.out.println(result.getString(inp));
                                                    TimeUnit.SECONDS.sleep(3);
                                                    System.out.println();
                                                    
                                                }
                                            }
                                        }
                                        else
                                        {
                                            break;
                                        }
                                    }
                            }
                            else if (answer.contains("4"))
                            {
                                    System.out.print("Table name:");
                                    String drop_this = scanner.nextLine();

                                    if (!drop_this.equals(""))
                                    {
                                        String drop_query = "DROP TABLE " + drop_this;
                                        PreparedStatement drop = connection.prepareStatement(drop_query);
                                        drop.executeUpdate();
                                        
                                        TimeUnit.SECONDS.sleep(1);

                                        continue;
                                    }
                            }
                            else if (answer.contains("5"))
                            {
                                System.out.println("Exiting...");
                                TimeUnit.SECONDS.sleep(1);
                                System.exit(0);
                            }
                        } 
                    }
                }
                else 
                {
                    System.out.println("Fail to connect!!");
                    System.out.println("Exiting...");
                    TimeUnit.SECONDS.sleep(1);
                    System.exit(0);
                }
            }        
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
    }
}