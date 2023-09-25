package common.core.Exceptions;
/**
 * Класс исключение на случай когда файла не существует
 * @author grigoryvolkov
 */
public class FileDoesNotExist extends Exception{
    public FileDoesNotExist(){
        super("Файла не существует");
    }
}
