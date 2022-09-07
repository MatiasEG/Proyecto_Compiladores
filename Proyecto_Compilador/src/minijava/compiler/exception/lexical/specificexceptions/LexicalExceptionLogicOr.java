package minijava.compiler.exception.lexical.specificexceptions;

import minijava.compiler.exception.lexical.LexicalException;

public class LexicalExceptionLogicOr extends LexicalException {

    public LexicalExceptionLogicOr(String lexemeError, int lineError, int columnError) {
        super("Error lexico en l:c -> "+lineError+":"+columnError+": Falto '|' para representar el operador logico OR.", lexemeError, lineError, columnError);
    }
}
