package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionAssignmentWithDiferentTypes extends SemanticException {

    public SemanticExceptionAssignmentWithDiferentTypes(Token assignmentToken){
        super("Los tipos de la asignacion no coinciden de ambos lados.", assignmentToken);
    }
}
