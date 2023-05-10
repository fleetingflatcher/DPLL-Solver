package xyz.HID;

import xyz.Engine.Clause;
import xyz.Engine.Literal;

public class Parser {
    public static Clause ParseClause(String s) {
        Clause clause = new Clause();
        s = s.strip();
        String[] tokens = s.split(" ");
        for (String token : tokens) {
            boolean negation = token.charAt(0) == '!' || token.charAt(0) == '~' || token.charAt(0) == '-';
            if (negation) token = token.substring(1);
            int designation;
            try {
                designation = Integer.parseInt(token);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                designation = (token.charAt(0) - 'a') / token.charAt(1);
            }
            if (designation == 0) return clause;
            Literal literal = new Literal(designation, negation);
            clause.addLiteral(literal);
        }
        return clause;
    }
}
