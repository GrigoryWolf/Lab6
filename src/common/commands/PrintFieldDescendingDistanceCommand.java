package common.commands;

import common.core.Exceptions.NotValidArgumentsException;
import common.core.Invoker;
import common.core.message.Response;
import common.core.model.Route;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Класс осуществляющий реализацию команды print_field_descending_distance
 * @author grigoryvolkov
 */
public class PrintFieldDescendingDistanceCommand extends Command{
    public PrintFieldDescendingDistanceCommand(Invoker invoker){
        super(invoker);
    }

    @Override
    public Response execute(String arg, Route route) throws NotValidArgumentsException {
        argsСheck(TypeArg.NOARGUMENT, arg, route);
        if (getInvoker().getModelManager().getCollection().isEmpty()){
            return new Response("Коллекция пуста");
        }
        ArrayList<Double> distances = new ArrayList<>();
        for (Route routes  : getInvoker().getModelManager().getCollection().values()){
            distances.add(routes.getDistance());
        }
        Collections.sort(distances, Collections.reverseOrder());
        return new Response("Дистанции маршрутов в порядке убывания: " + distances.toString());
    }

    @Override
    public String getDescription() {
        return "print_field_descending_distance : вывести значения поля distance всех элементов в порядке убывания";
    }
}
