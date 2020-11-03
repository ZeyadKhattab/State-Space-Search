package Project;

import java.util.*;

public class SearchList {

	PriorityQueue<State> priorityQueue;
	Stack<State> stack;
	Queue<State> queue;
	String type;

	public SearchList(String type) {
		this.type = type;
		if (this.type == "ucs")
			priorityQueue = new PriorityQueue<State>();
		if (this.type == "dfs" || this.type == "dls")
			stack = new Stack<State>();
		if (this.type == "bfs")
			queue = new LinkedList<State>();

	}

	public void add(Object problem) {
		// TODO Auto-generated method stub

	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		if (this.type == "ucs")
			return priorityQueue.isEmpty();
		if (this.type == "dfs" || this.type == "dls")
			return stack.isEmpty() ;
		if (this.type == "bfs")
			return queue.isEmpty();
		
		return true;
	}

	public State remove() {
		if (this.type == "ucs")
			return priorityQueue.poll();
		if (this.type == "dfs" || this.type == "dls")
			return !stack.isEmpty() ? stack.pop() : null;
		if (this.type == "bfs")
			return queue.poll();

		return null;
	}

	public void bfs(ArrayList<State> expandedArray) {
		// TODO Auto-generated method stub
	       queue.addAll(expandedArray);
	}

	public void dls(ArrayList<State> expandedArray, int maxDepth) {
		// TODO Auto-generated method stub
		stack.addAll(expandedArray);
		

	}

	public void ucs(ArrayList<State> expandedArray) {
		// TODO Auto-generated method stub

	}

	public void dfs(ArrayList<State> expandedArray) {
		// TODO Auto-generated method stub
		stack.addAll(expandedArray);
	}

}
