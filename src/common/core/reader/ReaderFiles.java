package common.core.reader;

import common.core.Exceptions.FileAccessException;
import common.core.Exceptions.FileDoesNotExist;
import common.core.Invoker;
import common.core.manager.CommandManager;

import java.io.*;
/**
 * Класс, который считывает и обрабатывает файлы
 * @author grigoryvolkov
 */
public class ReaderFiles implements IReader {
    private String filePath;
    private Boolean isWorking;
    private BufferedReader reader;
    private final Invoker invoker;
    private CommandManager parser;

    public ReaderFiles(String filePath, Invoker invoker){
        this.filePath = filePath;
        this.invoker = invoker;
    }
    @Override
    public void start() {
        isWorking = true;
        createReader();
        if(reader == null){
            return;
        }
        parser = new CommandManager(invoker);
         String line;
         while (isWorking) {
             line = nextLine();
             if (line == null) {
                 isWorking = false;
                 break;
             }
             parser.parseLine(line);
         }
    }
    public void stop(){
        isWorking = false;
    }


    @Override
    public String nextLine() {
        try {
            return reader.readLine();
        }
        catch (IOException ex){
            invoker.getPrinter().print("Произошла ошибка при чтении файла");
            return "";
        }
    }

    @Override
    public Boolean getWorking() {
        return isWorking;
    }

    @Override
    public CommandManager getCommandManager() {
        return parser;
    }

    private void createReader(){
        try{
            File file = new File(filePath);
            if (!file.exists()){
                throw new FileDoesNotExist();
            }
            if (!(file.canRead() && file.canWrite())){
                throw new FileAccessException();}
            reader = new BufferedReader(new FileReader(filePath));
        }
        catch (FileAccessException | FileNotFoundException | FileDoesNotExist ex){
            invoker.getPrinter().print(ex.getMessage());
        }
    }


}
