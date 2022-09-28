package minijava.compiler.semantic.tables;

import minijava.compiler.lexical.analyzer.Token;

public abstract class Variable {

    protected Tipo tipo;
    protected Token varToken;

    public void setTipo(Tipo tipo) { this.tipo = tipo; }

    public void setVarToken(Token varToken) { this.varToken = varToken; }

    public Token getVarToken() { return varToken; }

    public Tipo getTipo() { return tipo; }

    public String getNombre() { return varToken.getLexeme(); }

    public abstract boolean equals(Variable variable);

    public int getLineaDeclaracion() { return varToken.getLineNumber(); }
}
