package minijava.compiler.semantic.nodes.expresion.unaria;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionUnariaNodo;
import minijava.compiler.semantic.tables.Type;

import java.io.IOException;

public class ExpresionUnariaSinOperador extends ExpresionUnariaNodo {


    public ExpresionUnariaSinOperador() {
        super(null);
    }

    @Override
    public Type check(SymbolTable st) throws SemanticException {
        return operandoNodo.check(st);
    }

    @Override
    public void generar(SymbolTable st) throws IOException {
        operandoNodo.generar(st);
    }

}
