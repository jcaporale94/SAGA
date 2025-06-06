import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdoptanteService {
    public static void insertarAdoptante(String nombre, String dni, String telefono, String email, String perfilVivienda) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "INSERT INTO Adoptante (Nombre, DNI, Telefono, Email, PerfilVivienda) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, dni);
            ps.setString(3, telefono);
            ps.setString(4, email);
            ps.setString(5, perfilVivienda);
            ps.executeUpdate();
            System.out.println("✅ Adoptante insertado correctamente.");
        } catch (Exception e) {
            System.out.println("❌ Error al insertar adoptante: " + e.getMessage());
        }
    }

    public static void listarAdoptantes() {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT Nombre, DNI, Telefono, Email, PerfilVivienda FROM Adoptante";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("📋 Lista de adoptantes registrados:");
            while (rs.next()) {
                System.out.printf("- %s | DNI: %s | Tel: %s | Email: %s | Vivienda: %s%n",
                        rs.getString("Nombre"),
                        rs.getString("DNI"),
                        rs.getString("Telefono"),
                        rs.getString("Email"),
                        rs.getString("PerfilVivienda"));
            }
        } catch (Exception e) {
            System.out.println("❌ Error al listar adoptantes: " + e.getMessage());
        }
    }

    public static boolean existeAdoptantePorDNI(String dni) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT COUNT(*) FROM Adoptante WHERE DNI = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            System.out.println("❌ Error al verificar adoptante por DNI: " + e.getMessage());
        }
        return false;
    }
}