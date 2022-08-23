package minijava.compiler.lexical;

import minijava.compiler.exception.LexicalException;
import minijava.compiler.filemanager.FileManager;

import java.io.File;

public class MainModule {

    public static void main(String[]args){
        String lastToken = "";
        boolean error = false;

        ReservedWords.init();

        FileManager fileManager = new FileManager(new File("resources/sinErrores/comment.java"));

//        FileManager fileManager = new FileManager(new File(args[0]));

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(fileManager);

        Token token = null;

        //TODO test
        while(lastToken != "EOF"){
            try {
                token = lexicalAnalyzer.nextToken();
                lastToken = token.getToken();
            }catch(LexicalException e){
                System.out.println(e.getMessage());
                error = true;
            }

            if(token != null){
                System.out.println("Token: "+ token.getToken());
                System.out.println("Lexeme: "+ token.getLexeme());
                System.out.println("Line number: "+ token.getLineNumber());
                System.out.println("----------------------------------------\n");
            }
        }

        if(!error) System.out.println("[SinErrores]");
    }

}
