package minijava.compiler.semantic.nodes.sentencia;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.tables.Block;

public class BloqueNodo extends SentenciaNodo {

    private Block block;

    public BloqueNodo(Block block){
        this.block = block;
    }

    public void check(SymbolTable st) throws SemanticException {
        //TODO implementar bloques como sentencias
        block.check(st);
    }
}
