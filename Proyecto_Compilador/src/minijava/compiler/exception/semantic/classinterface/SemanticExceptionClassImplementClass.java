package minijava.compiler.exception.semantic.classinterface;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.ClassOrInterface;

public class SemanticExceptionClassImplementClass extends SemanticException {

    private ClassOrInterface claseImplementa;

    public SemanticExceptionClassImplementClass(ClassOrInterface claseImplementa){
        super("La clase \'"+claseImplementa.getNombre()+"\' esta intentando implementar una clase concreta.", claseImplementa.getClassOrinterfaceToken());
        this.claseImplementa = claseImplementa;
    }
}
