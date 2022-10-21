package minijava.compiler.semantic.nodes.sentencia;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionVarNotExist;
import minijava.compiler.exception.semanticP1.classinterface.SemanticExceptionClassRefNotExist;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionNodo;
import minijava.compiler.semantic.tables.Type;
import minijava.compiler.semantic.tables.variable.Variable;

public class VarLocalNodo extends SentenciaNodo{

    private Token idVarLocalToken;
    private Variable var;
    private String name;
    private ExpresionNodo parteDerecha;
    //TODO private resto var local....

    public VarLocalNodo(){}

    public void setVarLocalToken(Token idVarLocalToken){
        this.idVarLocalToken = idVarLocalToken;
        name = idVarLocalToken.getLexeme();
    }

    public void setParteDerecha(ExpresionNodo expresionNodo){ parteDerecha = expresionNodo; }

    @Override
    public void check(SymbolTable st) throws SemanticException {

        if(st.getActualMethod().getBlock().getVarsHashMap().containsKey(name))
            var = st.getActualMethod().getBlock().getVarsHashMap().get(name);
        else
            throw new SemanticExceptionVarNotExist(idVarLocalToken);

        var.setVarType(parteDerecha.check(st));


//        if(st.getActualMethod().getParameterHashMap().containsKey(name)){
//            var = st.getActualMethod().getParameter(name);
//        }else if(st.getActualMethod().getBlock().getVarsHashMap().containsKey(name)){
//            var = st.getActualMethod().getBlock().getVarsHashMap().get(name);
//        }else if(st.getActualClass().getHashMapAtributes().containsKey(name)){
//            var = st.getActualClass().getHashMapAtributes().get(name);
//        }else{
//            throw new SemanticExceptionVarNotExist(idVarLocalToken);
//        }
//
//        if(var.getVarType().isClassRef() && !st.haveClass(var.getVarType().getLexemeType())){
//            throw new SemanticExceptionClassRefNotExist(var.getVarType().getTokenType());
//        }
    }
}
