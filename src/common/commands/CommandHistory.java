package common.commands;

import common.core.Exceptions.NotValidArgumentsException;
import common.core.Invoker;
import common.core.message.Response;
import common.core.model.Route;

/**
 * Класс осуществляющий реализацию команды history
 * @author grigoryvolkov
 */
public class CommandHistory extends Command{
    public CommandHistory(Invoker invoker){
        super(invoker);
    }
    public Response execute(String  arg, Route route) throws NotValidArgumentsException {
        argsСheck(TypeArg.NOARGUMENT, arg, route);
        return new Response(getInvoker().getCommandManager().getHistory());
    }

    @Override
    public String getDescription() {
        return "history : вывести последние 13 команд (без их аргументов)";
    }
}

