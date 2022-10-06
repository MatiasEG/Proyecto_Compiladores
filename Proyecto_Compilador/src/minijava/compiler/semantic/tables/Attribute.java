package minijava.compiler.semantic.tables;

public class Attribute extends Variable{

    private boolean isPublic;
    private String belongClass;
    private boolean attributeIsFromFather;
    private boolean pisado;

    public Attribute(){
        attributeIsFromFather = false;
        pisado = false;
    }

    public void setPisado(boolean pisado){ this.pisado = pisado; }

    public boolean getPisado(){ return pisado; }

    public void setAttributeIsFromFather(boolean attributeIsFromFather) { this.attributeIsFromFather = attributeIsFromFather; }

    public boolean attributeIsFromFather(){ return attributeIsFromFather; }

    public void setClass_(String belongClass){ this.belongClass = belongClass; }

    public String getClass_(){ return belongClass; }

    public void setVisibilidad(boolean isVisible){ this.isPublic = isVisible; }

    public boolean isPublic() { return isPublic; }

    public static Attribute clone(Attribute attribute){
        Attribute clone = new Attribute();
        clone.setVarType(attribute.getVarType());
        clone.setVarToken(attribute.getVarToken());
        clone.setVisibilidad(attribute.isPublic());
        clone.setClass_(attribute.getClass_());
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
