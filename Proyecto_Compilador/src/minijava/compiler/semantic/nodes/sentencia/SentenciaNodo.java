package minijava.compiler.semantic.nodes.sentencia;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.semantic.SymbolTable;

public abstract class SentenciaNodo{


    public abstract void check(SymbolTable st) throws SemanticException;
}
