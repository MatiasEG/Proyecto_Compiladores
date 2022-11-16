package minijava.compiler.semantic.nodes.sentencia;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionEmptyReturn;
import minijava.compiler.exception.SemanticP2.SemanticExceptionReturn;
import minijava.compiler.exception.SemanticP2.SemanticExceptionWrongReturnType;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionNodo;
import minijava.compiler.semantic.tables.Type;

import java.io.IOException;

public class ReturnNodo extends SentenciaNodo {

    protected ExpresionNodo expresionNodo;
    protected Token returnToken;

    public ReturnNodo(Token returnToken){
        this.returnToken = returnToken;
    }

    public void setExpressionNode(ExpresionNodo expresionNodo){ this.expresionNodo = expresionNodo; }

    public void check(SymbolTable st) throws SemanticException {
        if(expresionNodo != null) {
            Type typeExp = expresionNodo.check(st);
            Type mType = st.getActualMethod().getMethodType();

            if (st.getActualMethod().needReturn()) {

                if (typeExp.isClassRef() && mType.isClassRef() &&
                        st.bSubtipoA(typeExp.getLexemeType(), mType.getLexemeType()) != null) {
                    // vacio, si se da el caso de que no coinciden el tercer if lo va a detectar
                } else if (typeExp.getLexemeType().equals("null") && mType.isClassRef()) {
                    // vacio, si se da el caso de que no coinciden el tercer if lo va a detectar
                } else if (!typeExp.getTypeForAssignment().equals(mType.getTypeForAssignment())) {
                    throw new SemanticExceptionWrongReturnType(st.getActualMethod(), typeExp.getTokenType());
                }

            } else {
                throw new SemanticExceptionReturn(st.getActualMethod(), returnToken);
            }
        }else if(st.getActualMethod().needReturn()){
            throw new SemanticExceptionEmptyReturn(returnToken);
        }
    }

    @Override
    public void generar(SymbolTable st) throws IOException {
        st.write("FMEM "+st.getActualMethod().getActualBlock().getCantVarLocales()+" # Libero los lugares de las var locales\n");
        if(st.getActualMethod().needReturn()){
            expresionNodo.generar(st);
            st.write("STORE "+st.getActualMethod().getOffsetStoreValorRetorno()+" # Se coloca el valor de retorno en su ubicacion reservada\n");
            st.write("STOREFP \n");
            st.write("RET "+st.getActualMethod().getOffsetRetornoMetodo()+" # Efectuamos el retorno liberando la cantidad de parametros\n");
        }else{
            st.write("STOREFP\n");
            st.write("RET "+st.getActualMethod().getOffsetRetornoMetodo()+" # Efectuamos el retorno liberando la cantidad de parametros\n");
        }
    }
}
