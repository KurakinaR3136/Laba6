package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Commandable {
    public void execute(Object o, StringBuffer msg) throws IOException;
    public String getName();
}
