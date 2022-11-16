package minijava.compiler.semantic.nodes.expresion.operando.primario;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionCantCallThisOnStaticMethod;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.operando.PrimarioNodo;
import minijava.compiler.semantic.tables.Type;

import java.io.IOException;

public class AccesoThisNodo extends PrimarioNodo {

    protected String nombreClase;

    public AccesoThisNodo(Token thisToken, String nombreClase){
        this.idPrimario = thisToken;
        this.nombreClase = nombreClase;
    }

    @Override
    public Type check(SymbolTable st) throws SemanticException {
        if(st.getActualMethod().isStatic()) throw new SemanticExceptionCantCallThisOnStaticMethod(idPrimario);

        return new Type(new Token("idClass", nombreClase, idPrimario.getRow()));
    }

    @Override
    public boolean isAssignable(SymbolTable st) {
        return false;
    }

    @Override
    public boolean esLlamable() {
        return false;
    }

    @Override
    public void generar(SymbolTable st) throws IOException {
        st.write("LOAD 3 # This\n");
    }
}
