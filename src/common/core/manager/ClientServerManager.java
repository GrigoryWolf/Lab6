package common.core.manager;

import common.commands.*;
import common.core.Exceptions.NotValidArgumentsException;
import common.core.Invoker;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public abstract class ClientServerManager {
    private Map<String, Command> commandsCollection;
    private final Invoker invoker;
    private LinkedList<String> commandHistory = new LinkedList<>();
    public ClientServerManager(Invoker invoker){
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
    public HashMap getCommandsCollection(){
        return (HashMap) commandsCollection;
    }
    public Invoker getInvoker(){return invoker;}

}
