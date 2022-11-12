package minijava.compiler.semantic.nodes.sentencia;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionThisNeedChained;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.operando.AccesoNodo;
import minijava.compiler.semantic.tables.Type;

import java.io.IOException;

public class LlamadaNodo extends SentenciaNodo{

    private AccesoNodo accesoNodo;
    private Type tipoAcceso;

    public void setAccesoNodo(AccesoNodo accesoNodo){ this.accesoNodo = accesoNodo; }

    @Override
    public void check(SymbolTable st) throws SemanticException {
//        if(accesoNodo.esLlamable()){
        accesoNodo.esLadoIzquierod(true);
        tipoAcceso = accesoNodo.check(st);
//        }else{
//            throw new SemanticExceptionThisNeedChained(accesoNodo.getIdPrimario());
//        }
    }

    @Override
    public void generar(SymbolTable st) throws IOException {
        accesoNodo.generar(st);
        if(!this.tipoAcceso.getLexemeType().equals("void")){
            st.write("POP # Llamada con valor no asignado\n");
        }
    }
}
