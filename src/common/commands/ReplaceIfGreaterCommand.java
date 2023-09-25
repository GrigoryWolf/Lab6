package common.commands;

import common.core.Exceptions.NotValidArgumentsException;
import common.core.Invoker;
import common.core.message.Response;
import common.core.model.Route;

import static java.lang.Integer.parseInt;
/**
 * Класс осуществляющий реализацию команды replace_if_greater
 * @author grigoryvolkov
 */
public class ReplaceIfGreaterCommand extends Command{
    public ReplaceIfGreaterCommand(Invoker invoker){
        super(invoker);
    }
    @Override
    public Response execute(String arg, Route route) throws NotValidArgumentsException {
        try {
            argsСheck(TypeArg.BOTH, arg, route);
            Integer id = parseInt(arg);
            if (!getInvoker().getModelManager().getCollection().containsKey(id)) {
                return new Response("Модели с таким id не существует");
            }
            if (route.getDistance() > getInvoker().getModelManager().getCollection().get(id).getDistance()) {
                getInvoker().getModelManager().update(route);
                return new Response("Модель успешно заменена на модель с большим значением distance");
            }
            return new Response("У новой модели значение distance меньше");
        }
        catch (NumberFormatException exception){return new Response("Аргумент должен быть числом типа Integer!");}
    }

    @Override
    public String getDescription() {
        return "replace_if_greater id : заменить значение по ключу, если новое значение больше старого";
    }
}
