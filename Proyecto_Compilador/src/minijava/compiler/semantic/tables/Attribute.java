package minijava.compiler.semantic.tables;

public class Attribute extends Variable{

    private boolean isVisible;

    public void setVisibilidad(boolean isVisible){ this.isVisible = isVisible; }

    public boolean isVisible() { return isVisible; }

    public static Attribute clone(Attribute attribute){
        Attribute clone = new Attribute();
        clone.setVarType(attribute.getVarType());
        clone.setVarToken(attribute.getVarToken());
        clone.setVisibilidad(attribute.isVisible());
        return clone;
    }

    public boolean equals(Variable var2compare){
        Attribute atr2compare = (Attribute) var2compare;
        if(this.getVarType().equals(atr2compare.getVarType()) && this.getVarName().equals(atr2compare.getVarName())){
            return true;
        }else{
            return false;
        }
    }
}
