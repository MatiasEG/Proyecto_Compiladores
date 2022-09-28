package minijava.compiler.exception.semantic;

import minijava.compiler.semantic.tables.Metodo;

public class SemanticExceptionWrongDefinedMain extends SemanticException{

    private Metodo wrongDefinedMain;
    public SemanticExceptionWrongDefinedMain(Metodo wrongDefinedMain){
        super("El metodo main declarado en la linea ("+wrongDefinedMain.getLineaDeclaracion()+") esta mal declarado.");
        this.wrongDefinedMain = wrongDefinedMain;
    }


    @Override
    public int getRow() {
        return wrongDefinedMain.getLineaDeclaracion();
    }

    @Override
    public String getLexeme() {
        return wrongDefinedMain.getNombre();
    }
}
