package Project;

public class SearchTreeNode {
	State state;
	int depth, cost;
	SearchTreeNode parent;
	Operator operator;

	SearchTreeNode(SearchTreeNode parent, State state, Operator operator) {
		// Cost assignment remaining
		this.state = state;
		this.parent = parent;
		this.operator = operator;
		depth = parent == null ? 0 : parent.depth + 1;
	}

}

class Node {

}
