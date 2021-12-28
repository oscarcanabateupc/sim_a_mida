import java.util.*;

import javax.sound.sampled.SourceDataLine;

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
    	generateStations();
        //aqui se rellenan tambien las estaciones
        generateActorsInterval();
        generateOperatorsInterval();
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
    	
    	simState.waitingStations.add(cut1); //0 - 30 //30 - 60
    	simState.waitingStations.add(cut2); //20 - 50 //50 - 80
    	simState.waitingStations.add(cut3); //40 - 70 //70 - 100
    	
    	simState.waitingStations.add(wash1); //30 - 40 //70 - 80 //100 - 110
    	simState.waitingStations.add(wash2); //50 - 60 //60 - 70 //80 - 90
    	
    	simState.waitingStations.add(wait1);
    	simState.waitingStations.add(wait2);
    	simState.waitingStations.add(wait3);
    	simState.waitingStations.add(wait4);

	}

	public void generateActorsInterval() {
        for(Integer i = 0; i <= maxtime; i += 20){
            List<Event> events = new ArrayList<>();
            Actor a = new Actor("Actor" + i.toString() ,events,simState,i);
            actors.add(a);
        }
        for(Actor a: actors){
            System.out.println(Float.toString(a.spawnTime) + " " + a.remainingEvents.toString() + " " + a.simState.toString() + " " + a.name);
        }
    }


    public void generateOperatorsInterval() {
        for(Integer i = 0; i <= maxtime; i += 240){
            List<Event> events = new ArrayList<>();
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
            endEvents();
            sendToWait();
            sendToWash();
            sendToProcess();
            sendToSink();

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

    private void endEvents() {
    	List<Actor> actor = new ArrayList<Actor>();
    	actor.addAll(simState.processing);
		for (Actor a : simState.processing)
		{
			Event e = a.remainingEvents.get(1);
			if (e.simTime <= currtime)
			{
                int index = simState.processingCutStations.indexOf(e.currentStation);
                Station s = simState.processingCutStations.get(index);
                s.clear();
                //simState.processingCutStations.get(index).clear();
                simState.processingCutStations.remove(index);
				simState.waitingStations.add(s);
                e.currentStation.clear();
				actor.remove(a);
                simState.waitingForAWash.add(a);
                simState.waitingOperators.remove(0);
			}
		}
		simState.processing.clear();
		simState.processing.addAll(actor);
	}

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
                if (!simState.waitingStations.isEmpty())
                {
                    for (Station s: simState.waitingStations)
                    {
                        if ((s.name.equals("wait1") || s.name.equals("wait2") || s.name.equals("wait3") || s.name.equals("wait4")))
                        {
                            if (waitingAux.contains(a)) waitingAux.remove(a);
                            simState.waitingForACut.add(a);
                        	a.enterSimTime = currtime;                            
                            simState.waitingStations.remove(s);
                            s.client = a;                            
                            Event e = s.send_event("WAIT");
                            a.remainingEvents.add(e);
                            e.printTimestamp();
                            s.printTimestamp();
                            break;
                        }
                    }                    
                }
            }
        }
        simState.waiting.clear();
        simState.waiting.addAll(waitingAux);
    }

    public void sendToProcess()
    {
    	List<Actor> waitingAux = new ArrayList<>();
    	waitingAux.addAll(simState.waitingForACut);
        for (Actor a: simState.waitingForACut) {
            if (a.remainingEvents.size() == 1) {

                if (!simState.waitingStations.isEmpty())
                {
                	for (Station s: simState.waitingStations)
                	{
                		if (!s.hasOperator && !s.hasClient && !simState.waitingOperators.isEmpty() && (s.name.equals("cut1") || s.name.equals("cut2") || s.name.equals("cut3")))
                		{   
                            simState.processing.add(a); 
                            waitingAux.remove(a);
                			simState.processingCutStations.add(s);
                            s.client = a;
                            s.operator = simState.waitingOperators.get(0);
                			//a.remainingEvents.clear();                			                			
                			simState.waitingStations.remove(s);
                			Event e = s.send_event("CUT");
                			a.remainingEvents.add(e);
                			e.printTimestamp();
                            s.printTimestamp();
                            //simState.waitingOperators.remove(0);
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
    	processingAux.addAll(simState.waitingForAWash);
        for (Actor a: simState.waitingForAWash) {
            if (a.remainingEvents.size() == 2) { //0
                
                if (!simState.processingCutStations.isEmpty())
                {
                	
                	for (Station s: simState.waitingStations)
                	{
                		if (!s.hasOperator && !s.hasClient && !simState.waitingOperators.isEmpty() && (s.name.equals("wash1") || s.name.equals("wash2")))
                		{               			
                            simState.washing.add(a);
                            processingAux.remove(a);
                			simState.processingWashStations.add(s);
                            s.client = a;
                            s.operator = simState.waitingOperators.get(0);
                            //
                            simState.waitingStations.remove(s);
                            Event e = s.send_event("WASH");
                			a.remainingEvents.add(e);
                			e.printTimestamp();
                            s.printTimestamp();
                            //simState.waitingOperators.remove(0);
                			break;
                		}                		
                	}
                }
            }
        }
        simState.waitingForAWash.clear();
        simState.waitingForAWash.addAll(processingAux);
    }

    public void sendToSink(){
        List<Actor> waitingAux = new ArrayList<>();
        waitingAux.addAll(simState.waitingForACut);
        for (Actor a: simState.waitingForACut) 
        {
            if (a.remainingEvents.size() == 0 && currtime - a.spawnTime >= 50) 
            {
                if (waitingAux.contains(a)) waitingAux.remove(a);
                simState.sink.add(a);
            }            
        }        
        simState.waiting.clear();
        simState.waiting.addAll(waitingAux);

        waitingAux = new ArrayList<>();
        waitingAux.addAll(simState.washing);

        for (Actor a: simState.washing) 
        {
            if (a.remainingEvents.size() == 3) 
            {
            	Event e = a.remainingEvents.get(2);                

                int index = simState.processingWashStations.indexOf(e.currentStation);
                Station s = simState.processingWashStations.get(index);
                s.clear();
                //simState.processingCutStations.get(index).clear();
                simState.processingWashStations.remove(index);
				simState.waitingStations.add(s);
                a.remainingEvents.clear();
                simState.sink.add(a);
                simState.waitingOperators.remove(0);
            }
            
        }
        simState.washing.clear();
        simState.washing.addAll(waitingAux);
    };
}
