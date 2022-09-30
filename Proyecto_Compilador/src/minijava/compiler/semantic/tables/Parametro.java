package minijava.compiler.semantic.tables;

public class Parametro extends Variable{

    private Metodo metodo;
    private int posicion;

    public Metodo getMetodo() { return metodo; }

    public void setMetodo(Metodo metodo) { this.metodo = metodo; }

    public int getPosicion() { return posicion; }

    public void setPosicion(int posicion) { this.posicion = posicion; }

    public boolean equals(Variable v2compare){
        Parametro p2compare = (Parametro) v2compare;
        if(this.getTipo().equals(p2compare.getTipo())){
            return true;
        }else{
            return false;
        }
    }
}
