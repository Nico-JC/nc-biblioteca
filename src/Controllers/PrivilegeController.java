package Controllers;

import Data.PrivilegeDAO;
import Models.Privilege;

import java.util.ArrayList;

public class PrivilegeController {

    private PrivilegeDAO privilege;

    public PrivilegeController(){
        privilege = new PrivilegeDAO();
    }

    public ArrayList<Privilege> getPrivilegeList(){
        ArrayList<Privilege> privilegeList = new ArrayList<>();
        try {
            privilegeList = privilege.getPrivilegeList();
            if (privilegeList == null) {
                System.out.println("No se pudo obtener la lista de privilegios.");
                return privilegeList = new ArrayList<>();
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error al obtener la lista de privilegios: " + e.getMessage());
            e.printStackTrace();
        }
        return privilegeList;
    }

    public <T> Privilege getPrivilege(String columna, T value){
        return privilege.getPrivilege(columna, value);
    }

    public boolean createPrivilege(String name) {
        return privilege.createPrivilege(name);
    }

    public boolean deletePrivilege(int id) {
        return privilege.deletePrivilege(id);
    }
}
