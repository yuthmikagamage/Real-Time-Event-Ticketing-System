import java.util.*;

public class TicketPool {

    private List<String> ticketList;
    private Configuration configuration;

    public TicketPool(Configuration configuration){
        this.configuration=configuration;
        this.ticketList = new ArrayList<>();
    }

    public synchronized void addTicket(int ticket , int VendorID){
        int capacityOFTicketPool = configuration.getMaximum_Ticket_Capacity();
        try{
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

    public synchronized void retreiveTicket(int CustID){
        try {
            while (ticketList.size() <= 0) {
                wait();
                System.out.println("No tickets available. Customer is waiting!");
            }
            ticketList.removeFirst();
            System.out.println("Ticket purchased by Customer - " + CustID);
            notifyAll();
        }catch (InterruptedException e){
            System.out.println("Error");
        }
    }
}
