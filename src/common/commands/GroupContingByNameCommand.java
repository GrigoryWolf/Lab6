package common.commands;

import common.core.Exceptions.NotValidArgumentsException;
import common.core.Invoker;
import common.core.message.Response;
import common.core.model.Route;

import java.util.HashMap;
import java.util.Map;
/**
 * Класс осуществляющий реализацию команды group_counting_by_name
 * @author grigoryvolkov
 */

public class GroupContingByNameCommand extends Command{
    public GroupContingByNameCommand(Invoker invoker){
        super(invoker);
    }
    @Override
    public Response execute(String arg, Route route) throws NotValidArgumentsException {
        argsСheck(TypeArg.STRINGARGUMENT, arg, route);
        HashMap<String, Integer> counter = new HashMap<>();
        if (getInvoker().getModelManager().getCollection().isEmpty()){
            return new Response("Коллекция пуста");
        }
        for (Route routes  : getInvoker().getModelManager().getCollection().values()){
            String name = routes.getName();
            if (counter.containsKey(name)){
                counter.put(name, counter.get(name) + Integer.valueOf(1));
            }
            else {
                counter.put(name, Integer.valueOf(1));
            }
        }
        String answer = "";
        for (Map.Entry<String, Integer> entry : counter.entrySet()){
            answer += (String.format("Имя \"%s\" находится в коллекции в количестве %s экземпляров", entry.getKey(), counter.get(entry.getKey())+"\n"));
        }
        return new Response(answer);
    }

    @Override
    public String getDescription() {
        return "group_counting_by_name : сгруппировать элементы коллекции по значению поля name, вывести количество элементов в каждой группе";
    }
}
