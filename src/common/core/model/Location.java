package common.core.model;

import common.core.Exceptions.NotValidArgumentsException;

import java.io.Serializable;

/**
 * Шаблон класса Location. Содержит геттеры и сеттеры для всех полей.
 * Содержит ограничения прописанные для каждого поля
 * @author grigoryvolkov
 */
public class Location implements Serializable {
    private long x; //Поле не может быть null
    private long y; //Поле не может быть null
    private Long z; //Поле не может быть null
    private String name; //Поле не может быть null
    public Location(long x, long y, Long z, String name) throws NotValidArgumentsException {
        setX(x);
        setY(y);
        setZ(z);
        setName(name);
    }
    public void setX(long x){this.x=x;}

    public void setY(long y) {
        this.y = y;
    }

    public void setZ(Long z) throws NotValidArgumentsException {
        if (z.equals(null)){
            throw new NotValidArgumentsException("Поле не может быть null");
        }this.z = z;
    }

    public void setName(String name) throws NotValidArgumentsException {
        if (name.equals(null)){
            throw new NotValidArgumentsException("Поле не может быть null");
        }
        this.name = name;
    }
    public long getX() {
        return x;
    }
    public long getY() {
        return y;
    }
    public Long getZ() {
        return z;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name+ "{" + x + ", " + y + ", " + z + '}';
    }


}


