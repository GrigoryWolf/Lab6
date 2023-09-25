package common.core.Exceptions;
/**
 * Класс исключение на случай невозможности получить доступ к файлу
 * @author grigoryvolkov
 */
public class FileAccessException extends Exception{
    public FileAccessException(){
        super("Нет доступа к файлу");
    }
}
