package common.commands;
import common.core.Exceptions.NotValidArgumentsException;
import common.core.Invoker;
import common.core.message.Response;
import common.core.model.Route;

import static java.lang.Integer.parseInt;
/**
 * Класс осуществляющий реализацию команды remove_lower_key
 * @author grigoryvolkov
 */
public class RemoveLowerKeyCommand extends Command{
    public RemoveLowerKeyCommand(Invoker invoker){
        super(invoker);
    }

    @Override
    public Response execute(String arg, Route route) throws NotValidArgumentsException {
        try {
            argsСheck(TypeArg.STRINGARGUMENT, arg, route);
            Integer id = parseInt(arg.trim());
            for (Route routes : getInvoker().getModelManager().getCollection().values()) {
                if (id > routes.getId()) {
                    getInvoker().getModelManager().deleteModel(routes.getId());
                }
            }
            return new Response(String.format("Все элементы, id которых меньше %s были удалены", id));
        }
        catch (NumberFormatException exception){
            return new Response("Аргумент должен быть числом типа Integer!");
        }

    }

    @Override
    public String getDescription() {
        return "remove_lower_key id : удалить из коллекции все элементы, ключ которых меньше, чем заданный.";
    }
}
