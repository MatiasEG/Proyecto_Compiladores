package minijava.compiler.semantic.nodes.expresion.unaria;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionInvalidTypeForUnaryOperator;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionUnariaNodo;
import minijava.compiler.semantic.tables.Type;

public class NegacionUnariaNodo extends ExpresionUnariaNodo {

    public NegacionUnariaNodo(Token negacionOp){ super(negacionOp); }

    @Override
    public Type check(SymbolTable st) throws SemanticException {
        if(unaryOperator == null){
            return operandoNodo.check(st);
        }else{
            Type tipoOperando = operandoNodo.check(st);
            if(tipoOperando.getTypeForAssignment().equals("boolean")){
                if(tipoOperando.getLexemeType().equals("true")){
                    return new Type(new Token("idKeyWord_false", "false", tipoOperando.getTokenType().getRow()));
                }else{
                    return new Type(new Token("idKeyWord_true", "true", tipoOperando.getTokenType().getRow()));
                }
            }else{
                throw new SemanticExceptionInvalidTypeForUnaryOperator(unaryOperator);
            }
        }
    }

    @Override
    public void generar(SymbolTable st) {
        //TODO generar
    }
}
