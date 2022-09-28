package minijava.compiler.exception.semantic;

import minijava.compiler.semantic.tables.Clase;

public class SemanticExceptionCircleExtend extends SemanticException{

    private Clase claseHereda;
    private Clase claseHeredada;

    public SemanticExceptionCircleExtend(Clase claseHereda, Clase claseHeredada){
        super("La clase \'"+claseHereda.getNombre()+"\' hereda de \'"+claseHeredada.getNombre()+"\' y viceversa.");
        this.claseHereda = claseHereda;
        this.claseHeredada = claseHeredada;
    }

    @Override
    public int getRow() {
        return claseHeredada.getLineaDeclaracion();
    }

    @Override
    public String getLexeme() {
        return claseHeredada.getNombre();
    }
}
