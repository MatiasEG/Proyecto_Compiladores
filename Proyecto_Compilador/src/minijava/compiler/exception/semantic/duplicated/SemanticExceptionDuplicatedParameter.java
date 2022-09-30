package minijava.compiler.exception.semantic.duplicated;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.Parametro;

public class SemanticExceptionDuplicatedParameter extends SemanticException {

    private Parametro parametro;

    public SemanticExceptionDuplicatedParameter(Parametro parametro){
        super("El parametro numero ("+parametro.getPosicion()+"): \'"+parametro.getNombre()+"\' del metodo \'"+parametro.getMetodo().getLexeme()+"\' tiene un nombre repetido.", parametro.getVarToken());
        this.parametro = parametro;
    }
}
