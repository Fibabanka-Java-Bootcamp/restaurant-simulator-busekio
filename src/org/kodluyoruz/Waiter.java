
package org.kodluyoruz;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Waiter implements Runnable {
    
    private final Event event;
    
    public Waiter(Event event){
    
        this.event = event;
    }
    
    public void makeServe() throws InterruptedException{
    
        synchronized(event){
        
            event.notifyAll();
            while(event.isGetOrderFromCustomer == false)
                event.wait();
            
            System.out.println("Garson servise basliyor.. ");
            
            Thread.sleep(100);            
            event.siparisiSefeVer(event.musteridenSiparisAl());//düzenle
            
            while(event.isOrderReady == false)
                event.wait();
            event.siparisiMüsteriyeVer(event.hazırlananSiparisiGarsonaVer());
            
            System.out.println("Garson servisi bitirdi.. ");
            
        }
    }

    @Override
    public void run() {
        try {
            makeServe();
        } catch (InterruptedException ex) {
            Logger.getLogger(Waiter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

 