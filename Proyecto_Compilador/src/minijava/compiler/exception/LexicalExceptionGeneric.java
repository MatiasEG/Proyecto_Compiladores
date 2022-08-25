package minijava.compiler.exception;

public class LexicalExceptionGeneric extends Exception{

    private int lineError;
    private int columnError;
    private String lexemeError;
    private String detailedMsg;

    public LexicalExceptionGeneric(String lexemeError, int lineError, int columnError){
        super("[Error:"+lexemeError+"|"+lineError+"]");
        this.lineError = lineError;
        this.columnError = columnError;
        this.lexemeError = lexemeError;
        this.detailedMsg = "Error lexico en linea "+lineError+": Error al analizar el siguiente token.";
    }

    public String getDetailedMsg(){
        return detailedMsg;
    }

    public int getLineError(){ return lineError; }

    public int getColumnError(){ return columnError; }
}
