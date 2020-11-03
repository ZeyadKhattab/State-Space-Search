package Project;

import java.util.ArrayList;

public abstract class Search {

	public static Object generalSearch(Object problem, String type, int k) {
		SearchList nodes = new SearchList(type);
		nodes.add(problem);

		while (!nodes.isEmpty()) {

			State node = nodes.remove();

			if (goalTestFunction(node))
				return node;

			// getting all the possible states that can be reached from that node
			ArrayList<State> expandedArray = expand(node, problem);

			if (type == "bfs")
				nodes.bfs(expandedArray);
			if (type == "dfs")
				nodes.dfs( expandedArray);
			if (type == "ucs")
				nodes.ucs( expandedArray);
			if (type == "dls")
				nodes.dls( expandedArray, k);

		}
		return false;

	}
	
	
	private static boolean goalTestFunction(State node) {
		return true;
	}

	private static ArrayList<State> expand(State node, Object problem) {
		ArrayList<State> x = new ArrayList<State>();
		return x;

	}

}
