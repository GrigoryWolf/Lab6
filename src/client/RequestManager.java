package client;
import common.commands.*;
import common.core.Deserializer;
import common.core.Exceptions.NotValidArgumentsException;
import common.core.Invoker;
import common.core.Printer;
import common.core.message.Request;
import common.core.message.Response;
import common.core.model.Coordinates;
import common.core.model.Location;
import common.core.model.Route;

import java.io.IOException;
import java.util.*;

import static java.lang.Integer.parseInt;

public class RequestManager{
    private ClientInvoker clientInvoker;
    private Map<String, Command> commandsCollection;
    public RequestManager(ClientInvoker clientInvoker){
        this.clientInvoker=clientInvoker;
        initializeCommands();
    }
    private void initializeCommands(){
        Invoker invoker = new Invoker(clientInvoker.getPrinter());
        commandsCollection = new HashMap<>();
        commandsCollection.put("help", new CommandHelp(invoker));
        commandsCollection.put("info", new InfoCommand(invoker));
        commandsCollection.put("show", new ShowCommand(invoker));
        commandsCollection.put("insert", new InsertCommand(invoker));
        commandsCollection.put("update", new UpdateCommand(invoker));
        commandsCollection.put("clear", new ClearCommand(invoker));
        commandsCollection.put("remove_key", new RemoveByKeyCommand(invoker));
        commandsCollection.put("execute_script", new ExecuteCommand(invoker));
        commandsCollection.put("history", new CommandHistory(invoker));
        commandsCollection.put("exit", new ExitCommand(invoker));
        commandsCollection.put("replace_if_greater", new ReplaceIfGreaterCommand(invoker));
        commandsCollection.put("remove_lower_key", new RemoveLowerKeyCommand(invoker));
        commandsCollection.put("group_counting_by_name", new GroupContingByNameCommand(invoker));
        commandsCollection.put("count_by_distance", new CountByDistanceCommand(invoker));
        commandsCollection.put("print_field_descending_distance", new PrintFieldDescendingDistanceCommand(invoker));
    }
    public Request buildRequest(String command, String arg, Route route){
        Request request = new Request(command, arg, route);
        return request;
    }
    public void parseLine(String line) throws IOException {
        line = line.trim();
        if(line == null || line.equals("")) return;
        if (line.contains(" ")){
            String[] commandLine = line.split(" ",2);
            callCommand(commandLine[0].trim(), commandLine[1].trim());
        }
        else{
            callCommand(line);
        }
    }
    public void callCommand(String stringCommand, String ... arg) throws IOException {
        if (!commandsCollection.containsKey(stringCommand)){
            clientInvoker.getPrinter().print("Такой команды не существует. Введите help для справки по командам:");
            return;
        }
        if ((stringCommand.equals("exit"))&& arg.length == 0){
            clientInvoker.getPrinter().print("До свидания!");
            clientInvoker.getReader().stop();
            System.exit(0);
        }
        Route route = new Route();
        switch(stringCommand){
            case "insert", "replace_if_greater" -> {
                if (!(setRoute(route) && arg.length == 0)) {
                    clientInvoker.getPrinter().print("Число аргументов должно быть равно нулю");
                    return;
                }
                clientInvoker.sendDataToServer(buildRequest(stringCommand, null, route));
                clientInvoker.getPrinter().print(clientInvoker.getDataFromServer().toString());
            }
            case "execute_script", "remove_lower_key", "group_counting_by_name", "count_by_distance", "remove_key" -> {
                if (!(arg.length == 1)) {clientInvoker.getPrinter().print("Число аргументов должно быть равно одному");
                    return;}
                clientInvoker.sendDataToServer(buildRequest(stringCommand, arg[0], null));
                clientInvoker.getPrinter().print(clientInvoker.getDataFromServer().toString());

            }
            case "update" -> {
                if (!(arg.length == 1)) {
                    clientInvoker.getPrinter().print("Число аргументов должно быть равно одному");
                    return;
                }
                clientInvoker.sendDataToServer(buildRequest(stringCommand, arg[0], null));
                Response response = clientInvoker.getDataFromServer();
                clientInvoker.getPrinter().print(response.toString());
                if(response.getResult()){
                    Integer id = parseInt(arg[0]);
                    clientInvoker.sendDataToServer(buildRequest(stringCommand, null, setRoute(id)));
                }
            }
            case "help" -> {
                if (!(arg.length == 0)) {
                    clientInvoker.getPrinter().print("Число аргументов должно быть равно нулю");
                    return;
                }
                clientInvoker.getPrinter().print(commandHelp());
            }

            default -> {
                if (!(arg.length == 0)){
                    clientInvoker.getPrinter().print("Число аргументов должно быть равно нулю");
                    return;
                }
                clientInvoker.sendDataToServer(buildRequest(stringCommand, null, null));
                clientInvoker.getPrinter().print(clientInvoker.getDataFromServer().toString());
            }
        }
    }

