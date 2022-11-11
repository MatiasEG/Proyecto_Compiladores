package minijava.compiler.semantic;

import minijava.compiler.exception.SemanticException;
import minijava.compiler.exception.semanticP1.duplicated.SemanticExceptionDuplicatedParameter;
import minijava.compiler.lexical.analyzer.Token;
import minijava.compiler.semantic.tables.Block;
import minijava.compiler.semantic.tables.Class;
import minijava.compiler.semantic.tables.Method;
import minijava.compiler.semantic.tables.variable.Parameter;
import minijava.compiler.semantic.tables.Type;

import java.io.IOException;

public class PredefinedClasses {

    public static Class createObjectClass() throws SemanticException {
        Parameter parameterIf;
        Class object = new Class(new Token("idClass", "Object", 0));

        Method debugPrint = new Method();
        debugPrint.setStatic(true);
        debugPrint.setClassDeclaredMethod("Object");
        debugPrint.setMethodType(new Type(new Token("idKeyWord_void", "void", 0)));
        debugPrint.setMethodToken(new Token("idMetVar", "debugPrint", 0));
        Parameter i = new Parameter();
        i.setMethod(debugPrint);
        i.setParameterPosition(0);
        i.setVarToken(new Token("idMetVar", "i", 0));
        i.setVarType(new Type(new Token("idKeyWord_int", "int", 0)));
        if((parameterIf = debugPrint.addParameter(i)) != null) throw new SemanticExceptionDuplicatedParameter(parameterIf);
        object.addMetodo(debugPrint);
        debugPrint.setMainBlock(new Block(debugPrint));

        return object;
    }

    public static Class createStringClass(){
        Class string = new Class(new Token("idClass", "String", 0));
        return string;
    }

