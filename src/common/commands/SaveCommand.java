package common.commands;
import common.core.Exceptions.NotValidArgumentsException;
import common.core.Invoker;
import common.core.manager.FileManager;
import common.core.message.Response;
import common.core.model.Route;

import java.io.IOException;
/**
 * Класс осуществляющий реализацию команды save
 * @author grigoryvolkov
 */
public class SaveCommand extends Command{
    FileManager fileManager;
    public SaveCommand(Invoker invoker){
        super(invoker);
    }

    @Override
    public Response execute(String arg, Route route) throws NotValidArgumentsException, IOException {
        try {
            argsСheck(TypeArg.STRINGARGUMENT, arg, route);
            fileManager = new FileManager(arg, getInvoker().getPrinter());
            fileManager.collectionSave(getInvoker().getModelManager().getCollection(), arg);
            return new Response("Файл успешно сохранен");
        }
        catch (IOException exception){
            return new Response("Не удалось сохранить данные в файл");
        }
    }

    @Override
    public String getDescription() {
        return "save : сохранить коллекцию в файл.";
    }
}
