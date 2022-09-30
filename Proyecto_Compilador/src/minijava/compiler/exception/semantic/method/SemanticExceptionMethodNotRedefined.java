package minijava.compiler.exception.semantic.method;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.ClaseInterface;
import minijava.compiler.semantic.tables.Metodo;

public class SemanticExceptionMethodNotRedefined extends SemanticException {

    private Metodo metodoNoRedefinido;
    private ClaseInterface errorClass;

    public SemanticExceptionMethodNotRedefined(Metodo metodoMalRedefinido, ClaseInterface errorClass){
        super("El metodo \'"+metodoMalRedefinido.getLexeme()+"\' no fue redefinido correctamente por \'"+errorClass.getNombre()+"\'.", metodoMalRedefinido.getToken());
        this.metodoNoRedefinido = metodoMalRedefinido;
        this.errorClass = errorClass;
    }
}
