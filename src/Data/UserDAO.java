package Data;

import Models.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO  extends TableDAO {

    public User login(String name, String pass) {
        String hashedPassword = hashPassword(pass);
        String sql = "SELECT * FROM user WHERE name = ? AND password = ?";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, hashedPassword);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UserSingleton.inicializarInstancia(rs.getInt("id_user"), rs.getString("name"), rs.getInt("id_privilege"));

                    return new User(rs.getInt("id_user"), rs.getString("name"), rs.getInt("id_privilege"));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> User getUser(String columna,T value) {
        return getTableInfo("user", columna, value, lambdaRs -> {
            try {
                return new User(lambdaRs.getInt("id_user"), lambdaRs.getString("name"), lambdaRs.getInt("id_privilege"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Object[][] getUserList() {
        String sql = "SELECT u.id_user, u.name, " +
                "(SELECT COUNT(*) FROM loan WHERE id_user = u.id_user AND status = 1) AS PrestamosActivos, " +
                "(SELECT COUNT(*) FROM loan WHERE id_user = u.id_user) AS PrestamosTotales " +
                "FROM user u";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                ArrayList<Object[]> infoList = new ArrayList<>();
                while (rs.next()) {
                    Object[] row = {
                            rs.getInt("id_user"),
                            rs.getString("name"),
                            rs.getInt("PrestamosActivos"),
                            rs.getInt("PrestamosTotales")
                    };
                    infoList.add(row);
                }
                return infoList.toArray(new Object[0][0]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createUser(String name, String pass) {
        if (getUser("name", name) == null) {
            String sql = "INSERT INTO user (name, password, id_privilege) VALUES (?, ?, ?)";
            String passHashed = hashPassword(pass);
            try (PreparedStatement stmt = db.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, passHashed);
                stmt.setInt(3, 1);

                return stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return false;
        }
        return false;
    }

    public boolean editUser(int id,String name, int permisos){
        if(getUser("id_user",id) != null){
            String sql = "UPDATE user SET name = ?, id_privilege = ? WHERE id_user = ?";
            try (PreparedStatement stmt = db.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setInt(2, permisos);
                stmt.setInt(3, id);

                return stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }
        else{
            return false;
        }
    }

    public boolean deleteUser(int id) {
        if (getUser("id_user", id) != null) {
            String sql = "DELETE FROM user WHERE id_user = ?";
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

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            System.out.println(sb.toString());
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

}