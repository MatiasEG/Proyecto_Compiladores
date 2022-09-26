package minijava.compiler.semantic;

public class Parametro extends Variable{

    private Metodo metodo;
    private int posicion;

    public Metodo getMetodo() { return metodo; }

    public void setMetodo(Metodo metodo) { this.metodo = metodo; }

    public int getPosicion() { return posicion; }

    public void setPosicion(int posicion) { this.posicion = posicion; }

    // todo no puedo agregar parametros con el mismo nombre que uno que ya estaba

    public boolean equals(Variable v2compare){
        Parametro p2compare = (Parametro) v2compare;
        if(this.getTipo().equals(p2compare.getTipo())){
            return true;
        }else{
            return false;
        }
    }
}
