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
 * @author Chris Nevison
 * @author Barbara Cloud Wells
 */

import info.gridworld.actor.*;
import info.gridworld.grid.*;
import java.awt.Color;

/**
 * A <code>BoxBug</code> traces out a square "box" of a given size. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class LoveBug extends Bug
{
    private int row;
    private Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.PINK};
    private int[][] col = {
        {2, 3, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 8}, {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
        {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},{0, 1, 2, 3, 4, 5, 6, 7, 8, 9},{0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
        {1, 2, 3, 4, 5, 6, 7, 8}, {2, 3, 4, 5, 6, 7},  {3, 4, 5, 6}, {4, 5,}
    };

    public void act()
    {
        int c =  (int) (Math.random() * 4);
        for (int i = 0 ; i < col[row].length ; i++) {
            Flower alice = new Flower(colors[c]);
            alice.putSelfInGrid(getGrid(), new Location(row, col[row][i]));
        }
        row++;
        if (row > 9)
            row = 0;
    }
}
