import java.awt.*;
import java.util.List;

public class Orquestrator {
    float currtime;
    float maxtime;
    SimState simState;
    List<Actor> actors;

    public Orquestrator(float currtime, float maxtime, SimState simState) {
        this.currtime = currtime;
        this.maxtime = maxtime;
        this.simState = simState;
    }

    public void setUp(){}
    public void generateActors(){
        //llenamos la lista de actores mediante una distribucion o algo y luego se ordenan por spawntime
    };

    public void runSim(){
        float firstEventTime = simState.eventPool.get(0).simTime;
        float firstspawActor = actors.get(0).spawnTime;
        float nextCurrtime;
        if (firstEventTime < firstspawActor) nextCurrtime = firstEventTime;
        else nextCurrtime = firstspawActor;

        //logica de los eventos

    };

    public void spawnActor(){
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
    public void SendToSink(){
        for (Actor a: simState.waiting) {
            if (a.remainingEvents.length == 0) {
                if (simState.waiting.contains(a)) simState.waiting.remove(a);
                if (simState.processing.contains(a))  simState.processing.remove(a);
                simState.sink.add(a);
            }
        }
    };

}
