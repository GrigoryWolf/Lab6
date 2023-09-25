package common.commands;

import common.core.Exceptions.NotValidArgumentsException;
import common.core.Exceptions.RecursionException;
import common.core.Invoker;
import common.core.message.Response;
import common.core.model.Route;
import common.core.reader.ReaderFiles;
import common.core.reader.IReader;


import javax.imageio.IIOException;
import java.io.IOException;
import java.util.LinkedList;
/**
 * Класс осуществляющий реализацию команды execute_script
 * @author grigoryvolkov
 */
public class ExecuteCommand extends Command{
    private String filePath;
    private IReader reader;
    private static LinkedList<IReader> readersQueue = new LinkedList<>();
    private static boolean recursionFlag = false;
    private static LinkedList<String> pathChain = new LinkedList<>();
    public ExecuteCommand(Invoker invoker){
        super(invoker);
    }

    @Override
    public Response execute(String arg, Route route) throws NotValidArgumentsException, IOException {
        argsСheck(TypeArg.STRINGARGUMENT, arg, route);
        String file = arg.trim();
        try {
            if (recursionCheck(file)){
                reader = new ReaderFiles(file, getInvoker());
                readersQueue.add(reader);
                reader.start();
                pathChain.remove(file);
            }
            else{
                for(IReader reader : readersQueue){
                    reader.stop();
                }
                throw new RecursionException("Рекурсия! Execute вызывает тот же файл внутри себя");
            }
            return new Response("Команды из файла успешно выполнены");
        }
        catch (RecursionException ex){
            return new Response(ex.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }

    private boolean recursionCheck(String filePath){
        if (pathChain.contains(filePath)){
            return false;
        }
        pathChain.add(filePath);
        return true;
    }
}
