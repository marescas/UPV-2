// CSD feb 2015 Juansa Sendra

public class Pool2 extends Pool{ //max kids/instructor
    int ki, cap, numKids = 0, numInstructor = 0;
    public void init(int ki, int cap)           {
        this.ki = ki;
        this.cap = cap;
    }
     public synchronized void kidSwims() throws InterruptedException{
         
        while(numInstructor==0 || Math.max((numKids/numInstructor)+1, ki)>ki){
        log.waitingToSwim();
        wait();
        }
        numKids++;
        log.swimming();
        notifyAll();
        
    }
    public  synchronized void kidRests()      {log.resting(); numKids--;  notifyAll();}
    public synchronized void instructorSwims()   {log.swimming(); numInstructor++; notifyAll(); }
    public  synchronized void instructorRests() throws InterruptedException  {
        
        while(numInstructor==1 && numKids > 0 || (((numInstructor-1)*ki)<=numKids && numKids > 0)){
            log.waitingToRest();
            wait();
        }
        numInstructor--;
        log.resting(); 
        notifyAll(); }
}
