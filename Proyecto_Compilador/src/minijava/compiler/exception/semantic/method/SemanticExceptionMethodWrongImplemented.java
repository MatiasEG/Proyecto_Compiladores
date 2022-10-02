package minijava.compiler.exception.semantic.method;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.Class;
import minijava.compiler.semantic.tables.Method;

public class SemanticExceptionMethodWrongImplemented extends SemanticException {

    private Class claseQueImplementaMal;
    private Method methodMalImplementado;

    public SemanticExceptionMethodWrongImplemented(Class claseQueImplementaMal, Method methodMalImplementado){
        super("La clase \'"+claseQueImplementaMal.getNombre()+"\' implementa, pero no correctamente el metodo \'" + methodMalImplementado.getMethodName()+
                "\' de la interface \'"+ methodMalImplementado.getClassDeclaredMethod()+"\'.", methodMalImplementado.getMethodToken());
        this.claseQueImplementaMal = claseQueImplementaMal;
        this.methodMalImplementado = methodMalImplementado;
    }
}
