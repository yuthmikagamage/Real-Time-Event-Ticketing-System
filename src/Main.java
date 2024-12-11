import java.beans.Customizer;
import java.util.*;
public class Main {
    //User Choise as a Number
    private static int Choise;
    public static void main(String[] args) {
        //Displaying the Menu to User
        boolean CorrectInput = false;
        while (!CorrectInput){
            System.out.println("------------------------------------------------------");
            System.out.println("--                      Menu                        --");
            System.out.println("------------------------------------------------------");
            System.out.println("1) Start New Event  and Continue");
            System.out.println("2) Load Configurations and Continue ");
            System.out.println("------------------------------------------------------");
            System.out.println();
            boolean choose_excep=false;
            while(!choose_excep){
                try {
                    Scanner user_choise=new Scanner(System.in);
                    System.out.print("Please select an option: ");
                    Choise=user_choise.nextInt();
                    choose_excep=true;
                }
                catch (InputMismatchException e){
                    System.out.println("Choose from above menu numbers");
                }
            }

            switch (Choise) {
                case 1:
                    System.out.println("Enter Configuration Settings and Continue");
                    System.out.println();
                    UserInputConfigurations();
                    CorrectInput=true;
                    break;
                case 2:
                    System.out.println("Loading Existing Configuration in JSON File");
                    System.out.println();
                    SystemLoadConfigurationFile();
                    CorrectInput=true;
                    break;
                default:
                    System.out.println("Invalid Input Enter a Number Again According Your Need");
                    break;
            }
        }
    }

    // Method to get configuration input from user when user choose configure settings
    public static void UserInputConfigurations(){
        //Getting Correct Inputs by Calling the validation method
        int total_No_Tickets = getCorrectInputs("Enter the Total Number of Tickets : ");
        int ticket_Release_Rate = getCorrectInputs("Enter the Release Rate of Tickets : ");
        int customer_Retreival_rate = getCorrectInputs("Enter the Customer Retrieval rate of Tickets : ");

        int maximum_Ticket_Capacity = getCorrectInputs("Enter the Maximum Capacity of Tickets : ");
        //Checking Maximum Ticket Capacity is Less than Total Number of Tickets
        while (maximum_Ticket_Capacity>total_No_Tickets){
            System.out.println("Maximun Ticket Capacity Need to be Less than Total Tickets");
            maximum_Ticket_Capacity = getCorrectInputs("Enter the Maximum Capacity of Tickets : ");
        }

        int Customer_Ticket = getCorrectInputs("Enter the ticket capacity you want to buy : ");
        //Checking the Customer Ticket Count is Less than Or Equal Total Number of Tickets
        while (Customer_Ticket>total_No_Tickets) {
            System.out.println("Customer ticket capacity cannot be higher than total no of tickets!");
            Customer_Ticket = getCorrectInputs("Enter the ticket capacity you want to buy : ");
        }

        //Creating object with Configuration Class
        Configuration Configuration_Object = new Configuration();
        //Passing the User Entered Values
        Configuration_Object.setTotal_No_Tickets(total_No_Tickets);
        Configuration_Object.setTicket_Release_Rate(ticket_Release_Rate);
        Configuration_Object.setCustomer_Retreival_rate(customer_Retreival_rate);
        Configuration_Object.setMaximum_Ticket_Capacity(maximum_Ticket_Capacity);
        Configuration_Object.setCustomer_Ticket(Customer_Ticket);

        //Save Configuration Object to JSON File
        Configuration_Object.saveToJson("config.json");


        //Creating object with TicketPool Class
        TicketPool Ticket_Object = new TicketPool(Configuration_Object);

        //Creating object with Vendor Class
        Vendor newvendor = new Vendor(Configuration_Object,Ticket_Object);
        //Vendor Thread
        Thread VendorThread = new Thread(newvendor);


        //Creating object with Customer Class
        Customer newCustomer = new Customer(Ticket_Object,Configuration_Object);
        //Customer Thread
        Thread CustomerThread = new Thread(newCustomer);

        Customer newCustomer2 = new Customer(Ticket_Object,Configuration_Object);
        //Customer Thread
        Thread SecondCustomerThread = new Thread(newCustomer2);

        //Creating second object with Vendor class as second vendor
        Vendor newvendor2 = new Vendor(Configuration_Object,Ticket_Object);
        //Second vendors' Thread
        Thread SecondVendorThread = new Thread(newvendor2);

        //Starting Threads
        VendorThread.start();
        SecondVendorThread.start();
        CustomerThread.start();
        SecondCustomerThread.start();

        //System.out.println("After retreiving available tickets are : " + object.getTotal_No_Tickets());
    }

