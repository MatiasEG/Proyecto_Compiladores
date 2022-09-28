package minijava.compiler.exception.semantic;

import minijava.compiler.semantic.tables.Clase;
import minijava.compiler.semantic.tables.ClaseInterface;
import minijava.compiler.semantic.tables.Metodo;

public class SemanticExceptionDuplicatedMain extends SemanticException{

    private Metodo mainDuplicado;
    private ClaseInterface claseOriginal;

    public SemanticExceptionDuplicatedMain(Metodo mainDuplicado, ClaseInterface claseOriginal){
        super("El metodo main esta duplicado en la linea: "+mainDuplicado.getLineaDeclaracion()+" se definio antes en la clase \'"+claseOriginal.getNombre()+"\'.");
        this.mainDuplicado = mainDuplicado;
        this.claseOriginal = claseOriginal;
    }

    @Override
    public int getRow() {
        return mainDuplicado.getLineaDeclaracion();
    }

    @Override
    public String getLexeme() {
        return mainDuplicado.getNombre();
    }
}
