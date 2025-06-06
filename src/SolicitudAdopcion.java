public class SolicitudAdopcion {
    private int id;
    private int idAdoptante;
    private String fecha;
    private String estado;

    public SolicitudAdopcion(int id, int idAdoptante, String fecha, String estado) {
        this.id = id;
        this.idAdoptante = idAdoptante;
        this.fecha = fecha;
        this.estado = estado;
    }

    public int getId() { return id; }
    public int getIdAdoptante() { return idAdoptante; }
    public String getFecha() { return fecha; }
    public String getEstado() { return estado; }

    @Override
    public String toString() {
        return "SolicitudAdopcion{" +
                "id=" + id +
                ", idAdoptante=" + idAdoptante +
                ", fecha='" + fecha + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}