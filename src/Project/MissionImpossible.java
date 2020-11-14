package Project;

import java.util.ArrayList;
import java.util.Random;

public class MissionImpossible extends SearchProblem {

	public MissionImpossible(State initialState) {
		super(initialState);
	}

	public MissionImpossible(String initialGrid) {
		super(new State(initialGrid));
	}

	@Override
	State stateTransition(State state, Operator operator) {
		state = state.clone();
		boolean succeded = false;
		if (operator.equals(Operator.DOWN))
			succeded = state.moveDown();
		if (operator.equals(Operator.UP))
			succeded = state.moveUp();
		if (operator.equals(Operator.LEFT))
			succeded = state.moveLeft();
		if (operator.equals(Operator.RIGHT))
			succeded = state.moveRight();
		if (operator.equals(Operator.PICKUP))
			succeded = state.pickUp();
		if (operator.equals(Operator.DROP))
			succeded = state.leave();
		if (succeded) {
			state.decreaseHealth();
			return state;
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
		return 100 * state.getNumberOfDeaths() + state.totalDamage;
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

	static SearchTreeNode solve(String grid, String strategy, boolean visualize) {
		State initialState = new State(grid);
		MissionImpossible problem = new MissionImpossible(initialState);
		SearchTreeNode ans = null;
		if (strategy.equals("BF") || strategy.equals("DF") || strategy.equals("UC"))
			ans = Search.generalSearch(problem, strategy, Integer.MAX_VALUE);
		else if (strategy.equals("ID"))
			ans = Search.IDSearch(problem);
		// remaining Greedy and a star
		return ans;

	}

	@Override
	ArrayList<Operator> getOperators() {
		ArrayList<Operator> ans = new ArrayList<>();
		ans.add(Operator.DOWN);
		ans.add(Operator.UP);
		ans.add(Operator.LEFT);
		ans.add(Operator.RIGHT);
		ans.add(Operator.PICKUP);
		ans.add(Operator.DROP);
		return ans;

	}

}
