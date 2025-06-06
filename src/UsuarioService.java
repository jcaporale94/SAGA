import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioService {

    public static void insertarUsuario(String nombreUsuario, String contraseña, int idRol) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "INSERT INTO Usuario (NombreUsuario, Contraseña, ID_Rol) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombreUsuario);
            ps.setString(2, contraseña); // ⚠️ sin encriptar por ahora
            ps.setInt(3, idRol);
            ps.executeUpdate();
            System.out.println("✅ Usuario insertado correctamente.");
        } catch (Exception e) {
            System.out.println("❌ Error al insertar usuario: " + e.getMessage());
        }
    }

    public static void listarUsuarios() {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = """
                    SELECT U.ID_Usuario, U.NombreUsuario, R.NombreRol
                    FROM Usuario U
                    JOIN Rol R ON U.ID_Rol = R.ID_Rol
                    """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("📋 Lista de usuarios:");
            while (rs.next()) {
                System.out.printf("- ID: %d | Usuario: %s | Rol: %s\n",
                        rs.getInt("ID_Usuario"),
                        rs.getString("NombreUsuario"),
                        rs.getString("NombreRol"));
            }
        } catch (Exception e) {
            System.out.println("❌ Error al listar usuarios: " + e.getMessage());
        }
    }
}