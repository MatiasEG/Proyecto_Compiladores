package minijava.compiler.semantic.nodes.expresion;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.operando.PrimarioNodo;
import minijava.compiler.semantic.tables.Type;

import java.io.IOException;

public abstract class ExpresionNodo extends PrimarioNodo {

    public abstract Type check(SymbolTable st) throws SemanticException;

    public abstract void generar(SymbolTable st) throws IOException;
}
