package Project;

import java.util.*;

public class SearchList {

	PriorityQueue<SearchTreeNode> priorityQueue;
	Stack<SearchTreeNode> stack;
	Queue<SearchTreeNode> queue;
	String type;

	public SearchList(String type) {
		this.type = type;
		if (this.type == "ucs")
			priorityQueue = new PriorityQueue<SearchTreeNode>();
		if (this.type == "dfs" || this.type == "dls")
			stack = new Stack<SearchTreeNode>();
		if (this.type == "bfs")
			queue = new LinkedList<SearchTreeNode>();

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

	public SearchTreeNode remove() {
		if (this.type == "ucs")
			return priorityQueue.poll();
		if (this.type == "dfs" || this.type == "dls")
			return !stack.isEmpty() ? stack.pop() : null;
		if (this.type == "bfs")
			return queue.poll();

		return null;
	}

	public void bfs(ArrayList<SearchTreeNode> expandedArray) {
		// TODO Auto-generated method stub
	       queue.addAll(expandedArray);
	}

	public void dls(ArrayList<SearchTreeNode> expandedArray, int maxDepth) {
		// TODO Auto-generated method stub
		stack.addAll(expandedArray);
		

	}

	public void ucs(ArrayList<SearchTreeNode> expandedArray) {
		// TODO Auto-generated method stub

	}

	public void dfs(ArrayList<SearchTreeNode> expandedArray) {
		// TODO Auto-generated method stub
		stack.addAll(expandedArray);
	}

}
