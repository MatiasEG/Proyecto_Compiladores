package minijava.compiler.semantic.nodes.sentencia;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.operando.AccesoNodo;
import minijava.compiler.semantic.tables.Type;

import java.io.IOException;

public class LlamadaNodo extends SentenciaNodo{

    protected AccesoNodo accesoNodo;
    protected Type tipoAcceso;

    public void setAccesoNodo(AccesoNodo accesoNodo){ this.accesoNodo = accesoNodo; }

    @Override
    public void check(SymbolTable st) throws SemanticException {
        accesoNodo.esLadoIzquierod(true);
        tipoAcceso = accesoNodo.check(st);
    }

    @Override
    public void generar(SymbolTable st) throws IOException {
        accesoNodo.generar(st);
        if(!this.tipoAcceso.getLexemeType().equals("void")){
            st.write("POP # Llamada con valor no asignado\n");
        }
    }
}
