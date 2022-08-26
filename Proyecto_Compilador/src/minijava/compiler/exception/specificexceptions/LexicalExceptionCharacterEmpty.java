package minijava.compiler.exception.specificexceptions;

import minijava.compiler.exception.LexicalException;

public class LexicalExceptionCharacterEmpty extends LexicalException {

    public LexicalExceptionCharacterEmpty(String lexemeError, int lineError, int columnError) {
        super("Error lexico en l:c -> "+lineError+":"+columnError+": Literal caracter vacio.", lexemeError, lineError, columnError);
    }
}
