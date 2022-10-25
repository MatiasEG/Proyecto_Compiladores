package minijava.compiler.semantic.nodes.sentencia;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionWrongConditionType;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionNodo;
import minijava.compiler.semantic.tables.Type;

public class WhileNodo extends SentenciaNodo{

    protected Token whileToken;
    protected SentenciaNodo sentenciaWhile;
    protected ExpresionNodo condicion;

    public WhileNodo(Token whileToken){ this.whileToken = whileToken; }

    public void setCondicion(ExpresionNodo condicion){ this.condicion = condicion; }

    public void setSentenciaWhile(SentenciaNodo sentenciaWhile){ this.sentenciaWhile = sentenciaWhile; }

    @Override
    public void check(SymbolTable st) throws SemanticException {
        Type tipoCondicion = condicion.check(st);

        if(tipoCondicion.getTypeForAssignment().equals("boolean")){
            sentenciaWhile.check(st);
        }else{
            throw new SemanticExceptionWrongConditionType(whileToken);
        }
    }
}
