package Data;

public class UserSingleton {
    private static UserSingleton instancia;

    private int id;
    private String name;
    private int permisosId;

    private UserSingleton() {
    }

    private UserSingleton(int id, String name, int permisosId) {
        this.id = id;
        this.name = name;
        this.permisosId = permisosId;
    }

    // estese bva a usar en toda la plicacion para obtener la info del usuario
    public static UserSingleton obtenerInstancia() {
        if (instancia == null) {
            instancia = new UserSingleton();
        }
        return instancia;
    }

    // el inicializar se usa en el login para poder crear la instancia del usuario con su nfo
    public static void inicializarInstancia(int id, String name, int permisosId) {
        if (instancia == null) {
            instancia = new UserSingleton(id, name, permisosId);
        } else {
            instancia.id = id;
            instancia.name = name;
            instancia.permisosId = permisosId;
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPermisosId() {
        return permisosId;
    }
}