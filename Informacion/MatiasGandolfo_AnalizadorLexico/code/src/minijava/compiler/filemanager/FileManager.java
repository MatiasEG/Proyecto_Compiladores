package minijava.compiler.filemanager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {

    private int row;
    private int column;
    private File file;
    private FileReader fileReader;
    private int actualContent;

    public FileManager(File file){
        row = 1;
        column = 0;
        this.file = file;
        try {
            fileReader = new FileReader(file);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public char getNextChar(){
        char character2return = '\0';   //\0 represents an empty char
        checkNewLine();
        try{
            actualContent = fileReader.read();
            if( (char) actualContent == '\r') actualContent = fileReader.read();
            if(actualContent != -1){
                character2return = (char) actualContent;
                column++;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return character2return;
    }

    private void checkNewLine(){
        if('\n' == actualContent){
            row++;
            column = 0;
        }
    }

    public String getLine(int line) throws IOException {
        return Files.readAllLines(Paths.get(file.getPath())).get(line);
    }

    public boolean isEOF(){ return actualContent == -1; }

    public int getRow() { return row; }

    public int getColumn() { return column; }

}
