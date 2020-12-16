package project1.listeners;

import project1.Simulation;
import project1.actors.Animal;
import project1.actors.WorldActor;

public interface MoveObserver {
    void moved(WorldActor actor);
}
