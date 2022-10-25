package minijava.compiler.semantic.tables;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionVarLocalAlreadyExist;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.sentencia.BloqueNodo;
import minijava.compiler.semantic.nodes.sentencia.SentenciaNodo;
import minijava.compiler.semantic.tables.variable.VarLocal;
import minijava.compiler.semantic.tables.variable.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Block {

    private Method method;
    private ArrayList<VarLocal> vars;
    private HashMap<String, VarLocal> varsHashMap;
    private BloqueNodo bloqueNodo;
    private ArrayList<SentenciaNodo> statements;
    private Block bloquePadre;
    private ArrayList<Block> bloques;

    public Block(Method method){
        this.method = method;
        vars = new ArrayList<>();
        varsHashMap = new HashMap<>();
        statements = new ArrayList<>();
        bloques = new ArrayList<>();
        bloquePadre = null;
    }

    public Block(Method method, Block bloquePadre){
        this.method = method;
        vars = new ArrayList<>();
        varsHashMap = new HashMap<>();
        statements = new ArrayList<>();
        bloques = new ArrayList<>();
        this.bloquePadre = bloquePadre;
    }

    public Block getBloquePadre(){ return bloquePadre; }

    public void setBlockNode(BloqueNodo bloqueNodo){ this.bloqueNodo = bloqueNodo; }

    public void addSentenciaNodo(SentenciaNodo sentenciaNodo){ statements.add(sentenciaNodo); }

    public void addVar(VarLocal v) throws SemanticException {
        if (bloquePadre!= null && bloquePadre.contains(v.getVarName()) == null && !varsHashMap.containsKey(v.getVarName())){
            vars.add(v);
            varsHashMap.put(v.getVarName(), v);
        }else if(bloquePadre == null && !method.getParameterHashMap().containsKey(v.getVarName()) && !varsHashMap.containsKey(v.getVarName())) {
            vars.add(v);
            varsHashMap.put(v.getVarName(), v);
        }else{
            throw new SemanticExceptionVarLocalAlreadyExist(method, v);
        }
    }

    public Variable contains(String varName){
        if(method.getParameterHashMap().containsKey(varName)){
            return method.getParameterHashMap().get(varName);
        }else if(varsHashMap.containsKey(varName)){
            return varsHashMap.get(varName);
        }else if(bloquePadre != null){
            return bloquePadre.contains(varName);
        }else{
            return null;
        }
    }

    public void check(SymbolTable st) throws SemanticException {
        for(SentenciaNodo sentencia: statements){
            sentencia.check(st);
        }
    }

    public HashMap<String, VarLocal> getVarsHashMap(){ return varsHashMap; }

    public HashMap<String, VarLocal> getVarsAccesiblesDesdeElBloque(){
        if(bloquePadre!=null){
            HashMap<String, VarLocal> varsTotales = new HashMap<>();
            HashMap<String, VarLocal> varsDelPadre = bloquePadre.getVarsHashMap();

            varsTotales.putAll(varsDelPadre);
            varsTotales.putAll(varsHashMap);
            return varsTotales;
        }else{
            return varsHashMap;
        }
    }
}
