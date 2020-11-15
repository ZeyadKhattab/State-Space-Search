package Project;

public class MissionImpossibleOperator extends Operator {
	enum Operator {
		RIGHT, LEFT, UP, DOWN, PICKUP, DROP

	}

	Operator operator;

	public MissionImpossibleOperator(Operator operator) {
		this.operator = operator;
	}
}
