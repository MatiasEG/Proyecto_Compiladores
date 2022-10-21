package minijava.compiler.semantic.nodes.expresion.operando.literales;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.tables.Type;

public class LiteralNullNodo extends LiteralNodo{

    public LiteralNullNodo(Token nullToken){
        this.literalToken = nullToken;
    }

    @Override
    public Type check(SymbolTable st) throws SemanticException {
        return new Type(literalToken);
    }
}
