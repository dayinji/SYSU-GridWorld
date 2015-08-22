
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.actor.*;
import java.lang.Math;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class KillerJumper extends Actor
{
    private int killCount;
    /**
     * Constructs a red Jumper.
     */
    public KillerJumper()
    {
        setColor(Color.GREEN);
        killCount = 0;
    }

    /**
     * Moves if it can move, turns otherwise.
     */
    public void act()
    {
        setRandomDirection();
        if (canMove()) {
            move();
            kill();
        } 
    }

    /**
     * Turns the bug 45 degrees to the right without changing its location.
     */
    public void turn()
    {
        setDirection(getDirection() + Location.HALF_RIGHT);
    }

    /**
     * Moves the bug forward, putting a flower into the location it previously
     * occupied.
     */
    public void move()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null) {
            return;
        }
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        Location next2 = next.getAdjacentLocation(getDirection());
        moveTo(next2);
    }

    public boolean canMove()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null) {
            return false;
        }
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        // 如果位置不合法(边界),返回false, 设置turnDegree为Location.RIGHT.
        if (!gr.isValid(next)) {
            return false;
        }
        // 如果actor不是花,石头,或者空的话,返回false, 设置turnDegree为Location.HALF_RIGHT.
        Actor nextActor = gr.get(next);
        if (!(nextActor instanceof Flower || nextActor instanceof Rock || nextActor == null)) {
            return false;
        }

        Location next2 = next.getAdjacentLocation(getDirection());
        // 如果位置不合法(边界),返回false, 设置turnDegree为Location.RIGHT.
        if (!gr.isValid(next2)) {
            return false;
        }
        Actor next2Actor = gr.get(next2);
        // 如果next2的actor是石头或者Jumper的话.返回false;
        if (next2Actor instanceof Rock || next2Actor instanceof NewJumper|| next2Actor instanceof Bug) {
            return false;
        }
        return true;
        // ok to move into empty location or onto flower
        // not ok to move onto any other actor
    }
 
    private void kill() {
        Location loc = getLocation();
         ArrayList<Actor> newJumpers = getGrid().getNeighbors(getLocation());
         for (Actor a : newJumpers) {
            if (a instanceof NewJumper) {
                a.removeSelfFromGrid();
                killCount++;
            }
         }
    }
    private void setRandomDirection() {
        int factor = (int)(Math.random()*8);
        setDirection(45*factor);
    }

    public String  getdata() {
        String result = "";
        result += "杀掉的New Jumper数量是 " + killCount + "\n";

        return result;
    }
}
