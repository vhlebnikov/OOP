package ru.nsu.khlebnikov

import lombok.Getter
import lombok.ToString
import ru.nsu.khlebnikov.model.ControlMark
import ru.nsu.khlebnikov.model.Group
import ru.nsu.khlebnikov.model.Lesson
import ru.nsu.khlebnikov.model.Student
import ru.nsu.khlebnikov.model.Task

import static groovy.lang.Closure.DELEGATE_ONLY

@Getter
@ToString
class GroupConfig {
    static public Group group
    static public List<Task> tasksList = new ArrayList<>()
    static public List<Lesson> lessonsList = new ArrayList<>()
    static public List<ControlMark> controlMarksList = new ArrayList<>()

    static public GroupParam groupParam = new GroupParam()

    static class GroupParam {
        static void group(String name) {
            group = new Group(name)
        }
    }

    static void group (@DelegatesTo(value = GroupParam, strategy = DELEGATE_ONLY) Closure closure) {
        closure.delegate = groupParam
        closure.resolveStrategy = DELEGATE_ONLY
        closure.call()
    }

    static public StudentParam studentParam = new StudentParam()

    static class StudentParam {
        static void student(String nickName, String url, String surname, String name, String patronymic) {
            group.addStudent(new Student(nickName, url, surname, name, patronymic))
        }
    }

    static void students (@DelegatesTo(value = StudentParam, strategy = DELEGATE_ONLY) Closure closure) {
        closure.delegate = studentParam
        closure.resolveStrategy = DELEGATE_ONLY
        closure.call()
    }

    static public TaskParam taskParam = new TaskParam()

    static class TaskParam {
        static void task(String id, String title, int points, String deadline){
            tasksList.add(new Task(id, title, points, deadline))
        }
    }

    static void tasks (@DelegatesTo(value = TaskParam, strategy = DELEGATE_ONLY) Closure closure) {
        closure.delegate = taskParam
        closure.resolveStrategy = DELEGATE_ONLY
        closure.call()
    }

    static public LessonParam lessonParam = new LessonParam()

    static class LessonParam {
        static void lesson(String date) {
            lessonsList.add(new Lesson(date))
        }
    }

    static void lessons (@DelegatesTo(value = LessonParam, strategy = DELEGATE_ONLY) Closure closure) {
        closure.delegate = lessonParam
        closure.resolveStrategy = DELEGATE_ONLY
        closure.call()
    }

    static public ControlMarkParam controlMarkParam = new ControlMarkParam()

    static class ControlMarkParam {
        static void controlMark(String name, String date) {
            controlMarksList.add(new ControlMark(name, date))
        }
    }

    static void controlMarks (@DelegatesTo(value = ControlMark, strategy = DELEGATE_ONLY) Closure closure) {
        closure.delegate = controlMarkParam
        closure.resolveStrategy = DELEGATE_ONLY
        closure.call()
    }
}
