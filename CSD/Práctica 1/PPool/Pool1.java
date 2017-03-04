// CSD feb 2015 Juansa Sendra

public class Pool1 extends Pool {   //no kids alone
    int numInstructor = 0;
    int numKids  = 0;
    public void init(int ki, int cap)           {
        
       
    }
    public synchronized void kidSwims() throws InterruptedException{
        while(numInstructor==0){
        log.waitingToSwim();
        wait();
        }
        numKids++;
        log.swimming();
        notifyAll();
        
    }
    public synchronized void kidRests()      {
        log.resting();
        numKids--; 
        notifyAll();
       }
    public synchronized void instructorSwims()   {log.swimming(); numInstructor++;}
    public synchronized void instructorRests() throws InterruptedException  {
        while(numInstructor==1 && numKids > 0){
            log.waitingToRest();
            wait();
        }
        numInstructor--;
        log.resting(); 
        notifyAll();
    }
}
