package minijava.compiler.semantic.nodes.expresion;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.operando.OperandoNodo;
import minijava.compiler.semantic.tables.Type;

public abstract class ExpresionUnariaNodo extends ExpresionNodo {

    protected Token unaryOperator;
    protected OperandoNodo operandoNodo;

    public ExpresionUnariaNodo(Token unaryOperator){ this.unaryOperator = unaryOperator; }

    public void setOperateNode(OperandoNodo operandoNodo){ this.operandoNodo = operandoNodo; }

    @Override
    public abstract Type check(SymbolTable st) throws SemanticException;

    @Override
    public boolean isAssignable(SymbolTable st) {
        return false;
    }
}
