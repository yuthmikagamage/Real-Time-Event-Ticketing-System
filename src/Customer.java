import java.util.concurrent.TimeUnit;

public class Customer implements Runnable{

    private TicketPool ticketPool;
    private Configuration configuration;
    private int CustomerID;

    public Customer(TicketPool ticketPool,Configuration configuration, int CustomerID){
        this.configuration=configuration;
        this.ticketPool=ticketPool;
        this.CustomerID = CustomerID;
    }

    @Override
    public void run() {
        int CustomerTicketCount = configuration.getCustomer_Ticket();
        int CustomerRetreivalRate = configuration.getCustomer_Retreival_rate();
        try {
            int i=1;
            while(i<=CustomerTicketCount) {
                ticketPool.retreiveTicket(CustomerID);
                Thread.sleep(CustomerRetreivalRate);
                i++;
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}