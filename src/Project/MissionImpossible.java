package Project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public class MissionImpossible extends SearchProblem {

	public MissionImpossible(MissionImpossibleState initialState) {
		super(initialState);
	}

	public MissionImpossible(String initialGrid) {
		super(new MissionImpossibleState(initialGrid));
	}

	@Override
	MissionImpossibleState stateTransition(State state, Operator operator) {
		MissionImpossibleState ans = cast(state).clone();
		MissionImpossibleOperator MIOperator = (MissionImpossibleOperator) operator;
		boolean succeded = false;
		if (MIOperator.operator.equals(MissionImpossibleOperator.Operator.PICKUP)) {
			int memberIdx = MIOperator.memberIdx;
			succeded = ans.pickUp(memberIdx);
		}
//		if (MIOperator.operator.equals(MissionImpossibleOperator.Operator.DOWN))
//			succeded = ans.moveDown();
//		if (MIOperator.operator.equals(MissionImpossibleOperator.Operator.UP))
//			succeded = ans.moveUp();
//		if (MIOperator.operator.equals(MissionImpossibleOperator.Operator.LEFT))
//			succeded = ans.moveLeft();
//		if (MIOperator.operator.equals(MissionImpossibleOperator.Operator.RIGHT))
//			succeded = ans.moveRight();
		if (MIOperator.operator.equals(MissionImpossibleOperator.Operator.DROP))

			succeded = ans.leave();

		if (succeded) {

			return ans;
		} else
			return null;
	}

	static MissionImpossibleState cast(State state) {
		return (MissionImpossibleState) state;
	}

	@Override
	boolean goalTest(State state) {
		return cast(state).getRemainingMembers() == 0;
	}

	@Override
	int pathCost(SearchTreeNode node) {
		MissionImpossibleState state = cast(node.state);
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
				|| strategy.equals("AS2") || strategy.equals("GR1") || strategy.equals("GR2"))
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

	public static String getSolutionAsString(ArrayList<SearchTreeNode> pathToGoal, SearchTreeNode goalNode) {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < pathToGoal.size(); i++) {
			if (i > 1)
				sb.append(",");
			sb.append(operatorToString(pathToGoal.get(i).operator));
		}
		sb.append(";" + cast(goalNode.state).getNumberOfDeaths() + ";");
		IMF[] members = cast(goalNode.state).getMembers();
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
		MissionImpossibleOperator MIOperator = (MissionImpossibleOperator) operator;
		if (MIOperator.operator.equals(MissionImpossibleOperator.Operator.DOWN))
			return "down";
		if (MIOperator.operator.equals(MissionImpossibleOperator.Operator.UP))
			return "up";
		if (MIOperator.operator.equals(MissionImpossibleOperator.Operator.LEFT))
			return "left";
		if (MIOperator.operator.equals(MissionImpossibleOperator.Operator.RIGHT))
			return "right";
		if (MIOperator.operator.equals(MissionImpossibleOperator.Operator.PICKUP))
			return "carry";
		if (MIOperator.operator.equals(MissionImpossibleOperator.Operator.DROP))
			return "drop";
		return null;
	}

	@Override
	ArrayList<Operator> getOperators() {
		ArrayList<Operator> ans = new ArrayList<>();
		ans.add(new MissionImpossibleOperator(MissionImpossibleOperator.Operator.DROP));
		for (int i = 0; i < MissionImpossibleState.numberOfMembers; i++)
			ans.add(new MissionImpossibleOperator(MissionImpossibleOperator.Operator.PICKUP, i));
//		ans.add(new MissionImpossibleOperator(MissionImpossibleOperator.Operator.DOWN));
//		ans.add(new MissionImpossibleOperator(MissionImpossibleOperator.Operator.UP));
//		ans.add(new MissionImpossibleOperator(MissionImpossibleOperator.Operator.LEFT));
//		ans.add(new MissionImpossibleOperator(MissionImpossibleOperator.Operator.RIGHT));
		return ans;

	}

	static int distance(Character a, Character b) {
		return Math.abs(a.posX - b.posX) + Math.abs(a.posY - b.posY);
	}

	static int[][] getPermutations(int n) {
		if (n == 1)
			return new int[][] { { 0 } };
		if (n == 2) {
			return new int[][] { { 0, 1 }, { 1, 0 } };
		} else
			return new int[][] { { 0, 1, 2 }, { 0, 2, 1 }, { 1, 0, 2 }, { 1, 2, 0 }, { 2, 0, 1 }, { 2, 1, 0 } };
	}

	static int costAfterDistance(int distance, IMF member) {
		if (2 * distance + member.health >= 100)
			return (member.health == 100 ? 0 : 2000) + 100 - member.health;
		else
			return 2 * distance;
	}

	@Override
	public int heuristic(State state, int id) {
		int ans = 0;
		if (id == 1) {
			for (IMF member : cast(state).members) {
				if (!member.saved) {
					int distance = distance(cast(state).ethan, member);
					ans += costAfterDistance(distance, member);
				}
			}
		} else {
			ArrayList<IMF> members = new ArrayList();
			for (IMF member : cast(state).members)
				if (!member.saved)
					members.add(member);
			if (members.size() == 0) {
				return 0;
			}
			Collections.sort(members,
					(x, y) -> Integer.compare(distance(cast(state).ethan, y), distance(cast(state).ethan, x)));
			int[][] permutations = getPermutations(Math.min(members.size(), 3));
			ans = Integer.MAX_VALUE;
			for (int[] permutation : permutations) {
				int curr = 0;
				for (int i = 0; i < permutation.length; i++) {
					Character prev = i == 0 ? cast(state).ethan : members.get(permutation[i - 1]);
					IMF member = members.get(permutation[i]);
					int distance = distance(prev, member);
					curr += costAfterDistance(distance, member);
				}
				ans = Math.min(ans, curr);
			}

		}
		return ans;
	}

}
