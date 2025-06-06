import java.sql.*;

public class ConexionDB {

    // ─────────────────────────────
    // Conexión a la base de datos
    // ─────────────────────────────
    private static final String URL = "jdbc:mysql://localhost:3306/SAGA";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    public static Connection conectar() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexión exitosa a la base de datos SAGA.");
        } catch (SQLException e) {
            System.out.println("❌ Error de conexión: " + e.getMessage());
        }
        return conexion;
    }

    // ─────────────────────────────
    // Tabla Animal
    // ─────────────────────────────
    public static void crearTablaAnimal() {
        String sql = "CREATE TABLE IF NOT EXISTS Animal (" +
                "ID_Animal INT AUTO_INCREMENT PRIMARY KEY," +
                "Nombre VARCHAR(100)," +
                "Especie VARCHAR(50)," +
                "Raza VARCHAR(50)," +
                "Edad INT," +
                "Sexo VARCHAR(10)," +
                "EstadoSalud VARCHAR(100)," +
                "FechaIngreso DATE" +
                ");";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("✅ Tabla Animal creada o ya existente.");
        } catch (SQLException e) {
            System.out.println("❌ Error al crear tabla Animal: " + e.getMessage());
        }
    }

    public static void insertarAnimal(String nombre, String especie, String raza, int edad, String sexo, String estadoSalud) {
        String sql = "INSERT INTO Animal (Nombre, Especie, Raza, Edad, Sexo, EstadoSalud, FechaIngreso) VALUES (?, ?, ?, ?, ?, ?, CURDATE())";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, especie);
            stmt.setString(3, raza);
            stmt.setInt(4, edad);
            stmt.setString(5, sexo);
            stmt.setString(6, estadoSalud);
            stmt.executeUpdate();
            System.out.println("✅ Animal insertado correctamente.");
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar animal: " + e.getMessage());
        }
    }

    public static void listarAnimales() {
        String sql = "SELECT * FROM Animal";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("📋 Lista de animales registrados:");
            while (rs.next()) {
                System.out.println("- " + rs.getString("Nombre") + " | " + rs.getString("Especie") +
                        " | " + rs.getString("Raza") + " | Edad: " + rs.getInt("Edad") +
                        " | Estado: " + rs.getString("EstadoSalud"));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al listar animales: " + e.getMessage());
        }
    }

    // ─────────────────────────────
    // Tabla Adoptante
    // ─────────────────────────────
    public static void crearTablaAdoptante() {
        String sql = "CREATE TABLE IF NOT EXISTS Adoptante (" +
                "ID_Adoptante INT AUTO_INCREMENT PRIMARY KEY," +
                "Nombre VARCHAR(100)," +
                "DNI VARCHAR(20)," +
                "Telefono VARCHAR(20)," +
                "Email VARCHAR(100)," +
                "PerfilVivienda TEXT" +
                ");";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("✅ Tabla Adoptante creada o ya existente.");
        } catch (SQLException e) {
            System.out.println("❌ Error al crear tabla Adoptante: " + e.getMessage());
        }
    }

    public static void insertarAdoptante(String nombre, String dni, String telefono, String email, String perfilVivienda) {
        String sql = "INSERT INTO Adoptante (Nombre, DNI, Telefono, Email, PerfilVivienda) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, dni);
            stmt.setString(3, telefono);
            stmt.setString(4, email);
            stmt.setString(5, perfilVivienda);
            stmt.executeUpdate();
            System.out.println("✅ Adoptante insertado correctamente.");
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar adoptante: " + e.getMessage());
        }
    }

    public static void listarAdoptantes() {
        String sql = "SELECT * FROM Adoptante";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("📋 Lista de adoptantes registrados:");
            while (rs.next()) {
                System.out.println("- " + rs.getString("Nombre") + " | DNI: " + rs.getString("DNI") +
                        " | Tel: " + rs.getString("Telefono") + " | Email: " + rs.getString("Email") +
                        " | Vivienda: " + rs.getString("PerfilVivienda"));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al listar adoptantes: " + e.getMessage());
        }
    }

    // ─────────────────────────────
    // Tabla Solicitud de Adopción
    // ─────────────────────────────
    public static void crearTablaSolicitudAdopcion() {
        String sql = "CREATE TABLE IF NOT EXISTS SolicitudAdopcion (" +
                "ID_Solicitud INT AUTO_INCREMENT PRIMARY KEY," +
                "Fecha DATE," +
                "Estado VARCHAR(50)," +
                "ID_Adoptante INT," +
                "FOREIGN KEY (ID_Adoptante) REFERENCES Adoptante(ID_Adoptante)" +
                ");";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("✅ Tabla SolicitudAdopcion creada o ya existente.");
        } catch (SQLException e) {
            System.out.println("❌ Error al crear tabla SolicitudAdopcion: " + e.getMessage());
        }
    }

    public static void insertarSolicitudDemo() {
        String sql = "INSERT INTO SolicitudAdopcion (Fecha, Estado, ID_Adoptante) VALUES (CURDATE(), 'Pendiente', 1);";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("✅ Solicitud de adopción insertada correctamente.");
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar solicitud: " + e.getMessage());
        }
    }

}