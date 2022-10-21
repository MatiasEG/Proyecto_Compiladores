package minijava.compiler.exception;

import minijava.compiler.lexical.analyzer.Token;

public abstract class SemanticException extends Exception{

    protected Token errorToken;

    public SemanticException(String msg, Token errorToken){
        super(msg);
        this.errorToken = errorToken;
    }

    public int getRow(){ return errorToken.getRow(); };

    public String getLexeme(){ return errorToken.getLexeme(); };
}
