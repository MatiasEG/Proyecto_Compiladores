package minijava.compiler.semantic;

import minijava.compiler.exception.*;
import minijava.compiler.exception.lexical.LexicalException;
import minijava.compiler.exception.SemanticException;
import minijava.compiler.filemanager.FileManager;
import minijava.compiler.lexical.LexicalErrorManager;
import minijava.compiler.lexical.analyzer.LexicalAnalyzer;
import minijava.compiler.syntactic.SyntacticAnalyzer;

import java.io.File;

public class MainModule {

    public static void main(String[]args){

        FileManager fileManager = new FileManager(new File(args[0]));

        SymbolTable st = new SymbolTable();

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(fileManager);

        SyntacticAnalyzer syntacticAnalyzer = new SyntacticAnalyzer(lexicalAnalyzer, st);

        boolean error = false;

        try {
            st.createConcreteClasses();

            syntacticAnalyzer.inicial();

            st.check();

            st.consolidacion(); // TODO comentar de ser necesario

            st.checkSentences(); // TODO comentar de ser necesario

        } catch (LexicalException e) {
            error = true;
            LexicalErrorManager.showErrorMsg(e, fileManager);
        } catch (SyntacticException e) {
            error = true;
            System.out.println("[Error:"+e.getErrorToken().getLexeme()+"|"+e.getRow()+"]");
            System.out.println(e.getMessage());
        } catch (SemanticException e) {
            error = true;
            System.out.println("[Error:"+e.getLexeme()+"|"+e.getRow()+"]");
            System.out.println(e.getMessage());
        }

        if(!error) {
            System.out.println("[SinErrores]");
            st.imprimirTablas();
        }

    }
}
