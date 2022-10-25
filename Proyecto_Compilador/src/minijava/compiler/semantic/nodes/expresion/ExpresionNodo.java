package minijava.compiler.semantic.nodes.expresion;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.operando.PrimarioNodo;
import minijava.compiler.semantic.tables.Type;

public abstract class ExpresionNodo extends PrimarioNodo {

    public abstract Type check(SymbolTable st) throws SemanticException;
}
