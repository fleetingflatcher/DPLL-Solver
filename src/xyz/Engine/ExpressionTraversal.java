package xyz.Engine;

import java.util.Stack;

class ExpressionTraversalOperation {
    Variable var;
    boolean value;
}
public class ExpressionTraversal {

    ExpressionTraversal(int attempt) {
        this.attempt = attempt;
    }
    int attempt;

    Stack<ExpressionTraversalOperation> ops = new Stack<>();
}
