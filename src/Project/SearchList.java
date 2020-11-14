package Project;

import java.util.*;

public class SearchList {

	PriorityQueue<SearchTreeNode> ucsQueue;
	Stack<SearchTreeNode> stack;
	Queue<SearchTreeNode> queue;
	String type;

	public SearchList(String type,SearchProblem problem) {
		this.type = type;
		SearchTreeNode initialNode = new SearchTreeNode(null, problem.initialState, null);

		if (this.type.equals("ucs")) {
			ucsQueue = new PriorityQueue<SearchTreeNode>();
			ucsQueue.add(initialNode);
		}
		if (this.type.equals("dfs") || this.type.equals("dls")) {
			stack = new Stack<SearchTreeNode>();
			stack.add(initialNode);

		}
		if (this.type.equals("bfs")) {
			queue = new LinkedList<SearchTreeNode>();
			queue.add(initialNode);
		}

	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		if (this.type.equals("ucs"))
			return ucsQueue.isEmpty();
		if (this.type.equals("dfs") || this.type.equals("dls"))
			return stack.isEmpty();
		if (this.type.equals("bfs"))
			return queue.isEmpty();

		return true;
	}

	public SearchTreeNode remove() {
		if (this.type.equals("ucs"))
			return ucsQueue.poll();
		if (this.type.equals("dfs") || this.type.equals("dls"))
			return !stack.isEmpty() ? stack.pop() : null;
		if (this.type.equals("bfs"))
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
