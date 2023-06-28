package Ex1;

public class CompanyAdapter {
    private Database db;
    private Registos reg;

    public CompanyAdapter(Registos reg, Database db) {
        this.reg = reg;
        this.db = db;
    }

    public boolean addEmployee(Employee e) {
        return db.addEmployee(e);
    }

    public void addEmployee(Empregado emp) {
        reg.insere(emp);
    }

    public void deleteEmployee(int empNum) {
        db.deleteEmployee(empNum);
        reg.remove(empNum);
    }

    public boolean isEmployee(int empNum) {
        boolean isEmp = false;
        for (Employee e : db.getAllEmployees())
            if (e.getEmpNum() == empNum)
                isEmp = true;
        return isEmp || reg.isEmpregado(empNum);
    }

    public String listEmployees() {
        StringBuilder sb = new StringBuilder();
        for (Employee e : db.getAllEmployees())
            sb = sb.append(e.getEmpNum() + ": " + e.getName() + "; Salary: €" + e.getSalary() + "\n");
        for (Empregado emp : reg.listaDeEmpregados())
            sb = sb.append(emp.codigo() + ": " + emp.nome() + " " + emp.apelido() + "; Salary: €" + emp.salario() + "\n");
        return sb.subSequence(0, sb.length() - 1).toString();
    }
}
