package minijava.compiler.semantic.nodes.expresion.operando;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.tables.Type;

public abstract class LiteralNodo extends OperandoNodo {

    protected Token literalToken;

    public abstract Type check(SymbolTable st) throws SemanticException;
}
