package minijava.compiler.exception.semantic.classinterface;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.ClaseInterface;

public class SemanticExceptionInterfaceExtendsClase extends SemanticException {

    private ClaseInterface intDesc;

    public SemanticExceptionInterfaceExtendsClase(ClaseInterface intDesc){
        super("La interfaz \'"+intDesc.getNombre()+"\' esta intentando heredar de una clase.", intDesc.getClaseOrinterfaceToken());
        this.intDesc = intDesc;
    }
}
