package minijava.compiler.exception.semantic;

import minijava.compiler.lexical.analyzer.Token;

public abstract class SemanticException extends Exception{

    protected Token errorToken;

    public SemanticException(String msg, Token errorToken){
        super(msg);
        this.errorToken = errorToken;
    }

    public int getRow(){ return errorToken.getLineNumber(); };

    public String getLexeme(){ return errorToken.getLexeme(); };
}
