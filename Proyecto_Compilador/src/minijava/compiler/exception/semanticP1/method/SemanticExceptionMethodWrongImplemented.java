package minijava.compiler.exception.semanticP1.method;

import minijava.compiler.exception.SemanticException;
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
