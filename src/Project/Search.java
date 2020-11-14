package Project;

import java.util.ArrayList;
import java.util.TreeSet;

public abstract class Search {
	static int expandedNodes = 0;
	static int MILLION = (int) 1e6;
	static TreeSet<State> allNodes;

	public static SearchTreeNode generalSearch(SearchProblem problem, String type, int k) {
		SearchList nodes = new SearchList(type, problem);
		allNodes = new TreeSet<>();
		allNodes.add(problem.initialState);
		while (!nodes.isEmpty()) {
			SearchTreeNode node = nodes.remove();
			if (problem.goalTest(node.state))
				return node;

			expandedNodes++;
			if (expandedNodes % MILLION == 0)
				System.out.println(expandedNodes);
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
		boolean succeded = false;
		for (Operator operator : operators) {
//			 if (operator.equals(Operator.DOWN) && succeded)
//			 break;
			State newState = problem.stateTransition(node.state, operator);
			if (newState == null)
				continue;
			succeded = true;
			if (!isRepeated(newState)) {
				SearchTreeNode newNode = new SearchTreeNode(node, newState, operator, problem);
				newNodes.add(newNode);
				 allNodes.add(newState);
			}
		}
		return newNodes;
	}

	private static boolean isRepeated(State newState) {
//		for (State other : allNodes)
//			if (other.equals(newState))
//				return true;
//		return false;
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
