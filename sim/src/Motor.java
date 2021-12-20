import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Motor {
	private double simTime;
	private SimState simState;
	private ArrayList<Event> eventList;
	private Actor[] actorQueue;
	private Sink sink;
	public Motor()
	{
		eventList = new ArrayList<Event>();
		actorQueue = new Actor[] {};
		simState = new SimState();
		sink = new Sink();
	}
	
	public void Run()
	{
		float firstEventTime = simState.eventPool.get(0).simTime;
		eventList.add(new Event(this,firstEventTime, 0));
        Event event;
		//Executa la simulació
	}
	
	public void HandleEvent(Event e)
	{
		
	}
	
	public void SimulationEnd()
	{
		
	}
}