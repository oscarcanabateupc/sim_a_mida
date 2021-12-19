import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class Orquestrator {
    float currtime;
    int maxtime;
    SimState simState;
    List<Actor> actors;
    private Random fRandom;

    public Orquestrator(float currtime, int maxtime, SimState simState,long seed) {
        this.currtime = currtime;
        this.maxtime = maxtime;
        this.simState = simState;
        fRandom = new Random(seed);
    }

    public void setUp(){}

    public void generateActors(Integer numActors,Event[] events){
        //llenamos la lista de actores mediante una distribucion o algo y luego se ordenan por spawntime
        List<Actor> Actors = new ArrayList<>();
        for(int i = 0; i < numActors; ++i){
            Float rn = fRandom.nextFloat(0,maxtime);
            Actor a = new Actor("Actor" + rn.toString() ,events,simState,rn);
            Actors.add(a);
        }
        System.out.println(Actors);
        for(Actor a: Actors){
            System.out.println(Float.toString(a.spawnTime) + " " + a.remainingEvents.toString() + " " + a.simState.toString() + " " + a.name);
        }
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


    private Random generator = new Random();
    double randomGenerator() {
        return generator.nextDouble()*0.5;
    }
}
