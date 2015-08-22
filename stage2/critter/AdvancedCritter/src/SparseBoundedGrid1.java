import java.util.Map;
import java.util.ArrayList;
import info.gridworld.grid.*;
import java.util.LinkedHashMap;
/**
 * <code>AbstractGrid</code> contains the methods that are common to grid
 * implementations. <br />
 * The implementation of this class is testable on the AP CS AB exam.
 */
public class SparseBoundedGrid1<E> extends AbstractGrid<E>
{
    /**
    * A data struction for storing the object and col.
    */
    private Map<Location, E> map;
    // The number of cols
    private int colNum;
    // The number of rows
    private int rowNum;

    public SparseBoundedGrid1(int r, int c) {
        colNum = c;
        rowNum = r;
        map = new LinkedHashMap<Location, E>();
    }
    // 获取行数
    public int getNumRows() {
        return rowNum;
    }

    // 获取列数
    public int getNumCols() {
        return colNum;
    }

    // 判断是否有效
    public boolean isValid(Location loc) {
        return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                && 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }

    // 根据location获取Actor
    public E get(Location loc) {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }
        return map.get(loc);
    }

    // 加入Actor
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
        map.put(loc, obj);
        return oldOccupant;
    }

    // 除掉Actor
    public E remove(Location loc) {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }
        
        // Remove the object from the grid.
        E obj = get(loc);
        map.remove(loc);
        return obj;
    }

    public ArrayList<Location> getOccupiedLocations() {
        ArrayList<Location> locList = new ArrayList<Location>();
        for (Location loc : map.keySet()) {
            locList.add(loc);
        }
        return locList;
    }

    // 这个函数供AdvancedCritter的getData函数调用
    public Map<Location, E> getMap() {
        return map;
    }
}
