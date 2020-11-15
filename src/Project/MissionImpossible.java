package Project;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MissionImpossible extends SearchProblem {

	public MissionImpossible(State initialState) {
		super(initialState);
	}

	public MissionImpossible(String initialGrid) {
		super(new State(initialGrid));
	}

	@Override
	State stateTransition(State state, Operator operator) {
		State ans = state.clone();
		boolean succeded = false;
		if (operator.equals(Operator.PICKUP))
			succeded = ans.pickUp();
		if (operator.equals(Operator.DOWN))
			succeded = ans.moveDown();
		if (operator.equals(Operator.UP))
			succeded = ans.moveUp();
		if (operator.equals(Operator.LEFT))
			succeded = ans.moveLeft();
		if (operator.equals(Operator.RIGHT))
			succeded = ans.moveRight();
		if (operator.equals(Operator.DROP))
			succeded = ans.leave();
		if (succeded) {
			ans.decreaseHealth();
			return ans;
		} else
			return null;
	}

	@Override
	boolean goalTest(State state) {
		return state.getRemainingMembers() == 0;
	}

	@Override
	int pathCost(SearchTreeNode node) {
		State state = node.state;
		return 2000 * state.getNumberOfDeaths() + state.totalDamage;
	}

	static int randomNumber(int l, int r) {
		Random random = new Random();
		return l + random.nextInt(r - l + 1);
	}

	static int[][] generatePositions(int n, int m, int target) {
		int[][] possible = new int[n * m][2];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				possible[i * m + j] = new int[] { i, j };
		// shuffling
		for (int i = 0; i < n * m; i++) {
			int swapWith = randomNumber(i, n * m - 1);
			int[] tmp = possible[i];
			possible[i] = possible[swapWith];
			possible[swapWith] = tmp;
		}
		int[][] ans = new int[target][2];
		for (int i = 0; i < target; i++)
			ans[i] = possible[i];
		return ans;
	}

	static String genGrid() {
		int n = randomNumber(5, 15), m = randomNumber(5, 15);
		int IMFMembers = randomNumber(5, 10);
		int[] health = new int[IMFMembers];
		int[][] generatedPositions = generatePositions(n, m, IMFMembers + 2);
		int[] xPos = new int[IMFMembers], yPos = new int[IMFMembers];
		for (int i = 0; i < IMFMembers; i++) {
			health[i] = randomNumber(1, 99);
			xPos[i] = generatedPositions[i][0];
			yPos[i] = generatedPositions[i][1];
		}

		int c = randomNumber(1, IMFMembers);
		int ex = generatedPositions[IMFMembers][0], ey = generatedPositions[IMFMembers][1];
		int sx = generatedPositions[IMFMembers + 1][0], sy = generatedPositions[IMFMembers + 1][1];
		StringBuilder sb = new StringBuilder();
		sb.append(m);
		sb.append(",");
		sb.append(n);
		sb.append(";");
		sb.append(ex);
		sb.append(",");
		sb.append(ey);
		sb.append(";");
		sb.append(sx);
		sb.append(",");
		sb.append(sy);
		sb.append(";");
		for (int i = 0; i < IMFMembers; i++) {
			if (i > 0)
				sb.append(",");
			sb.append(xPos[i] + "," + yPos[i]);
		}
		sb.append(";");
		for (int i = 0; i < IMFMembers; i++) {
			if (i > 0)
				sb.append(",");
			sb.append(health[i]);
		}
		sb.append(";");
		sb.append(c);
		return sb.toString();

	}

	// remaining Greedy and a star
	static String solve(String grid, String strategy, boolean visualize) {
		MissionImpossible problem = new MissionImpossible(grid);
		SearchTreeNode ans = null;
		Search.expandedNodes = 0;
		if (strategy.equals("BF") || strategy.equals("DF") || strategy.equals("UC") || strategy.equals("AS1")
				|| strategy.equals("AS2"))
			ans = Search.generalSearch(problem, strategy, Integer.MAX_VALUE);
		else if (strategy.equals("ID"))
			ans = Search.IDSearch(problem);
		ArrayList<SearchTreeNode> pathToGoal = getPathToGoal(ans);
		if (visualize) {
			for (SearchTreeNode node : pathToGoal)
				System.out.println(node.state + "-----------------\n");
		}
		return getSolutionAsString(pathToGoal, ans);

	}

	// missing # nodes expanded
	public static String getSolutionAsString(ArrayList<SearchTreeNode> pathToGoal, SearchTreeNode goalNode) {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < pathToGoal.size(); i++) {
			if (i > 1)
				sb.append(",");
			sb.append(operatorToString(pathToGoal.get(i).operator));
		}
		sb.append(";" + goalNode.state.getNumberOfDeaths() + ";");
		IMF[] members = goalNode.state.getMembers();
		for (int i = 0; i < members.length; i++) {
			if (i > 0)
				sb.append(",");
			sb.append(members[i].health);
		}
		sb.append(";");
		sb.append(Search.expandedNodes);
		return sb.toString();
	}

	public static ArrayList<SearchTreeNode> getPathToGoal(SearchTreeNode goalNode) {
		Stack<SearchTreeNode> stack = new Stack();
		SearchTreeNode node = goalNode;
		while (node != null) {
			stack.add(node);
			node = node.parent;
		}
		ArrayList<SearchTreeNode> ans = new ArrayList<>();
		while (!stack.isEmpty())
			ans.add(stack.pop());
		return ans;
	}

	public static String operatorToString(Operator operator) {

		if (operator.equals(Operator.DOWN))
			return "down";
		if (operator.equals(Operator.UP))
			return "up";
		if (operator.equals(Operator.LEFT))
			return "left";
		if (operator.equals(Operator.RIGHT))
			return "right";
		if (operator.equals(Operator.PICKUP))
			return "carry";
		if (operator.equals(Operator.DROP))
			return "drop";
		return null;
	}

	@Override
	ArrayList<Operator> getOperators() {
		ArrayList<Operator> ans = new ArrayList<>();
		ans.add(Operator.DROP);
		ans.add(Operator.PICKUP);
		ans.add(Operator.DOWN);
		ans.add(Operator.UP);
		ans.add(Operator.LEFT);
		ans.add(Operator.RIGHT);
		return ans;

	}

	static int distance(Character a, Character b) {
		return Math.abs(a.posX - b.posX) + Math.abs(a.posY - b.posY);
	}

	public int heuristic(State state, int id) {
		int ans = 0;
		for (IMF member : state.members) {
			if (!member.saved) {
				int distance = distance(state.ethan, member);
				if (2 * distance + member.health >= 100)
					ans += (member.health == 100 ? 0 : 2000) + 100 - member.health;
				else
					ans += 2 * distance;
			}
		}
		return ans;
	}

}
