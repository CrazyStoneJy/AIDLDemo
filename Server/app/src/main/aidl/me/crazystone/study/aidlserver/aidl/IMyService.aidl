// IMyService.aidl
package me.crazystone.study.aidlserver.aidl;

// Declare any non-default types here with import statements
import me.crazystone.study.aidlserver.aidl.Student;

interface IMyService {

    List<Student> getStudents();

    void addStudent(in Student student);

}
