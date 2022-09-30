package minijava.compiler.semantic.tables;

import minijava.compiler.lexical.analyzer.Token;

public abstract class Variable {

    protected Tipo tipo;
    protected Token varToken;
    protected boolean visibilidadHerencia;

    public void setTipo(Tipo tipo) { this.tipo = tipo; }

    public void setVarToken(Token varToken) { this.varToken = varToken; }

    public void setVisibilidadHerencia(boolean visibilidadHerencia) { this.visibilidadHerencia = visibilidadHerencia; }

    public Token getVarToken() { return varToken; }

    public Tipo getTipo() { return tipo; }

    public String getNombre() { return varToken.getLexeme(); }

    public int getLineaDeclaracion() { return varToken.getLineNumber(); }

    public boolean getVisibilidadHerencia(){ return visibilidadHerencia; }

    public abstract boolean equals(Variable variable);
}
