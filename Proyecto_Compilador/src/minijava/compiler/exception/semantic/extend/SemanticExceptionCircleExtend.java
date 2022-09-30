package minijava.compiler.exception.semantic.extend;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.tables.Clase;

public class SemanticExceptionCircleExtend extends SemanticException {

    private Clase descendiente;
    private Token padre;

    public SemanticExceptionCircleExtend(Token padre, Clase descendiente){
        super("La clase \'"+padre.getLexeme()+"\' posee un problema de herencia circular que afecta a \'"+descendiente.getNombre()+"\'.",padre);
        this.descendiente = descendiente;
        this.padre = padre;
    }

}
