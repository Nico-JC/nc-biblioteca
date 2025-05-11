package Models;

public class Privilege {
    private int id_permisos;
    private String nombre;

    public Privilege(int id, String nombre) {
        this.id_permisos = id;
        this.nombre = nombre;
    }

    public int getId_permisos() {
        return id_permisos;
    }

    public String getNombre() {
        return nombre;
    }
}