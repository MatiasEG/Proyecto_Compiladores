package minijava.compiler.semantic;

public abstract class Variable {

    protected Tipo tipo;
    protected String nombre;
    protected int lineaDeclaracion;

    public void setTipo(Tipo tipo) { this.tipo = tipo; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public Tipo getTipo() { return tipo; }

    public String getNombre() { return nombre; }

    public abstract boolean equals(Variable variable);

    public void setLineaDeclaracion(int lineaDeclaracion) { this.lineaDeclaracion = lineaDeclaracion; }

    public int getLineaDeclaracion() { return lineaDeclaracion; }
}
