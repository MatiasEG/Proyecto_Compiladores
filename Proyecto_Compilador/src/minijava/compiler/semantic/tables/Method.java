package minijava.compiler.semantic.tables;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.tables.variable.Parameter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Method {

    private boolean isStatic;
    protected Token token;
    protected String definedClass;
    protected ArrayList<Parameter> parameters;
    protected HashMap<String, Parameter> parameterHashMap;
    protected Type type;
    protected Block block;
    protected Block actualBlock;
    protected int offsetParametro;

    public Method(){
        parameters = new ArrayList<>();
        isStatic = false;
        parameterHashMap = new HashMap<>();
        block = null;
        offsetParametro = 0;
    }

    public void setActualBlock(Block actualBlock){ this.actualBlock = actualBlock; }

    public Block getActualBlock(){ return actualBlock; }

    public void setMainBlock(Block block){ this.block = block; }

//    public Block getMainBlock(){ return block; }

    public boolean alreadyHaveBlock(){ return block != null; }

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

    public boolean needReturn(){ return !type.getLexemeType().equals("void"); }

    public void setClassDeclaredMethod(String claseDefinido){ this.definedClass = claseDefinido; }

    public String getClassDeclaredMethod() { return definedClass; }

    public Parameter getParameter(String parameterName){ return parameterHashMap.get(parameterName); }

    public Parameter addParameter(Parameter parameter){
        parameter.setParameterPosition(parameters.size()+1);
        for(Parameter p2compare: parameters){
            if(p2compare.getVarName().equals(parameter.getVarName())){
                return parameter;
            }
        }
        parameters.add(parameter);
        parameter.setOffset(offsetParametro--);
        parameterHashMap.put(parameter.getVarName(), parameter);
        return null;
    }

    public ArrayList<Parameter> getParameters() {
        return parameters;
    }

    public HashMap<String, Parameter> getParameterHashMap(){ return parameterHashMap; }

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

    public void checkBlock(SymbolTable st) throws SemanticException {
        block.check(st);
    }

    public void generarCodigoBloque(SymbolTable st) throws IOException {
        if(!getClassDeclaredMethod().equals("System") &&
                !getClassDeclaredMethod().equals("Object") &&
                !getClassDeclaredMethod().equals("String")){
            int espacios = this.getMethodName().length()+this.getClassDeclaredMethod().length()+1;
            String spaces = String.format("%"+(espacios)+"s", "");
            st.setIdentacionParaCodigo(spaces);
            st.writeLabel("# ---------------- "+ getMethodName()+""+getClassDeclaredMethod() +" ---------------- \n");
            st.writeLabel(this.getMethodName()+this.getClassDeclaredMethod()+":LOADFP\n" +
                    spaces+"LOADSP\n" +
                    spaces+"STOREFP\n");
            block.generarCodigoSentencias(st);
            st.write("STOREFP\n");
            st.write("RET "+this.getParameterHashMap().size()+"\n\n");
        }
    }
}
