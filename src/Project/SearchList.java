package Project;

import java.util.*;

public class SearchList {

	PriorityQueue<SearchTreeNode> priorityQueue;
	Stack<SearchTreeNode> stack;
	Queue<SearchTreeNode> queue;
	String type;

	public SearchList(String type, SearchProblem problem) {
		this.type = type;
		SearchTreeNode initialNode = new SearchTreeNode(problem);

		if (this.type.equals("UC")) {
			priorityQueue = new PriorityQueue<SearchTreeNode>((node1, node2) -> node1.cost - node2.cost);
			priorityQueue.add(initialNode);
		}
		if (this.type.equals("DF") || this.type.equals("DL")) {
			stack = new Stack<SearchTreeNode>();
			stack.add(initialNode);

		}
		if (this.type.equals("BF")) {
			queue = new LinkedList<SearchTreeNode>();
			queue.add(initialNode);
		}
		if (this.type.equals("AS1") || this.type.equals("AS2")) {
			int heuristicId = this.type.charAt(2) - '0';
			priorityQueue = new PriorityQueue<>(
					(node1, node2) -> Integer.compare(node1.cost + problem.heuristic(node1.state, heuristicId),
							node2.cost + problem.heuristic(node2.state, heuristicId)));
			priorityQueue.add(initialNode);
		}
		if (this.type.equals("GR1") || this.type.equals("GR2")) {
			int heuristicId = this.type.charAt(2) - '0';
			priorityQueue = new PriorityQueue<>(
					(node1, node2) -> Integer.compare(problem.heuristic(node1.state, heuristicId),
							 problem.heuristic(node2.state, heuristicId)));
			priorityQueue.add(initialNode);
		}

	}

	public boolean isEmpty() {
		if (this.type.equals("UC") || this.type.equals("AS1") || this.type.equals("AS2")|| this.type.equals("GR1") || this.type.equals("GR2"))
			return priorityQueue.isEmpty();
		if (this.type.equals("DF") || this.type.equals("DL"))
			return stack.isEmpty();
		if (this.type.equals("BF"))
			return queue.isEmpty();

		return true;
	}

	public SearchTreeNode remove() {
		if (this.type.equals("UC") || this.type.equals("AS1") || this.type.equals("AS2")|| this.type.equals("GR1") || this.type.equals("GR2"))
			return priorityQueue.poll();
		if (this.type.equals("DF") || this.type.equals("DL"))
			return stack.pop();
		if (this.type.equals("BF"))
			return queue.poll();

		return null;
	}

	public void bfs(ArrayList<SearchTreeNode> expandedArray) {
		queue.addAll(expandedArray);
	}

	public void dls(ArrayList<SearchTreeNode> expandedArray, int maxDepth) {
		stack.addAll(expandedArray);

	}

	public void ucs(ArrayList<SearchTreeNode> expandedArray) {
		priorityQueue.addAll(expandedArray);

	}

	public void dfs(ArrayList<SearchTreeNode> expandedArray) {
		stack.addAll(expandedArray);
	}

	public void aStar(ArrayList<SearchTreeNode> expandedArray) {
		priorityQueue.addAll(expandedArray);

	}

	public void greedy(ArrayList<SearchTreeNode> expandedArray) {
		priorityQueue.addAll(expandedArray);
		
	}

}
