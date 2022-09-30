package minijava.compiler.exception.semantic.duplicated;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.Metodo;

public class SemanticExceptionDuplicatedMethod extends SemanticException {

    private Metodo metodo;

    public SemanticExceptionDuplicatedMethod(Metodo metodo){
        super("El metodo \'"+metodo.getLexeme()+"\' de la clase \'"+metodo.getClaseDefinido()+"\' esta mal redefinido.", metodo.getToken());
        this.metodo = metodo;
    }

}
