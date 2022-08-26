package minijava.compiler.exception.specificexceptions;

import minijava.compiler.exception.LexicalException;

public class LexicalExceptionString extends LexicalException {

    public LexicalExceptionString(String lexemeError, int lineError, int columnError){
        super("Error lexico en l:c -> "+lineError+":"+columnError+": El string no fue cerrado correctamente.", lexemeError,lineError, columnError);
    }

}
