package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionVarNotExist extends SemanticException {

    public SemanticExceptionVarNotExist(Token varId){
        super("La variable \'"+varId.getLexeme()+"\' no fue declarada antes de ser usada.", varId);
    }
}
