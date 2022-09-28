package minijava.compiler.exception.semantic;

import minijava.compiler.semantic.tables.ClaseInterface;
import minijava.compiler.semantic.tables.Metodo;

public class SemanticExceptionMethodNotRedefined extends SemanticException{

    private Metodo metodoNoRedefinido;
    private ClaseInterface errorClass;

    public SemanticExceptionMethodNotRedefined(Metodo metodoMalRedefinido, ClaseInterface errorClass){
        super("El metodo \'"+metodoMalRedefinido.getNombre()+"\' no fue redefinido correctamente por \'"+errorClass.getNombre()+"\'.");
        this.metodoNoRedefinido = metodoMalRedefinido;
        this.errorClass = errorClass;
    }

    @Override
    public int getRow() {
        return metodoNoRedefinido.getLineaDeclaracion();
    }

    @Override
    public String getLexeme() {
        return metodoNoRedefinido.getNombre();
    }
}
