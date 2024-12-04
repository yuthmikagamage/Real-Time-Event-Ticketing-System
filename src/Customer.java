import java.util.concurrent.TimeUnit;

public class Customer implements Runnable{

    private TicketPool ticketPool;
    private Configuration configuration;

    public Customer(TicketPool ticketPool,Configuration configuration){
        this.configuration=configuration;
        this.ticketPool=ticketPool;
    }

    @Override
    public void run() {
        int CustomerTicketCount = configuration.getCustomer_Ticket();
        int CustomerRetreivalRate = configuration.getCustomer_Retreival_rate();
        try {
            int i=1;
            while(i<=CustomerTicketCount) {
                ticketPool.retreiveTicket();
                Thread.sleep(CustomerRetreivalRate);
                i++;
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}