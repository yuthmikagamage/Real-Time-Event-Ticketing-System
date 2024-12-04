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
            while (ticketList.size() >= capacityOFTicketPool) {
                System.out.println("Pool is full and Vendor is Waiting to Add Tickets! Vendor ID- " + Thread.currentThread().getName());
                wait();
            }
            ticketList.add(String.valueOf(ticket));
            System.out.println("Ticket added successfully " + ticket);
            configuration.setTotal_No_Tickets(configuration.getTotal_No_Tickets()-1);
            System.out.println(configuration.getTotal_No_Tickets());
            notifyAll();
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
            System.out.println("Ticket purchased by customer");
            notifyAll();
        }catch (InterruptedException e){
            System.out.println("Error");
        }
    }
}
