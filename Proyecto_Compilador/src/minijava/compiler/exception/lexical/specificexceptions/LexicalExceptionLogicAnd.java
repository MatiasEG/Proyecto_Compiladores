package minijava.compiler.exception.lexical.specificexceptions;

import minijava.compiler.exception.lexical.LexicalException;

public class LexicalExceptionLogicAnd extends LexicalException {
    public LexicalExceptionLogicAnd(String lexemeError, int lineError, int columnError) {
        super("Error lexico en l:c -> "+lineError+":"+columnError+": Falto '&' para representar el operador logico AND.", lexemeError, lineError, columnError);
    }
}
