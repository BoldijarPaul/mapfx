package util;

import com.fasterxml.jackson.xml.XmlMapper;

import javax.xml.stream.XMLStreamWriter;
import java.io.*;

/**
 * Created by Paul on 11/4/2016.
 */
public class Utils {

    public static int randomInt() {
        return (int) (Math.random() * 100000);
    }

    public static void writeToFile(Object object, String path) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public static <T> T readFromFile(String path) {
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (T) objectInputStream.readObject();
        } catch (Exception e) {
        }
        return null;
    }
}
