package minijava.compiler.semantic.nodes.expresion.operando.primario;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionCantCallThisOnStaticMethod;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.tables.Type;

public class AccesoThisNodo extends PrimarioNodo {

    private Token thisToken;
    private String nombreClase;

    public AccesoThisNodo(Token thisToken, String nombreClase){
        this.thisToken = thisToken;
        this.nombreClase = nombreClase;
    }

    @Override
    public Type check(SymbolTable st) throws SemanticException {
        if(st.getActualMethod().isStatic()) throw new SemanticExceptionCantCallThisOnStaticMethod(thisToken);

        return new Type(new Token("idClass", nombreClase, thisToken.getRow()));
    }

    @Override
    public boolean isAssignable(SymbolTable st) {
        return false;
    }
}
