import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdopcionService {

    public static void insertarAdopcion(int idAnimal, int idAdoptante, boolean confirmado) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "INSERT INTO Adopcion (Fecha, Confirmado, ID_Animal, ID_Adoptante) VALUES (CURDATE(), ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, confirmado);
            ps.setInt(2, idAnimal);
            ps.setInt(3, idAdoptante);
            ps.executeUpdate();
            System.out.println("✅ Adopción insertada correctamente.");
        } catch (Exception e) {
            System.out.println("❌ Error al insertar adopción: " + e.getMessage());
        }
    }

    public static void insertarSeguimiento(int idAdopcion, String comentarios) {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "INSERT INTO Seguimiento (Fecha, Comentarios, ID_Adopcion) VALUES (CURDATE(), ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, comentarios);
            ps.setInt(2, idAdopcion);
            ps.executeUpdate();
            System.out.println("✅ Seguimiento post-adopción registrado correctamente.");
        } catch (Exception e) {
            System.out.println("❌ Error al insertar seguimiento: " + e.getMessage());
        }
    }

    public static void listarAdopcionesConfirmadas() {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = """
            SELECT A.ID_Adopcion, An.Nombre AS Animal, Ad.Nombre AS Adoptante, A.Fecha
            FROM Adopcion A
            JOIN Animal An ON A.ID_Animal = An.ID_Animal
            JOIN Adoptante Ad ON A.ID_Adoptante = Ad.ID_Adoptante
            WHERE A.Confirmado = TRUE
        """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            boolean hayAdopciones = false;
            System.out.println("📋 Adopciones confirmadas:");
            while (rs.next()) {
                hayAdopciones = true;
                System.out.printf("- ID: %d | Animal: %s | Adoptante: %s | Fecha: %s%n",
                        rs.getInt("ID_Adopcion"),
                        rs.getString("Animal"),
                        rs.getString("Adoptante"),
                        rs.getDate("Fecha"));
            }

            if (!hayAdopciones) {
                System.out.println("⚠️ No hay adopciones confirmadas actualmente.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error al listar adopciones: " + e.getMessage());
        }
    }
}
