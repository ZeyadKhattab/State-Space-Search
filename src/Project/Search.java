package Project;

import java.util.ArrayList;

public abstract class Search {
	static int expandedNodes=0;

	public static SearchTreeNode generalSearch(SearchProblem problem, String type, int k) {
		SearchList nodes = new SearchList(type, problem);

		while (!nodes.isEmpty()) {
			SearchTreeNode node = nodes.remove();

			if (problem.goalTest(node.state))
				return node;
			
			expandedNodes++;

			// getting all the possible states that can be reached from that node
			ArrayList<SearchTreeNode> expandedArray = expand(node, problem, type, k);

			if (type.equals("BF"))
				nodes.bfs(expandedArray);
			if (type.equals("DF"))
				nodes.dfs(expandedArray);
			if (type.equals("UC"))
				nodes.ucs(expandedArray);
			if (type.equals("DL"))
				nodes.dls(expandedArray, k);

		}
		return null;

	}

	public static SearchTreeNode IDSearch(MissionImpossible problem) { // iterative deepening
		int k = 0;
		while (true) {
			SearchTreeNode node = generalSearch(problem, "DL", k++);
			if (node != null)
				return node;
		}
	}

	private static ArrayList<SearchTreeNode> expand(SearchTreeNode node, SearchProblem problem, String type, int k) {
		ArrayList<SearchTreeNode> newNodes = new ArrayList<>();
		if (node.depth >= k)
			return newNodes;
		ArrayList<Operator> operators = problem.getOperators();
		for (Operator operator : operators) {
			State newState = problem.stateTransition(node.state, operator);
			if (newState == null)
				continue;
			if (!isRepeatedWithAncestors(node, newState)) {
				SearchTreeNode newNode = new SearchTreeNode(node, newState, operator, problem);
				newNodes.add(newNode);
			}
		}
		return newNodes;
	}

	private static boolean isRepeatedWithAncestors(SearchTreeNode node, State newState) {

		while (node != null) {
			if (node.state.equals(newState))
				return true;
			node = node.parent;
		}
		return false;
	}

}
