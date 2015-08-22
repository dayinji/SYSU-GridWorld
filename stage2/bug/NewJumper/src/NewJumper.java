
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.actor.*;
import java.lang.Math;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class NewJumper extends Actor
{

    private int turnDegree = 0;
    private Actor meetActor = null;
    private int age = 0;
    /**
     * Generation indicates the generation of NewJumper
     */
    private int generation;
    /**
     * gender: 0=male, 1=female; 2=male&gay, 3=female&les
     */
    private int gender;
    private final Color[] genderColor = {Color.BLACK, Color.WHITE, Color.BLUE, Color.RED};

    public NewJumper(int gender, int generation)
    {
        if (gender > 3 || gender < 0) {
            throw new IllegalArgumentException(
                    "Param gender should be 0 ~ 3.");
        }
        this.gender = gender;
        this.generation = generation;
        setColor(genderColor[gender]);
    }

    /**
     * Moves if it can move, turns otherwise.
     */
    public void act()
    {
        age++;
        if (age > 400) {
            removeSelfFromGrid();
            return;
        }
        if (gender == 0) {
            if(canBear()) {
                bear();
                return;
            }
        }
        setRandomDirection();
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
        if (getGrid().get(next2) instanceof Flower)
            age = age - 5 < 0 ? 0 : age - 5;
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
        if (next2Actor instanceof Rock || next2Actor instanceof NewJumper|| next2Actor instanceof Bug) {
            turnDegree = Location.HALF_RIGHT;
            return false;
        }
        meetActor = next2Actor;
        return true;
        // ok to move into empty location or onto flower
        // not ok to move onto any other actor
    }
    private boolean canBear() {
        Grid<Actor> gr = getGrid();
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        if (!gr.isValid(next))
            return false;
        if (gr.get(next) != null)
            return false;
        Location next2 = next.getAdjacentLocation(getDirection());
        if (!gr.isValid(next2))
            return false;
        if (!(gr.get(next2) instanceof NewJumper))
            return false;
        NewJumper b = (NewJumper)gr.get(next2);
        // 如果他们正面相对,则体位正确
        if(Math.abs(b.getDirection() - getDirection()) == 180 && getGender() + b.getGender() == 1) {
            return true;
        } else if (getDirection() - b.getDirection() == 0) {
            if (getGender() == 1 && b.getGender() == 0) {
                return true;
            }
        }
        return false;
    }
    private void bear() {
        NewJumper child = new NewJumper(getRandomGender(), generation+1);
        Location loc = getLocation();
        Location childLoc = loc.getAdjacentLocation(getDirection());
        child.putSelfInGrid(getGrid(), childLoc);

    }
    private int getRandomGender() {
        double randNum = Math.random();
        if (randNum  <= 14.0/30.0) {
            return 0;
        } else if (randNum <= 28.0/30.0) {
            return 1;
        } else if (randNum <= 29.0/30.0) {
            return 2;
        } else {
            return 3;
        }
    }
    private void setRandomDirection() {
        int factor = (int)(Math.random()*8);
        setDirection(45*factor);
    }
    public int getGender() {
        return gender;
    }
    public int getGeneration() {
        return generation;
    }
    public int getAge() {
        return age;
    }

    public String  getdata() {
        SparseBoundedGrid<Actor> gr = (SparseBoundedGrid)getGrid();
        Map<Location, Actor> map = new HashMap<Location, Actor>();
        map = (HashMap<Location, Actor>)gr.getMap();
        int maleCount = 0;
        int femaleCount = 0;
        int maleGayCount = 0;
        int femaleLesCount = 0;
        double allCount = 0;
        HashMap<Integer, Integer>generationCount = new HashMap<Integer, Integer>();
        for (Location loc : map.keySet()) {
            if (gr.get(loc) instanceof NewJumper) {
                allCount++;
                NewJumper temp = (NewJumper)gr.get(loc);
                if (temp.getGender() == 0)
                    maleCount++;
                if (temp.getGender() == 1)
                    femaleCount++;
                if (temp.getGender() == 2)
                    maleGayCount++;
                if (temp.getGender() == 3)
                    femaleLesCount++;
                if (generationCount.containsKey(temp.getGeneration())) {
                    int originCount = generationCount.get(temp.getGeneration());
                    generationCount.put(temp.getGeneration(), originCount + 1);
                } else {
                    generationCount.put(temp.getGeneration(), 1);
                }
            }
        }
        String result = "";
        result += "总的New Jumper数量是 " + allCount + "\n";
        result += "男性数量是 " + maleCount + ",占据总数量的"+Double.toString(maleCount*100/allCount) + "%\n";
        result += "女性数量是 " + femaleCount + ",占据总数量的"+Double.toString(femaleCount*100/allCount) + "%\n";
        result += "男同性恋数量是 " + maleGayCount + ",占据总数量的"+Double.toString(maleGayCount*100/allCount) + "%\n";
        result += "女同性恋数量是 " + femaleLesCount + ",占据总数量的"+Double.toString(femaleLesCount*100/allCount) + "%\n";
        for (Integer g : generationCount.keySet()) {
            int count = generationCount.get(g);
            String rate = Double.toString(count*100/allCount);
            result +="第"+g+"代的NewJumper有"+count+"只, 占据总数量的"+ rate + "%\n";
        }
        return result;
    }
}
