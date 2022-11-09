package minijava.compiler.semantic.nodes.expresion.unaria;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionInvalidTypeForUnaryOperator;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionUnariaNodo;
import minijava.compiler.semantic.tables.Type;

public class SumaUnariaNodo extends ExpresionUnariaNodo {

    public SumaUnariaNodo(Token sumaOp){ super(sumaOp); }

    @Override
    public Type check(SymbolTable st) throws SemanticException {
        if(unaryOperator == null){
            return operandoNodo.check(st);
        }else{
            Type tipoOperando = operandoNodo.check(st);
            if(tipoOperando.getTypeForAssignment().equals("int")){
                return tipoOperando;
            }else{
                throw new SemanticExceptionInvalidTypeForUnaryOperator(unaryOperator);
            }
        }
    }

    @Override
    public void generar(SymbolTable st) {
        //TODO no hacer nada
    }
}
