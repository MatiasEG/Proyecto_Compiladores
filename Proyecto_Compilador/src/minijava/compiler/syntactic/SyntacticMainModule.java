package minijava.compiler.syntactic;

import minijava.compiler.exception.SyntacticException;
import minijava.compiler.exception.lexical.LexicalException;
import minijava.compiler.filemanager.FileManager;
import minijava.compiler.lexical.analyzer.LexicalAnalyzer;
import minijava.compiler.syntactic.analyzer.SyntacticAnalyzer;

import java.io.File;

public class SyntacticMainModule {

    public static void main(String[]args){


//        FileManager fileManager = new FileManager(new File("resources/sinErrores/sintCorrecto04.java"));
        FileManager fileManager = new FileManager(new File(args[0]));

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(fileManager);

        SyntacticAnalyzer syntacticAnalyzer = new SyntacticAnalyzer(lexicalAnalyzer);

        boolean error = false;

        try {

            syntacticAnalyzer.inicial();

        } catch (LexicalException e) {
            e.printStackTrace();
        } catch (SyntacticException e) {
            error = true;
            System.out.println("[Error:"+e.getErrorToken().getLexeme()+"|"+e.getRow()+"]");
            e.printStackTrace();
        }


        if(!error) System.out.println("[SinErrores]");

    }
}
