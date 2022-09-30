package minijava.compiler.exception.semantic.extend;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.Clase;

public class SemanticExceptionCircleExtend extends SemanticException {

    private Clase descendiente;
    private Clase padre;

    public SemanticExceptionCircleExtend(Clase descendiente, Clase padre){
        super("La clase \'"+descendiente.getNombre()+"\' hereda de \'"+padre.getNombre()+"\' y viceversa.", descendiente.getClaseOrinterfaceToken());
        this.descendiente = descendiente;
        this.padre = padre;
    }

}
