package minijava.compiler.exception.specificexceptions;

import minijava.compiler.exception.LexicalException;

public class LexicalExceptionGeneric extends LexicalException {

    public LexicalExceptionGeneric(String lexemeError, int lineError, int columnError){
        super("Error lexico en l:c -> "+lineError+":"+columnError+": Error al analizar el proximo token.", lexemeError,lineError, columnError);
    }

}
