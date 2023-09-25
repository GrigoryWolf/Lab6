package common.commands;
import common.core.Exceptions.NotValidArgumentsException;
import common.core.Invoker;
import common.core.message.Response;
import common.core.model.Route;

import static java.lang.Integer.parseInt;

/**
 * Класс осуществляющий реализацию команды update
 * @author grigoryvolkov
 */
public class UpdateCommand extends Command{
    public UpdateCommand(Invoker invoker){
        super(invoker);
    }

    @Override
    public Response execute(String arg, Route route) throws NotValidArgumentsException {
        try {
            if (arg!=null) {
                Integer id = parseInt(arg);
                if (getInvoker().getModelManager().getUsedIDs().contains(id)) {
                    getInvoker().getModelManager().getCollection().remove(id);
                    return new Response("Маршрут с id " + id + " найден");
                }
                return new Response("Маршрут с таким id не найден", false);
            } else if (route != null) {
                getInvoker().getModelManager().putModel(route);
                return new Response("Маршрут успешно обновлен", false);
            }
        }
        catch (NumberFormatException exception){return new Response("Аргумент должен быть числом типа Integer!", false);}
        return new Response("Что-то пошло не так", false);
    }

    @Override
    public String getDescription() {
        return "update id: обновить значение элемента коллекции, id которого равен заданному";
    }
}
