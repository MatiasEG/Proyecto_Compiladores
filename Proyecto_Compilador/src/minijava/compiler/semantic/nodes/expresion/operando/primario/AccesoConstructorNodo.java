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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class AccesoConstructorNodo extends PrimarioNodo {

    protected Type returnType;
    protected ArrayList<ExpresionNodo> actualArgsExpresionNodes;

    public AccesoConstructorNodo(Token idClase){
        this.idPrimario = idClase;
        returnType = new Type(idClase);
    }

    public void setArgumentosActuales(ArrayList<ExpresionNodo> actualArgsExpresionNodes){
        Collections.reverse(actualArgsExpresionNodes);
        this.actualArgsExpresionNodes = actualArgsExpresionNodes;
    }

    @Override
    public Type check(SymbolTable st) throws SemanticException {

        if(st.getClass(idPrimario.getLexeme())==null){
            throw new SemanticExceptionCantCallConstructor(idPrimario);
        }

        Method m = st.getClass(idPrimario.getLexeme()).getHashMapMethodsWithoutOverloaded().get(idPrimario.getLexeme());
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
                    throw new SemanticExceptionWrongTypeActualArgs(idPrimario);
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

    @Override
    public void generar(SymbolTable st) throws IOException {
        st.write("RMEM 1 # Resultado de malloc, referencia al CIR de un objeto\n");
        st.write("PUSH "+(st.getClass(idPrimario.getLexeme()).getHashMapAtributes().size()+1)+" # Parametro malloc, cant de atributos del objeto + 1 para VT\n");
        st.write("PUSH simple_malloc\n");
        st.write("CALL\n");
        st.write("DUP # de la referencia al nuevo CIR\n");
        st.write("PUSH "+st.getClass(idPrimario.getLexeme()).getVtLabel()+"\n");
        st.write("STOREREF 0 # Consume una de las dupicas de la referencia al CIR\n");
        st.write("DUP # de la referencia al objeto\n");

        for(ExpresionNodo parametroActualExp: actualArgsExpresionNodes){
            parametroActualExp.generar(st);
            st.write("SWAP # Muevo al tope el this, por cada parametro generado\n");
        }

        st.write("PUSH "+idPrimario.getLexeme()+"Constructor"+"\n");
        st.write("CALL # Invoco el constructor\n");
    }
}
