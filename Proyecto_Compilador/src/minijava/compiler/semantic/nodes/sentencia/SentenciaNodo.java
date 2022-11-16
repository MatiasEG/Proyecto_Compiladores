package minijava.compiler.semantic.nodes.sentencia;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.semantic.SymbolTable;

import java.io.IOException;

public abstract class SentenciaNodo{

    public abstract void check(SymbolTable st) throws SemanticException;

    public abstract void generar(SymbolTable st) throws IOException;
}
