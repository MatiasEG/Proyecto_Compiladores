package minijava.compiler.semantic.nodes.sentencia;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.semantic.tables.Block;

import java.io.IOException;

public class BloqueNodo extends SentenciaNodo {

    protected Block block;

    public BloqueNodo(Block block){
        this.block = block;
    }

    public void check(SymbolTable st) throws SemanticException {
        block.check(st);
    }

    @Override
    public void generar(SymbolTable st) throws IOException {
        block.generarCodigoSentencias(st);
    }
}
