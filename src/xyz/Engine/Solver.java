package xyz.Engine;

import java.util.*;

public class Solver {

    public static boolean solveRecursive(Expression expr) {
        Stack<Variable> varStack = new Stack<>();
        return solve(expr, varStack);
    }

    public static boolean solve(Expression expr, Stack<Variable> varStack) {
        simplifyAndCatchUnsat(expr);
        Variable var = expr.setFirstVariableToValue(BooleanValue.FALSE);
        varStack.push(var);
        if (solve(expr, varStack)) return true;
        varStack.peek().value = BooleanValue.TRUE;
        return solve(expr, varStack);
    }
    public static boolean simplifyAndCatchUnsat(Expression expr) {
        HashMap<Variable, LiteralOccurenceRecord> occurenceRecord = new HashMap<>();
        List<Clause> clauses = expr.clauses;
        List<Clause> newClauses = new ArrayList<>();

        Iterator<Clause> clauseIter = clauses.iterator();
        while (clauseIter.hasNext()) {
            Clause clause = clauseIter.next();
            if (!clause.isDecided()) {
                // Check for pure literals, count all literal occurrences
                // TODO:    This is no longer dynamically accurate, due to the
                // TODO:    removal of clauses by the cleanup section below.
                for (Literal l : clause.contents) {
                    LiteralOccurenceRecord record = occurenceRecord.getOrDefault(l.getVariable(), new LiteralOccurenceRecord());
                    record.occurred++;
                    if (l.negation) record.negated++;
                    occurenceRecord.put(l.getVariable(), record);
                }

                // Check if the clause contains only one literal; if it is a unit clause.
                // if so, Propagate the variable value, assigning it the only value which makes the clause true.
                if (clause.isUnit()) {
                    Variable unitVariable = clause.getUnitVariableOrNull();
                    if (clause.getUnitLiteralOrNull().negation) unitVariable.value = BooleanValue.FALSE;
                    else unitVariable.value = BooleanValue.TRUE;
                    expr.resolveVariable(unitVariable);
                }
            }

            // Clean up decided clauses, and catch unsatisfiable ones.
            if (clause.isDecided()) {
                if (clause.isTrue())
                    clauseIter.remove();
                else if (clause.isFalse())
                    return false;
            }
        }

        // Check occurrence record for pure literals
        // TODO: May fail once clauses are removed during iteration.
        for (Variable v : occurenceRecord.keySet()) {
            LiteralOccurenceRecord record = occurenceRecord.get(v);
            boolean pure = false;
            if (record.negated == record.occurred) {
                v.value = BooleanValue.FALSE;
                pure = true;
            }
            else if (record.negated == 0) {
                v.value = BooleanValue.TRUE;
                pure = true;
            }
            if (pure) expr.resolveVariable(v);
        }
        return true;
    }
}
