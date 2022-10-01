package minijava.compiler.exception.semantic.duplicated;

import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.semantic.tables.ClassOrInterface;
import minijava.compiler.semantic.tables.Method;

public class SemanticExceptionDuplicatedMain extends SemanticException {

    private Method mainDuplicado;
    private ClassOrInterface claseOriginal;

    public SemanticExceptionDuplicatedMain(Method mainDuplicado, ClassOrInterface claseOriginal){
        super("El metodo main esta duplicado en la linea: "+mainDuplicado.getMethodRow()+" se definio antes en la clase \'"+claseOriginal.getNombre()+"\'.", mainDuplicado.getMethodToken());
        this.mainDuplicado = mainDuplicado;
        this.claseOriginal = claseOriginal;
    }
}
