package tests;

import code.mission.MissionImpossible;

public class Test {

	public static void main(String[] args) {
		String grid14 = "5,5;1,2;4,0;0,3,2,1,3,0,3,2,3,4,4,3;20,30,90,80,70,60;3";
		String ans = MissionImpossible.solve(grid14, "AS1", true);
		System.out.println(ans);
	}

}
