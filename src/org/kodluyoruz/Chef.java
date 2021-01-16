package org.kodluyoruz;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Chef implements Runnable {
    
    private final Event event;
    
    public Chef(Event event){
    
        this.event = event;
    }
    
    public void makeCook() throws InterruptedException{
    
        synchronized(event){
        
            event.notifyAll();
            while(event.isOrderTaken == false)
                event.wait();
            
            System.out.println("Sef yemek yapmaya basladi...");
            
            Thread.sleep(100);            
            event.siparisTamalandi(event.siparisiAsciyaVer());
            System.out.println("Sef yemek yapmayi bitirdi... ");
                     
        }
    }
    


    @Override
    public void run() {
        try {
            makeCook();
        } catch (InterruptedException ex) {
            Logger.getLogger(Waiter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
