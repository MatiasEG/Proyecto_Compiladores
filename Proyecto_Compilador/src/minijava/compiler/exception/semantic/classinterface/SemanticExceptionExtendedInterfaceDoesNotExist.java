package minijava.compiler.exception.semantic.classinterface;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.tables.ClassOrInterface;

public class SemanticExceptionExtendedInterfaceDoesNotExist extends SemanticException {

    private ClassOrInterface classOrInterface;

    public SemanticExceptionExtendedInterfaceDoesNotExist(ClassOrInterface classOrInterface, Token errorInterface){
        super("La interfaz \'"+errorInterface.getLexeme()+"\' de la que busca heredar la interfaz \'"+ classOrInterface.getNombre()+"\' no esta definida en el codigo.", errorInterface);
        this.classOrInterface = classOrInterface;
    }
}
