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
        String string2push = literalToken.getLexeme();
        string2push = string2push.substring(1, string2push.length()-1);

        st.write("RMEM 1 # Comienzo del String\n");
        st.write("PUSH "+(string2push.length()+1)+" # Lugares del heap\n");
        st.write("PUSH simple_malloc\n");
        st.write("CALL\n");

        for(int i = 0; i < string2push.length(); i++){
            st.write("DUP # Ref del comienzo del String\n");
            st.write("PUSH "+'\''+string2push.charAt(i)+'\''+"\n");
            st.write("STOREREF "+i+"\n");
        }

        st.write("DUP # Ref del comienzo del String\n");
        st.write("PUSH "+0+" # Caracter terminador\n");
        st.write("STOREREF "+string2push.length()+"\n");
    }
}
