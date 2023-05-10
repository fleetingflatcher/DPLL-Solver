package xyz.Engine;

class LiteralOccurenceRecord {
    int occurred;
    int negated;
}
public class Literal {
    public Literal(int name, boolean neg) {
        this.designation = name;
        this.negation = neg;
        variable = null;
    }

    private final int designation;
    private Variable variable;
    public boolean negation;
    public boolean assignedVariable = false;

    public int getDesignation() { return designation; }
    public Variable getVariable() { return variable; }
    public boolean isOfVariable(Variable v) {
        if (designation == v.designation) {
            assignedVariable = true;
            variable = v;
            return true;
        }
        return false;
    }
    public String toString() {
        if (variable != null) return negation ? "~" + variable : variable.toString();
        return negation ? "~" + designation : String.valueOf(designation);
    }
}