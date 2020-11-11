package Project;

public class Main {

	public static void main(String[] args) {
		String grid = MissionImpossible.genGrid();
		System.out.println(grid);
		MissionImpossible.solve(grid, "bfs", false);
	}

}
