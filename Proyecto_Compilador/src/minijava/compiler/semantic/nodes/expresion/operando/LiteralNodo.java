package minijava.compiler.semantic.nodes.expresion.operando;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.tables.Type;

import java.io.IOException;

public abstract class LiteralNodo extends OperandoNodo {

    protected Token literalToken;

    public abstract Type check(SymbolTable st) throws SemanticException;

    public abstract void generar(SymbolTable st) throws IOException;
}
