package Project;

public class SearchTreeNode {
	State state;
	int depth, cost;
	SearchTreeNode parent;
	Operator operator;

	SearchTreeNode(SearchTreeNode parent, State state, Operator operator, SearchProblem problem) {
		cost = problem.pathCost(this);
		this.state = state;
		this.parent = parent;
		this.operator = operator;
		depth = parent.depth + 1;
	}

	SearchTreeNode(SearchProblem problem) { // called to create a root node
		state = problem.initialState;
		depth = 0;
		parent = null;
		operator = null;
		cost = problem.pathCost(this);
	}

}
