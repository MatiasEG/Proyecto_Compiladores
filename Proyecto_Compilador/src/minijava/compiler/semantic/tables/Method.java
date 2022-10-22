package minijava.compiler.semantic.tables;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.Node;
import minijava.compiler.semantic.tables.variable.Parameter;

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
    protected ArrayList<Node> nodes;
    protected Block block;
    protected Block actualBlock;

    public Method(){
        parameters = new ArrayList<>();
        isStatic = false;
        nodes = new ArrayList<>();
        parameterHashMap = new HashMap<>();
        block = null;
    }

    public void setMainBlock(Block block){ actualBlock = block; this.block = block; }

    public Block getMainBlock(){ return block; }

    public void addNewBlock(Block block){ actualBlock.addBlock(block); }

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
}
