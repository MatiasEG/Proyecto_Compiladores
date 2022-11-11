package minijava.compiler.semantic.nodes.sentencia;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionWrongConditionType;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionNodo;
import minijava.compiler.semantic.tables.Type;

import java.io.IOException;

public class IfNodo extends SentenciaNodo{

    protected Token ifToken;
    protected SentenciaNodo sentenciaIf;
    protected ExpresionNodo condicion;
    protected SentenciaNodo sentenciaElse;

    public IfNodo(Token ifToken){
        this.ifToken = ifToken;
        sentenciaElse = null;
    }

    public void setCondicion(ExpresionNodo condicion){ this.condicion = condicion; }

    public void setSentenciaIf(SentenciaNodo sentenciaIf){ this.sentenciaIf = sentenciaIf; }

    public void setSentenciaElse(SentenciaNodo sentenciaElse){ this.sentenciaElse = sentenciaElse; }

    @Override
    public void check(SymbolTable st) throws SemanticException {
        Type tipoCondicion = condicion.check(st);

        if(tipoCondicion.getTypeForAssignment().equals("boolean")){
            if(sentenciaIf != null)
                sentenciaIf.check(st);
            if(sentenciaElse != null )
                sentenciaElse.check(st);
        }else{
            throw new SemanticExceptionWrongConditionType(ifToken);
        }

    }

    @Override
    public void generar(SymbolTable st) throws IOException {
        String label_TerminaIf = "finIf_"+SymbolTable.getNro_TerminacionIf()+"_"+st.getActualMethod().getMethodName();
        String label_else = "else_"+SymbolTable.getNro_else()+"_"+st.getActualMethod().getMethodName();

        condicion.generar(st);

        if(sentenciaElse == null){
            st.write("BF "+label_TerminaIf+"\n");
            if(sentenciaIf != null)
                sentenciaIf.generar(st);
            st.write(label_TerminaIf+": NOP\n");
        }else{
            st.write("BF "+label_else+"\n");
            if(sentenciaIf != null){
                sentenciaIf.generar(st);
            }
            st.write("JUMP "+label_TerminaIf+"\n");
            st.write(label_else+": \n");
            sentenciaElse.generar(st);
            st.write(label_TerminaIf+": NOP\n");
        }
    }
}
