package minijava.compiler.exception;

import minijava.compiler.lexical.analyzer.Token;

import java.util.List;

public class SyntacticException extends Exception{

    private List<String> posibleTokens;
    private Token errorToken;
    private int row;

    public SyntacticException(Token errorToken, List<String> posibleTokens, int row){
        super(posibleTokens.get(0));
        this.posibleTokens = posibleTokens;
        this.errorToken = errorToken;
        this.row = row;
    }

    public List<String> getPosibleTokens() { return posibleTokens; }

    public Token getErrorToken() { return errorToken; }

    public int getRow(){ return row; }
}
