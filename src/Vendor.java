import java.util.concurrent.TimeUnit;

public class Vendor implements Runnable {

    private TicketPool ticketPool;
    private Configuration configuration;
    private int VendorID;

    public Vendor(Configuration configuration, TicketPool ticketPool, int VendorID){
        this.ticketPool=ticketPool;
        this.configuration=configuration;
        this.VendorID=VendorID;
    }

    @Override
    public void run() {
        int Total_Tickets = configuration.getTotal_No_Tickets();
        int Release_Rate = configuration.getTicket_Release_Rate();
        int i=1;
        boolean StopThread = false;
        while (!StopThread) {
            while(i<=Total_Tickets){
                try{
                    ticketPool.addTicket(i,VendorID);
                    i++;
                    Thread.sleep(Release_Rate);
                    if (configuration.getTotal_No_Tickets()==0){
                        StopThread = true;
                    }
                }
                catch (InterruptedException e) {
                    System.out.println("Error occured");
                }
            }
        }

    }
}
