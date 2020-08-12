package dsa_assignment2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * A Drone class to simulate the decisions and information collected by a drone
 * on exploring an underground maze.
 * 
 */
public class Drone implements DroneInterface
{
	private static final Logger logger     = Logger.getLogger(Drone.class);
	
	public String getStudentID()
	{
		//change this return value to return your student id number
		return "MY STUDENT ID";
	}

	public String getStudentName()
	{
		//change this return value to return your name
		return "MY NAME";
	}

	/**
	 * The Maze that the Drone is in
	 */
	private Maze                maze;

	/**
	 * The stack containing the portals to backtrack through when all other
	 * doorways of the current chamber have been explored (see assignment
	 * handout). Note that in Java, the standard collection class for both
	 * Stacks and Queues are Deques
	 */
	private Deque<Portal>       visitStack = new ArrayDeque<>();

	/**
	 * The set of portals that have been explored so far.
	 */
	private Set<Portal>         visited    = new HashSet<>();

	/**
	 * The Queue that contains the sequence of portals that the Drone has
	 * followed from the start
	 */
	private Deque<Portal>       visitQueue = new ArrayDeque<>();

	/**
	 * This constructor should never be used. It is private to make it
	 * uncallable by any other class and has the assert(false) to ensure that if
	 * it is ever called it will throw an exception.
	 */
	@SuppressWarnings("unused")
	private Drone()
	{
		assert (false);
	}

	/**
	 * Create a new Drone object and place it in chamber 0 of the given Maze
	 * 
	 * @param maze
	 *            the maze to put the Drone in.
	 */
	public Drone(Maze maze)
	{
		this.maze = maze;
	}

