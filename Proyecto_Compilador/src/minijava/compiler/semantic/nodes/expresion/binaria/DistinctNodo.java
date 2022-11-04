package minijava.compiler.semantic.nodes.expresion.binaria;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionIncompatibleTypesOnEquals;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionBinariaNodo;
import minijava.compiler.semantic.tables.Type;

public class DistinctNodo extends ExpresionBinariaNodo {

    public DistinctNodo(Token distinct){
        super(distinct);
    }

    public Type check(SymbolTable st) throws SemanticException {
        Type tipoLadoIzq = ladoIzquierdo.check(st);
        Type tipoLadoDer = ladoDerecho.check(st);

        if(tipoLadoIzq.isClassRef() && tipoLadoDer.isClassRef()){
            String subtipo = st.bSubtipoA(tipoLadoIzq.getLexemeType(), tipoLadoDer.getLexemeType());
            if(subtipo != null){
                // TRUE
                return new Type(new Token("idKeyWord_boolean", "boolean", tipoLadoIzq.getTokenType().getRow()));
            }else{
                subtipo = st.bSubtipoA(tipoLadoDer.getLexemeType(), tipoLadoIzq.getLexemeType());
                if(subtipo != null){
                    // TRUE
                    return new Type(new Token("idKeyWord_boolean", "boolean", tipoLadoIzq.getTokenType().getRow()));
                }else{
                    // FALSE
                    throw new SemanticExceptionIncompatibleTypesOnEquals(this.operatorToken);
                }
            }
        }else{
            if(tipoLadoIzq.getTypeForAssignment().equals(tipoLadoDer.getTypeForAssignment())){
                // TRUE
                return new Type(new Token("idKeyWord_boolean", "boolean", tipoLadoIzq.getTokenType().getRow()));
            }else{
                // FALSE
                return new Type(new Token("idKeyWord_boolean", "boolean", tipoLadoIzq.getTokenType().getRow()));
            }
        }
    }

    @Override
    public void generar(SymbolTable st) {
        //TODO generar
    }
}
