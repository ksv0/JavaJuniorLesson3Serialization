package ksv;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentApp {
    public static final String FILE_BIN = "src/main/resources/student.bin";
    public static final String FILE_XML = "src/main/resources/student.xml";
    public static final String FILE_JSON = "src/main/resources/student.json";

    private static final ObjectMapper mapper = new ObjectMapper();
    public static final XmlMapper xmlMapper = new XmlMapper();

    public static void saveStudent(String fileName, List<Student> students) {
        try {
            if (fileName.endsWith(".bin")) {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
                    oos.writeObject(students);
                }
            } else if (fileName.endsWith(".xml")) {// тут почему-то маппер отчаянно не хотел сериализовать структуру List, поэтому после 4 часов войны я решил сдаться и пошел к джипити
                xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
                xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
                xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                // Создаем обертку для списка студентов
                StudentsWrapper wrapper = new StudentsWrapper(students);
                // Сохраняем обертку в XML файл
                xmlMapper.writeValue(new File(fileName), wrapper);
            } else if (fileName.endsWith(".json")) {
                mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                mapper.writeValue(new File(fileName), students);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<Student> loadStudent(String fileName) {
        try {
            if (fileName.endsWith(".bin")) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
                    return (List<Student>) ois.readObject();
                }
            } else if (fileName.endsWith(".xml")) {
                return xmlMapper.readValue(new File(fileName), xmlMapper.getTypeFactory().constructCollectionType(List.class, Student.class));
            } else if (fileName.endsWith(".json")) {
                return mapper.readValue(new File(fileName), mapper.getTypeFactory().constructCollectionType(List.class, Student.class));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
