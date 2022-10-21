package minijava.compiler.semantic.nodes.sentencia;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionReturn;
import minijava.compiler.exception.SemanticP2.SemanticExceptionWrongReturnType;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.expresion.ExpresionNodo;
import minijava.compiler.semantic.tables.Method;
import minijava.compiler.semantic.tables.Type;

public class ReturnNodo extends SentenciaNodo {

    private ExpresionNodo expresionNodo;
    private Method method;
    private Token returnToken;


    public ReturnNodo(Method method, Token returnToken){
        this.method = method;
        this.returnToken = returnToken;
    }

    public void setExpressionNode(ExpresionNodo expresionNodo){ this.expresionNodo = expresionNodo; }

    public void check(SymbolTable st) throws SemanticException {
        Type expressionType = expresionNodo.check(st);
        if(st.getActualMethod().needReturn()){
            if(!st.getActualMethod().getMethodType().getLexemeType().equals(expressionType.getTypeForAssignment()))
                throw new SemanticExceptionWrongReturnType(st.getActualMethod(), expressionType.getTokenType());
        }else{
            throw new SemanticExceptionReturn(st.getActualMethod(), returnToken);
        }
    }
}
