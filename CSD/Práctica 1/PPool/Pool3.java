// CSD feb 2015 Juansa Sendra

public class Pool3 extends Pool{ //max capacity
    int ki, cap, numKids = 0, numInstructor = 0;
    public void init(int ki, int cap)           {
        this.ki = ki;
        this.cap = cap;
    }
     public synchronized void kidSwims() throws InterruptedException{
         
        while(numInstructor==0 || ((numKids)+1)>ki*numInstructor || cap==0){
        log.waitingToSwim();
        wait();
        }
        numKids++;
        cap--;
        log.swimming();
        notifyAll();
        
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
          notifyAll(); }
    public  synchronized void instructorRests() throws InterruptedException  {
        
        while(numInstructor==1 && numKids > 0 || (((numInstructor-1)*ki)<=numKids && numKids > 0)){
            log.waitingToRest();
            wait();
        }
        numInstructor--;
        log.resting(); 
        cap++;
        notifyAll(); 
    }
}
