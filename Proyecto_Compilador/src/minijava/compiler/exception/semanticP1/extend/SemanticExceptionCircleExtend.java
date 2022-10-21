package minijava.compiler.exception.semanticP1.extend;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.tables.ClassOrInterface;

public class SemanticExceptionCircleExtend extends SemanticException {

    private ClassOrInterface descendiente;
    private Token padre;

    public SemanticExceptionCircleExtend(Token padre, ClassOrInterface descendiente){
        super("La clase \'"+padre.getLexeme()+"\' posee un problema de herencia circular que afecta a \'"+descendiente.getNombre()+"\'.",padre);
        this.descendiente = descendiente;
        this.padre = padre;
    }

}
