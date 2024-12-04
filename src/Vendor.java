import java.util.concurrent.TimeUnit;

public class Vendor implements Runnable {

    private TicketPool ticketPool;
    private Configuration configuration;

    public Vendor(Configuration configuration, TicketPool ticketPool){
        this.ticketPool=ticketPool;
        this.configuration=configuration;
    }

    @Override
    public void run() {
        int Total_Tickets = configuration.getTotal_No_Tickets();
        int Release_Rate = configuration.getTicket_Release_Rate();
        int i=1;
        while(i<=Total_Tickets){
            try{
                ticketPool.addTicket(i);
                i++;
                Thread.sleep(Release_Rate);
            }
            catch (InterruptedException e) {
                System.out.println("Error occured");
            }
        }
    }
}
