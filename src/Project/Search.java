package Project;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Search {

	public static Object generalSearch(Object problem, String type, int k) {
		Queue<Object> nodes = new LinkedList<>();
		nodes.add(problem);

		while (!nodes.isEmpty()) {

			Object node = nodes.remove();

			if (goalTestFunction(node))
				return node;

			// getting all the possible states that can be reached from that node
			ArrayList<Object> expandedArray = expand(node, problem);

			if (type == "bfs")
				nodes = bfs(nodes, expandedArray);
			if (type == "dfs")
				nodes = dls(nodes, expandedArray, Integer.MAX_VALUE);
			if (type == "ucs")
				nodes = ucs(nodes, expandedArray);
			if (type == "dls")
				nodes = dls(nodes, expandedArray, k);

		}
		return false;

	}

	private static Queue<Object> ucs(Queue<Object> nodes, ArrayList<Object> expandedArray) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Queue<Object> dls(Queue<Object> nodes, ArrayList<Object> expandedArray, int depth) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Queue<Object> bfs(Queue<Object> nodes, ArrayList<Object> expandedArray) {
		// TODO Auto-generated method stub
		return null;
	}

	private static boolean goalTestFunction(Object state) {
		return true;
	}

	private static ArrayList<Object> expand(Object node, Object problem) {
		ArrayList<Object> x = new ArrayList<Object>();
		return x;

	}

}
