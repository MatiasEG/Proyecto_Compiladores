package minijava.compiler.exception.semantic.classinterface;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.ClassOrInterface;

public class SemanticExceptionInterfaceExtendsClase extends SemanticException {

    private ClassOrInterface intDesc;

    public SemanticExceptionInterfaceExtendsClase(ClassOrInterface intDesc){
        super("La interfaz \'"+intDesc.getNombre()+"\' esta intentando heredar de una clase.", intDesc.getClassOrinterfaceToken());
        this.intDesc = intDesc;
    }
}
