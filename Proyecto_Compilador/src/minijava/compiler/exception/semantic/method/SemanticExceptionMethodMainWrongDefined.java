package minijava.compiler.exception.semantic.method;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.Method;

public class SemanticExceptionMethodMainWrongDefined extends SemanticException {

    private Method wrongDefinedMain;

    public SemanticExceptionMethodMainWrongDefined(Method wrongDefinedMain){
        super("El metodo main declarado en la linea ("+wrongDefinedMain.getMethodRow()+") esta mal declarado.", wrongDefinedMain.getMethodToken());
        this.wrongDefinedMain = wrongDefinedMain;
    }
}
