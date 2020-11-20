package code.generic;

import java.util.ArrayList;
import java.util.TreeSet;

public abstract class Search {
	public static int expandedNodes = 0;
	static TreeSet<State> allNodes;

	public static SearchTreeNode generalSearch(SearchProblem problem, String type, int maxDepth) {
		SearchList nodes = new SearchList(type, problem);
		allNodes = new TreeSet<>();
		allNodes.add(problem.initialState);
		while (!nodes.isEmpty()) {
			SearchTreeNode node = nodes.remove();
			if (problem.goalTest(node.state))
				return node;
			expandedNodes++;

			// getting all the possible states that can be reached from that node
			ArrayList<SearchTreeNode> expandedArray = expand(node, problem, type, maxDepth);

			if (type.equals("BF"))
				nodes.bfs(expandedArray);

			if (type.equals("DF"))
				nodes.dfs(expandedArray);
			if (type.equals("UC"))
				nodes.ucs(expandedArray);
			if (type.equals("DL"))
				nodes.dls(expandedArray, maxDepth);
			if (type.equals("AS1") || type.equals("AS2"))
				nodes.aStar(expandedArray);
			if (type.equals("GR1") || type.equals("GR2"))
				nodes.greedy(expandedArray);


		}
		return null;

	}

	public static SearchTreeNode IDSearch(SearchProblem problem) { // iterative deepening
		int maxDepth = 0;
		while (true) {
			SearchTreeNode node = generalSearch(problem, "DL", maxDepth++);
			if (node != null)
				return node;
		}
	}

	private static ArrayList<SearchTreeNode> expand(SearchTreeNode node, SearchProblem problem, String type,
			int maxDepth) {
		ArrayList<SearchTreeNode> newNodes = new ArrayList<>();
		if (node.depth >= maxDepth)
			return newNodes;
		ArrayList<Operator> operators = problem.getOperators(node);
		for (Operator operator : operators) {
			State newState = problem.stateTransition(node.state, operator);
			if (newState == null)
				continue;
			if (!isRepeated(newState)) {
				SearchTreeNode newNode = new SearchTreeNode(node, newState, operator, problem);
				newNodes.add(newNode);
				allNodes.add(newState);
			}
		}
		return newNodes;
	}

	private static boolean isRepeated(State newState) {
		return allNodes.contains(newState);
	}

	private static boolean isRepeatedWithAncestors(SearchTreeNode node, State newState) {
		SearchTreeNode curr = node;
		while (curr != null) {
			if (curr.state.equals(newState))
				return true;
			curr = curr.parent;
		}
		return false;
	}

}
