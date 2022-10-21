package minijava.compiler.semantic.nodes.expresion;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.operando.OperandoNodo;
import minijava.compiler.semantic.tables.Type;

public class ExpresionUnariaNodo extends ExpresionNodo {

    private Token unaryOperator;
    private OperandoNodo operandoNodo;

    public ExpresionUnariaNodo(){}

    public void setUnaryOperator(Token unaryOperator){ this.unaryOperator = unaryOperator; }

    public void setOperateNode(OperandoNodo operandoNodo){ this.operandoNodo = operandoNodo; }

    @Override
    public Type check(SymbolTable st) throws SemanticException {
        if(unaryOperator == null){
            return operandoNodo.check(st);
        }else{
            //TODO ver else
            return null;
        }
    }

    @Override
    public boolean isAssignable(SymbolTable st) {
        return false;
    }
}
