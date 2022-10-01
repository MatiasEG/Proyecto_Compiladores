package minijava.compiler.exception.semantic.classinterface;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.ClassOrInterface;

public class SemanticExceptionClassInterfaceNameDuplicated extends SemanticException {

    private ClassOrInterface classOrInterface;

    public SemanticExceptionClassInterfaceNameDuplicated(String msg, ClassOrInterface classOrInterface){
        super(msg, classOrInterface.getClassOrinterfaceToken());
        this.classOrInterface = classOrInterface;
    }
}
