import java.util.*;

public class Orquestrator {
    int currtime;
    int maxtime;
    SimState simState;
    List<Actor> actors = new ArrayList<>();
    private Random fRandom;

    public Orquestrator(int currtime, int maxtime, SimState simState,long seed) {
        this.currtime = currtime;
        this.maxtime = maxtime;
        this.simState = simState;
        fRandom = new Random(seed);
    }

    public void setUp(Integer numActors,Event[] events){
        //aqui se rellenan tambien las estaciones
        generateActors(numActors,events);
    }

    public void generateActorsInterval(Event[] events) {
        for(Integer i = 0; i <= maxtime; i += 20){
            Float rn = Float.valueOf(i);
            Actor a = new Actor("Actor" + rn.toString() ,events,simState,rn);
            actors.add(a);
        }
        for(Actor a: actors){
            System.out.println(Float.toString(a.spawnTime) + " " + a.remainingEvents.toString() + " " + a.simState.toString() + " " + a.name);
        }
    }

    public void generateActors(Integer numActors,Event[] events){
        for(int i = 0; i < numActors; ++i){
            Integer rn = fRandom.nextInt(0,maxtime);
            Actor a = new Actor("Actor" + rn.toString() ,events,simState,rn);
            actors.add(a);
        }
        Collections.sort(actors);
        System.out.println("Generated actors:");
        for(Actor a: actors){
            System.out.println(Float.toString(a.spawnTime) + " " + a.remainingEvents.toString() + " " + a.simState.toString() + " " + a.name);
        }
    };

    public void runSim(){
        spawnDueActors();
        sendToProcess();
        sendToSink();
        //send due events?
    };

    public void spawnDueActors(){
        for(Actor a : actors){
            if (a.spawnTime == currtime){
                simState.waiting.add(a);
            }
        }
    }

    public void sendToProcess(){
        for (Actor a: simState.waiting) {
            if (a.remainingEvents.length == 0) {
                if (simState.waiting.contains(a)) simState.waiting.remove(a);
                simState.processing.add(a);
            }
        }
    }
    public void sendToSink(){
        for (Actor a: simState.waiting) {
            if (a.remainingEvents.length == 0) {
                if (simState.waiting.contains(a)) simState.waiting.remove(a);
                if (simState.processing.contains(a))  simState.processing.remove(a);
                simState.sink.add(a);
            }
        }
    };
}
