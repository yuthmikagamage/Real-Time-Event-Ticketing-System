import java.util.*;

public class TicketPool {

    private List<String> ticketList;
    private Configuration configuration;

    public TicketPool(Configuration configuration){
        this.configuration=configuration;
        this.ticketList = new ArrayList<>();
    }

    public synchronized void addTicket(int ticket){
        int capacityOFTicketPool = configuration.getMaximum_Ticket_Capacity();
        try{
            String threadName = Thread.currentThread().getName();
            String VendorID = threadName.replaceAll("\\D", "");
            while (ticketList.size() >= capacityOFTicketPool) {
                System.out.println("Pool is full and Vendor " + VendorID +" is Waiting to Add Tickets! ");
                wait();
            }
            if (configuration.getTotal_No_Tickets()>0){
                ticketList.add(String.valueOf(ticket));
                configuration.setTotal_No_Tickets(configuration.getTotal_No_Tickets()-1);
                System.out.println("Vendor ID - " + VendorID + " | Successfully Added Ticket! Remaining Total Tickets = " + configuration.getTotal_No_Tickets());
                notifyAll();
            }

        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public synchronized void retreiveTicket(){
        try {
            while (ticketList.size() <= 0) {
                wait();
                System.out.println("No tickets available. Customer is waiting!");
            }
            ticketList.removeFirst();
            //Getting the name of the thread and removing white spacing and displaying the thread number
            String threadName = Thread.currentThread().getName();
            String customerId = threadName.replaceAll("\\D", "");
            System.out.println("Ticket purchased by Customer - " + customerId);
            notifyAll();
        }catch (InterruptedException e){
            System.out.println("Error");
        }
    }
}
