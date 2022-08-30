package minijava.compiler.exception;

public class LexicalException extends Exception{

    private int lineError;
    private int columnError;
    private String lexemeError;
    private String detailedMsg;

    public LexicalException(String detailedMsg, String lexemeError, int lineError, int columnError){
        super("[Error:"+lexemeError+"|"+lineError+"]");
        this.lineError = lineError;
        this.columnError = columnError;
        this.lexemeError = lexemeError;
        this.detailedMsg = detailedMsg;
    }

    public String getDetailedMsg(){
        return detailedMsg;
    }

    public int getLineError(){ return lineError; }

    public int getColumnError(){ return columnError; }

    public String getLexemeError() { return lexemeError; }
}
