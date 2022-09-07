package minijava.compiler.exception.lexical.specificexceptions;

import minijava.compiler.exception.lexical.LexicalException;

public class LexicalExceptionCharacterNotClosed extends LexicalException {
    public LexicalExceptionCharacterNotClosed(String lexemeError, int lineError, int columnError) {
        super("Error lexico en l:c -> "+lineError+":"+columnError+": Literal caracter no fue cerrado correctamente.", lexemeError, lineError, columnError);
    }
}
