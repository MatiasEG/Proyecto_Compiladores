package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionIncompatibleTypesOnEquals extends SemanticException {

    public SemanticExceptionIncompatibleTypesOnEquals(Token equalTk){
        super("Los valores a comparar con el \'==\'/\'!=\' no son comparables.", equalTk);
    }
}
