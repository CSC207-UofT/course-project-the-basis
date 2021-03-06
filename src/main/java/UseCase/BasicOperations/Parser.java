package UseCase.BasicOperations;

import java.lang.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Parser Class that turns Strings into Expressions
 */
public class Parser {

    private String strExpr;
    private final ArrayList<Character> operators = new ArrayList<>(){{
        add('^');
        add('/');
        add('*');
        add('-');
        add('+');
    }};

    /**
     * @param strExpr String object
     */
    public Parser(String strExpr) {
        this.strExpr = strExpr;
    }

    /**
     * @return ArrayList<Integer>
     */
    public ArrayList<Integer> findOps() {
        /*
         * Function that finds the indices of operators of the Parser's string in order of precedence
         */
        ArrayList<ArrayList<Integer>> listOfOpIndexes = new ArrayList<>();
        for (Character operator : this.operators) {
            ArrayList<Integer> nest = new ArrayList<>();
            for (int j = 0; j < this.strExpr.length(); j++) {
                if (this.strExpr.charAt(j) == operator) {
                    nest.add(j);
                }
            }
            listOfOpIndexes.add(nest);
        }
        ArrayList<Integer> tempList = new ArrayList<>();
        tempList.addAll(listOfOpIndexes.get(1));
        tempList.addAll(listOfOpIndexes.get(2));
        ArrayList<Integer> tempList2 = new ArrayList<>();
        tempList2.addAll(listOfOpIndexes.get(3));
        tempList2.addAll(listOfOpIndexes.get(4));
        listOfOpIndexes.remove(listOfOpIndexes.get(2));
        Collections.sort(tempList);
        Collections.sort(tempList2);
        listOfOpIndexes.set(1, tempList);
        listOfOpIndexes.remove(listOfOpIndexes.get(3));
        listOfOpIndexes.set(2, tempList2);
        return listOfOpIndexes.stream()
                .flatMap(List::stream).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * @return boolean
     */
    public boolean checker() {
        /*
         * Function that checks if the Parser's string only contains a single operator
         */
        int count = 0;
        for (int i = 0; i<this.strExpr.length(); i++) {
            if (this.operators.contains(this.strExpr.charAt(i))) {
                count += 1;
            }
        }
        return count == 1;
    }

    /**
     * @return boolean
     */
    public boolean contains_onlyDigits() {
        /*
         * Function that checks if the Parser's string contains only digits.
         */
        for (int i = 0; i < this.strExpr.length(); i++){
            if (!Character.isDigit(this.strExpr.charAt(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * @return Expression
     * @throws ParserException
     * If the expression is invalid throws Parser Exception
     */
    public Expression stringToExpression() throws ParserException {
        /*
         * Function that converts the Parser's string into an Expression object
         */
        if (!this.validity()) {
            throw new ParserException("Invalid Input");
        }
        if (this.contains_onlyDigits()) {
            return new SingleExpression(Double.parseDouble(this.strExpr), "+", 0.0);
        }
        if ((Character.isDigit(this.strExpr.charAt(0)) ||
                Character.isDigit(this.strExpr.charAt(this.strExpr.length()-1))) && this.checker()) {
            int firstCount = 0;
            int i = 0;
            while (Character.isDigit(this.strExpr.charAt(i))) {
                firstCount += 1;
                i++;
            }
            return new SingleExpression(Double.parseDouble(this.strExpr.substring(0, firstCount)),
                    Character.toString(this.strExpr.charAt(1)),
                    Double.parseDouble(this.strExpr.substring(firstCount+1)));
        }
        else{
            ArrayList<Integer> listOfOpIndexes = this.findOps();
            int mainOp = listOfOpIndexes.get(listOfOpIndexes.size() - 1);
            Parser left = new Parser(this.strExpr.substring(0, mainOp));
            Parser right = new Parser(this.strExpr.substring(mainOp+1));
            Expression leftOp = left.stringToExpression();
            Expression rightOp = right.stringToExpression();
            return new MultiExpression(leftOp, Character.toString(this.strExpr.charAt(mainOp)), rightOp);
        }
    }

    /**
     * @return boolean
     */
    public boolean validity() {
        /*
         * Function that checks if the Parser's string is valid (can be turned into an expression)
         */
        this.strExpr = this.strExpr.replaceAll(" ", "");
        if (!Character.isDigit(this.strExpr.charAt(0)) ||
                !Character.isDigit(this.strExpr.charAt(this.strExpr.length()-1))) {
            return false;
        }
        List<Character> lst = Arrays.asList('+','-','*', '/', '^');
        for (int i=0; i<this.strExpr.length()-1; i++) {
            char curr = this.strExpr.charAt(i);
            char next = this.strExpr.charAt(i+1);
            if (lst.contains(curr) && !Character.isDigit(next)) {
                return false;
            }
            if (Character.isDigit(curr) && !lst.contains(next) && !Character.isDigit(next)) {
                return false;
            }
        }
        return true;
    }
}