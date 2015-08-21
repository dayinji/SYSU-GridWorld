
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.actor.*;

import java.awt.Color;

public class Jumper extends Actor
{

    private int turnDegree = 0;
    private Actor meetActor = null;
    /**
     * Constructs a red Jumper.
     */
    public Jumper()
    {
        setColor(Color.RED);
    }

    /**
     * Constructs a bug of a given color.
     * @param bugColor the color for this bug
     */
    public Jumper(Color jumperColor)
    {
        setColor(jumperColor);
    }

    /**
     * Moves if it can move, turns otherwise.
     */
    public void act()
    {
        if (canMove()) {
            if (meetActor instanceof Bug || meetActor instanceof Flower) {
                meetActor.removeSelfFromGrid();
            } else if (meetActor instanceof Actor) {
                Location loc = meetActor.getLocation();
                int d = getLocation().getDirectionToward(loc);
                meetActor.moveTo(loc.getAdjacentLocation(d));
            }
            move();
        } else{
            setDirection(getDirection() + turnDegree);
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
            turnDegree = Location.RIGHT;
            return false;
        }
        // 如果actor不是花,石头,或者空的话,返回false, 设置turnDegree为Location.HALF_RIGHT.
        Actor nextActor = gr.get(next);
        if (!(nextActor instanceof Flower || nextActor instanceof Rock || nextActor == null)) {
            turnDegree = Location.HALF_RIGHT;
            return false;
        }

        Location next2 = next.getAdjacentLocation(getDirection());
        // 如果位置不合法(边界),返回false, 设置turnDegree为Location.RIGHT.
        if (!gr.isValid(next2)) {
            turnDegree = Location.RIGHT;
            return false;
        }
        Actor next2Actor = gr.get(next2);
        // 如果next2的actor是石头或者Jumper的话.返回false;
        if (next2Actor instanceof Rock || next2Actor instanceof Jumper) {
            turnDegree = Location.HALF_RIGHT;
            return false;
        }
        meetActor = next2Actor;
        return true;
        // ok to move into empty location or onto flower
        // not ok to move onto any other actor
    }
}
