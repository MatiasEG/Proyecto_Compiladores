package minijava.compiler.semantic;

public class Atributo extends Variable{

    private boolean isVisible;

    public void setVisibilidad(boolean isVisible){ this.isVisible = isVisible; }

    public boolean isVisible() { return isVisible; }

    public static Atributo clone(Atributo atributo){
        Atributo clone = new Atributo();
        clone.setTipo(atributo.getTipo());
        clone.setNombre(atributo.getNombre());
        clone.setVisibilidad(atributo.isVisible());
        clone.setLineaDeclaracion(atributo.getLineaDeclaracion());
        return clone;
    }

    public boolean equals(Variable var2compare){
        Atributo atr2compare = (Atributo) var2compare;
        if(this.getTipo().equals(atr2compare.getTipo()) && this.getNombre().equals(atr2compare.getNombre())){
            return true;
        }else{
            return false;
        }
    }
}
