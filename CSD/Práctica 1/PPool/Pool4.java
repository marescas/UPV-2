// CSD feb 2013 Juansa Sendra

public class Pool4 extends Pool { //kids cannot enter if there are instructors waiting to exit
    int ki, cap, numKids = 0, numInstructor = 0, waitingInstructor = 0;
  
    public void init(int ki, int cap){
        this.ki = ki;
        this.cap = cap;
    }
     public synchronized void kidSwims() throws InterruptedException{
         
        while(numInstructor==0 || ((numKids/numInstructor)+1)>ki || cap==0 || waitingInstructor > 0){
        log.waitingToSwim();
        wait();
        }
        numKids++;
        cap--;
        log.swimming();
        
        
    }
    public  synchronized void kidRests()      {log.resting(); numKids--;  cap++; notifyAll();}
    public synchronized void instructorSwims() throws InterruptedException  {
        while(cap==0){
            log.waitingToSwim();
            wait();
        }
          log.swimming(); 
          numInstructor++;
          cap--;
          notifyAll(); 
        }
    public  synchronized void instructorRests() throws InterruptedException  {
        
        
        while(numInstructor==1 && numKids > 0 || (((numInstructor-1)*ki)<=numKids && numKids > 0)){
            waitingInstructor++;
            log.waitingToRest();
           
            wait();
            waitingInstructor--;
        }
        
        numInstructor--;
        log.resting(); 
        cap++;
        notifyAll(); 
    }
}
