package minijava.compiler.exception.semantic.method;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.Metodo;

public class SemanticExceptionMethodMainWrongDefined extends SemanticException {

    private Metodo wrongDefinedMain;

    public SemanticExceptionMethodMainWrongDefined(Metodo wrongDefinedMain){
        super("El metodo main declarado en la linea ("+wrongDefinedMain.getLineNumber()+") esta mal declarado.", wrongDefinedMain.getToken());
        this.wrongDefinedMain = wrongDefinedMain;
    }
}
