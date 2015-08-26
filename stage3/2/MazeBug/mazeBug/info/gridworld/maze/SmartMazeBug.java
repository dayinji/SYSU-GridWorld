
import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.HashMap;
import java.util.Map;
import java.lang.*;

import javax.swing.JOptionPane;

/**
 * A <code>MazeBug</code> can find its way in a maze. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class SmartMazeBug extends Bug {
	public Location next;
	public Location last;
	public boolean isEnd = false;
	//public Stack<ArrayList<Location>> crossLocation = new Stack<ArrayList<Location>>();
	public Stack<Location> crossLocation = new Stack<Location>();
	public Integer stepCount = 0;
	public Map<Location, Boolean> visitedMap;
	private int[] estimate = new int[4];
				
	boolean hasShown = false;//final message has been shown

	/**
	 * Constructs a box bug that traces a square of a given side length
	 * 
	 * @param length
	 *            the side length
	 */
	public SmartMazeBug() {
		setColor(Color.BLUE);
		last = new Location(0, 0);
		visitedMap = new HashMap<Location, Boolean>();
		for (int i = 0 ; i < 4  ; i++) {
			estimate[i] = 1;
		}
	}

	/**
	 * Moves to the next location of the square.
	 */
	public void act() {
		boolean willMove = canMove();
		if (isEnd == true) {
		//to show step count when reach the goal        
			if (hasShown == false) {
				String msg = stepCount.toString() + " steps";
				JOptionPane.showMessageDialog(null, msg);
				hasShown = true;
			}
		} else if (willMove) {
			move();
			//increase step count when move 
			stepCount++;
			if (hasFound()) {
				isEnd = true;
			}
		} 
	}
	/**
	 * Find all positions that can be move to.
	 * 
	 * @param loc
	 *            the location to detect.
	 * @return List of positions.
	 */
	public ArrayList<Location> getValid(Location loc) {
		Grid<Actor> gr = getGrid();
		if (gr == null) {
			return null;
		}
		ArrayList<Location> valid = new ArrayList<Location>();
		
		return valid;
	}

	/**
	 * Tests whether this bug can move forward into a location that is empty or
	 * contains a flower.
	 * 
	 * @return true if this bug can move.
	 */
	public boolean canMove() {
		Grid<Actor> grid = getGrid();
		Location loc = getLocation();
		ArrayList<Location> emptyLocList = grid.getEmptyAdjacentLocations(loc);
		ArrayList<Location> validLocList = new ArrayList<Location>();
		ArrayList<Location> canMoveLocList = new ArrayList<Location>();
		for (Location l : emptyLocList) {
			if (l.getCol() == loc.getCol() || l.getRow() == loc.getRow()) {
				validLocList.add(l);
			}
		}
		canMoveLocList  = removeVisitedLoc(validLocList);
		if (canMoveLocList.size() == 0) {
			if (!crossLocation.isEmpty()) {
				// 返回上一格
				next = crossLocation.pop();
				// Update estimate
				updateEstimate(next, -1);
			} else {
				// 无路可走
				return false;
			}
		} else {
			// push当前格
			crossLocation.push(loc);
			//next = canMoveLocList.get((int)(Math.random()*canMoveLocList.size()));
			next = getBestChoise(canMoveLocList);
			// Update estimate
			updateEstimate(next, 1);
		}
		return true;
	}
	/*
	 * 去除掉已经走过的格子
	 */
	private ArrayList<Location> removeVisitedLoc(ArrayList<Location> validLocList) {
		ArrayList<Location> canMoveLocList = new ArrayList<Location>();
		for (Location l : validLocList) {
			if (visitedMap.get(l) == null) {
				canMoveLocList.add(l);
			}
		}
		return canMoveLocList;
	}
	/**
	 * Moves the bug forward, putting a flower into the location it previously
	 * occupied.
	 */
	public void move() {
		Grid<Actor> gr = getGrid();
		if (gr == null) {
			return;
		}
		Location loc = getLocation();
		if (gr.isValid(next)) {
			last = loc;
			// 设置visited为true
			visitedMap.put(last, true);
			setDirection(getLocation().getDirectionToward(next));
			moveTo(next);
		} else {
			removeSelfFromGrid();
		}
		Flower flower = new Flower(getColor());
		flower.putSelfInGrid(gr, loc);
	}
	/*
	 * 判断是否达到终点找到红石头
	 */
	public boolean hasFound() {
		Location loc = getLocation();
		Grid<Actor> grid = getGrid();
		ArrayList<Actor> neighbors =  (ArrayList<Actor>)grid.getNeighbors(loc);
		for (Actor ac : neighbors) {
			if (ac instanceof Rock && ac.getColor().equals(Color.RED)) {
				Location acLoc = ac.getLocation();
				if (acLoc.getCol() == loc.getCol() || acLoc.getRow() == loc.getRow()) {
					return true;
				}
			} 
		}
		return false;
	}
	private void updateEstimate(Location next, int bool) {
		if (bool == 1) {
			Location loc = getLocation();
			int index = loc.getDirectionToward(next) / 90;
			estimate[index]++;
		} else if (bool == -1) {
			Location loc = getLocation();
			int index = next.getDirectionToward(loc) / 90;
			estimate[index]--;
		}
	}
	private Location getBestChoise(ArrayList<Location> canMoveLocList) {
		int best = 0;
		int bestId = 0;
		/*String[] fangxiang = new String[] {
			"北", "东", "南", "西"
		};
		String bestFangxiang = "";*/
		Location loc = getLocation();
                            double sum = 0;
                            ArrayList<int> fangxiang = new ArrayList<int>();
		for (int i = 0 ; i < canMoveLocList.size() ; i++) {
			int index = loc.getDirectionToward(canMoveLocList.get(i))/90;
                                          sum += estimate[index];
                                          fangxiang.add(index);
			/*if (estimate[index] > best) {
				best = estimate[index];
				//bestFangxiang = fangxiang[index];
				bestId = i;
			}*/
		}
                            for (int index : fangxiang) {

                            }
                            double randNum = Math.random();
                            double bei = estimate[0] / sum;
                            double dong = estimate[1] / sum;
                            double nan = estimate[2] / sum;
                            double xi = estimate[3] / sum;

		//System.out.println("最好的选择是" +bestFangxiang);
		return canMoveLocList.get(bestId);
	}
}
听说今年没有答辩,我很失望,感觉找不到方式检验我这十天来的成果,感觉我的满腹才气找不到地方可以释放.
在此希望TA们能单独给我举办一个个人答辩秀.
希望答辩秀的内容不要局限于这次实训的知识,我希望能涵盖Java, Python, Php, android开发,ios开发, web安全, 操作系统开发, 机器语言, 汇编语言, 数据库优化等层面,以全面考验我的优秀程度.
我要享受那种所有人都被我的我的口才和智慧征服而自惭形秽的样子,希望全级同学或者全院同学都来旁听仰慕,同时欢迎已经毕业就业的师兄师姐回到母校来观赏前无古人后无来者的我的巅峰个人答辩秀