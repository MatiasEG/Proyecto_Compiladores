package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionInvalidNullAsignment extends SemanticException {

    public SemanticExceptionInvalidNullAsignment(Token idVar){
        super("Asignar null a la declaracion de una variable local es invalido.", idVar);
    }
}
