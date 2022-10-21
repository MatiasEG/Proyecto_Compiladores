package minijava.compiler.semantic.nodes.expresion.operando.literales;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.tables.Type;

public class LiteralCharacterNodo extends LiteralNodo{

    public LiteralCharacterNodo(Token charToken){
        this.literalToken = charToken;
    }

    @Override
    public Type check(SymbolTable st) throws SemanticException {
        return new Type(literalToken);
    }
}
