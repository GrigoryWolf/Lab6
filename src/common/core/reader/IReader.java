package common.core.reader;
import common.core.manager.CommandManager;

import java.io.IOException;

/**
 * Интерфейс, содержит методы для чтения
 * @author grigoryvolkov
 */
public interface IReader {
    void start() throws IOException;
    void stop();

    String nextLine();
    Boolean getWorking();
    CommandManager getCommandManager();
}
