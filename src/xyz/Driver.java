package xyz;

import xyz.Engine.Expression;
import xyz.Engine.Simplifier;
import xyz.Engine.Solver;
import xyz.HID.Parser;

import java.util.Scanner;
public class Driver {


    public static Scanner in = new Scanner(System.in);
    public static void main(String args[]) {

        Expression currentExpression = new Expression();

        System.out.println("Enter number of inputs: ");
        int numInputs = in.nextInt();
        currentExpression.setNumInputs(numInputs);

        System.out.println("Number of clauses? ");
        int numClause = in.nextInt();
        if (in.hasNextLine()) in.nextLine();    // Flush the buffer.
        System.out.println("""
                Preparing to enter clauses. Enter clauses in CNF format:
                \t-\tSeries of integer literals delineated by spaces.
                \t-\tLiterals can be preceded by '-' in order to negate the literal.
                \t-\tEnd each clause with a zero.""");
        for (int i = 0; i < numClause; ++i) {
            System.out.println("Enter clause number " + i + ": ");
            String clauseString = in.nextLine();
            currentExpression.addClause(Parser.ParseClause(clauseString));
        }
        System.out.println("Variable association check: " + currentExpression.variableAssociationCheck());
        System.out.println(currentExpression);

        System.out.println("Simplifying once...");
        Solver.simplifyAndCatchUnsat(currentExpression);
        System.out.println(currentExpression);
        System.out.println("Attepting to solve...");
        Solver.solveRecursive(currentExpression);
    }
}