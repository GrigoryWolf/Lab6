package common.commands;

import common.core.Exceptions.NotValidArgumentsException;
import common.core.Invoker;
import common.core.message.Response;
import common.core.model.Route;

/**
 * Класс осуществляющий реализацию команды help
 * @author grigoryvolkov
 */
public class CommandHelp extends Command {
    public CommandHelp(Invoker invoker){
        super(invoker);
    }

    @Override
    public Response execute(String arg, Route route) throws NotValidArgumentsException {
        argsСheck(TypeArg.NOARGUMENT, arg, route);
        StringBuilder helpInformation = new StringBuilder("Список доступных команд: \n");
        for (Command command : getInvoker().getCommandManager().getCommands()){
            helpInformation.append(command.getDescription()).append("\n");
        }
        System.out.println(helpInformation.toString());
        return new Response(helpInformation.toString());
    }
    @Override
    public String getDescription() {
        return "help : вывести справку по доступным командам";
    }
}
