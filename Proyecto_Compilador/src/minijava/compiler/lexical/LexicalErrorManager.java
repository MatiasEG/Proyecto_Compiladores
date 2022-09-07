package minijava.compiler.lexical;

import minijava.compiler.exception.lexical.LexicalException;
import minijava.compiler.filemanager.FileManager;

import java.io.IOException;

public class LexicalErrorManager {

    public static void showErrorMsg(LexicalException e, FileManager fileManager){
        System.out.println(e.getDetailedMsg());
        try {
            System.out.println("Detalle: "+fileManager.getLine(e.getLineError()-1));
            String spaces = String.format("%"+(e.getColumnError()+8)+"s", "");
            System.out.println(spaces+"^");
        } catch (IOException ex) {
            System.out.println("Error al mostrar la linea que contiene el error lexico.");
        }
        System.out.println(e.getMessage()+"\n");
    }
}
