package minijava.compiler.exception.semantic.method;

import minijava.compiler.exception.semantic.SemanticException;
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
