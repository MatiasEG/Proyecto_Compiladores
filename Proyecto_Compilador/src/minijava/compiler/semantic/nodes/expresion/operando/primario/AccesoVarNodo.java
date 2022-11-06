package minijava.compiler.semantic.nodes.expresion.operando.primario;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionCantAccessAtributesOnStaticMethod;
import minijava.compiler.exception.SemanticP2.SemanticExceptionVarNotExist;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.operando.PrimarioNodo;
import minijava.compiler.semantic.tables.Block;
import minijava.compiler.semantic.tables.Type;
import minijava.compiler.semantic.tables.variable.Variable;

import java.io.IOException;

public class AccesoVarNodo extends PrimarioNodo {

    private Variable var;
    private String name;
    private Block bloqueAcceso;
    private boolean esAtributo, esParametro, esVarLocal;

    public AccesoVarNodo(Token varToken, Block bloqueAcceso){
        this.var = null;
        name = varToken.getLexeme();
        this.idPrimario = varToken;
        this.bloqueAcceso = bloqueAcceso;
        esAtributo = false;
        esParametro = false;
        esVarLocal = false;
    }

    public Type check(SymbolTable st) throws SemanticException {
        if(st.getActualMethod().getParameterHashMap().containsKey(name)){
            var = st.getActualMethod().getParameter(name);
            esParametro = true;
        }else if(bloqueAcceso.getVarsAccesiblesDesdeElBloque().containsKey(name)){
            var = bloqueAcceso.getVarsAccesiblesDesdeElBloque().get(name);
            esVarLocal = true;
        }else if(st.getActualClass().getHashMapAtributes().containsKey(name)){
            if(st.getActualMethod().isStatic()) throw new SemanticExceptionCantAccessAtributesOnStaticMethod(idPrimario);
            var = st.getActualClass().getHashMapAtributes().get(name);
            esAtributo = true;
        }else{
            throw new SemanticExceptionVarNotExist(idPrimario);
        }

        return var.getVarType();
    }

    @Override
    public boolean isAssignable(SymbolTable st) {
        return true;
    }

    @Override
    public void generar(SymbolTable st) throws IOException {
        if(esVarLocal){
            st.write("LOAD "+var.getOffset()+" # Apilo el valor de la variable.\n");
        }else if(esParametro){
            st.write("LOAD "+(3-var.getOffset())+" # Apilo el valor de la variable.\n");
        }else if(esAtributo){
            // TODO completar
        }
    }

}
