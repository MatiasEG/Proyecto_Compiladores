package minijava.compiler.exception.semanticP1.method;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.semantic.tables.ClassOrInterface;
import minijava.compiler.semantic.tables.Method;

public class SemanticExceptionMethodNotRedefined extends SemanticException {

    private Method methodNoRedefinido;
    private ClassOrInterface errorClass;

    public SemanticExceptionMethodNotRedefined(Method methodMalRedefinido, ClassOrInterface errorClass){
        super("El metodo \'"+ methodMalRedefinido.getMethodName()+"\' no fue redefinido correctamente por \'"+errorClass.getNombre()+"\'.", methodMalRedefinido.getMethodToken());
        this.methodNoRedefinido = methodMalRedefinido;
        this.errorClass = errorClass;
    }
}
