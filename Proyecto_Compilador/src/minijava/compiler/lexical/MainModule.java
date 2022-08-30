package minijava.compiler.lexical;

import minijava.compiler.exception.LexicalException;
import minijava.compiler.filemanager.FileManager;

import java.io.File;
import java.io.IOException;

public class MainModule {

    public static void main(String[]args){
        boolean error = false;

        ReservedWords.init();

        //TODO test

//        FileManager fileManager = new FileManager(new File("resources/conErrores/lexCELiteralesEnteros_01.java"));

        FileManager fileManager = new FileManager(new File(args[0]));

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(fileManager);

        Token nullToken = new Token("", "", -1);

        Token token = nullToken;

        do{
            try {
                token = lexicalAnalyzer.nextToken();
            }catch(LexicalException e){
                showErrorMsg(e, fileManager);
                token = nullToken;
                error = true;
            }

            if(token != nullToken){
                System.out.println("("+ token.getToken()+", "+token.getLexeme()+", "+token.getLineNumber()+")\n");
            }
        }while(!token.getToken().equals("EOF"));

        if(!error) System.out.println("[SinErrores]");
    }

    private static void showErrorMsg(LexicalException e, FileManager fileManager){
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
