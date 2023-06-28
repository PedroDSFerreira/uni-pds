package Ex1;

import java.util.*;

public class CompanyPst {
    public static void main(String[] args) {
        // ------------------- 1) -------------------
        Employee e1 = new Employee("Alice", 364, 1050);
        Employee e2 = new Employee("Bob", 24, 1050);
        Employee e3 = new Employee("Charlie", 179, 800);

        Database db = new Database();
        db.addEmployee(e1);
        db.addEmployee(e2);
        db.addEmployee(e2);
        db.addEmployee(e3);
        db.deleteEmployee(179);
        db.deleteEmployee(0);

        Employee[] empList = db.getAllEmployees();
        for (Employee e : empList)
            System.out.println(e.getEmpNum() + ": " + e.getName() + "; Salary: €" + e.getSalary());

        System.out.println();
        Empregado emp1 = new Empregado("João", "Santos", 45, 800);
        Empregado emp2 = new Empregado("Maria", "Vaz", 78, 700);
        Empregado emp3 = new Empregado("Joana", "Pires", 26, 850);

        Registos reg = new Registos();
        reg.insere(emp1);
        reg.insere(emp2);
        reg.insere(emp3);
        reg.remove(78);

        System.out.println(reg.isEmpregado(78));
        List<Empregado> list = reg.listaDeEmpregados();
        for (Empregado emp : list)
            System.out.println(emp.codigo() + ": " + emp.nome() + " " + emp.apelido() + "; Salário: €");
        System.out.println("-------------------");
        
        // ------------------- 2) -------------------
        CompanyAdapter pst = new CompanyAdapter(reg, db);

        Empregado emp4 = new Empregado("Pedro", "Correia", 12, 900);
        Empregado emp5 = new Empregado("Ana", "Silva", 34, 900);
        Employee e4 = new Employee("David", 89, 900);
        Employee e5 = new Employee("Eve", 91, 900);
        pst.addEmployee(emp4);
        pst.addEmployee(e4);
        pst.addEmployee(e5);
        pst.deleteEmployee(91);
        System.out.println("Employee 34 exists: " + pst.isEmployee(34));
        System.out.println(pst.listEmployees());
    }
}
