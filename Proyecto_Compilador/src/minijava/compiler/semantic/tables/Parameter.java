package minijava.compiler.semantic.tables;

public class Parameter extends Variable{

    private Method method;
    private int position;

    public Method getMethodOfDefinedParameter() { return method; }

    public void setMethod(Method method) { this.method = method; }

    public int getParameterPosition() { return position; }

    public void setParameterPosition(int position) { this.position = position; }

    public boolean equals(Variable v2compare){
        Parameter p2compare = (Parameter) v2compare;
        if(this.getVarType().equals(p2compare.getVarType())){
            return true;
        }else{
            return false;
        }
    }
}
