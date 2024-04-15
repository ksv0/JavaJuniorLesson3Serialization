package ksv;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "students")
public class StudentsWrapper {
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Student> students;

    public StudentsWrapper(List<Student> students) {
        this.students = students;
    }
    // region Геттеры и сеттеры

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
    // endregion
}