    //Method for Getting the inputs of Total Number of Tickets , Ticket Release Rate, Customer Retrieval Rate
    //Maximum Ticket Capacity, and Customers' Purchasing ticket count
    private static int AddedValue;
    public static int getCorrectInputs(String InputPrompt){
        boolean CorrectInput = false;
        while (!CorrectInput){
            try {
                Scanner UserInput = new Scanner(System.in);
                System.out.print(InputPrompt);
                AddedValue = UserInput.nextInt();
                if (AddedValue>0){
                    CorrectInput = true;
                }else {
                    System.out.println("Enter a positive number!");
                }
            }
            catch (InputMismatchException e){
                System.out.println("Enter the Value as Integer, Only Integers can add");
            }
        }
        return AddedValue;
    }

    public static void SystemLoadConfigurationFile(){
        // Creating object with Configuration Class
        Configuration Configuration_Object = new Configuration();
        // Loading the configurations using load method
        Configuration newConfigurationObject = Configuration_Object.loadConfiguration("config.json");
        int  Loaded_Total_Ticket=0; int Loaded_Ticket_Release_Rate=0;int Loaded_Customer_Retreival_Rate=0;int Loaded_Maximum_Ticket=0;

        try {
            // Setting loaded values to variables
            Loaded_Total_Ticket = newConfigurationObject.getTotal_No_Tickets();
            Loaded_Ticket_Release_Rate = newConfigurationObject.getTicket_Release_Rate();
            Loaded_Customer_Retreival_Rate = newConfigurationObject.getCustomer_Retreival_rate();
            Loaded_Maximum_Ticket = newConfigurationObject.getMaximum_Ticket_Capacity();

        }catch (NullPointerException e ){
            System.out.println("Error Occured Loading... Input Configurations to Continue");
            UserInputConfigurations();
        }

        //
        if (newConfigurationObject != null) {
            System.out.println("------------------------------------------------------");;
            System.out.println("--         Configuration Load Successfully          --");
            System.out.println("------------------------------------------------------");;
            System.out.println("Total Number of Tickets: " + newConfigurationObject.getTotal_No_Tickets());
            System.out.println("Ticket Release Rate: " + newConfigurationObject.getTicket_Release_Rate());
            System.out.println("Customer Retrieval Rate: " + newConfigurationObject.getCustomer_Retreival_rate());
            System.out.println("Maximum Ticket Capacity: " + newConfigurationObject.getMaximum_Ticket_Capacity());
            System.out.println("------------------------------------------------------");;
            System.out.println();
            // Getting Customers input how many tickets need to buy
            int Customer_Ticket = getCorrectInputs("Enter the ticket capacity you want to buy : ");
            System.out.println();
            //Checking the Customer Ticket Count is Less than Or Equal Total Number of Tickets
            while (Customer_Ticket>Loaded_Total_Ticket) {
                System.out.println("Customer ticket capacity cannot be higher than total no of tickets!");
                Customer_Ticket = getCorrectInputs("Enter the ticket capacity you want to buy : ");
                System.out.println();
            }

            // Setting the loaded values and again and setting the inputted customer ticket count
            Configuration_Object.setTotal_No_Tickets(Loaded_Total_Ticket);
            Configuration_Object.setTicket_Release_Rate(Loaded_Ticket_Release_Rate);
            Configuration_Object.setCustomer_Retreival_rate(Loaded_Customer_Retreival_Rate);
            Configuration_Object.setMaximum_Ticket_Capacity(Loaded_Maximum_Ticket);
            Configuration_Object.setCustomer_Ticket(Customer_Ticket);

            //Creating object with TicketPool Class
            TicketPool Ticket_Object = new TicketPool(Configuration_Object);

            //Creating object with Vendor Class
            Vendor newvendor = new Vendor(Configuration_Object,Ticket_Object);
            //First Vendor Thread
            Thread VendorThread = new Thread(newvendor);

            Vendor newvendor2 = new Vendor(Configuration_Object,Ticket_Object);
            //Second Vendors Thread
            Thread SecondVendorThread = new Thread(newvendor2);

            //Creating object with Customer Class
            Customer newCustomer = new Customer(Ticket_Object,Configuration_Object);
            //First Customer Thread
            Thread CustomerThread = new Thread(newCustomer);

            Customer secondCustomer= new Customer(Ticket_Object,Configuration_Object);
            //Second Customers Thread
            Thread SecondCustomerThread = new Thread(secondCustomer);

            //Starting Customer Threads and Vendor Threads
            VendorThread.start();
            SecondVendorThread.start();
            CustomerThread.start();
            SecondCustomerThread.start();
        }
    }
}