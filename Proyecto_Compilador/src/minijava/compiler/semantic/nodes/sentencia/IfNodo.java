package minijava.compiler.semantic.nodes.sentencia;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionWrongConditionType;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionNodo;
import minijava.compiler.semantic.tables.Type;

public class IfNodo extends SentenciaNodo{

    protected Token ifToken;
    protected SentenciaNodo sentenciaIf;
    protected ExpresionNodo condicion;
    protected SentenciaNodo sentenciaElse;

    public IfNodo(Token ifToken){ this.ifToken = ifToken; sentenciaElse = null; }

    public void setCondicion(ExpresionNodo condicion){ this.condicion = condicion; }

    public void setSentenciaIf(SentenciaNodo sentenciaIf){ this.sentenciaIf = sentenciaIf; }

    public void setSentenciaElse(SentenciaNodo sentenciaElse){ this.sentenciaElse = sentenciaElse; }

    @Override
    public void check(SymbolTable st) throws SemanticException {
        Type tipoCondicion = condicion.check(st);

        if(tipoCondicion.getTypeForAssignment().equals("boolean")){
            sentenciaIf.check(st);
            if(sentenciaElse !=null ) sentenciaElse.check(st);
        }else{
            throw new SemanticExceptionWrongConditionType(ifToken);
        }

    }

    @Override
    public void generar(SymbolTable st) {
        //TODO generar
    }
}
