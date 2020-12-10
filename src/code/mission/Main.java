package code.mission;

public class Main {

	public static void main(String[] args) {
		String grid = MissionImpossible.genGrid();
		grid = "6,5;0,1;0,2;0,4,0,5,1,0,1,5,2,0,3,3,4,1,4,2,4,4;39,71,63,10,97,85,43,5,82;1";
		System.out.println(grid);
		String ans = MissionImpossible.solve(grid, "UC", true);
		System.out.println(ans);
	}

}