    public static Class createSystemClass() throws SemanticException{
        Parameter parameterIf;
        Class system = new Class(new Token("idClass", "System", 0));

        Method read = new Method();
        read.setStatic(true);
        read.setClassDeclaredMethod("System");
        read.setMethodType(new Type(new Token("idKeyWord_int", "int", 0)));
        read.setMethodToken(new Token("idMetVar", "read", 0));
        system.addMetodo(read);
        read.setMainBlock(new Block(read));

        Method printB = new Method();
        printB.setStatic(true);
        printB.setClassDeclaredMethod("System");
        printB.setMethodType(new Type(new Token("idKeyWord_void", "void", 0)));
        printB.setMethodToken(new Token("idMetVar", "printB", 0));
        Parameter b = new Parameter();
        b.setMethod(printB);
        b.setParameterPosition(0);
        b.setVarToken(new Token("idMetVar", "b", 0));
        b.setVarType(new Type(new Token("idKeyWord_boolean", "boolean", 0)));
        if((parameterIf = printB.addParameter(b)) != null) throw new SemanticExceptionDuplicatedParameter(parameterIf);
        system.addMetodo(printB);
        printB.setMainBlock(new Block(printB));

        Method printC = new Method();
        printC.setStatic(true);
        printC.setClassDeclaredMethod("System");
        printC.setMethodType(new Type(new Token("idKeyWord_void", "void", 0)));
        printC.setMethodToken(new Token("idMetVar", "printC", 0));
        Parameter c = new Parameter();
        c.setMethod(printC);
        c.setParameterPosition(0);
        c.setVarToken(new Token("idMetVar", "c", 0));
        c.setVarType(new Type(new Token("literalCharacter", "char", 0)));
        if((parameterIf = printC.addParameter(c)) != null) throw new SemanticExceptionDuplicatedParameter(parameterIf);
        system.addMetodo(printC);
        printC.setMainBlock(new Block(printC));

        Method printI = new Method();
        printI.setStatic(true);
        printI.setClassDeclaredMethod("System");
        printI.setMethodType(new Type(new Token("idKeyWord_void", "void", 0)));
        printI.setMethodToken(new Token("idMetVar", "printI", 0));
        Parameter i = new Parameter();
        i.setMethod(printI);
        i.setParameterPosition(0);
        i.setVarToken(new Token("idMetVar", "i", 0));
        i.setVarType(new Type(new Token("idKeyWord_int", "int", 0)));
        if((parameterIf = printI.addParameter(i)) != null) throw new SemanticExceptionDuplicatedParameter(parameterIf);
        system.addMetodo(printI);
        printI.setMainBlock(new Block(printI));

        Method printS = new Method();
        printS.setStatic(true);
        printS.setClassDeclaredMethod("System");
        printS.setMethodType(new Type(new Token("idKeyWord_void", "void", 0)));
        printS.setMethodToken(new Token("idMetVar", "printS", 0));
        Parameter s = new Parameter();
        s.setMethod(printS);
        s.setParameterPosition(0);
        s.setVarToken(new Token("idMetVar", "s", 0));
        s.setVarType(new Type(new Token("idClass", "String", 0)));
        if((parameterIf = printS.addParameter(s)) != null) throw new SemanticExceptionDuplicatedParameter(parameterIf);
        system.addMetodo(printS);
        printS.setMainBlock(new Block(printS));

        Method println = new Method();
        println.setStatic(true);
        println.setClassDeclaredMethod("System");
        println.setMethodType(new Type(new Token("idKeyWord_void", "void", 0)));
        println.setMethodToken(new Token("idMetVar", "println", 0));
        system.addMetodo(println);
        println.setMainBlock(new Block(println));

        Method printBln = new Method();
        printBln.setStatic(true);
        printBln.setClassDeclaredMethod("System");
        printBln.setMethodType(new Type(new Token("idKeyWord_void", "void", 0)));
        printBln.setMethodToken(new Token("idMetVar", "printBln", 0));
        Parameter bln = new Parameter();
        bln.setMethod(printBln);
        bln.setParameterPosition(0);
        bln.setVarToken(new Token("idMetVar", "b", 0));
        bln.setVarType(new Type(new Token("idKeyWord_boolean", "boolean", 0)));
        if((parameterIf = printBln.addParameter(bln)) != null) throw new SemanticExceptionDuplicatedParameter(parameterIf);
        system.addMetodo(printBln);
        printBln.setMainBlock(new Block(printBln));

        Method printCln = new Method();
        printCln.setStatic(true);
        printCln.setClassDeclaredMethod("System");
        printCln.setMethodType(new Type(new Token("idKeyWord_void", "void", 0)));
        printCln.setMethodToken(new Token("idMetVar", "printCln", 0));
        Parameter cln = new Parameter();
        cln.setMethod(printCln);
        cln.setParameterPosition(0);
        cln.setVarToken(new Token("idMetVar", "c", 0));
        cln.setVarType(new Type(new Token("literalCharacter", "char", 0)));
        if((parameterIf = printCln.addParameter(cln)) != null) throw new SemanticExceptionDuplicatedParameter(parameterIf);
        system.addMetodo(printCln);
        printCln.setMainBlock(new Block(printCln));

        Method printIln = new Method();
        printIln.setStatic(true);
        printIln.setClassDeclaredMethod("System");
        printIln.setMethodType(new Type(new Token("idKeyWord_void", "void", 0)));
        printIln.setMethodToken(new Token("idMetVar", "printIln", 0));
        Parameter iln = new Parameter();
        iln.setMethod(printIln);
        iln.setParameterPosition(0);
        iln.setVarToken(new Token("idMetVar", "i", 0));
        iln.setVarType(new Type(new Token("idKeyWord_int", "int", 0)));
        if((parameterIf = printIln.addParameter(iln)) != null) throw new SemanticExceptionDuplicatedParameter(parameterIf);
        system.addMetodo(printIln);
        printIln.setMainBlock(new Block(printIln));

        Method printSln = new Method();
        printSln.setStatic(true);
        printSln.setClassDeclaredMethod("System");
        printSln.setMethodType(new Type(new Token("idKeyWord_void", "void", 0)));
        printSln.setMethodToken(new Token("idMetVar", "printSln", 0));
        Parameter sln = new Parameter();
        sln.setMethod(printSln);
        sln.setParameterPosition(0);
        sln.setVarToken(new Token("idMetVar", "s", 0));
        sln.setVarType(new Type(new Token("idClass", "String", 0)));
        if((parameterIf = printSln.addParameter(sln)) != null) throw new SemanticExceptionDuplicatedParameter(parameterIf);
        system.addMetodo(printSln);
        printSln.setMainBlock(new Block(printSln));

        return system;
    }

