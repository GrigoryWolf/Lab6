package common.core;
import common.core.manager.CommandManager;
import common.core.manager.FileManager;
import common.core.manager.ModelManager;
import java.io.ObjectOutputStream;

/**
 * Класс вызывающий команды, организует работу всех manager-классов
 * @author grigoryvolkov
 */
public class Invoker {
    private final ModelManager modelManager;
    private FileManager fileManager;
    private CommandManager commandManager;
    private final Printer printer;
    private ObjectOutputStream out;
    public Invoker(Printer printer, FileManager fileManager){
        this.printer = printer;
        modelManager = new ModelManager(this);
        commandManager =new CommandManager(this);
        this.fileManager = fileManager;
    }
    public Invoker(Printer printer){
        this.printer = printer;
        modelManager = new ModelManager(this);
        commandManager =new CommandManager(this);

    }
    public ModelManager getModelManager() {
        return modelManager;
    }

    public Printer getPrinter() {
        return printer;
    }

    public FileManager getFileManager(){
        return fileManager;
    }
    public CommandManager getCommandManager(){return commandManager;}
}

