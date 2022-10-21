package minijava.compiler.semantic.nodes;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.semantic.SymbolTable;

public abstract class Node {

    public abstract void check(SymbolTable st) throws SemanticException;
}
