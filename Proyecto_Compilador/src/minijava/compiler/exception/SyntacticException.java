package minijava.compiler.exception;

import minijava.compiler.lexical.analyzer.Token;

public class SyntacticException extends Exception{

    private String posibleTokens;
    private Token errorToken;

    public SyntacticException(Token errorToken, String posibleTokens){
        super("Error en la linea: "+errorToken.getRow()+", se encontro el lexema "+errorToken.getLexeme()+" y se esperaba un elemento del conjunto "+posibleTokens);
        this.posibleTokens = posibleTokens;
        this.errorToken = errorToken;
    }

    public String getPosibleTokens() { return posibleTokens; }

    public Token getErrorToken() { return errorToken; }

    public int getRow(){ return errorToken.getRow(); }
}
