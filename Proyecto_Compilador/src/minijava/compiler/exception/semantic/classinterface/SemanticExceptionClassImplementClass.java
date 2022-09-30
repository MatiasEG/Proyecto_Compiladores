package minijava.compiler.exception.semantic.classinterface;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.ClaseInterface;

public class SemanticExceptionClassImplementClass extends SemanticException {

    private ClaseInterface claseImplementa;

    public SemanticExceptionClassImplementClass(ClaseInterface claseImplementa){
        super("La clase \'"+claseImplementa.getNombre()+"\' esta intentando implementar una clase concreta.", claseImplementa.getClaseOrinterfaceToken());
        this.claseImplementa = claseImplementa;
    }
}
