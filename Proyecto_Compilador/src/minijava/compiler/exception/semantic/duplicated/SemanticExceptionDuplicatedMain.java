package minijava.compiler.exception.semantic.duplicated;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.ClaseInterface;
import minijava.compiler.semantic.tables.Metodo;

public class SemanticExceptionDuplicatedMain extends SemanticException {

    private Metodo mainDuplicado;
    private ClaseInterface claseOriginal;

    public SemanticExceptionDuplicatedMain(Metodo mainDuplicado, ClaseInterface claseOriginal){
        super("El metodo main esta duplicado en la linea: "+mainDuplicado.getLineNumber()+" se definio antes en la clase \'"+claseOriginal.getNombre()+"\'.", mainDuplicado.getToken());
        this.mainDuplicado = mainDuplicado;
        this.claseOriginal = claseOriginal;
    }
}
