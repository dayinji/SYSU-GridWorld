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
 * @author Chris Nevison
 * @author Barbara Cloud Wells
 * @author Cay Horstmann
 */

import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.*;

import java.util.ArrayList;
import java.awt.Color;

/**
 * A <code>ChameleonCritter</code> takes on the color of neighboring actors as
 * it moves through the grid. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class BlusterCritter extends Critter
{
    private int courage;
    private static final double DARKENING_FACTOR = 0.05;

    // Store the offset of Row and Col
    private int[][]offset = {
        {-2, -2}, {-2, -1}, {-2, 0}, {-2, 1},  {-2, 2},
        {-1, -2}, {-1, -1}, {-1, 0}, {-1, 1},  {-1, 2},
        {0, -2}, {0, -1}, {0, 1},  {0, 2},
        {1, -2}, {1, -1}, {1, 0}, {1, 1},  {1, 2},
        {2, -2}, {2, -1}, {2, 0}, {2, 1},  {2, 2},
    };

    public BlusterCritter(int c) {
        courage = c;
    }
    public ArrayList<Actor> getActors()
    {
        // Get the grid
        Grid<Actor> gr = getGrid();
        // Get the row and col of current location
        int r = getLocation().getRow();
        int c = getLocation().getCol();
        ArrayList<Actor> list = new ArrayList<Actor>();
        // Add the Actors into list
        for (int i = 0 ; i < offset.length ; i++) {
            Location loc =new Location(r+offset[i][0], c+offset[i][1]);
            if (gr.isValid(loc)) {
                Actor a = gr.get(loc);
                if (a != null) {
                    list.add(a);
                }
            }
        }
        return list;
    }
    public void processActors(ArrayList<Actor> actors)
    {
        int count = 0;
        for (Actor a : actors)
        {
            if (a instanceof Critter) {
                count++;
            }
        }
        if (count >= courage) {
            setDarker();
        } else {
            setBrighter();
        }
    }
    // Set a brighter color
    private void setBrighter() {
        Color c = getColor();
        int red = (int) (c.getRed() * (1 + DARKENING_FACTOR));
        int green = (int) (c.getGreen() * (1 + DARKENING_FACTOR));
        int blue = (int) (c.getBlue() * (1 + DARKENING_FACTOR));
        // Prevent red or green or blue illegal
        red = red > 255 ? 255 : red;
        green = green > 255 ? 255 : green;
        blue = blue > 255 ? 255 : blue;
        setColor(new Color(red, green, blue));
    }
    // Set a darker color
    private void setDarker() {
        Color c = getColor();
        int red = (int) (c.getRed() * (1 - DARKENING_FACTOR));
        int green = (int) (c.getGreen() * (1 - DARKENING_FACTOR));
        int blue = (int) (c.getBlue() * (1 - DARKENING_FACTOR));
        setColor(new Color(red, green, blue));
    }
}
