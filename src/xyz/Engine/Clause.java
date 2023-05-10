package xyz.Engine;

import java.util.ArrayList;
import java.util.Iterator;

public class Clause {
    ArrayList<Literal> contents = new ArrayList<>();

    public boolean isDecided() {
        return decided;
    }
    public void decideTrue() { decided = true; isTrue = true; }
    public void decideFalse() { decided = true; }
    public boolean isTrue() { return decided && isTrue; }
    public boolean isFalse() { return decided && !isTrue; }
    private boolean decided = false;
    private boolean isTrue = false;


    public boolean isUnit() { return contents.size() == 1; }
    public Variable getUnitVariableOrNull() { if (isUnit()) return getUnitLiteralOrNull().getVariable(); return null; }
    public Literal getUnitLiteralOrNull() { if (isUnit()) return contents.iterator().next(); return null; }
    public boolean isEmpty() { return contents.isEmpty(); }
    public int size() { return contents.size(); }
    public void addLiteral(Literal l) {
        contents.add(l);
    }
    public void removeLiteral(Literal l) {
        Iterator<Literal> litIt = contents.iterator();
        while (litIt.hasNext()) {
            if (litIt.equals(l)) litIt.remove();
        }
    }

    @Override
    public String toString() {
        if (contents.size() == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (Literal l : contents) {
            sb.append(l);
            sb.append(" v ");
        }
        return sb.substring(0, sb.length() - 3);
    }
}