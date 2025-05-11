package Data;

import Models.Privilege;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrivilegeDAO extends TableDAO {

    public ArrayList<Privilege> getPrivilegeList(){
        ArrayList<Privilege> privilegeList = new ArrayList<>();
        String sql = "SELECT * FROM privilege";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Privilege permiso = new Privilege(rs.getInt("id_privilege"), rs.getString("nombre"));
                    privilegeList.add(permiso);
                }
                return privilegeList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> Privilege getPrivilege(String columna, T value){
        return getTableInfo("permisos", columna, value, lambdaRs -> {
            try {
                return new Privilege(lambdaRs.getInt("id_privilege"), lambdaRs.getString("nombre"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public boolean createPrivilege(String name) {
        if (getPrivilege("nombre", name) == null) {
            String sql = "INSERT INTO privilege (nombre) VALUES (?)";
            // Agrego data al sql
            try (PreparedStatement stmt = db.prepareStatement(sql)) {
                stmt.setString(1, name);
                return stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return false;
        }
        return false;
    }

    public boolean deletePrivilege(int id) {
        if (getPrivilege("id_privilege", id) != null) {
            String sql = "DELETE FROM privilege WHERE id_privilege = ?";
            try (PreparedStatement stmt = db.prepareStatement(sql)) {
                stmt.setInt(1, id);
                return stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return false;
        }
        return false;
    }
}