package minijava.compiler.filemanager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileManager {

    private int row;
    private int column;
    private File file;
    private FileReader fileReader;
    private int actualContent;
    private int previousContent;

    public FileManager(File file){
        row = 1;
        column = 1;
        previousContent = -1;
        this.file = file;
        try {
            fileReader = new FileReader(file);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public char getNextChar(){
        char character2return = '\0';   //\0 represents an empty char
        updatePreviousContent(actualContent);
        try{
            actualContent = fileReader.read();
            if(actualContent != -1){
                character2return = (char) actualContent;
                checkNewLine();
                column++;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return character2return;
    }

    private void updatePreviousContent(int actualContent){
        previousContent = actualContent;
    }

    private void checkNewLine(){
        if('\n' == actualContent){
            row++;
        }
    }

    public boolean isEOF(){
        return actualContent == -1;
    }

    public int getRow() { return row; }

    public int getColumn() { return column; }

    public int getActualContent() { return actualContent; }
}