    public Date generateDate(){
        return new Date();
    }
    public String requestName(String message){
        while(clientInvoker.getReader().getWorking()){
            try{
                clientInvoker.getPrinter().print(message);
                String name = clientInvoker.getReader().nextLine().trim();
                nameCheck(name);
                return name;
            }
            catch (NotValidArgumentsException ex){
                clientInvoker.getPrinter().print(ex.getMessage());
            }
        }
        return null;
    }
    public Long requestCoordinate(String message){
        while(clientInvoker.getReader().getWorking()){
            try{
                clientInvoker.getPrinter().print(message);
                String stringCoordinate = clientInvoker.getReader().nextLine().trim();
                nullCheck(stringCoordinate);
                return Long.parseLong(stringCoordinate);
            }
            catch (NotValidArgumentsException | NumberFormatException ex){
                clientInvoker.getPrinter().print("Координата должна быть Long и не null");
            }
        }
        return null;
    }
    public Double requestDistance(){
        while(clientInvoker.getReader().getWorking()){
            try{
                clientInvoker.getPrinter().print("Введите дистанцию маршрута в формате Double больше 1: ");
                String stringDistance = clientInvoker.getReader().nextLine().trim().replace(",",".");
                nullCheck(stringDistance);
                if (Double.parseDouble(stringDistance)>1){
                    return Double.parseDouble(stringDistance);
                }
            }
            catch (NotValidArgumentsException | NumberFormatException ex){
                clientInvoker.getPrinter().print("distance должен быть double и не null");
            }
        }
        return null;
    }
    public Coordinates requestCoordinates(String message) throws NotValidArgumentsException {
        long x;
        Long y;
        clientInvoker.getPrinter().print(message);
        do {
            x = requestCoordinate("Введите координату x в формате long." +
                    " Значение поля должно быть больше -866: ");
        } while (clientInvoker.getReader().getWorking() && x <= -866);
        do {
            y = requestCoordinate("Введите координату y в формате Long." +
                    " Максимальное значение поля 107, Поле не может быть null: ");
        } while (clientInvoker.getReader().getWorking() && y > 107);
        return new Coordinates(x, y);
    }
    public Location requestLocation(String message) throws NotValidArgumentsException {
        clientInvoker.getPrinter().print(message);
        long x = requestCoordinate("Введите координату x в формате long: ");
        long y = requestCoordinate("Введите координату y в формате long: ");
        Long z = requestCoordinate("Введите координату z в формате Long." +
                " Поле не может быть null: ");
        String name = requestName("Введите название Локации." +
                " Название Локации не может быть null: ");
        return new Location(x, y, z, name);
    }
    public Route setRoute(int id){
        Route route = new Route();
        setRoute(route);
        try {
            route.setId(id);
            return route;
        } catch (NotValidArgumentsException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean setRoute(Route route){
        try{
            route.setName(requestName("Введите название маршрута. " +
                    "Название не может быть пустым или null: "));
            route.setCoordinates(requestCoordinates("Введите координаты маршрута: "));
            route.setCreationDate(generateDate());
            route.setFrom(requestLocation("Введите данные для локации " +
                    "из которой идет маршрут: "));
            route.setTo(requestLocation("Введите данные для локации " +
                    "в которую идет маршрут: "));
            route.setDistance(requestDistance());
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    public void nameCheck(String ... names) throws NotValidArgumentsException {
        for(String name : names){
            if(name == null || name.equals("")){
                throw new NotValidArgumentsException("Аргумент" +
                        " не может быть null");}
        }
    }
    public void nullCheck(Object ... objects) throws NotValidArgumentsException {
        for(Object object : objects){if(object == null){
            throw new NotValidArgumentsException("Аргумент" +
                    " не может быть null");}
        }
    }
    public String commandHelp(){
        return "Список доступных команд:\n"+
        "print_field_descending_distance : вывести значения поля distance всех элементов в порядке убывания\n"+
        "group_counting_by_name : сгруппировать элементы коллекции по значению поля name, вывести количество элементов в каждой группе\n"+
        "replace_if_greater id : заменить значение по ключу, если новое значение больше старого\n"+
        "count_by_distance distance : вывести количество элементов, значение поля distance которых равно заданному\n"+
        "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n"+
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n"+
        "clear : очистить коллекцию\n"+
        "insert: добавить новый элемент с заданным ключом\n"+
        "update id: обновить значение элемента коллекции, id которого равен заданному\n"+
        "history : вывести последние 13 команд (без их аргументов)\n"+
        "help : вывести справку по доступным командам\n"+
        "exit : завершить программу (без сохранения в файл)\n"+
        "remove_lower_key id : удалить из коллекции все элементы, ключ которых меньше, чем заданный.\n"+
                "remove_key id: удалить элемент из коллекции по его ключу\n"+
        "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n";

    }
}
