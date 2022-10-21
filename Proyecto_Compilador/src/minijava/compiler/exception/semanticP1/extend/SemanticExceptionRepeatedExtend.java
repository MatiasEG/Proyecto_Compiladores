package minijava.compiler.exception.semanticP1.extend;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionRepeatedExtend extends SemanticException {

    public SemanticExceptionRepeatedExtend(Token repeatedExtend){
        super("La clase \'"+repeatedExtend.getLexeme()+"\' esta siendo extendida dos veces por una misma clase.", repeatedExtend);
    }
}
