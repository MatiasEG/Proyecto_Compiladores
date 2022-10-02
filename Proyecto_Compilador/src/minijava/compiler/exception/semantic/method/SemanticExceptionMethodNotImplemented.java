package minijava.compiler.exception.semantic.method;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.Class;
import minijava.compiler.semantic.tables.Method;

public class SemanticExceptionMethodNotImplemented extends SemanticException {

    private Class claseQueNoImplementaMetodo;
    private Method methodNoImplementado;

    public SemanticExceptionMethodNotImplemented(Class claseQueNoImplementaMetodo, Method methodNoImplementado){
        super("La clase \'"+claseQueNoImplementaMetodo.getNombre()+"\' no implementa el metodo \'" + methodNoImplementado.getMethodName()+
                "\' de la interface \'"+ methodNoImplementado.getClassDeclaredMethod()+"\'.", methodNoImplementado.getMethodToken());
        this.claseQueNoImplementaMetodo = claseQueNoImplementaMetodo;
        this.methodNoImplementado = methodNoImplementado;
    }
}
