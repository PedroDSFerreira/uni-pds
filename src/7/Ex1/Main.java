package lab07.Ex1;

import java.util.Date;

public class Main {
    private static EmployeeInterface e;
    public static void main(String[] args) {
        e = new Manager(
            new TeamLeader(
            new TeamMember(
            new Employee("John"))));
        System.out.println(e.work());
        System.out.println(e.start(new Date()));
        System.out.println(e.terminate(new Date()));
    }
}