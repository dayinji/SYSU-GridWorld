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

import info.gridworld.actor.*;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A <code>CrabCritter</code> looks at a limited set of neighbors when it eats and moves.
 * <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class AdvancedCritter extends Critter
{
    private static final int MAXAGE = 100;
    private static final int HEALTH = 5;
    private static final double RATE1 = 14.0/30.0;
    private static final double RATE2 = 1.0/30.0;
    private static final int MALE = 0;
    private static final int FEMALE = 1;
    private static final int GAY = 2;
    private static final int LES = 3;
    private int age = 0;
    /**
     * Generation indicates the generation of NewJumper
     */
    private int generation;
    /**
     * gender: 0=male, 1=female; 2=male&gay, 3=female&les
     */
    private int gender;
    /**
     * 性别对应不同的颜色
     */
    private final Color[] genderColor = {Color.BLACK, Color.WHITE, Color.BLUE, Color.RED};

    public AdvancedCritter(int gender, int generation)
    {
        this.gender = gender;
        this.generation = generation;
        setColor(genderColor[gender]);
    }

    public void act()
    {
        if (getGrid() == null) {
            return;
        }
        age++;
        // 判断age是否超过最大,是则死亡
        if (age > MAXAGE) {
            removeSelfFromGrid();
            return;
        }
        if ((gender == 0 || gender == 1) && canBear()) {
            bear();
            return;
        }
        super.act();
    }

    public void processActors(ArrayList<Actor> actors)
    {
        for (Actor a : actors)
        {
            // 吃到花的延年益寿,少5岁
            if (a instanceof Flower) {
                a.removeSelfFromGrid();
                age = age - HEALTH;
            }
        }
    }

    // 移动的时候改变方向
    public void makeMove(Location loc)
    {
        setDirection(getLocation().getDirectionToward(loc));
        super.makeMove(loc);
    }

    // 判断是否可以生孩子
    private boolean canBear() {
        Location loc = getLocation();
        int direction = getDirection();
        Grid<Actor> gr = getGrid();
        // 判断下一个位置是否合法&是否为空
        Location next = loc.getAdjacentLocation(direction);
        if (!gr.isValid(next) || gr.get(next) != null) {
            return false;
        }
        // 判断下下个位置是否合法&是否生物属于AdvancedCritter
        Location next2 = next.getAdjacentLocation(direction);
        if (!gr.isValid(next2) || !(gr.get(next2) instanceof AdvancedCritter)) {
            return false;
        }
        AdvancedCritter ac = (AdvancedCritter)gr.get(next2);
        // 判断体位是否正确
        if (Math.abs(direction - ac.getDirection()) != 180) {
            return false;
        }
        return true;
    }

    private void bear() {
        Location loc = getLocation();
        int direction = getDirection();
        Grid<Actor> gr = getGrid();
        Location next = loc.getAdjacentLocation(direction);
        // 随机出生性别
        AdvancedCritter child = new AdvancedCritter(getRandomGender(), generation + 1);
        child.putSelfInGrid(gr, next);
    }

    // 获取随机性别
    private int getRandomGender() {
        double randNum = Math.random();
        if (randNum  <= RATE1) {
            return MALE;
        } else if (randNum <= 2*RATE1) {
            return FEMALE;
        } else if (randNum <= 2*RATE1 + RATE2) {
            return GAY;
        } else {
            return LES;
        }
    }

    // 获取年龄
    public int getAge() {
        return age;
    }
    // 获取代数
    public int getGeneration() {
        return generation;
    }
    // 获取性别
    public int getGender() {
        return gender;
    }

    // 获取当前全部AdvancedCritter数据
    public String  getdata() {
        SparseBoundedGrid1<Actor> gr = (SparseBoundedGrid1)getGrid();
        //获取自定义网格的存储数据
        Map<Location, Actor> map = new HashMap<Location, Actor>();
        map = (LinkedHashMap<Location, Actor>)gr.getMap();
        int maleCount = 0;
        int femaleCount = 0;
        int maleGayCount = 0;
        int femaleLesCount = 0;
        int allCount = 0;
        // generationCount用于计数每一代有多少数量的AdvancedCritter
        HashMap<Integer, Integer>generationCount = new HashMap<Integer, Integer>();
        for (Location loc : map.keySet()) {
            if (gr.get(loc) instanceof AdvancedCritter) {
                allCount++;
                AdvancedCritter temp = (AdvancedCritter)gr.get(loc);
                if (temp.getGender() == 0) {
                    maleCount++;
                }
                if (temp.getGender() == 1) {
                    femaleCount++;
                }
                if (temp.getGender() == 2) {
                    maleGayCount++;
                }
                if (temp.getGender() == 3) {
                    femaleLesCount++;
                }
                if (generationCount.containsKey(temp.getGeneration())) {
                    int originCount = generationCount.get(temp.getGeneration());
                    generationCount.put(temp.getGeneration(), originCount + 1);
                } else {
                    generationCount.put(temp.getGeneration(), 1);
                }
            }
        }
        String result = "";
        double hundred = 100.0;
        result += "总的New Jumper数量是 " + allCount + "\n";
        result += "男性数量是 " + maleCount + ",占据总数量的"+Double.toString(maleCount*hundred/allCount) + "%\n";
        result += "女性数量是 " + femaleCount + ",占据总数量的"+Double.toString(femaleCount*hundred/allCount) + "%\n";
        result += "男同性恋数量是 " + maleGayCount + ",占据总数量的"+Double.toString(maleGayCount*hundred/allCount) + "%\n";
        result += "女同性恋数量是 " + femaleLesCount + ",占据总数量的"+Double.toString(femaleLesCount*hundred/allCount) + "%\n";
        for (Integer g : generationCount.keySet()) {
            int count = generationCount.get(g);
            String rate = Double.toString(count*hundred/allCount);
            result +="第"+g+"代的NewJumper有"+count+"只, 占据总数量的"+ rate + "%\n";
        }
        return result;
    }

}
