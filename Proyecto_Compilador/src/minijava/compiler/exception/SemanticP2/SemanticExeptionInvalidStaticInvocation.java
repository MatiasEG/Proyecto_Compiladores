package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExeptionInvalidStaticInvocation extends SemanticException {

    public SemanticExeptionInvalidStaticInvocation(Token idMet){
        super("Solo es posible invocar de manera estatica metodos estaticos.", idMet);
    }
}
