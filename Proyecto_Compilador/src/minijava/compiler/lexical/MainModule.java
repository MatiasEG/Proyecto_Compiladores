package minijava.compiler.lexical;

import minijava.compiler.exception.LexicalException;
import minijava.compiler.filemanager.FileManager;

import java.io.File;
import java.io.IOException;

public class MainModule {

    public static void main(String[]args){
        boolean error = false;

        ReservedWords.init();

        //TODO si el comentario multilinea tiene una unica linea,
        // el piquito que seniala el error va al principio del comentario tambien?

        //TODO los test de error no pasan porque detectan el detalle del error, como lo oculto?

        //TODO el test tiene un error que hace que mi caso de prueba de comentariomultilinea_02 falle porque tiene la informacion del comentariomultilinea_01

        //TODO test
//        FileManager fileManager = new FileManager(new File("resources/conErrores/lexCEIdentificadores_01.java"));

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
        }while(token.getToken() != "EOF");

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
