package minijava.compiler.semantic.nodes.expresion.operando;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionAttributteOrMethodNotDefinedInClassRef;
import minijava.compiler.exception.SemanticP2.SemanticExceptionWrongQuantityParameters;
import minijava.compiler.exception.SemanticP2.SemanticExceptionWrongTypeActualArgs;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionNodo;
import minijava.compiler.semantic.tables.Method;
import minijava.compiler.semantic.tables.Type;
import minijava.compiler.semantic.tables.variable.Parameter;
import minijava.compiler.semantic.tables.variable.Variable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class EncadenadoOptNodo {

    private EncadenadoOptNodo encadenadoOptNodo;
    private Variable var;
    private Token idMetVar;
    private ArrayList<ExpresionNodo> actualArgsExpresionNodes;
    private Type tipoPrimarioNodo;

    public EncadenadoOptNodo(){
        actualArgsExpresionNodes = null;
        encadenadoOptNodo = null;
        var = null;
        idMetVar = null;
        tipoPrimarioNodo = null;
    }

    public void setIdMetVarToken(Token idMetVarToken){ idMetVar = idMetVarToken; }

    public void setChainedOptNode(EncadenadoOptNodo encadenadoOptNodo){ this.encadenadoOptNodo = encadenadoOptNodo; }

    public void setArgumentos(ArrayList<ExpresionNodo> expresionNodos){
        Collections.reverse(expresionNodos);
        actualArgsExpresionNodes = expresionNodos;
    }

//    public Type check(Variable classRefVar, SymbolTable st) throws SemanticException {
//        if(st.getClass(classRefVar.getVarName()).getHashMapAtributes().containsKey(classRefVar.getVarName())){
//            var = st.getClass(classRefVar.getVarName()).getHashMapAtributes().get(classRefVar.getVarName());
//        }else{
//            throw new SemanticExceptionClassRefNotExist(classRefVar.getVarType().getTokenType());
//        }
//
//        if(encadenadoOptNodo == null){
//            return var.getVarType();
//        }else{
//            return encadenadoOptNodo.check(var, st);
//        }
//    }

    public boolean isAssignable(SymbolTable st){
        if(encadenadoOptNodo == null){
            if(isAtribute(tipoPrimarioNodo, st))
                return true;
            else if(isMethod(tipoPrimarioNodo, st) && tipoPrimarioNodo.isClassRef())
                return true;
            else
                return false;
        }else{
            return encadenadoOptNodo.isAssignable(st);
        }
    }

    public Type check(Type tipoPrimarioNodo, SymbolTable st) throws SemanticException {
        this.tipoPrimarioNodo = tipoPrimarioNodo;
        if(isAtribute(tipoPrimarioNodo, st)){
            if(encadenadoOptNodo == null){
                return st.getClass(tipoPrimarioNodo.getLexemeType()).getHashMapAtributes().get(idMetVar.getLexeme()).getVarType();
            }else{
                return encadenadoOptNodo.check(st.getClass(tipoPrimarioNodo.getLexemeType()).getHashMapAtributes().get(idMetVar.getLexeme()).getVarType(), st);
            }
        }else if(isMethod(tipoPrimarioNodo, st)){
            //TODO ver codigo repetido con AccesoMetNodo, se puede resolver esto mejor?
            if(encadenadoOptNodo == null){
                Method m = st.getClass(tipoPrimarioNodo.getLexemeType()).getHashMapMethodsWithoutOverloaded().get(idMetVar.getLexeme());
                ArrayList<Parameter> tiposParametrosDeclarados = m.getParameters();
                Iterator<ExpresionNodo> expresionNodosIterablre = actualArgsExpresionNodes.iterator();
                if(actualArgsExpresionNodes.size() == tiposParametrosDeclarados.size()){
                    ExpresionNodo exp;
                    for(Parameter p: tiposParametrosDeclarados){
                        exp = expresionNodosIterablre.next();
                        if(!exp.check(st).getTypeForAssignment().equals(p.getVarType().getTypeForAssignment())) throw new SemanticExceptionWrongTypeActualArgs(idMetVar);
                    }
                }else{
                    throw new SemanticExceptionWrongQuantityParameters(m.getMethodToken());
                }
                return st.getClass(tipoPrimarioNodo.getLexemeType()).getHashMapMethodsWithoutOverloaded().get(idMetVar.getLexeme()).getMethodType();
            }else{
                return encadenadoOptNodo.check(st.getClass(tipoPrimarioNodo.getLexemeType()).getHashMapMethodsWithoutOverloaded().get(idMetVar.getLexeme()).getMethodType(), st);
            }
        }else
            throw new SemanticExceptionAttributteOrMethodNotDefinedInClassRef(idMetVar);
    }

    private boolean isAtribute(Type tipoPrimarioNodo, SymbolTable st){
        return st.getClass(tipoPrimarioNodo.getLexemeType()).getHashMapAtributes().containsKey(idMetVar.getLexeme());
    }

    private boolean isMethod(Type tipoPrimarioNodo, SymbolTable st){
        return st.getClass(tipoPrimarioNodo.getLexemeType()).getHashMapMethodsWithoutOverloaded().containsKey(idMetVar.getLexeme());
    }
}
