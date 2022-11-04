package minijava.compiler.semantic.nodes.expresion.operando;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.tables.Type;

import java.io.IOException;

public abstract class OperandoNodo{

    public abstract Type check(SymbolTable st) throws SemanticException;

    public abstract void generar(SymbolTable st) throws IOException;
}
