
package org.kodluyoruz;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Customer implements Runnable{

    private final Event event;
    
    public Customer (Event event){
    
        this.event = event;
    }
    
    public void makeOrder() throws InterruptedException{
    
        synchronized (event){
        
            event.notifyAll();
            
            System.out.println("Musteri siparis vermeye basliyor.. ");
            Thread.sleep(100);
            
            event.siparisVer(event.orderId);
            System.out.println("Musteri siparislerini vermeyi bitirdi.. ");
            while(event.isOrderReceived == false)
                event.wait();
            event.siparisiniTeslimAl();
            System.out.println("Musteri restorandan ayrıldı.. ");
        }
    }
    

    @Override
    public void run() {
        try { 
            makeOrder();
        } catch (InterruptedException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
