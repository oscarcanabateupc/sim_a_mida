import java.util.*;

public class Main {
    public static void main(String[] args) {
        
    	SimState s = new SimState();
        Orquestrator o = new Orquestrator(0,720,s,0);
        List<Event> e = new ArrayList<>();
        o.setUp();
    }
}
