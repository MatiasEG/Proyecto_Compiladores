package minijava.compiler.semantic.nodes.expresion.operando.primario;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.tables.Type;

public abstract class PrimarioNodo {

    public abstract Type check(SymbolTable st) throws SemanticException;

    public abstract boolean isAssignable(SymbolTable st);
}
