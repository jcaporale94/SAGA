public class Adoptante {
    private int id;
    private String nombre;
    private String dni;
    private String telefono;
    private String email;
    private String perfilVivienda;

    public Adoptante(int id, String nombre, String dni, String telefono, String email, String perfilVivienda) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
        this.perfilVivienda = perfilVivienda;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDni() { return dni; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public String getPerfilVivienda() { return perfilVivienda; }

    @Override
    public String toString() {
        return nombre + " | DNI: " + dni + " | Tel: " + telefono + " | Email: " + email + " | Vivienda: " + perfilVivienda;
    }
}
