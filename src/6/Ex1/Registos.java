package Ex1;

import java.util.*;

class Registos {
    // Data elements
    private ArrayList<Empregado> empregados; // Stores the employees

    public Registos() {
        empregados = new ArrayList<>();
    }

    public void insere(Empregado emp) {
        if (!empregados.contains(emp))
            empregados.add(emp);
    }

    public void remove(int codigo) {
        for (Empregado e : empregados) {
            if (e.codigo() == codigo) {
                empregados.remove(e);
                break;
            }
        }
    }

    public boolean isEmpregado(int codigo) {
        for (Empregado e : empregados) {
            if (e.codigo() == codigo)
                return true;
        }
        return false;
    }

    public List<Empregado> listaDeEmpregados() {
        return empregados;
    }
}
