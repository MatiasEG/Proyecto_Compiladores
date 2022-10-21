package minijava.compiler.semantic.tables;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.SemanticP2.SemanticExceptionVarLocalAlreadyExist;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.nodes.sentencia.BloqueNodo;
import minijava.compiler.semantic.nodes.sentencia.SentenciaNodo;
import minijava.compiler.semantic.tables.variable.VarLocal;

import java.util.ArrayList;
import java.util.HashMap;

public class Block {

    private Method method;
    private ArrayList<VarLocal> vars;
    private HashMap<String, VarLocal> varsHashMap;
    private BloqueNodo bloqueNodo;
    private ArrayList<SentenciaNodo> statements;

    public Block(Method method){
        this.method = method;
        vars = new ArrayList<>();
        varsHashMap = new HashMap<>();
        statements = new ArrayList<>();
    }

    public void setBlockNode(BloqueNodo bloqueNodo){ this.bloqueNodo = bloqueNodo; }

    public void addSentenciaNodo(SentenciaNodo sentenciaNodo){ statements.add(sentenciaNodo); }

    public void addVar(VarLocal v) throws SemanticException {
        if(!method.getParameterHashMap().containsKey(v.getVarName())){
            vars.add(v);
            varsHashMap.put(v.getVarName(), v);
        }else{
            throw new SemanticExceptionVarLocalAlreadyExist(method, v);
        }
    }

    public void check(SymbolTable st) throws SemanticException {
        for(SentenciaNodo sentencia: statements){
            sentencia.check(st);
        }
    }

    public HashMap<String, VarLocal> getVarsHashMap(){ return varsHashMap; }
}
