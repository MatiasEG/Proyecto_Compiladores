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

    // Chequear a mano
    // OK > agregar atributos heredados
    // OK > agregar constructor por defecto a las clases
    // OK > las clases heredan de Object las interfaces no

    //TODO para semantico etapa 4:
    // VarLocales
    //      ok -> VarLocal con tipo ref no definido
    //      ok -> VarLocal con mismo nombre que un parametro
    //      ok -> VarLocal no definida


    public static void main(String[]args){

//        FileManager fileManager = new FileManager(new File("resources/sinErrores/Correcciones.java"));
        FileManager fileManager = new FileManager(new File("resources/sinErrores/Clase.java"));
//
//        FileManager fileManager = new FileManager(new File("resources/conErrores/errVarEncadenadaNoCoincideConTipoAsignacion.java"));
//        FileManager fileManager = new FileManager(new File(args[0]));

        SymbolTable st = new SymbolTable();

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(fileManager);

        SyntacticAnalyzer syntacticAnalyzer = new SyntacticAnalyzer(lexicalAnalyzer, st);

        boolean error = false;

        try {
            st.createConcreteClasses();

            syntacticAnalyzer.inicial();

            st.check();

            st.consolidacion();

            st.checkSentences();

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
