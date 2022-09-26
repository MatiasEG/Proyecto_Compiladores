package minijava.compiler.syntactic;

import minijava.compiler.exception.*;
import minijava.compiler.exception.lexical.LexicalException;
import minijava.compiler.exception.semantic.SemanticException;
import minijava.compiler.filemanager.FileManager;
import minijava.compiler.lexical.LexicalErrorManager;
import minijava.compiler.lexical.analyzer.LexicalAnalyzer;
import minijava.compiler.semantic.SymbolTable;
import minijava.compiler.syntactic.analyzer.SyntacticAnalyzer;

import java.io.File;

public class SyntacticMainModule {

    // TODO
    // OK (Sint) > chequeo de nombre de clases/interfaces repetidos
    // OK (Sint) > chequeo de redefinicion de metodos en la misma clase/interface
    // ? > chequeo de redefinicion de metodos por herencia
    // OK (Sint) > chequeo de declaracion de parametros con el mismo nombre
    // OK (Sint) > chequeo de declaracion de atributos con el mismo nombre
    // ? > agregar atributos heredados
    // ? > agregar metodos heredados
    // OK (Check) > chequear si existen las clases heredadas
    // OK (Check) > chequear si existen las clases implementadas


    public static void main(String[]args){

        FileManager fileManager = new FileManager(new File("resources/Clases.java"));
//        FileManager fileManager = new FileManager(new File(args[0]));


        SymbolTable st = new SymbolTable();

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(fileManager);

        SyntacticAnalyzer syntacticAnalyzer = new SyntacticAnalyzer(lexicalAnalyzer, st);

        boolean error = false;

        try {

            syntacticAnalyzer.inicial();

            st.check();

        } catch (LexicalException e) {
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
