package minijava.compiler.semantic.nodes.expresion.operando.literales;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.operando.LiteralNodo;
import minijava.compiler.semantic.tables.Type;

import java.io.IOException;

public class LiteralStringNodo extends LiteralNodo {

    public LiteralStringNodo(Token txt){
        this.literalToken = txt;
    }

    @Override
    public Type check(SymbolTable st) throws SemanticException {
        return new Type(literalToken);
    }

    @Override
    public void generar(SymbolTable st) throws IOException {
        st.write("PUSH "+literalToken.getLexeme()+"\n");
    }
}
