
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AnimalService {
    public static void insertarAnimalCompleto(String nombre, String especie, String raza, int edad, String sexo, String estadoSalud,
                                              String diagnostico, String tratamiento,
                                              String comportamiento, String nivelSociabilidad) {
        try (Connection conn = ConexionDB.conectar()) {
            String sqlAnimal = "INSERT INTO Animal (Nombre, Especie, Raza, Edad, Sexo, EstadoSalud, FechaIngreso) VALUES (?, ?, ?, ?, ?, ?, CURDATE())";
            PreparedStatement psAnimal = conn.prepareStatement(sqlAnimal, Statement.RETURN_GENERATED_KEYS);
            psAnimal.setString(1, nombre);
            psAnimal.setString(2, especie);
            psAnimal.setString(3, raza);
            psAnimal.setInt(4, edad);
            psAnimal.setString(5, sexo);
            psAnimal.setString(6, estadoSalud);
            psAnimal.executeUpdate();

            ResultSet generatedKeys = psAnimal.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idAnimal = generatedKeys.getInt(1);

                // Insertar Ficha Médica
                String sqlFicha = "INSERT INTO FichaMedica (Diagnostico, Tratamiento, Fecha, ID_Animal) VALUES (?, ?, CURDATE(), ?)";
                PreparedStatement psFicha = conn.prepareStatement(sqlFicha);
                psFicha.setString(1, diagnostico);
                psFicha.setString(2, tratamiento);
                psFicha.setInt(3, idAnimal);
                psFicha.executeUpdate();

                // Insertar Comportamiento
                String sqlComp = "INSERT INTO Comportamiento (Observaciones, NivelSociabilidad, ID_Animal) VALUES (?, ?, ?)";
                PreparedStatement psComp = conn.prepareStatement(sqlComp);
                psComp.setString(1, comportamiento);
                psComp.setString(2, nivelSociabilidad);
                psComp.setInt(3, idAnimal);
                psComp.executeUpdate();

                System.out.println("✅ Animal, ficha médica y comportamiento insertados correctamente.");
            } else {
                System.out.println("❌ No se pudo obtener el ID del animal insertado.");
            }

        } catch (Exception e) {
            System.out.println("❌ Error al insertar animal completo: " + e.getMessage());
        }
    }

    public static void listarAnimales() {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = """
            SELECT A.ID_Animal, A.Nombre, A.Especie, A.Raza, A.Edad, A.EstadoSalud,
                   FM.Diagnostico, FM.Tratamiento,
                   (SELECT COUNT(*) FROM Adopcion AD WHERE AD.ID_Animal = A.ID_Animal AND AD.Confirmado = TRUE) AS Adoptado
            FROM Animal A
            LEFT JOIN FichaMedica FM ON A.ID_Animal = FM.ID_Animal
        """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("📋 Lista de animales registrados:");
            while (rs.next()) {
                boolean estaAdoptado = rs.getInt("Adoptado") > 0;
                String estadoAdopcion = estaAdoptado ? "❌ ADOPTADO" : "✅ DISPONIBLE";
                String diagnostico = rs.getString("Diagnostico") != null ? rs.getString("Diagnostico") : "N/D";
                String tratamiento = rs.getString("Tratamiento") != null ? rs.getString("Tratamiento") : "N/D";

                System.out.printf("- %s | %s | %s | Edad: %d | Estado: %s | Dx: %s | Tto: %s | %s%n",
                        rs.getString("Nombre"),
                        rs.getString("Especie"),
                        rs.getString("Raza"),
                        rs.getInt("Edad"),
                        rs.getString("EstadoSalud"),
                        diagnostico,
                        tratamiento,
                        estadoAdopcion);
            }
        } catch (Exception e) {
            System.out.println("❌ Error al listar animales: " + e.getMessage());
        }
    }
}
