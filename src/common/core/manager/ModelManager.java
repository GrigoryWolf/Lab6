package common.core.manager;
import common.core.Exceptions.NotValidArgumentsException;
import common.core.Invoker;
import common.core.model.Coordinates;
import common.core.model.Location;
import common.core.model.Route;

import java.util.*;
/**
 * Класс, который управляет коллекцией
 * @author grigoryvolkov
 */
public class ModelManager {
    private final ArrayList<Integer> usedIDs = new ArrayList<>();
    private LinkedHashMap models;
    private Date creationDate;
    private final Invoker invoker;
    public ModelManager(Invoker invoker){
        models = new LinkedHashMap<Integer, Route>();
        creationDate = new Date();
        this.invoker = invoker;
    }
    public void putModel(Route route){
        models.put(route.getId(), route);
    }
    public void update(Route route){
        models.remove(route.getId());
        models.put(route.getId(), route);
    }
    public void deleteModel(Integer id){
        if (usedIDs.contains(id)) {
            models.remove(id);
            usedIDs.remove(id);
        }
        else {invoker.getPrinter().print("Элемента с таким id не существует!");}
    }
    public void clearCollection(){
        models.clear();
        usedIDs.clear();
    }
    public Integer generateId(){
        Random rnd = new Random();
        Integer id = rnd.nextInt(Integer.MAX_VALUE);
        while(usedIDs.contains(id)){
            id = rnd.nextInt();
        }
        usedIDs.add(id);
        return id;
    }
    public String getInfoAboutCollection() {
        return("Тип коллекции: " + models.getClass().getSimpleName() +
        "\nКоличество элементов в коллекции: " + models.size()+
        "\nДата инициализации: " + creationDate);
    }
    public void modelCheck(LinkedHashMap<Integer,Route> checkedCollection){
        Collection<Route> checkedRoutes = checkedCollection.values();
        for (Route route : checkedRoutes){
            modelCheck(route);
        }
    }
    public boolean modelCheck(Route route){
        try {
            Coordinates coordinates = route.getCoordinates();
            Location from = route.getFrom();
            Location to = route.getTo();
            if (route.getId() == null){
                route.setId(generateId());
            }
            usedIDs.add(route.getId());
            nullCheck(coordinates, to, from, route.getDistance(),
                    route.getCreationDate(), coordinates.getX(), coordinates.getY(), to.getX(),
                    to.getY(), to.getZ(), from.getX(), from.getY(), from.getZ());
            nameCheck(route.getName(), from.getName(), to.getName());
            if (coordinates.getX()<=-866){
                throw new NotValidArgumentsException();
            }
            if (coordinates.getY()>107){
                throw new NotValidArgumentsException();
            }
            putModel(route);
            usedIDs.add(route.getId());
            return true;
        }
        catch (NotValidArgumentsException exception){
            invoker.getPrinter().print("Один из объектов Route имеет не валидные значения," +
                    " он не будет добавлен в коллекцию");
            return false;
        }
}

    public ArrayList<Integer> getUsedIDs(){
        return usedIDs;
    }
    public LinkedHashMap<Integer, Route> getCollection(){
        return models;
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

}
