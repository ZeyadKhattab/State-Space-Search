package Project;

import java.util.Random;

public class MissionImpossible extends SearchProblem {

	public MissionImpossible(State initialState) {
		super(initialState);
	}

	enum Operator {
		RIGHT, LEFT, UP, DOWN, PICKUP, DROP
	}

	@Override
	State stateTransition(State state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	boolean goalTest(State state) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	int pathCost(SearchTreeNode node) {
		// TODO Auto-generated method stub
//		return node.cost;
		return 0;
	}

	static int randomNumber(int l, int r) {
		Random random = new Random();
		return l + random.nextInt(r - l + 1);
	}

	static int[][] generatePositions(int n, int m, int target) {
		int[][] ans = new int[target][2];
		boolean[][] generateed = new boolean[n][m];
		Random random = new Random();
		while (target > 0) {
			int i = random.nextInt(n), j = random.nextInt(m);
			if (!generateed[i][j]) {
				generateed[i][j] = true;
				ans[target - 1][0] = i;
				ans[target - 1][1] = j;
				target--;
			}
		}
		return ans;
	}

	static String genGrid() {
		Random random = new Random();
		int n = random.nextInt(15) + 1, m = random.nextInt(15) + 1;
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

	}

}
