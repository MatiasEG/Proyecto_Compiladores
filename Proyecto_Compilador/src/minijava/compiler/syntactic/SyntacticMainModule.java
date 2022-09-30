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
import java.util.ArrayList;

public class SyntacticMainModule {

    // OK (Sint) > chequeo de nombre de clases/interfaces repetidos
    // OK (Sint) > chequeo de redefinicion de metodos en la misma clase/interface
    // OK > chequeo de redefinicion de metodos por herencia
    // OK (Sint) > chequeo de declaracion de parametros con el mismo nombre
    // OK (Sint) > chequeo de declaracion de atributos con el mismo nombre
    // OK > agregar atributos heredados
    // OK > agregar metodos heredados
    // OK (Check) > chequear si existen las clases heredadas (solo ver mapeo de clases)
    // OK (Check) > chequear si existen las clases implementadas (solo ver mapeo de interfaces)
    // OK > chequear si no tiene herencia circular
    // OK > agregar constructor por defecto a las clases
    // OK > chequear que al menos una clase tiene metodo main
    // OK > las clases heredan de Object las interfaces no

    // TODO que hago con los constructores(los descarto) y el main que heredo en otras clases(los heredo)?

    // TODO tengo que chequear herencia circular entre varias clases (mas de dos)?
    // TODO TESTEAR que pasa si heredo el metodo de una clase A que tengo que implementar de una interface B?
    // TODO las clases e interfaces extienden de Object por default?
    // TODO consideraciones: solo permito definir un metodo main si se declara de la siguiente manera: static void main(){}

    // TODO los atributos heredados que fueron declarados nuevamente, aparecen ocultos en la clase? o no los agrego a la tabla?

    public static void main(String[]args){

//        FileManager fileManager = new FileManager(new File("resources/Clases.java"));
//        FileManager fileManager = new FileManager(new File("resources/conErrores/errExtiendoImplemento.java"));
        FileManager fileManager = new FileManager(new File(args[0]));


        SymbolTable st = new SymbolTable();

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(fileManager);

        SyntacticAnalyzer syntacticAnalyzer = new SyntacticAnalyzer(lexicalAnalyzer, st);

        boolean error = false;

        try {
            st.createConcreteClasses();

            syntacticAnalyzer.inicial();

            st.check();

            st.consolidacion();

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