    public static void generarCodigoClasesXDefecto(SymbolTable st) throws IOException {
        st.writeLabel("# ---------------- debugPrintObject ---------------- \n");
        String spaces = String.format("%"+("debugPrint".length()+"Object".length()+1)+"s", "");
        st.writeLabel("debugPrintObject:\n" +
                spaces+"LOADFP\n" +
                spaces+"LOADSP\n" +
                spaces+"STOREFP\n" +
                spaces+"LOAD 3\n" +
                // TODO generar debugPrintObject...
                spaces+"STOREFP\n" +
                spaces+"RET 1\n\n");

        // TODO generar codigo de clase String?

        st.writeLabel("# ---------------- readSystem ---------------- \n");
        spaces = String.format("%"+("read".length()+"System".length()+1)+"s", "");
        st.writeLabel("readSystem:\n" +
                spaces+"LOADFP\n" +
                spaces+"LOADSP\n" +
                spaces+"STOREFP\n" +
                // TODO generar readSystem...
                spaces+"STOREFP\n" +
                spaces+"RET 0\n\n");

        st.writeLabel("# ---------------- printBSystem ---------------- \n");
        spaces = String.format("%"+("printB".length()+"System".length()+1)+"s", "");
        st.writeLabel("printBSystem:\n" +
                spaces+"LOADFP\n" +
                spaces+"LOADSP\n" +
                spaces+"STOREFP\n" +
                spaces+"LOAD 3\n" +
                spaces+"BPRINT\n" +
                spaces+"STOREFP\n" +
                spaces+"RET 1\n\n");

        st.writeLabel("# ---------------- printCSystem ---------------- \n");
        spaces = String.format("%"+("printC".length()+"System".length()+1)+"s", "");
        st.writeLabel("printCSystem:\n" +
                spaces+"LOADFP\n" +
                spaces+"LOADSP\n" +
                spaces+"STOREFP\n" +
                spaces+"LOAD 3\n" +
                spaces+"CPRINT\n" +
                spaces+"STOREFP\n" +
                spaces+"RET 1\n\n");

        st.writeLabel("# ---------------- printISystem ---------------- \n");
        spaces = String.format("%"+("printI".length()+"System".length()+1)+"s", "");
        st.writeLabel("printISystem:\n" +
                spaces+"LOADFP\n" +
                spaces+"LOADSP\n" +
                spaces+"STOREFP\n" +
                spaces+"LOAD 3\n" +
                spaces+"IPRINT\n" +
                spaces+"STOREFP\n" +
                spaces+"RET 1\n\n");

        st.writeLabel("# ---------------- printSSystem ---------------- \n");
        spaces = String.format("%"+("printS".length()+"System".length()+1)+"s", "");
        st.writeLabel("printSSystem:\n" +
                spaces+"LOADFP\n" +
                spaces+"LOADSP\n" +
                spaces+"STOREFP\n" +
                spaces+"LOAD 3\n" +
                spaces+"SPRINT\n" +
                spaces+"STOREFP\n" +
                spaces+"RET 1\n\n");

        st.writeLabel("# ---------------- printlnSystem ---------------- \n");
        spaces = String.format("%"+("println".length()+"System".length()+1)+"s", "");
        st.writeLabel("printlnSystem:\n" +
                spaces+"LOADFP\n" +
                spaces+"LOADSP\n" +
                spaces+"STOREFP\n" +
                spaces+"PRNLN\n" +
                spaces+"STOREFP\n" +
                spaces+"RET 0\n\n");

        st.writeLabel("# ---------------- printBlnSystem ---------------- \n");
        spaces = String.format("%"+("printBln".length()+"System".length()+1)+"s", "");
        st.writeLabel("printBlnSystem:\n" +
                spaces+"LOADFP\n" +
                spaces+"LOADSP\n" +
                spaces+"STOREFP\n" +
                spaces+"LOAD 3\n" +
                spaces+"BPRINT\n" +
                spaces+"PRNLN\n" +
                spaces+"STOREFP\n" +
                spaces+"RET 1\n\n");

        st.writeLabel("# ---------------- printClnSystem ---------------- \n");
        spaces = String.format("%"+("printCln".length()+"System".length()+1)+"s", "");
        st.writeLabel("printClnSystem:\n" +
                spaces+"LOADFP\n" +
                spaces+"LOADSP\n" +
                spaces+"STOREFP\n" +
                spaces+"LOAD 3\n" +
                spaces+"CPRINT\n" +
                spaces+"PRNLN\n" +
                spaces+"STOREFP\n" +
                spaces+"RET 1\n\n");

        st.writeLabel("# ---------------- printIlnSystem ---------------- \n");
        spaces = String.format("%"+("printIln".length()+"System".length()+1)+"s", "");
        st.writeLabel("printIlnSystem:\n" +
                spaces+"LOADFP\n" +
                spaces+"LOADSP\n" +
                spaces+"STOREFP\n" +
                spaces+"LOAD 3\n" +
                spaces+"IPRINT\n" +
                spaces+"PRNLN\n" +
                spaces+"STOREFP\n" +
                spaces+"RET 1\n\n");

        st.writeLabel("# ---------------- printSlnSystem ---------------- \n");
        spaces = String.format("%"+("printSln".length()+"System".length()+1)+"s", "");
        st.writeLabel("printSlnSystem:\n" +
                spaces+"LOADFP\n" +
                spaces+"LOADSP\n" +
                spaces+"STOREFP\n" +
                spaces+"LOAD 3\n" +
                spaces+"SPRINT\n" +
                spaces+"PRNLN\n" +
                spaces+"STOREFP\n" +
                spaces+"RET 1\n\n");
    }
}
