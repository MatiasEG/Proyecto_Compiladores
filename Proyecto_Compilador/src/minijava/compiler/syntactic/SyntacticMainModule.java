package minijava.compiler.syntactic;

import minijava.compiler.exception.SyntacticException;
import minijava.compiler.exception.lexical.LexicalException;
import minijava.compiler.filemanager.FileManager;
import minijava.compiler.lexical.LexicalErrorManager;
import minijava.compiler.lexical.analyzer.LexicalAnalyzer;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.syntactic.analyzer.SyntacticAnalyzer;

import java.io.File;

public class SyntacticMainModule {

    public static void main(String[]args){

        FileManager fileManager = new FileManager(new File("resources/Clases.java"));
//        FileManager fileManager = new FileManager(new File(args[0]));


        SymbolTable st = new SymbolTable();

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(fileManager);

        SyntacticAnalyzer syntacticAnalyzer = new SyntacticAnalyzer(lexicalAnalyzer, st);

        boolean error = false;

        try {

            syntacticAnalyzer.inicial();

        } catch (LexicalException e) {
            LexicalErrorManager.showErrorMsg(e, fileManager);
        } catch (SyntacticException e) {
            error = true;
            System.out.println("[Error:"+e.getErrorToken().getLexeme()+"|"+e.getRow()+"]");
            System.out.println(e.getMessage());
        }

        if(!error) System.out.println("[SinErrores]");

        st.imprimirTablas();

    }
}
