package minijava.compiler.semantic;

import minijava.compiler.exception.*;
import minijava.compiler.exception.lexical.LexicalException;
import minijava.compiler.exception.SemanticException;
import minijava.compiler.filemanager.FileManager;
import minijava.compiler.lexical.LexicalErrorManager;
import minijava.compiler.lexical.analyzer.LexicalAnalyzer;
import minijava.compiler.syntactic.SyntacticAnalyzer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainModule {

    // TODO cmd: java -jar CeIVM-cei2011.jar CodigoMaquina.txt

    public static void main(String[]args){
        File myObj = new File("CodigoMaquina.txt");

//        FileManager fileManager = new FileManager(new File("resources/sinErrores/correcciones.java"));
        FileManager fileManager = new FileManager(new File("resources/codigoMaquina/test3Main.java"));
//        FileManager fileManager = new FileManager(new File(args[0]));

        SymbolTable st = new SymbolTable();

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(fileManager);

        SyntacticAnalyzer syntacticAnalyzer = new SyntacticAnalyzer(lexicalAnalyzer, st);

        boolean error = false;

        try {

            if (!myObj.createNewFile()) {
                myObj.delete();
                myObj.createNewFile();
            }

            FileWriter writer = new FileWriter("CodigoMaquina.txt");
//            writer.write("HolaMundo\n");

            st.setWriter(writer);

            st.createConcreteClasses();

            syntacticAnalyzer.inicial();

            st.check();

            st.consolidacion(); // TODO comentar de ser necesario

            st.checkSentences(); // TODO comentar de ser necesario

            st.generarCodigo();

            writer.close();

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
        } catch (IOException e) {
            System.out.println("Ocurrio un error con el archivo de codigo maquina.");
            e.printStackTrace();
        }

        if(!error) {
            System.out.println("[SinErrores]");
//            st.imprimirTablas();
        }

    }
}
