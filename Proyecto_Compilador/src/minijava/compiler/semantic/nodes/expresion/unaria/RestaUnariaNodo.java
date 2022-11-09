package minijava.compiler.semantic.nodes.expresion.unaria;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionInvalidTypeForUnaryOperator;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionUnariaNodo;
import minijava.compiler.semantic.tables.Type;

import java.io.IOException;

public class RestaUnariaNodo extends ExpresionUnariaNodo {

    public RestaUnariaNodo(Token restaOp){ super(restaOp); }

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
    public void generar(SymbolTable st) throws IOException {
        operandoNodo.generar(st);
        st.write("NEG # Menos unario\n");
    }
}
