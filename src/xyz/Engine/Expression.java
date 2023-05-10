package xyz.Engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class Expression {
    ArrayList<Clause> clauses = new ArrayList<>();

    ArrayList<Variable> variables = new ArrayList<>();
    private int numInputs;

    public void setNumInputs(int i) { numInputs = i; }

    public boolean addClause(Clause c) {
        for (Literal l : c.contents) {
            boolean exists = false;
            for (Variable v : variables) {
                if (l.isOfVariable(v)) {
                    exists = true;
                    break;
                }
            }
            if (!exists)
                if (variables.size() == numInputs) return false;
                else {
                    Variable newVar = new Variable(l.getDesignation());
                    variables.add(newVar);
                    l.isOfVariable(newVar);
                }

        }
        clauses.add(c);
        return true;
    }
    public void resolveVariable(Variable v) {
        for (Clause c : clauses) {
            Iterator<Literal> litIt = c.contents.iterator();
            while (litIt.hasNext()) {
                Literal l = litIt.next();
                if (l.isOfVariable(v)) {
                    if (!l.negation) {
                        if (v.value == BooleanValue.TRUE) {
                            litIt.remove();
                            c.decideTrue();
                        }
                        if (v.value == BooleanValue.FALSE) {
                            litIt.remove();
                        }
                    }
                    else {
                        if (v.value == BooleanValue.TRUE) litIt.remove();
                        if (v.value == BooleanValue.FALSE) {
                            litIt.remove();
                            c.decideTrue();
                        }
                    }
                }
            }
            if (c.isEmpty() & !c.isTrue())
                // The clause is definitively false, and the expression is unsatisfiable.
                c.decideFalse();
        }

    }
    public Variable setFirstVariableToValue(BooleanValue value) {
        Variable select = null;
        for (Variable v : variables) {
            if (v.value == BooleanValue.VAR) {
                select = v;
                break;
            }
        }
        select.value = value;
        resolveVariable(select);
        return select;
    }
    public Variable selectModeVariable() {
        HashMap<Variable, Integer> map = new HashMap<>();
        for (Clause c : clauses) {
            for (Literal l : c.contents) {
                map.put(l.getVariable(), 1 + map.getOrDefault(l.getVariable(), 0));
            }
        }
        Variable mode = null;
        for (Variable v : map.keySet()) {
            if (mode == null) mode = v;
            else if (map.get(mode) < map.get(v)) {
                mode = v;
            }
        }
        return mode;
    }
    public boolean variableAssociationCheck() {
        for (Clause c : clauses) {
            for (Literal l : c.contents) {
                if (!l.assignedVariable) return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("c CNF Expression with ");
        sb.append(variables.size());
        sb.append(" inputs.\n");
        sb.append("c ");
        for (int i = 0; i < variables.size(); ++i) {
            sb.append(variables.get(i));
            if (i < variables.size() - 1) sb.append(", ");
        }
        sb.append('\n');

        for (Clause c : clauses) {
            sb.append("{ ");
            sb.append(c);
            sb.append(" }, ");
        }
        return sb.substring(0, sb.length() - 2);
    }
}
