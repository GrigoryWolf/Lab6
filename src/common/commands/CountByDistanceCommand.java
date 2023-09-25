package common.commands;

import common.core.Exceptions.NotValidArgumentsException;
import common.core.Invoker;
import common.core.message.Response;
import common.core.model.Route;

import static java.lang.Double.parseDouble;

/**
 * Класс осуществляющий реализацию команды count_by_distance
 * @author grigoryvolkov
 */
public class CountByDistanceCommand extends Command{
    public CountByDistanceCommand(Invoker invoker){
        super(invoker);
    }

    @Override
    public Response execute(String arg, Route route) throws NotValidArgumentsException {
        try {
            argsСheck(TypeArg.STRINGARGUMENT, arg, route);
            Double distance = parseDouble(arg.trim().replace(",","."));
            long count = 0;
            if (getInvoker().getModelManager().getCollection().isEmpty()) {
                return new Response("Коллекция пуста");
            }
            for (Route routes : getInvoker().getModelManager().getCollection().values()) {
                if (distance.equals(routes.getDistance())){
                    count += 1;
                }
            }
            return new Response(String.format("Число объектов класса route со значением distance %s: %s", distance, count));
        }
        catch (NumberFormatException exception){
            return new Response("Аргумент должен быть числом типа Double!");
        }
    }

    @Override
    public String getDescription() {
        return "count_by_distance distance : вывести количество элементов, значение поля distance которых равно заданному";
    }
}
