import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Rock;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Bug;
import info.gridworld.grid.*;
/**
* This class runs a world with additional grid choices.
*/
public class SparseGridRunner
{
  public static void main(String[] args)
  {
    SparseBoundedGrid<Actor> grid = new SparseBoundedGrid<Actor>(20, 20);
    ActorWorld world = new ActorWorld(grid);
    world.addGridClass("SparseBoundedGrid");
    /*world.addGridClass("SparseGrid2");
    world.addGridClass("SparseGrid3");
    world.addGridClass("UnboundedGrid2");*/
    world.add(new Location(2, 2), new Bug());
    world.show();
  }
}