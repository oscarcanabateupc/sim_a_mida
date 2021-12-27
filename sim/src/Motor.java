import java.util.*;

public class Motor {
    int currtime;
    int maxtime;
    SimState simState;
    Queue<Actor> actors;
    Queue<Actor> operators;
    List<Actor> processingActors = new ArrayList<>();
    private Random fRandom;

    public Motor(int currtime, int maxtime, long seed) {
        this.currtime = currtime;
        this.maxtime = maxtime;
        actors = new LinkedList<Actor>();
        operators = new LinkedList<Actor>();
        this.simState = new SimState();
        fRandom = new Random(seed);
    }

    public void setUp(){
    	List<Event> startingEvents = new ArrayList<>();
    	generateStations();
        //aqui se rellenan tambien las estaciones
        generateActorsInterval(startingEvents);
        generateOperatorsInterval(startingEvents);
        runSim();
    }

    private void generateStations() {
    	Station cut1 = new Station("cut1", 30, simState);
    	Station cut2 = new Station("cut2", 30, simState);  
    	Station cut3 = new Station("cut3", 30, simState);  
    	
    	Station wash1 = new Station("wash1", 10, simState);  
    	Station wash2 = new Station("wash2", 10, simState);
    	
    	Station wait1 = new Station("wait1", 50, simState);  
    	Station wait2 = new Station("wait2", 50, simState);
    	Station wait3 = new Station("wait3", 50, simState);  
    	Station wait4 = new Station("wait4", 50, simState);
    	
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

	public void generateActorsInterval(List<Event> events) {
        for(Integer i = 0; i <= maxtime; i += 20){
           
            Actor a = new Actor("Actor" + i.toString() ,events,simState,i);
            actors.add(a);
        }
        for(Actor a: actors){
            System.out.println(Float.toString(a.spawnTime) + " " + a.remainingEvents.toString() + " " + a.simState.toString() + " " + a.name);
        }
    }


    public void generateOperatorsInterval(List<Event> events) {
        for(Integer i = 0; i <= maxtime; i += 240){

            Actor a = new Actor("Operator" + i.toString() ,events,simState,i);
            operators.add(a);
        }
        for(Actor a: operators){
            System.out.println(Float.toString(a.spawnTime) + " " + a.remainingEvents.toString() + " " + a.simState.toString() + " " + a.name);
        }
    }

    public void runSim(){
    	while (simState.simTime != 720)
    	{
    		simState.simTime = currtime;
            spawnDueActors();
            sendToSink();
            //sendToWash();
            //sendToProcess();
            sendToWait();
            
            if(simState.eventPool.size() > 0 && !actors.isEmpty())
            {
            currtime = Math.min(simState.eventPool.get(0).simTime, actors.peek().spawnTime);
            if (Math.min(simState.eventPool.get(0).simTime, actors.peek().spawnTime) == simState.eventPool.get(0).simTime)simState.eventPool.remove(0);
            }
            else if (!actors.isEmpty()) currtime = actors.peek().spawnTime;
            else break;
        }
    	
        //send due events?
    };

    public void spawnDueActors(){
    	Queue<Actor> actor = new LinkedList<Actor>();
    	actor.addAll(actors);
        for(Actor a : actors){
            if (a.spawnTime == currtime){
                simState.waiting.add(a);
                actor.remove(a);
            }
        }
        actors.clear();
        actors.addAll(actor);
        Queue<Actor> operator = new LinkedList<Actor>();
    	operator.addAll(operators);
        for(Actor a : operators){
            if (a.spawnTime == currtime){
                simState.waitingOperators.add(a);
                operator.remove(a);
            }
        }
        operators.clear();
        operators.addAll(operator);
    }

    public void sendToWait(){
        List<Actor> waitingAux = new ArrayList<>();
        waitingAux.addAll(simState.waiting);
        for (Actor a: simState.waiting) {
            if (a.remainingEvents.size() == 0) {
                if (waitingAux.contains(a)) waitingAux.remove(a);
                simState.waitingForACut.add(a);
                if (!simState.waitingStations.isEmpty())
                {
                    for (Station s: simState.waitingStations)
                    {
                        if ((s.name.equals("wait1") || s.name.equals("wait2") || s.name.equals("wait3") || s.name.equals("wait4")))
                        {
                            Event e = s.send_event("WAIT");
                            e.printTimestamp();
                            simState.addEvent(e);
                            simState.waitingStations.remove(s);
                            s.client = a;
                            break;
                        }
                    }
                }
            }
        }
        simState.waiting.clear();
        simState.waiting.addAll(waitingAux);
    }

    public void sendToProcess(){
    	List<Actor> waitingAux = new ArrayList<>();
    	waitingAux.addAll(simState.waitingForACut);
        for (Actor a: simState.waitingForACut) {
            if (a.remainingEvents.size() == 0) {
                if (waitingAux.contains(a)) waitingAux.remove(a);
                simState.processing.add(a); 
                if (!simState.waitingStations.isEmpty())
                {
                	for (Station s: simState.waitingStations)
                	{
                		if (!simState.waitingOperators.isEmpty() && (s.name.equals("cut1") || s.name.equals("cut2") || s.name.equals("cut3")))
                		{
                			Event e = s.send_event("CUT");
                			a.remainingEvents.clear();
                			a.remainingEvents.add(e);
                			e.printTimestamp();
                			simState.addEvent(e);               			
                			simState.processingCutStations.add(s);
                			simState.waitingStations.remove(s);
                            s.client = a;
                            s.operator = simState.waitingOperators.get(0);
                            simState.waitingOperators.remove(0);

                			break;
                		}                		
                	}
                }
            }
        }
        simState.waitingForACut.clear();
        simState.waitingForACut.addAll(waitingAux);
    }
    
    public void sendToWash()
    {
    	List<Actor> processingAux = new ArrayList<>();
    	processingAux.addAll(simState.processing);
        for (Actor a: simState.processing) {
            if (a.remainingEvents.size() == 0) {
                if (processingAux.contains(a)) processingAux.remove(a);
                simState.washing.add(a); 
                if (!simState.processingCutStations.isEmpty())
                {
                	
                	for (Station s: simState.waitingStations)
                	{
                		if (!simState.waitingOperators.isEmpty() && (s.name.equals("wash1") || s.name.equals("wash2") || s.name.equals("wash3") || s.name.equals("wash4")))
                		{
                			Event e = s.send_event("WASH");
                			e.printTimestamp();
                			simState.addEvent(e);               			
                			simState.processingWashStations.add(s);
                			simState.processingCutStations.remove(s);
                            s.client = a;
                            s.operator = simState.waitingOperators.get(0);
                            simState.waitingOperators.remove(0);

                			break;
                		}                		
                	}
                }
            }
        }
        simState.processing.clear();
        simState.processing.addAll(processingAux);
    }
    public void sendToSink(){
        for (Actor a: simState.waiting) {
            if (a.remainingEvents.size() == 0 && currtime - a.spawnTime >= 50) 
            {
                if (simState.waiting.contains(a)) simState.waiting.remove(a);
                if (simState.processing.contains(a))  simState.processing.remove(a);
                simState.sink.add(a);
            }
        }
    };
}
