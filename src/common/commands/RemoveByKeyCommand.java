package common.commands;

import common.core.Exceptions.NotValidArgumentsException;
import common.core.Invoker;
import common.core.message.Response;
import common.core.model.Route;
import static java.lang.Integer.parseInt;
/**
 * Класс осуществляющий реализацию команды remove_key
 * @author grigoryvolkov
 */
public class RemoveByKeyCommand extends Command{
    public RemoveByKeyCommand(Invoker invoker){
        super(invoker);
    }

    @Override
    public Response execute(String arg, Route route) throws NotValidArgumentsException {
        try{
            argsСheck(TypeArg.STRINGARGUMENT, arg, route);
            Integer id = parseInt(arg.trim());
            getInvoker().getModelManager().deleteModel(id);
            return new Response(String.format("Маршрут по id: %s успешно удален", id));}
        catch(NumberFormatException exception){
            return new Response("Аргумент должен быть числом типа Integer!");
        }
    }

    @Override
    public String getDescription() {
        return "remove_key id: удалить элемент из коллекции по его ключу";
    }
}
