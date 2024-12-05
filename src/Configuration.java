import java.io.*;
import com.google.gson.Gson;


public class Configuration {
    private int total_No_Tickets;
    private int ticket_Release_Rate;
    private int customer_Retreival_rate;
    private int maximum_Ticket_Capacity;
    private int Customer_Ticket;

    public Configuration(){
    }

    public int getTotal_No_Tickets() {
        return total_No_Tickets;
    }

    public void setTotal_No_Tickets(int total_No_Tickets) {
        this.total_No_Tickets = total_No_Tickets;
    }

    public int getTicket_Release_Rate() {
        return ticket_Release_Rate;
    }

    public void setTicket_Release_Rate(int ticket_Release_Rate) {
        this.ticket_Release_Rate = ticket_Release_Rate;
    }

    public int getCustomer_Retreival_rate() {
        return customer_Retreival_rate;
    }

    public void setCustomer_Retreival_rate(int customer_Retreival_rate) {
        this.customer_Retreival_rate = customer_Retreival_rate;
    }

    public int getMaximum_Ticket_Capacity() {
        return maximum_Ticket_Capacity;
    }

    public void setMaximum_Ticket_Capacity(int maximum_Ticket_Capacity) {
        this.maximum_Ticket_Capacity = maximum_Ticket_Capacity;
    }

    public int getCustomer_Ticket() {
        return Customer_Ticket;
    }

    public void setCustomer_Ticket(int customer_Ticket) {
        Customer_Ticket = customer_Ticket;
    }

    public void TextFileSaving(String filePath) {
        try (FileWriter writer = new FileWriter(filePath))  {
            writer.write("Total Number of Tickets " + total_No_Tickets + "\n");
            writer.write("Enter the Release Rate of Tickets " + ticket_Release_Rate + "\n");
            writer.write("Enter the Customer Retrieval rate of Tickets " + customer_Retreival_rate + "\n");
            writer.write("Enter the Maximum Capacity of Tickets " + maximum_Ticket_Capacity + "\n");
        } catch (IOException e) {
            System.out.println("Error saving configuration to text file: ");
        }
    }

    // Saving Configuration data to a JSON File
    public void saveToJson(String filePath) {
        //Creating GSON Object imported GSON
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(filePath)) {
            //Converting Configuration Object to JSON and Save
            gson.toJson(this, writer);
            System.out.println("Configuration saved to " + filePath);
        } catch (IOException e) {
            System.out.println("Error saving configuration to JSON file.");
            e.printStackTrace();
        }
    }

    // Load past Configured data from JSON File
    public static Configuration loadConfiguration(String filePath) {
        //Creating GSON Object imported GSON
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            // Deserializing serialised to a object
            return gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            System.out.println("Error loading configuration from JSON file.");
            e.printStackTrace();
            return null;
        }
    }
}