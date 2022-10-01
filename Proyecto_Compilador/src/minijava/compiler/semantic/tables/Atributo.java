package minijava.compiler.semantic.tables;

public class Atributo extends Variable{

    private boolean isVisible;

    public void setVisibilidad(boolean isVisible){ this.isVisible = isVisible; }

    public boolean isVisible() { return isVisible; }

    public static Atributo clone(Atributo atributo){
        Atributo clone = new Atributo();
        clone.setVarType(atributo.getVarType());
        clone.setVarToken(atributo.getVarToken());
        clone.setVisibilidad(atributo.isVisible());
        return clone;
    }

    public boolean equals(Variable var2compare){
        Atributo atr2compare = (Atributo) var2compare;
        if(this.getVarType().equals(atr2compare.getVarType()) && this.getVarName().equals(atr2compare.getVarName())){
            return true;
        }else{
            return false;
        }
    }
}
