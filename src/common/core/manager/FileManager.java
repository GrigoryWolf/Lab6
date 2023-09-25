package common.core.manager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import common.core.Invoker;
import common.core.Printer;
import common.core.model.Route;

import java.io.*;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
/**
 * Класс, который управляет записью и чтением json файлов
 * @author grigoryvolkov
 */
public class FileManager {
    private final Scanner scan;
    private final Gson gson;
    private final Type type = new TypeToken<LinkedHashMap<Integer, Route>>() {}.getType();
    private final Printer printer;
    public FileManager(String path, Printer printer) throws IOException {
        this.printer = printer;
        File file = new File(path);
        if (file.createNewFile())
            printer.print("Файл создан");
        FileReader reader = new FileReader(file);
        scan = new Scanner(reader);
        gson = new GsonBuilder().create();
    }
    public void collectionLoad(Invoker invoker){
        try{
            StringBuilder jsonString = new StringBuilder();
            while(scan.hasNextLine()){
                jsonString.append(scan.nextLine());
            }
            LinkedHashMap<Integer,Route> checkCollection;
            checkCollection = gson.fromJson(jsonString.toString(), type);
            invoker.getModelManager().modelCheck(checkCollection);
        }
        catch (NoSuchElementException | NullPointerException exception){
            printer.print("Файл пуст");
        }
        catch (NumberFormatException exception){
            printer.print("Некоторые поля открываемого файла не подходят по формату");
        }
    }
    public void collectionSave(LinkedHashMap<Integer, Route> collection, String savePath) throws IOException {
        File file = new File(savePath);
        FileWriter writer = new FileWriter(file, false);
        writer.write(gson.toJson(collection));
        writer.close();

    }

}
