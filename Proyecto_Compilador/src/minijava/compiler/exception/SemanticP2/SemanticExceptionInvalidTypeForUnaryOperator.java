package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionInvalidTypeForUnaryOperator extends SemanticException {

    public SemanticExceptionInvalidTypeForUnaryOperator(Token unaryOperator){
        super("El tipo al que se le quiere aplicar el operador unario \'"+unaryOperator.getLexeme()+"\' es un tipo incompatible." ,unaryOperator);
    }
}
