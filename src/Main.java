import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConexionDB.crearTablas();

        int opcion;
        do {
            System.out.println("\n🐾 Bienvenido a SAGA 🐾");
            System.out.println("1. Ver animales");
            System.out.println("2. Ver adoptantes");
            System.out.println("3. Insertar nuevo animal");
            System.out.println("4. Insertar nuevo adoptante");
            System.out.println("5. Registrar solicitud de adopción");
            System.out.println("6. Ver adopciones confirmadas");
            System.out.println("7. Cargar datos de prueba");
            System.out.println("8. Salir");
            System.out.print("Elija una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("❌ Ingrese un número válido.");
                scanner.nextLine();
                opcion = -1;
                continue;
            }

            switch (opcion) {
                case 1:
                    AnimalService.listarAnimales();
                    break;
                case 2:
                    AdoptanteService.listarAdoptantes();
                    break;
                case 3:
                    System.out.print("Nombre: ");
                    String nombreAnimal = leerSoloTexto(scanner);

                    System.out.print("Especie: ");
                    String especie = leerSoloTexto(scanner);

                    System.out.print("Raza: ");
                    String raza = leerSoloTexto(scanner);

                    int edad = leerNumero(scanner, "Edad");

                    System.out.print("Sexo: ");
                    String sexo = leerSoloTexto(scanner);

                    System.out.print("Estado de salud: ");
                    String estadoSalud = leerSoloTexto(scanner);

                    System.out.print("Diagnóstico médico: ");
                    String diagnostico = scanner.nextLine();

                    System.out.print("Tratamiento médico: ");
                    String tratamiento = scanner.nextLine();

                    System.out.print("Observaciones de comportamiento: ");
                    String comportamiento = scanner.nextLine();

                    System.out.print("Nivel de sociabilidad: ");
                    String nivelSociabilidad = leerSoloTexto(scanner);

                    AnimalService.insertarAnimalCompleto(nombreAnimal, especie, raza, edad, sexo, estadoSalud,
                            diagnostico, tratamiento, comportamiento, nivelSociabilidad);
                    break;
                case 4:
                    System.out.print("Nombre: ");
                    String nombreAdoptante = leerSoloTexto(scanner);

                    String dni;
                    do {
                        System.out.print("DNI: ");
                        dni = scanner.nextLine().trim();
                        if (!dni.matches("\\d+")) {
                            System.out.println("❌ Ingrese solo números.");
                        }
                    } while (!dni.matches("\\d+"));

                    String telefono;
                    do {
                        System.out.print("Teléfono: ");
                        telefono = scanner.nextLine();
                        if (!telefono.matches("\\d+")) {
                            System.out.println("❌ Ingrese solo números.");
                        }
                    } while (!telefono.matches("\\d+"));

                    String email;
                    do {
                        System.out.print("Email: ");
                        email = scanner.nextLine();
                        if (!email.contains("@")) {
                            System.out.println("❌ El email debe contener '@'.");
                        }
                    } while (!email.contains("@"));

                    System.out.print("Perfil de vivienda: ");
                    String perfil = leerSoloTexto(scanner);

                    AdoptanteService.insertarAdoptante(nombreAdoptante, dni, telefono, email, perfil);
                    break;
                case 5:
                    String dniBusqueda;
                    boolean adoptanteEncontrado = false;

                    do {
                        System.out.print("🔎 Ingrese DNI del adoptante (o escriba 'salir' para cancelar): ");
                        dniBusqueda = scanner.nextLine();

                        if (dniBusqueda.equalsIgnoreCase("salir")) {
                            System.out.println("🔙 Operación cancelada.");
                            break;
                        }

                        if (!dniBusqueda.matches("\\d+")) {
                            System.out.println("❌ El DNI debe ser numérico.");
                            continue;
                        }

                        adoptanteEncontrado = AdoptanteService.existeAdoptantePorDNI(dniBusqueda);

                        if (!adoptanteEncontrado) {
                            System.out.println("ℹ️ No se encontró un adoptante con ese DNI.");
                            System.out.print("¿Desea ingresar un nuevo DNI? (s/n): ");
                            String respuesta = scanner.nextLine();
                            if (!respuesta.equalsIgnoreCase("s")) {
                                System.out.println("🔙 Operación cancelada.");
                                break;
                            }
                        }

                    } while (!adoptanteEncontrado);

                    if (adoptanteEncontrado) {
                        SolicitudAdopcionService.insertarSolicitudYAdopcionPorDNI(dniBusqueda, scanner);
                    }
                    break;
                case 6:
                    AdopcionService.listarAdopcionesConfirmadas();
                    break;
                case 7:
                    AnimalService.insertarAnimalCompleto("Luna", "Perro", "Mestizo", 3, "Hembra", "Buena", "N/D", "N/D", "Tranquila", "Alta");
                    AnimalService.insertarAnimalCompleto("Toby", "Gato", "Siames", 2, "Macho", "Regular", "N/D", "N/D", "Juguetón", "Media");
                    AdoptanteService.insertarAdoptante("Laura Pérez", "40123456", "1123456789", "laura@prueba.com", "Casa con patio");
                    System.out.println("📦 Datos de ejemplo insertados.");
                    break;
                case 8:
                    System.out.println("👋 Gracias por usar SAGA. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("❌ Opción inválida. Intente nuevamente.");
            }

        } while (opcion != 8);
    }

    private static String leerSoloTexto(Scanner scanner) {
        String input;
        do {
            input = scanner.nextLine();
            if (!input.matches("[a-zA-ZÁÉÍÓÚáéíóúñÑ ]+")) {
                System.out.println("❌ Ingrese solo letras (sin números ni símbolos). Intente de nuevo:");
            }
        } while (!input.matches("[a-zA-ZÁÉÍÓÚáéíóúñÑ ]+"));
        return input;
    }

    private static int leerNumero(Scanner scanner, String campo) {
        int numero = -1;
        while (numero < 0) {
            System.out.print(campo + ": ");
            if (scanner.hasNextInt()) {
                numero = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("❌ Ingrese un número válido.");
                scanner.nextLine();
            }
        }
        return numero;
    }
}
