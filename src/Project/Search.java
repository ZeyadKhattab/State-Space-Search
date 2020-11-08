package Project;

import java.util.ArrayList;

public abstract class Search {

	public static SearchTreeNode generalSearch(MissionImpossible problem, String type, int k) {
		SearchList nodes = new SearchList(type);
		nodes.add(problem);

		while (!nodes.isEmpty()) {

			SearchTreeNode node = nodes.remove();

			if (problem.goalTest(node.state))
				return node;

			// getting all the possible states that can be reached from that node
			ArrayList<SearchTreeNode> expandedArray = expand(node, problem, type, k);

			if (type == "bfs")
				nodes.bfs(expandedArray);
			if (type == "dfs")
				nodes.dfs(expandedArray);
			if (type == "ucs")
				nodes.ucs(expandedArray);
			if (type == "dls")
				nodes.dls(expandedArray, k);

		}
		return null;

	}

	public static SearchTreeNode dLSearch(MissionImpossible problem) {
		int k = 0;
		while (true) {
			SearchTreeNode node = generalSearch(problem, "dls", k);
			if (node != null)
				return node;
		}
	}

	private static ArrayList<SearchTreeNode> expand(SearchTreeNode node, MissionImpossible problem, String type,
			int k) {
		ArrayList<SearchTreeNode> newNodes = new ArrayList<>();
		if (node.depth >= k)
			return newNodes;
		ArrayList<Operator> operators = problem.getOperators();
		for (Operator operator : operators) {
			State newState = stateTransition(node.state, operator);
			if (!isRepeatWithAncestors(node, newState)) {
				SearchTreeNode newNode = new SearchTreeNode(node, newState);
				newNodes.add(newNode);
			}
		}
		return null;
	}

	private static Boolean isRepeatedWithAncestors(SearchTreeNode node, State newState) {

		while (node.parent != null) {
			// == still not implemented should be method called isIdentical
			if (node.state == newState)
				return true;
			node = node.parent;
		}
		return false;
	}

}
