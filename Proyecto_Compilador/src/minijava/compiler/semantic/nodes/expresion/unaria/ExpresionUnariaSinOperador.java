package minijava.compiler.semantic.nodes.expresion.unaria;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionUnariaNodo;
import minijava.compiler.semantic.tables.Type;

public class ExpresionUnariaSinOperador extends ExpresionUnariaNodo {


    public ExpresionUnariaSinOperador() {
        super(null);
    }

    @Override
    public Type check(SymbolTable st) throws SemanticException {
        return operandoNodo.check(st);
    }
}
