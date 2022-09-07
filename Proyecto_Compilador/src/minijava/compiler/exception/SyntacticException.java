package minijava.compiler.exception;

import minijava.compiler.lexical.analyzer.Token;

import java.util.ArrayList;
import java.util.List;

public class SyntacticException extends Exception{

    private List<String> obtainedTokens;

    public SyntacticException(Token expectedToken, List<String> obtainedTokens){
        super(obtainedTokens.get(0));
        this.obtainedTokens = obtainedTokens;
    }
}
