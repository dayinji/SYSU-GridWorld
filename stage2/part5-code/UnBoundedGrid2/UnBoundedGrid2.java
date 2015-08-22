/* 
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2002-2006 College Entrance Examination Board 
 * (http://www.collegeboard.com).
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
 * @author Alyce Brady
 * @author APCS Development Committee
 * @author Cay Horstmann
 */

import java.util.ArrayList;

import java.util.*;

import info.gridworld.actor.*;
import info.gridworld.grid.*;

/**
 * An <code>UnboundedGrid</code> is a rectangular grid with an unbounded number of rows and
 * columns. <br />
 * The implementation of this class is testable on the AP CS AB exam.
 */
public class UnBoundedGrid2<E> extends AbstractGrid<E>
{
    private Object[][] occupantArray;
    private int size;

    /**
     * Constructs an empty unbounded grid.
     */
    public UnBoundedGrid2()
    {
        size = 16;
        occupantArray = new Object[size][size];
    }

    public int getNumRows()
    {
        return -1;
    }

    public int getNumCols()
    {
        return -1;
    }

    public boolean isValid(Location loc)
    {
        return loc.getRow() >= 0 && loc.getCol() >= 0;
    }

    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> a = new ArrayList<Location>();
        for (int i = 0 ; i < size ; i++) {
            for (int j = 0 ; j < size ; j++) {
                Location loc = new Location(i, j);
                if (get(loc) != null) {
                    a.add(loc);
                }
            }
        }
        return a;
    }

    public E get(Location loc)
    {
        if (loc == null) {
            throw new NullPointerException("loc == null");
        }
        if (!isValid(loc)) {
            System.out.println(loc.getCol() + " " + loc.getRow());
            throw new IllegalArgumentException("Illegal Location");
        }
        if (loc.getCol() >= size || loc.getRow() >= size) {
            return null;
        }
        if (occupantArray[loc.getRow()][loc.getCol()] == null) {
            return null;
        }
        return (E)occupantArray[loc.getRow()][loc.getCol()];
    }

    public E put(Location loc, E obj)
    {
        if (loc == null) {
            throw new NullPointerException("loc == null");
        }
        if (obj == null) {
            throw new NullPointerException("obj == null");
        }
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Illegal Loction");
        }
        while (loc.getRow() >= size || loc.getCol() >= size) {
            extend();
        }
        occupantArray[loc.getRow()][loc.getCol()] = obj;
        return (E)occupantArray[loc.getRow()][loc.getCol()];
    }

    public E remove(Location loc)
    {
        if (loc == null)
            throw new NullPointerException("loc == null");
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Illegal Loction");
        }
        if (loc.getCol() >= size || loc.getRow() >= size ) {
            return null;
        }
        if (occupantArray[loc.getRow()][loc.getCol()] == null)
            return null;
        E a = (E)occupantArray[loc.getRow()][ loc.getCol()];
        occupantArray[loc.getRow()][loc.getCol()] = null;
        return a;
    }
    private void extend() {
        int originSize = size;
        int newSize = 2*originSize;
        Object[][] newArray = new Object[newSize][newSize];
        for (int i = 0 ; i < originSize ; i++) {
            for (int j = 0 ; j < originSize ; j++) {
                if (occupantArray[i][j] != null) {
                    newArray[i][j] = occupantArray[i][j];
                }
            }
        }
        size = newSize;
        occupantArray =  newArray;
    }
}
