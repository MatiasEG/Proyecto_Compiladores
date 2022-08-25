package minijava.compiler.lexical;

import minijava.compiler.exception.LexicalException;
import minijava.compiler.filemanager.FileManager;

import java.io.File;
import java.io.IOException;

public class MainModule {

    public static void main(String[]args){
        boolean error = false;

        ReservedWords.init();

        FileManager fileManager = new FileManager(new File("resources/sinErrores/lexSinErrores02.java"));

//        FileManager fileManager = new FileManager(new File(args[0]));

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(fileManager);

        Token nullToken = new Token("", "", -1);

        Token token = nullToken;

//        System.out.println("Valor: "+(Character.getNumericValue('a') < Character.getNumericValue('b')));

//        System.out.println("a: "+Character.getNumericValue('a'));
//        System.out.println("b: "+Character.getNumericValue('b'));
//        System.out.println("c: "+Character.getNumericValue('c'));
//        System.out.println("d: "+Character.getNumericValue('d'));
//        System.out.println("e: "+Character.getNumericValue('e'));
//        System.out.println("f: "+Character.getNumericValue('f'));
//        System.out.println("A: "+Character.getNumericValue('A'));
//        System.out.println("B: "+Character.getNumericValue('B'));
//        System.out.println("C: "+Character.getNumericValue('C'));
//        System.out.println("D: "+Character.getNumericValue('D'));
//        System.out.println("E: "+Character.getNumericValue('E'));
//        System.out.println("F: "+Character.getNumericValue('F'));
//        System.out.println("Valor: "+(Character.getNumericValue('F') < Character.getNumericValue('b')));
//        System.out.println("Valor: "+('F' < 'b'));

        //TODO test
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
