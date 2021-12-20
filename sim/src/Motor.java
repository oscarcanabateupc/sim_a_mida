import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Motor {
	private static Motor instance;
	private double simTime;
	private SimState simState;
	private ArrayList<Event> eventList;
	private Actor[] actorQueue;
	public Motor()
	{
		eventList = new ArrayList<Event>();
		actorQueue = new Actor[] {};
		simState = new SimState();
	}
	public static Motor getInstance()
	{
		if (instance == null)
		{
			instance = new Motor();
			
		}
		return instance;
	}
	
	public void Run()
	{
		float firstEventTime = simState.eventPool.get(0).simTime;
		eventList.add(new Event("SIMULATIONSTART",firstEventTime, 0));
        Event event;
		//Executa la simulació
	}
	
	public void SimulationStart()
	{
		
	}
	
	public void SimulationEnd()
	{
		
	}
}