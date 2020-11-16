package Project;

import java.util.ArrayList;

public abstract class SearchProblem {

	State initialState;

	abstract State stateTransition(State state, Operator operator);

	abstract boolean goalTest(State state);

	abstract int pathCost(SearchTreeNode node);

	abstract ArrayList<Operator> getOperators();

	public SearchProblem(State initialState) {
		this.initialState = initialState;
	}

	abstract int heuristic(State state, int id);

}
