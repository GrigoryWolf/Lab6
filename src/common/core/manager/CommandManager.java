package common.core.manager;

import java.io.IOException;
import java.util.*;

import common.commands.*;
import common.commands.Command;
import common.core.Exceptions.NotValidArgumentsException;
import common.core.Invoker;
import common.core.message.Response;
import common.core.model.Route;

/**
 * Класс, который управляет вызовом команд
 * @author grigoryvolkov
 */
public class CommandManager {
    private Map<String, Command> commandsCollection;
    private final Invoker invoker;
    private LinkedList<String> commandHistory = new LinkedList<>();
    public CommandManager(Invoker invoker){
        this.invoker = invoker;
        initializeCommands();
    }
    private void initializeCommands(){
        commandsCollection = new HashMap<>();
        commandsCollection.put("help", new CommandHelp(invoker));
        commandsCollection.put("info", new InfoCommand(invoker));
        commandsCollection.put("show", new ShowCommand(invoker));
        commandsCollection.put("insert", new InsertCommand(invoker));
        commandsCollection.put("update", new UpdateCommand(invoker));
        commandsCollection.put("clear", new ClearCommand(invoker));
        commandsCollection.put("remove_key", new RemoveByKeyCommand(invoker));
        commandsCollection.put("save", new SaveCommand(invoker));
        commandsCollection.put("execute_script", new ExecuteCommand(invoker));
        commandsCollection.put("exit", new ExitCommand(invoker));
        commandsCollection.put("history", new CommandHistory(invoker));
        commandsCollection.put("replace_if_greater", new ReplaceIfGreaterCommand(invoker));
        commandsCollection.put("remove_lower_key", new RemoveLowerKeyCommand(invoker));
        commandsCollection.put("group_counting_by_name", new GroupContingByNameCommand(invoker));
        commandsCollection.put("count_by_distance", new CountByDistanceCommand(invoker));
        commandsCollection.put("print_field_descending_distance", new PrintFieldDescendingDistanceCommand(invoker));
    }
    public void parseLine(String line){
        line = line.trim();
        if(line == null || line.equals("")) return;
        if (line.contains(" ")){
            String[] commandLine = line.split(" ",2);
            callCommand(commandLine[0], commandLine[1], null);
        }
        else{
            callCommand(line, null, null);
        }
    }
    public Response callCommand(String stringCommand, String arg, Route route){
        try {
            Command command = commandsCollection.get(stringCommand);
            rememberCommand(stringCommand);
            return command.execute(arg, route);
        }
        catch (NotValidArgumentsException | IOException | NullPointerException ex){
            invoker.getPrinter().print("Задача выполнена без отправки результата клиенту");
        }
        return new Response("Не удалось выполнить команду");

    }
    private void rememberCommand(String command){
        if (commandHistory.size() == 13){
            commandHistory.remove(0);
        }
        commandHistory.add(command);
    }
    public String getHistory(){
        StringBuilder answer = new StringBuilder("Последние 13 введенных команд:\n");
        for(String command : commandHistory){
            if(command != null) answer.append(command).append(" \n");
        }
        return answer.toString();
    }
    public Collection<Command> getCommands(){
        return commandsCollection.values();
    }

}
