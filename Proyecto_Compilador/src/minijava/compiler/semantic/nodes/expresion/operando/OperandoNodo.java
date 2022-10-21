package minijava.compiler.semantic.nodes.expresion.operando;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.tables.Type;

public abstract class OperandoNodo{

    public abstract Type check(SymbolTable st) throws SemanticException;
}
