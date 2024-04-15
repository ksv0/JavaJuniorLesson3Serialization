package ksv;



import java.util.ArrayList;
import java.util.List;

import static ksv.StudentApp.*;



/**
 * <p>
 * Разработайте класс Student с полями String name, int age, transient double GPA (средний балл).
 * Обеспечьте поддержку сериализации для этого класса.
 * Создайте объект класса Student и инициализируйте его данными.
 * Сериализуйте этот объект в файл.
 * Десериализуйте объект обратно в программу из файла.
 * Выведите все поля объекта, включая GPA, и ответьте на вопрос,
 * почему значение GPA не было сохранено/восстановлено.
 *</p>
 * 2. * Выполнить задачу 1 используя другие типы сериализаторов (в xml и json документы).
 */

public class Main {
    public static void main(String[] args)  {
        List<Student> list = new ArrayList<>(){
            {
                add(new Student("Ivan", 25, 4.5));
                add(new Student("Petr", 20, 3.5));
                add(new Student("Sidor", 30, 5.0));
            }
        };
        System.out.println(list);

        saveStudent(FILE_BIN, list);
        // тут мы используем легаси сериализатор который учитывает ключевое слово "transient"
        // а следовательно не сериализует поле GPA
        System.out.println(loadStudent(FILE_BIN));
        saveStudent(FILE_XML, list);
        // а тут,
        System.out.println(loadStudent(FILE_XML));
        saveStudent(FILE_JSON, list);
        // и тут используем подключенную библиотеку Jackson,
        // которая использует геттеры для получения данных и не учитывает "transient"
        System.out.println(loadStudent(FILE_JSON));

    }

}
