package Project;

public abstract class SearchProblem {

	State initialState;

	abstract State stateTransition(State state);

	abstract boolean goalTest(State state);

	abstract int pathCost(SearchTreeNode node);

	public SearchProblem(State initialState) {
		this.initialState = initialState;
	}

}
