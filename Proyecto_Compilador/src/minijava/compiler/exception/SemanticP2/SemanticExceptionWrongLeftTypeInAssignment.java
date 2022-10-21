package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
public class SemanticExceptionWrongLeftTypeInAssignment extends SemanticException {

    public SemanticExceptionWrongLeftTypeInAssignment(Token tipoAsignacion){
        super("El tipo izquierdo de la asignacion no es valido para estar de ese lado de la asignacion.", tipoAsignacion);
    }
}
