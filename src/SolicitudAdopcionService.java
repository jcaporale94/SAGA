import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
public class SolicitudAdopcionService {

    public static void insertarSolicitudYAdopcionPorDNI(String dni, Scanner scanner) {
        try (Connection conn = ConexionDB.conectar()) {
            int idAdoptante = -1;

            // Buscar ID del adoptante por DNI
            String query = "SELECT ID_Adoptante FROM Adoptante WHERE DNI = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idAdoptante = rs.getInt("ID_Adoptante");
            } else {
                System.out.println("❌ No se encontró el adoptante.");
                return;
            }

            // Insertar solicitud
            String insertSolicitud = "INSERT INTO SolicitudAdopcion (Fecha, Estado, ID_Adoptante) VALUES (CURDATE(), 'Confirmada', ?)";
            PreparedStatement psSolicitud = conn.prepareStatement(insertSolicitud);
            psSolicitud.setInt(1, idAdoptante);
            psSolicitud.executeUpdate();
            System.out.println("✅ Solicitud de adopción registrada correctamente.");

            // Mostrar animales disponibles
            String sqlAnimalesDisponibles = """
            SELECT A.ID_Animal, A.Nombre, A.Especie, A.Raza
            FROM Animal A
            WHERE A.ID_Animal NOT IN (SELECT ID_Animal FROM Adopcion WHERE Confirmado = TRUE)
        """;

            PreparedStatement psAnimales = conn.prepareStatement(sqlAnimalesDisponibles);
            ResultSet rsAnimales = psAnimales.executeQuery();

            boolean hayAnimales = false;
            System.out.println("🐾 Animales disponibles para adoptar:");
            while (rsAnimales.next()) {
                hayAnimales = true;
                System.out.printf("- ID: %d | Nombre: %s | Especie: %s | Raza: %s%n",
                        rsAnimales.getInt("ID_Animal"),
                        rsAnimales.getString("Nombre"),
                        rsAnimales.getString("Especie"),
                        rsAnimales.getString("Raza"));
            }

            if (!hayAnimales) {
                System.out.println("⚠️ No hay animales disponibles para adoptar actualmente.");
                return;
            }

            System.out.print("👉 Ingrese el ID del animal que desea adoptar: ");
            int idAnimal = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            // Insertar adopción
            AdopcionService.insertarAdopcion(idAnimal, idAdoptante, true);
        } catch (Exception e) {
            System.out.println("❌ Error en solicitud y adopción: " + e.getMessage());
        }
    }
}