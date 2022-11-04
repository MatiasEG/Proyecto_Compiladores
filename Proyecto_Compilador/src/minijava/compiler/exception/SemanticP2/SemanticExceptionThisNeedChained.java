package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionThisNeedChained extends SemanticException {

    public SemanticExceptionThisNeedChained(Token thisTk){
        super("El acceso a this requiere de un encadenado.", thisTk);
    }
}
