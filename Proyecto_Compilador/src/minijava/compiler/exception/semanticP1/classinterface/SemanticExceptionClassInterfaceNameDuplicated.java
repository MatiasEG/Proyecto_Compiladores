package minijava.compiler.exception.semanticP1.classinterface;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.semantic.tables.ClassOrInterface;

public class SemanticExceptionClassInterfaceNameDuplicated extends SemanticException {

    private ClassOrInterface classOrInterface;

    public SemanticExceptionClassInterfaceNameDuplicated(String msg, ClassOrInterface classOrInterface){
        super(msg, classOrInterface.getClassOrinterfaceToken());
        this.classOrInterface = classOrInterface;
    }
}
