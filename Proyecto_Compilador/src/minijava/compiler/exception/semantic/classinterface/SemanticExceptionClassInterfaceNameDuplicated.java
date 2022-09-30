package minijava.compiler.exception.semantic.classinterface;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.ClaseInterface;

public class SemanticExceptionClassInterfaceNameDuplicated extends SemanticException {

    private ClaseInterface claseInterface;

    public SemanticExceptionClassInterfaceNameDuplicated(String msg, ClaseInterface claseInterface){
        super(msg, claseInterface.getClaseOrinterfaceToken());
        this.claseInterface = claseInterface;
    }
}
