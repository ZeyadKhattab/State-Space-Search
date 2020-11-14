package Project;

public class Main {

	public static void main(String[] args) {
		String grid = MissionImpossible.genGrid();
		grid = "5,5;1,2;4,0;0,3,2,1,3,0,3,2,3,4,4,3;20,30,90,80,70,60;3";
		System.out.println(grid);
		String ans = MissionImpossible.solve(grid, "ID", false);
		System.out.println(ans);
	}

}
