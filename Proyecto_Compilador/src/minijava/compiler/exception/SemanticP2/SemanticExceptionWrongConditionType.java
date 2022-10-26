package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionWrongConditionType extends SemanticException {

    public SemanticExceptionWrongConditionType(Token estructuraDeControl){
        super("El tipo de la expresion de condicion debe ser boolean.", estructuraDeControl);
    }
}
