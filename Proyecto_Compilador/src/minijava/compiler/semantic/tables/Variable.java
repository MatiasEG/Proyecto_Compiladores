package minijava.compiler.semantic.tables;

import minijava.compiler.lexical.analyzer.Token;

public abstract class Variable {

    protected Type type;
    protected Token varToken;
    protected boolean heredityVisibility;

    public void setVarType(Type type) { this.type = type; }

    public void setVarToken(Token varToken) { this.varToken = varToken; }

    public void setHeredityVisibility(boolean heredityVisibility) { this.heredityVisibility = heredityVisibility; }

    public Token getVarToken() { return varToken; }

    public Type getVarType() { return type; }

    public String getVarName() { return varToken.getLexeme(); }
}
