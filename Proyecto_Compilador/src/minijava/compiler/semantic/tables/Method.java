package minijava.compiler.semantic.tables;

import minijava.compiler.lexical.analyzer.Token;

import java.util.ArrayList;
import java.util.Iterator;

public class Method {

    private boolean isStatic;
    protected Token token;
    protected String definedClass;
    protected ArrayList<Parameter> parameters;
    protected Type type;

    public Method(){
        parameters = new ArrayList<>();
        isStatic = false;
    }

    public void setMethodToken(Token token) { this.token = token; }

    public Token getMethodToken() { return token; }

    public int getMethodRow(){ return token.getRow(); }

    public String getMethodName(){ return token.getLexeme(); };

    public void setMethodType(Type type){ this.type = type; }

    public Type getMethodType() {
        return type;
    }

    public void setStatic(boolean isStatic) { this.isStatic = isStatic; }

    public boolean isStatic() {
        return isStatic;
    }

    public void setClassDeclaredMethod(String claseDefinido){ this.definedClass = claseDefinido; }

    public String getClassDeclaredMethod() { return definedClass; }

    public Parameter addParameter(Parameter parameter){
        parameter.setParameterPosition(parameters.size()+1);
        for(Parameter p2compare: parameters){
            if(p2compare.getVarName().equals(parameter.getVarName())){
                return parameter;
            }
        }
        parameters.add(parameter);
        return null;
    }

    public ArrayList<Parameter> getParameters() {
        return parameters;
    }

    public String getMapKey(){
        String encabezadoMet = this.getMethodName();
        encabezadoMet = encabezadoMet.concat("(");

        Iterator<Parameter> itParametros = parameters.iterator();
        if(itParametros.hasNext()) encabezadoMet = encabezadoMet.concat(itParametros.next().getVarType().getLexemeType());
        while(itParametros.hasNext()) encabezadoMet = encabezadoMet.concat(","+itParametros.next().getVarType().getLexemeType());

        encabezadoMet = encabezadoMet.concat(")");
        return encabezadoMet;
    }

    public boolean equals(Method met2compare){
        if(this.isStatic == met2compare.isStatic && this.getMethodType().equals(met2compare.getMethodType())){
            return this.getMapKey().equals(met2compare.getMapKey());
        }else{
            return false;
        }
    }
}
