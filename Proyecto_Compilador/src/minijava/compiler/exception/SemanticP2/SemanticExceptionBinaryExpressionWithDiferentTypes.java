package minijava.compiler.exception.SemanticP2;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;

public class SemanticExceptionBinaryExpressionWithDiferentTypes extends SemanticException {

    public SemanticExceptionBinaryExpressionWithDiferentTypes(Token operadorBinario){
        super("Los tipos de ambos lados del operador binario no coinciden.", operadorBinario);
    }
}
