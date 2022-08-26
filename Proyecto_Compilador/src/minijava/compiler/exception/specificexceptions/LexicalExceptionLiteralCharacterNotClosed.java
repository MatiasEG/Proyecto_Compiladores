package minijava.compiler.exception.specificexceptions;

import minijava.compiler.exception.LexicalException;

public class LexicalExceptionLiteralCharacterNotClosed extends LexicalException {
    public LexicalExceptionLiteralCharacterNotClosed(String lexemeError, int lineError, int columnError) {
        super("Error lexico en l:c -> "+lineError+":"+columnError+": Literal caracter no fue cerrado correctamente.", lexemeError, lineError, columnError);
    }
}
