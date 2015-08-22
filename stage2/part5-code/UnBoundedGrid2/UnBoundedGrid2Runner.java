import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Rock;
import info.gridworld.actor.Flower;
import info.gridworld.grid.*;
/**
* This class runs a world with additional grid choices.
*/
public class UnBoundedGrid2Runner
{
  public static void main(String[] args)
  {
    UnBoundedGrid2<Actor> grid = new UnBoundedGrid2<Actor>();
    ActorWorld world = new ActorWorld(grid);
    world.addGridClass("UnBoundedGrid2");
    world.add(new Location(2, 2), new Critter());
    world.show();
  }
}