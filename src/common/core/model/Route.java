package common.core.model;

import common.core.Exceptions.NotValidArgumentsException;

import java.io.Serializable;
import java.util.Date;
/**
 * Шаблон класса Route. Содержит геттеры и сеттеры для всех полей.
 * Содержит ограничения прописанные для каждого поля
 * @author grigoryvolkov
 */
public class Route implements Serializable {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location from; //Поле может быть null
    private Location to; //Поле не может быть null
    private Double distance; //Поле не может быть null, Значение поля должно быть больше 1
    public Integer getId(){return id;}
    public void setId(Integer id) throws NotValidArgumentsException {
        if (id == null || id < 0){
            throw new NotValidArgumentsException("Поле не может быть null, Значение поля должно быть больше 0");
        }
        this.id = id;
    }

    public void setName(String name) throws NotValidArgumentsException {
        if (name.isBlank() || name.equals(null)){
            throw new NotValidArgumentsException("Поле не может быть null, Строка не может быть пустой");
        }
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) throws NotValidArgumentsException {
        if (coordinates.equals(null)){
            throw new NotValidArgumentsException("Поле не может быть null");
        }
        this.coordinates = coordinates;
    }

    public void setCreationDate(Date creationDate) throws NotValidArgumentsException {
        if (creationDate.equals(null)){
            throw new NotValidArgumentsException("Поле не может быть null");
        }
        this.creationDate = creationDate;
    }

    public void setDistance(Double distance) throws NotValidArgumentsException {
        if (distance.equals(null) || distance<=1){
            throw new NotValidArgumentsException("Поле не может быть null, Значение поля должно быть больше 1");
        }
        this.distance = distance;
    }

    public void setFrom(Location from) throws NotValidArgumentsException {
        if (from.equals(null)){
            throw new NotValidArgumentsException("Поле не может быть null");
        }
        this.from = from;
    }

    public void setTo(Location to) throws NotValidArgumentsException {
        if (to.equals(null)){
            throw new NotValidArgumentsException("Поле не может быть null");
        }
        this.to = to;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public Double getDistance(){return distance;}
    public String getName(){return name;}
    public Location getFrom(){return from;}
    public Location getTo(){return to;}
    public Coordinates getCoordinates(){return coordinates;}
    @Override
    public String toString(){
        return String.format("id: %s\n" +
                "name: %s\n" +
                "coordinates: %s\n" +
                "creationDate: %s\n" +
                "from: %s\n" +
                "to: %s\n" +
                "distance: %s\n" +
                "---------------------------------", id, name, coordinates.toString(), creationDate, from.toString(), to.toString(), distance);
    }
}