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

		if(srcChamber==0) {     //�����ǰΪ��ʼ�ڵ�ʱ
			for(i=0; i<prime; i++) {
				dst = this.maze.traverse(i);          //�ҵ���һ�������portal���÷�������֮�󣬵�ǰ��chamberΪdst����
				src = new Portal(srcChamber,i);       //������ǰ������portal
				
				if(this.visited.add(dst)==true) {     //���Ϊ��һ�η��ʵģ�����뵽ջ��
					if(visitStack.isEmpty()) {        //�����ʱ���2�������ջΪ��ʱ����Ϊ��ʼ״̬����src��dst������
						visitStack.offerFirst(src);
						visitStack.offerFirst(dst);						
					}
					else {                            //�����Ϊ�գ���ֻ����dst
						visitStack.offerFirst(dst);					
					}
					
					visitQueue.addFirst(src);        //�������ĺͽ���ĳɶԲ��뵽������
					visitQueue.addFirst(dst);
					
					return dst;
				}
				else { 
					src = this.maze.traverse(dst.getDoor());	//���dst���ѷ��ʵģ���Ҫ����֮ǰ��chamber����ʹsrc��Ϊ��ǰ��
				}
			}
		}
		else {
			Portal p1 = visitStack.pollFirst();       //�����ǰchamber��Ϊ��ʼ��0�����ջ�е�������portal
			Portal p2 = visitStack.pollFirst();
			
			int backtrack = -1;
			
			for(i=0; i<prime; i++) {
				dst = this.maze.traverse(i);
				src = new Portal(srcChamber,i);
				
				if(dst.getChamber()==p2.getChamber()) {   //��Ӧ���ص�·�����Ȳ����ǣ����ȿ�������·��������¼�¶�Ӧdoor
					backtrack = i;
					src = this.maze.traverse(dst.getDoor());     //����֮ǰ��portal  
					continue;
				}
				else {
					if(this.visited.add(dst)==true) {      //��������·�����򽫵�����portalѹ��ջ���ٽ��µ�dstѹ��ջ
						visitStack.offerFirst(p2);
						visitStack.offerFirst(p1);
						visitStack.offerFirst(dst);		
						
						visitQueue.addFirst(src);        
						visitQueue.addFirst(dst);
						
						return dst;
					}
					else src = this.maze.traverse(dst.getDoor());     //����Ч·��ʱ������֮ǰ��chamberΪ��ǰ��
				}				
			}
			if(backtrack>-1) {                //����ѭ����ɺ���������е��˴���˵��û����Ч�ĺ���·���������ǰһ�ڵ�
				dst = this.maze.traverse(backtrack);
				src = new Portal(srcChamber,backtrack);
				
				this.visited.add(dst);
				
				visitStack.offerFirst(dst);    //���û�н�����������portalѹ��ȥ����ֱ�ӽ�dstѹ�룬֮ǰ����������
				
				visitQueue.addFirst(src);
				visitQueue.addFirst(dst);
				
				return dst;
			}
		}
		 
		//��������е����λ�ã���˵��������û���κο�ͨ�е���һchamber������ǰ��Ϊ��һ��chamberʱ������null
		if(this.maze.getCurrentChamber()==0) return null;
		else {
			return src;              //���򣬽���ǰ���ڵ�chamber����
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
			dst = this.maze.traverse(0);          //�ҵ���һ�������portal���÷�������֮�󣬵�ǰ��chamberΪdst����
			src = new Portal(srcChamber,0);       //������ǰ������portal

			this.visited.add(dst);
			
			visitStack.offerFirst(src);
			visitStack.offerFirst(dst);						

			visitQueue.addFirst(src);        //�������ĺͽ���ĳɶԲ��뵽������
			visitQueue.addFirst(dst);
					
			return dst;
		}
		else {
			cur = visitStack.peekFirst();
			
			for(i=0; i<prime; i++) {
				if(i==cur.getDoor()) continue;
				dst = this.maze.traverse(i);          //�ҵ���һ�������portal���÷�������֮�󣬵�ǰ��chamberΪdst����
				src = new Portal(srcChamber,i);
				
				if(this.visited.add(dst)==true) {     //���Ϊ��һ�η��ʵģ�����뵽ջ��
					
					visitStack.offerFirst(dst);						
					
					visitQueue.addFirst(src);        //�������ĺͽ���ĳɶԲ��뵽������
					visitQueue.addFirst(dst);
					
					return dst;
				}
				else { 
					this.maze.traverse(dst.getDoor());	//���dst���ѷ��ʵģ���Ҫ����֮ǰ��chamber����ʹsrc��Ϊ��ǰ��
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
