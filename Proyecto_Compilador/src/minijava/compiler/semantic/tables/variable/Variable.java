package minijava.compiler.semantic.tables.variable;

import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.tables.Type;

public abstract class Variable {

    protected Type type;
    protected Token varToken;

    public void setVarType(Type type) { this.type = type; }

    public void setVarToken(Token varToken) { this.varToken = varToken; }

    public Token getVarToken() { return varToken; }

    public Type getVarType() { return type; }

    public String getVarName() { return varToken.getLexeme(); }
}
