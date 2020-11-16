package code.generic;

import java.util.ArrayList;

public abstract class SearchProblem {

	public State initialState;

	public abstract State stateTransition(State state, Operator operator);

	public abstract boolean goalTest(State state);

	public abstract int pathCost(SearchTreeNode node);

	public abstract ArrayList<Operator> getOperators();

	public SearchProblem(State initialState) {
		this.initialState = initialState;
	}

	public abstract int heuristic(State state, int id);

}
