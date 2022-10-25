package minijava.compiler.semantic.nodes.sentencia;

import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionNodo;

public class WhileNodo extends SentenciaNodo{

    protected Token whileToken;
    protected SentenciaNodo sentenciaWhile;
    protected ExpresionNodo condicion;

    public WhileNodo(Token whileToken){ this.whileToken = whileToken; }

    public void setCondicion(ExpresionNodo condicion){ this.condicion = condicion; }

    public void setSentenciaWhile(SentenciaNodo sentenciaWhile){ this.sentenciaWhile = sentenciaWhile; }

    @Override
    public void check(SymbolTable st) {

    }
}
