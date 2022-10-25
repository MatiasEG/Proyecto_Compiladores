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

public class AccesoVarNodo extends PrimarioNodo {

    private Variable var;
    private String name;
    private Token varToken;
    private Block bloqueAcceso;

    public AccesoVarNodo(Token varToken, Block bloqueAcceso){
        this.var = null;
        name = varToken.getLexeme();
        this.varToken = varToken;
        this.bloqueAcceso = bloqueAcceso;
    }

    public Type check(SymbolTable st) throws SemanticException {
        if(st.getActualMethod().getParameterHashMap().containsKey(name)){
            var = st.getActualMethod().getParameter(name);
        }else if(bloqueAcceso.getVarsAccesiblesDesdeElBloque().containsKey(name)){
            var = bloqueAcceso.getVarsAccesiblesDesdeElBloque().get(name);
        }else if(st.getActualClass().getHashMapAtributes().containsKey(name)){
            if(st.getActualMethod().isStatic()) throw new SemanticExceptionCantAccessAtributesOnStaticMethod(varToken);
            var = st.getActualClass().getHashMapAtributes().get(name);
        }else{
            throw new SemanticExceptionVarNotExist(varToken);
        }

        return var.getVarType();
    }

    @Override
    public boolean isAssignable(SymbolTable st) {
        return true;
    }
}
