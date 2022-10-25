package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionInvalidTypeForIntAsignation extends SemanticException {

    public SemanticExceptionInvalidTypeForIntAsignation(Token asignacion){
        super("Los tipos de ambos lados de la asignacion \'"+asignacion.getLexeme()+"\' deben ser enteros.", asignacion);
    }
}
