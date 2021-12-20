import java.util.*;

public class Orquestrator {
    int currtime;
    int maxtime;
    SimState simState;
    Queue<Actor> actors;
    List<Actor> processingActors = new ArrayList<>();
    private Random fRandom;

    public Orquestrator(int currtime, int maxtime, SimState simState,long seed) {
        this.currtime = currtime;
        this.maxtime = maxtime;
        this.simState = simState;
        fRandom = new Random(seed);
    }

    public void setUp(Integer numActors,Event[] events){
        //aqui se rellenan tambien las estaciones
        generateActorsInterval(events);
        generateStations();
    }

    private void generateStations() {
    	Station cut1 = new Station("cut chair 1", 30, simState);
    	Station cut2 = new Station("cut chair 2", 30, simState);  
    	Station cut3 = new Station("cut chair 3", 30, simState);  
    	
    	Station wash1 = new Station("wash chair 1", 10, simState);  
    	Station wash2 = new Station("wash chair 2", 10, simState);
    	
    	Station wait1 = new Station("wash chair 1", 50, simState);  
    	Station wait2 = new Station("wash chair 2", 50, simState);
    	Station wait3 = new Station("wash chair 1", 50, simState);  
    	Station wait4 = new Station("wash chair 2", 50, simState);
    	
    	simState.waitingStations.add(cut1);
    	simState.waitingStations.add(cut2);
    	simState.waitingStations.add(cut3);
    	
    	simState.waitingStations.add(wash1);
    	simState.waitingStations.add(wash2);
    	
    	simState.waitingStations.add(wait1);
    	simState.waitingStations.add(wait2);
    	simState.waitingStations.add(wait3);
    	simState.waitingStations.add(wait4);

	}

	public void generateActorsInterval(Event[] events) {
        for(Integer i = 0; i <= maxtime; i += 20){
           
            Actor a = new Actor("Actor" + i.toString() ,events,simState,i);
            actors.add(a);
        }
        for(Actor a: simState.waiting){
            System.out.println(Float.toString(a.spawnTime) + " " + a.remainingEvents.toString() + " " + a.simState.toString() + " " + a.name);
        }
    }
    /*
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
	*/
    public void runSim(){
    	while (simState.simTime != 720)
    	{
    		simState.simTime = currtime;
            spawnDueActors();
            sendToProcess();
            sendToSink();
            currtime = Math.max(simState.eventPool.get(0).simTime, actors.peek().spawnTime);
    	}
    	
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
