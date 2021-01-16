package org.kodluyoruz;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Event {
    
    boolean isGetOrderFromCustomer = true;
    boolean isOrderTaken = true;
    boolean isOrderReady = true;
    boolean isOrderReceived = true;
    
    int orderId;
    static int counter = 0;
    
    public Event(){
                
    }
    
    public void runSimulation() {
        System.out.println("Simulasyon Basliyor...");
        
        Customer c = new Customer(this);
        Chef ch = new Chef(this);
        Waiter w = new Waiter(this);
        Thread t1 = new Thread(c);
        Thread t2 = new Thread(w);
        Thread t3 = new Thread(ch);

        t1.start();
        t2.start();
        t3.start();
        //System.out.println("Simulasyon Bitti...");
    }
    
    //waiter operation
    
    synchronized int musteridenSiparisAl(){
        
        while(!isGetOrderFromCustomer){
        
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        isGetOrderFromCustomer = false;
        notifyAll();
        
        System.out.println("Musteriden siparis alindi : " + orderId);
        
        return orderId;

    }
    
    synchronized void siparisiSefeVer(int id){
    
        while(isOrderTaken){
        
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        isOrderTaken = true;
        this.orderId = id;
        
        notifyAll();
        
        System.out.println("Ascıya siparis verildi " + id);
    
    }
    
    synchronized int hazırlananSiparisiGarsonaVer(){
        
        while(!isOrderReady){
        
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        isOrderReady = false;
        
        notifyAll();
        System.out.println("Hazirlanan siparisler garsona teslim edildi " + orderId);
        
        return orderId;

    }
    
    synchronized void siparisiMüsteriyeVer(int id){
        
        while(!isOrderReceived){
        
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        isOrderReceived = true;
        this.orderId = id;
        
        notifyAll();
        
        System.out.println("Siparis musteriye verildi " + id);        

    }
    
    //chef operation
    
    synchronized int siparisiAsciyaVer(){
        
        while(!isOrderTaken){
        
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
            isOrderTaken = false;            
            notifyAll();
            
            System.out.println("Siparis garsondan alındı " + orderId);
            
            return orderId;
        }

    synchronized void siparisTamalandi(int id){
        
        while(!isOrderReady){
        
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
            isOrderReady = true;
            this.orderId = id;
            
            notifyAll();
            
            System.out.println("Asci siparisi verdi #" + id);
     
    }
    
    //customer operations
    synchronized void siparisVer(int id) {
        
        while(!isGetOrderFromCustomer){
        
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        isGetOrderFromCustomer = true;
        this.orderId = id;
        
        notifyAll();
        
        System.out.println("Musteri siparis verdi " + id);

        
    }
    
        synchronized int siparisiniTeslimAl() {
        while (!isOrderReceived) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isOrderReceived = false;
        notifyAll();
        System.out.println("Musteri siparisini aldi " + orderId);
        return orderId;
    }
    

    

    
   
}
