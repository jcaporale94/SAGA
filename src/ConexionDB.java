import java.sql.*;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/SAGA";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    /**
     * Establece la conexión con la base de datos SAGA.
     */
    public static Connection conectar() {
        try {
            Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexión exitosa a la base de datos SAGA.");
            return conexion;
        } catch (SQLException e) {
            System.out.println("❌ Error de conexión: " + e.getMessage());
            return null;
        }
    }

    /**
     * Crea todas las tablas necesarias para el sistema SAGA.
     */
    public static void crearTablas() {
        String[] scripts = new String[] {
                // Roles de usuario
                "CREATE TABLE IF NOT EXISTS Rol (" +
                        "ID_Rol INT AUTO_INCREMENT PRIMARY KEY, " +
                        "NombreRol VARCHAR(50) NOT NULL)",

                // Usuarios del sistema
                "CREATE TABLE IF NOT EXISTS Usuario (" +
                        "ID_Usuario INT AUTO_INCREMENT PRIMARY KEY, " +
                        "NombreUsuario VARCHAR(50) NOT NULL, " +
                        "Contraseña VARCHAR(100) NOT NULL, " +
                        "ID_Rol INT, " +
                        "FOREIGN KEY (ID_Rol) REFERENCES Rol(ID_Rol))",

                // Animales ingresados al refugio
                "CREATE TABLE IF NOT EXISTS Animal (" +
                        "ID_Animal INT AUTO_INCREMENT PRIMARY KEY, " +
                        "Nombre VARCHAR(100), " +
                        "Especie VARCHAR(50), " +
                        "Raza VARCHAR(50), " +
                        "Edad INT, " +
                        "Sexo VARCHAR(10), " +
                        "EstadoSalud VARCHAR(100), " +
                        "FechaIngreso DATE)",

                // Información veterinaria de cada animal
                "CREATE TABLE IF NOT EXISTS FichaMedica (" +
                        "ID_Ficha INT AUTO_INCREMENT PRIMARY KEY, " +
                        "Diagnostico TEXT, " +
                        "Tratamiento TEXT, " +
                        "Fecha DATE, " +
                        "ID_Animal INT, " +
                        "FOREIGN KEY (ID_Animal) REFERENCES Animal(ID_Animal))",

                // Comportamiento observado de los animales
                "CREATE TABLE IF NOT EXISTS Comportamiento (" +
                        "ID_Comp INT AUTO_INCREMENT PRIMARY KEY, " +
                        "Observaciones TEXT, " +
                        "NivelSociabilidad VARCHAR(50), " +
                        "ID_Animal INT, " +
                        "FOREIGN KEY (ID_Animal) REFERENCES Animal(ID_Animal))",

                // Adoptantes registrados
                "CREATE TABLE IF NOT EXISTS Adoptante (" +
                        "ID_Adoptante INT AUTO_INCREMENT PRIMARY KEY, " +
                        "Nombre VARCHAR(100), " +
                        "DNI VARCHAR(20), " +
                        "Telefono VARCHAR(20), " +
                        "Email VARCHAR(100), " +
                        "PerfilVivienda TEXT)",

                // Solicitudes de adopción realizadas
                "CREATE TABLE IF NOT EXISTS SolicitudAdopcion (" +
                        "ID_Solicitud INT AUTO_INCREMENT PRIMARY KEY, " +
                        "Fecha DATE, " +
                        "Estado VARCHAR(50), " +
                        "ID_Adoptante INT, " +
                        "FOREIGN KEY (ID_Adoptante) REFERENCES Adoptante(ID_Adoptante))",

                // Adopciones confirmadas
                "CREATE TABLE IF NOT EXISTS Adopcion (" +
                        "ID_Adopcion INT AUTO_INCREMENT PRIMARY KEY, " +
                        "Fecha DATE, " +
                        "Confirmado BOOLEAN, " +
                        "ID_Animal INT, " +
                        "ID_Adoptante INT, " +
                        "FOREIGN KEY (ID_Animal) REFERENCES Animal(ID_Animal), " +
                        "FOREIGN KEY (ID_Adoptante) REFERENCES Adoptante(ID_Adoptante))",

                // Seguimiento post-adopción
                "CREATE TABLE IF NOT EXISTS Seguimiento (" +
                        "ID_Seguimiento INT AUTO_INCREMENT PRIMARY KEY, " +
                        "Fecha DATE, " +
                        "Comentarios TEXT, " +
                        "ID_Adopcion INT, " +
                        "FOREIGN KEY (ID_Adopcion) REFERENCES Adopcion(ID_Adopcion))"
        };

        try (Connection conn = conectar(); Statement stmt = conn.createStatement()) {
            for (String script : scripts) {
                stmt.execute(script);
            }
            System.out.println("✅ Todas las tablas fueron creadas correctamente o ya existen.");
        } catch (SQLException e) {
            System.out.println("❌ Error al crear tablas: " + e.getMessage());
        }
    }

    // 👉 Métodos CRUD adicionales se agregan aquí si necesitás acceso directo a tablas como Usuario, Rol, etc.
}
