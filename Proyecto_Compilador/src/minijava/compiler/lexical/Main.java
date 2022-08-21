package minijava.compiler.lexical;

import minijava.compiler.exception.LexicalException;
import minijava.compiler.filemanager.FileManager;

import java.io.File;

public class Main {

    public static void main(String[]args){
//        FileManager fileManager = new FileManager(new File(args.toString()));

        ReservedWords.init();

        FileManager fileManager = new FileManager(new File("resources/sinErrores/comment.java"));

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(fileManager);

        Token token1 = null;

        System.out.println("Value 1: "+ Character.getNumericValue('\n')+" // Value 2: "+Character.getNumericValue(' '));
        System.out.println("Boolean: "+ ('\n' == ' '));

        //TODO test
        for(int i = 0; i<7; i++){
            try {
                token1 = lexicalAnalyzer.nextToken();
            }catch(LexicalException e){
                e.printStackTrace();
            }

            if(token1 != null){
                System.out.println("Token 1: "+ token1.getToken());
                System.out.println("Token 1 lexeme: "+ token1.getLexeme());
                System.out.println("Token 1 line number: "+ token1.getLineNumber());
                System.out.println("----------------------------------------\n");
            }
        }

    }

}
