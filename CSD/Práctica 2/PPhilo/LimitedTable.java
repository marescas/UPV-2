// CSD Mar 2013 Juansa Sendra

public class LimitedTable extends RegularTable { //max 4 in dinning-room
    int c = 0;
    public LimitedTable(StateManager state) {super(state);}
    public synchronized void enter(int id) throws InterruptedException {
        while(c > 3){
        state.wenter(id);
        wait();
        }
        c++;
        state.enter(id);
        
    }
    public synchronized void exit(int id)  {
        state.exit(id);
        c--;
        notifyAll();
    
    }
}