	/* 
	 * @see dsa_assignment2.DroneInterface#searchStep()
	 */
/*	
	@Override
	public Portal searchStep()
	{
		
		int prime = this.maze.getNumDoors();
		int srcChamber = this.maze.getCurrentChamber();
		Portal src = null;
		Portal dst = null;

		int i;

		if(srcChamber==0) {     //如果当前为初始节点时
			for(i=0; i<prime; i++) {
				dst = this.maze.traverse(i);          //找到下一个进入的portal，该方法调用之后，当前的chamber为dst的了
				src = new Portal(srcChamber,i);       //建立当前出发的portal
				
				if(this.visited.add(dst)==true) {     //如果为第一次访问的，则插入到栈中
					if(visitStack.isEmpty()) {        //插入的时候分2种情况，栈为空时，则为初始状态，将src和dst都插入
						visitStack.offerFirst(src);
						visitStack.offerFirst(dst);						
					}
					else {                            //如果不为空，则只插入dst
						visitStack.offerFirst(dst);					
					}
					
					visitQueue.addFirst(src);        //将出发的和进入的成对插入到队列中
					visitQueue.addFirst(dst);
					
					return dst;
				}
				else { 
					src = this.maze.traverse(dst.getDoor());	//如果dst是已访问的，则要返回之前的chamber，即使src成为当前的
				}
			}
		}
		else {
			Portal p1 = visitStack.pollFirst();       //如果当前chamber不为初始的0，则从栈中弹出两个portal
			Portal p2 = visitStack.pollFirst();
			
			int backtrack = -1;
			
			for(i=0; i<prime; i++) {
				dst = this.maze.traverse(i);
				src = new Portal(srcChamber,i);
				
				if(dst.getChamber()==p2.getChamber()) {   //对应返回的路径，先不考虑，优先考虑其它路径，并记录下对应door
					backtrack = i;
					src = this.maze.traverse(dst.getDoor());     //返回之前的portal  
					continue;
				}
				else {
					if(this.visited.add(dst)==true) {      //有其它的路径，则将弹出的portal压入栈后，再将新的dst压入栈
						visitStack.offerFirst(p2);
						visitStack.offerFirst(p1);
						visitStack.offerFirst(dst);		
						
						visitQueue.addFirst(src);        
						visitQueue.addFirst(dst);
						
						return dst;
					}
					else src = this.maze.traverse(dst.getDoor());     //无有效路径时，返回之前的chamber为当前的
				}				
			}
			if(backtrack>-1) {                //上面循环完成后，如果能运行到此处，说明没有有效的后续路径，则回溯前一节点
				dst = this.maze.traverse(backtrack);
				src = new Portal(srcChamber,backtrack);
				
				this.visited.add(dst);
				
				visitStack.offerFirst(dst);    //这里，没有将弹出的两个portal压回去，而直接将dst压入，之前的两个抛弃
				
				visitQueue.addFirst(src);
				visitQueue.addFirst(dst);
				
				return dst;
			}
		}
		 
		//如果能运行到这个位置，则说明上面已没有任何可通行的下一chamber，当当前的为第一个chamber时，返回null
		if(this.maze.getCurrentChamber()==0) return null;
		else {
			return src;              //否则，将当前所在的chamber返回
		}
	}

	
*/
	@Override
	public Portal searchStep()
	{
		
		int prime = this.maze.getNumDoors();
		int srcChamber = this.maze.getCurrentChamber();
		Portal src = null;
		Portal dst = null;
		Portal cur = null;

		int i;

		if(visitStack.isEmpty()) {
			dst = this.maze.traverse(0);          //找到下一个进入的portal，该方法调用之后，当前的chamber为dst的了
			src = new Portal(srcChamber,0);       //建立当前出发的portal

			this.visited.add(dst);
			
			visitStack.offerFirst(src);
			visitStack.offerFirst(dst);						

			visitQueue.addFirst(src);        //将出发的和进入的成对插入到队列中
			visitQueue.addFirst(dst);
					
			return dst;
		}
		else {
			cur = visitStack.peekFirst();
			
			for(i=0; i<prime; i++) {
				if(i==cur.getDoor()) continue;
				dst = this.maze.traverse(i);          //找到下一个进入的portal，该方法调用之后，当前的chamber为dst的了
				src = new Portal(srcChamber,i);
				
				if(this.visited.add(dst)==true) {     //如果为第一次访问的，则插入到栈中
					
					visitStack.offerFirst(dst);						
					
					visitQueue.addFirst(src);        //将出发的和进入的成对插入到队列中
					visitQueue.addFirst(dst);
					
					return dst;
				}
				else { 
					this.maze.traverse(dst.getDoor());	//如果dst是已访问的，则要返回之前的chamber，即使src成为当前的
				}
			}
			
			visitStack.removeFirst();
			
			if(this.maze.getCurrentChamber()==0) return null;
			
			dst = this.maze.traverse(src.getDoor());
			visitQueue.addFirst(cur);
			visitQueue.addFirst(dst);
			return dst;			
		}		
	}
	
	/* 
	 * @see dsa_assignment2.DroneInterface#getVisitOrder()
	 */
	@Override
	public Portal[] getVisitOrder()
	{
		/* WRITE YOUR CODE HERE */
		Portal[] visitOrder = new Portal[visitQueue.size()];

		visitQueue.toArray(visitOrder);
		
		Portal tmp;
		
		int i,j;
		
		for(i=0,j=visitQueue.size()-1; i<j; i++, j--) {
			tmp = visitOrder[i];
			visitOrder[i] = visitOrder[j];
			visitOrder[j] = tmp;
		}
		
		return visitOrder;
	}

	/*
	 * @see dsa_assignment2.DroneInterface#findPathBack()
	 */
	@Override
	public Portal[] findPathBack()
	{
		/* WRITE YOUR CODE HERE */
		int i=0;
		int size = visitStack.size();
		int begin,end;
		
		
		if(size<1) return null;
		
		Portal[] findPath = new Portal[size];

		visitStack.toArray(findPath);
		
		int beginChamber = findPath[0].getChamber();
		
		for(i=size-1; i>=0; i--) {
			if(beginChamber==findPath[i].getChamber()) break;			
		}
		begin = i;
		
		int endChamber = 0;
		for(i=begin; i<size; i++) {
			if(endChamber==findPath[i].getChamber()) break;
		}
		end = i;
		
		Portal[] shortestPath = Arrays.copyOfRange(findPath, begin, end);

		return shortestPath;
	}

}
