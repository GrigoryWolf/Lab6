package common.commands;

import common.core.Exceptions.NotValidArgumentsException;
import common.core.Invoker;
import common.core.message.Response;
import common.core.model.Route;

/**
 * Класс осуществляющий реализацию команды exit
 * @author grigoryvolkov
 */
public class ExitCommand extends Command {
    public ExitCommand(Invoker invoker){
        super(invoker);
    }
    public Response execute(String arg, Route route) throws NotValidArgumentsException {
        argsСheck(TypeArg.NOARGUMENT, arg, route);
        return new Response("До свидания");
    }

    @Override
    public String getDescription() {
        return "exit : завершить программу (без сохранения в файл)";
    }
}
