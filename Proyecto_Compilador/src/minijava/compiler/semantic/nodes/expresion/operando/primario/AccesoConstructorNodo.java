package minijava.compiler.semantic.nodes.expresion.operando.primario;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionCantCallConstructor;
import minijava.compiler.exception.SemanticP2.SemanticExceptionWrongQuantityParameters;
import minijava.compiler.exception.SemanticP2.SemanticExceptionWrongTypeActualArgs;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionNodo;
import minijava.compiler.semantic.nodes.expresion.operando.PrimarioNodo;
import minijava.compiler.semantic.tables.Method;
import minijava.compiler.semantic.tables.Type;
import minijava.compiler.semantic.tables.variable.Parameter;

import java.util.ArrayList;
import java.util.Iterator;

public class AccesoConstructorNodo extends PrimarioNodo {

    private Token idClase;
    private Type returnType;
    private ArrayList<ExpresionNodo> actualArgsExpresionNodes;

    public AccesoConstructorNodo(Token idClase){
        this.idClase = idClase;
        returnType = new Type(idClase);
    }

    public void setArgumentosActuales(ArrayList<ExpresionNodo> actualArgsExpresionNodes){ this.actualArgsExpresionNodes = actualArgsExpresionNodes; }

    @Override
    public Type check(SymbolTable st) throws SemanticException {

        if(st.getClass(idClase.getLexeme())==null){
            throw new SemanticExceptionCantCallConstructor(idClase);
        }

        Method m = st.getClass(idClase.getLexeme()).getHashMapMethodsWithoutOverloaded().get(idClase.getLexeme());
        ArrayList<Parameter> tiposParametrosDeclarados = m.getParameters();
        Iterator<ExpresionNodo> expresionNodosIterablre = actualArgsExpresionNodes.iterator();
        if(actualArgsExpresionNodes.size() == tiposParametrosDeclarados.size()){
            ExpresionNodo exp;
            for(Parameter p: tiposParametrosDeclarados){
                exp = expresionNodosIterablre.next();
                Type typeExp = exp.check(st);
                if(typeExp.isClassRef() && p.getVarType().isClassRef() &&
                        st.bSubtipoA(typeExp.getLexemeType(), p.getVarType().getLexemeType()) != null){
                    // vacio, si se da el caso de que no coinciden el tercer if lo va a detectar
                }else if(typeExp.getLexemeType().equals("null") && p.getVarType().isClassRef()){
                    // vacio, si se da el caso de que no coinciden el tercer if lo va a detectar
                }else if(!typeExp.getTypeForAssignment().equals(p.getVarType().getTypeForAssignment())){
                    throw new SemanticExceptionWrongTypeActualArgs(idClase);
                }
            }

        }else{
            throw new SemanticExceptionWrongQuantityParameters(m.getMethodToken());
        }

        return returnType;
    }

    @Override
    public boolean isAssignable(SymbolTable st) {
        return false;
    }
}
