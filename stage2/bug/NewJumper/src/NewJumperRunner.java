/* 
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2005-2006 Cay S. Horstmann (http://horstmann.com)
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * @author Cay Horstmann
 */

import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.*;
import info.gridworld.actor.*;

import java.awt.Color;
/**
 * This class runs a world that contains a bug and a rock, added at random
 * locations. Click on empty locations to add additional actors. Click on
 * populated locations to invoke methods on their occupants. <br />
 * To build your own worlds, define your own actors and a runner class. See the
 * BoxBugRunner (in the boxBug folder) for an example. <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class NewJumperRunner
{
    public static void main(String[] args)
    {
        SparseBoundedGrid<Actor> grid = new SparseBoundedGrid(60, 100);
        ActorWorld world = new ActorWorld(grid);
        for (int i = 0 ; i < 250 ; i++) {
            world.add(new NewJumper(0, 0));
            world.add(new NewJumper(1, 0));
        }
        for (int i = 0 ; i < 10 ; i++) {
            world.add(new Bug());
        }
        for (int i = 0 ; i < 15 ; i++) {
            world.add(new Rock());
        }
        world.add( new Location(0, 5), new Rock());
        world.add(new Location(0, 20), new Rock() );
        world.add(new Location(20, 0), new Rock() );
        world.add(new Location(30, 0),new Rock() );
        world.show();
    }
}
