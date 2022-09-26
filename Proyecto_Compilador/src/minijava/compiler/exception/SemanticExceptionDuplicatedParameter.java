package minijava.compiler.exception;

import minijava.compiler.semantic.Parametro;

public class SemanticExceptionDuplicatedParameter extends SemanticException{

    private Parametro parametro;

    public SemanticExceptionDuplicatedParameter(Parametro parametro){
        super("El parametro numero ("+parametro.getPosicion()+"): \'"+parametro.getNombre()+"\' del metodo \'"+parametro.getMetodo().getNombre()+"\' tiene un nombre repetido.");
        this.parametro = parametro;
    }

    public String getLexeme(){ return parametro.getNombre(); }

    public int getRow(){ return parametro.getLineaDeclaracion(); }
}
