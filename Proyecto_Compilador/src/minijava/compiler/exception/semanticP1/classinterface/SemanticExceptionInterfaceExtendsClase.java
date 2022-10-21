package minijava.compiler.exception.semanticP1.classinterface;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.semantic.tables.ClassOrInterface;

public class SemanticExceptionInterfaceExtendsClase extends SemanticException {

    private ClassOrInterface intDesc;

    public SemanticExceptionInterfaceExtendsClase(ClassOrInterface intDesc){
        super("La interfaz \'"+intDesc.getNombre()+"\' esta intentando heredar de una clase.", intDesc.getClassOrinterfaceToken());
        this.intDesc = intDesc;
    }
}
