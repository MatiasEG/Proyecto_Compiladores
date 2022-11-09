package minijava.compiler.semantic.nodes.sentencia;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionInvalidNullAsignment;
import minijava.compiler.exception.SemanticP2.SemanticExceptionVarNotExist;
import minijava.compiler.exception.SemanticP2.SemanticExceptionVoidAsignment;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionNodo;
import minijava.compiler.semantic.tables.Block;
import minijava.compiler.semantic.tables.Type;
import minijava.compiler.semantic.tables.variable.Variable;

import java.io.IOException;

public class VarLocalNodo extends SentenciaNodo{

    private Token idVarLocalToken;
    private Variable var;
    private String name;
    private ExpresionNodo parteDerecha;
    private Block bloqueVarLocal;

    public VarLocalNodo(Block bloqueVarLocal){
        this.bloqueVarLocal = bloqueVarLocal;
    }

    public void setVarLocalToken(Token idVarLocalToken){
        this.idVarLocalToken = idVarLocalToken;
        name = idVarLocalToken.getLexeme();
    }

    public void setParteDerecha(ExpresionNodo expresionNodo){ parteDerecha = expresionNodo; }

    @Override
    public void check(SymbolTable st) throws SemanticException {

        if(bloqueVarLocal.contains(name) != null)
            var = bloqueVarLocal.contains(name);
        else
            throw new SemanticExceptionVarNotExist(idVarLocalToken);

        Type type = parteDerecha.check(st);

        if(!type.getLexemeType().equals("null")){
            if(type.getLexemeType().equals("void"))
                throw new SemanticExceptionVoidAsignment(idVarLocalToken);
            var.setVarType(type);
        }else
            throw new SemanticExceptionInvalidNullAsignment(idVarLocalToken);
    }

    @Override
    public void generar(SymbolTable st) throws IOException {
        // TODO borrar
//        st.write("RMEM 1 # Se guarda un espacio para la varLocal.\n");
        parteDerecha.generar(st);
//        st.write("STORE "+var.getOffset()+" # Posicion de la var local.\n");
    }
}
