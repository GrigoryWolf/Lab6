package common.commands;

import common.core.Exceptions.NotValidArgumentsException;
import common.core.Invoker;
import common.core.message.Response;
import common.core.model.Route;

/**
 * Класс осуществляющий реализацию команды clear
 * @author grigoryvolkov
 */
public class ClearCommand extends Command{
    public ClearCommand(Invoker invoker){super(invoker);}

    @Override
    public Response execute(String arg, Route route) throws NotValidArgumentsException {
        argsСheck(TypeArg.NOARGUMENT, arg, route);
        getInvoker().getModelManager().clearCollection();
        return new Response("Коллекция успешно очищена");
    }

    @Override
    public String getDescription() {
        return "clear : очистить коллекцию";
    }
}
