package Project;

public class MissionImpossibleOperator extends Operator {
	enum Operator {
		RIGHT, LEFT, UP, DOWN, PICKUP, DROP

	}

	Operator operator;
	int memberIdx;
	public MissionImpossibleOperator(Operator operator,int memberIdx) {
		this.operator = operator;
		this.memberIdx=memberIdx;
	}
	public MissionImpossibleOperator(Operator operator) {
		this.operator = operator;
	}
}
