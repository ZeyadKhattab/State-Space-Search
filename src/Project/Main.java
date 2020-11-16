package Project;

public class Main {

	public static void main(String[] args) {
		String grid14 = "14,14;13,9;1,13;5,3,9,7,11,10,8,3,10,7,13,6,11,1,5,2;76,30,2,49,63,43,72,1;6";
		String ans = MissionImpossible.solve(grid14, "GR2", true);
		System.out.println(ans);
	}

}
