import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;

import info.gridworld.grid.*;
/**
 * <code>AbstractGrid</code> contains the methods that are common to grid
 * implementations. <br />
 * The implementation of this class is testable on the AP CS AB exam.
 */
public class SparseBoundedGrid<E> extends AbstractGrid<E>
{
    /**
    * A data struction for storing the object and col.
    */
    private class OccupantInCol
    {
        private Object occupant;
        private int col;
        public OccupantInCol(Object obj, int c) {
            col = c;
            occupant = obj;
        }
        public int getCol() {
            return col;
        }
        public void setCol(int c) {
            col = c;
        }
        public Object getObj() {
            return occupant;
        }
        public void setObj(Object obj) {
            occupant = obj;
        }
    };
    // The number of cols
    private int colNum;
    // The number of rows
    private int rowNum;
    // The ArrayList for saving every row's OccupantInCol as a LinkedList
    private ArrayList<LinkedList<OccupantInCol>> occupantArray;

    public SparseBoundedGrid(int r, int c) {
        colNum = c;
        rowNum = r;
        occupantArray = new ArrayList<LinkedList<OccupantInCol>>();
        for (int i = 0 ; i < r ; i++) {
            occupantArray.add(new LinkedList<OccupantInCol>());
        }
    }

    public int getNumRows() {
        return rowNum;
    }

    public int getNumCols() {
        return colNum;
    }

    public boolean isValid(Location loc) {
        return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                && 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }

    public E get(Location loc) {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        LinkedList<OccupantInCol> linkedList = occupantArray.get(loc.getRow());
        if (linkedList != null) {
            for (OccupantInCol occ : linkedList) {
                if (occ.getCol() == loc.getCol()) {
                    return  (E)(occ.getObj());
                }
            } 
        }
        return null;
    }

    public E put(Location loc, E obj) {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }
        if (obj == null) {
            throw new NullPointerException("obj == null");
        }

        // Add the object to the grid.
        E oldOccupant = get(loc);
        remove(loc);
        LinkedList<OccupantInCol> linkedList = occupantArray.get(loc.getRow());
        linkedList.add(new OccupantInCol(obj, loc.getCol()));
        return oldOccupant;
    }

    public E remove(Location loc) {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        
        // Remove the object from the grid.
        E obj = get(loc);
        if (obj == null) {
            return null;
        }
        LinkedList<OccupantInCol> linkedList = occupantArray.get(loc.getRow());
        Iterator<OccupantInCol> itr = linkedList.iterator();
        while (itr.hasNext()) {
            if (itr.next().getCol() == loc.getCol()) {
                itr.remove();
                break;
            }
        }
        return obj;
    }

    public ArrayList<Location> getOccupiedLocations() {
        ArrayList<Location> locList = new ArrayList<Location>();
        for (int i = 0 ; i < rowNum ; i++) {
            LinkedList<OccupantInCol> linkedList = occupantArray.get(i);
            for (OccupantInCol occ : linkedList) {
                locList.add(new Location(i, occ.getCol()));
            }
        }
        return locList;
    }
}
