package common.commands;
import common.core.Exceptions.NotValidArgumentsException;
import common.core.Invoker;
import common.core.message.Response;
import common.core.model.Route;
/**
 * Класс осуществляющий реализацию команды insert
 * @author grigoryvolkov
 */
public class InsertCommand extends Command{
    public InsertCommand(Invoker invoker){
        super(invoker);
    }
    @Override
    public Response execute(String arg, Route route) throws NotValidArgumentsException {
        argsСheck(TypeArg.ROUTE, arg, route);
        getInvoker().getPrinter().print(route.toString());
        if (getInvoker().getModelManager().modelCheck(route)){
            getInvoker().getModelManager().putModel(route);
            return new Response("Новый маршрут успешно добавлен в коллекцию");
        }
        else return new Response("Произошла ошибка во время создания маршрута");
    }

    @Override
    public String getDescription() {
        return "insert: добавить новый элемент с заданным ключом";
    }
}
