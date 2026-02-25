package Controllers;

import Model.*;
import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para gestionar las operaciones relacionadas con estudiantes
 * incluyendo alta de estudiantes y consulta de estado de carrera
 */
public class EstudianteController {
    
    private Universidad universidad;
    private JTextArea consola;
    
    // Componentes UI para alta de estudiante
    private JTextField altaEstNombreJT;
    private JTextField altaEstApellidoJT;
    private JTextField altaEstDniJT;
    
    // Componentes UI para inscripción a carrera
    private JComboBox<Estudiante> inscCarrEstudianteCB;
    private JComboBox<Carrera> inscCarrCarreraCB;
    
    // Componentes UI para verificación de carrera
    private JComboBox<Estudiante> verFinEstudianteCB;
    
    public EstudianteController(Universidad universidad, JTextArea consola) {
        this.universidad = universidad;
        this.consola = consola;
    }
    
    // Configuración de componentes UI
    public void setAltaEstudianteComponents(JTextField nombreJT, JTextField apellidoJT, JTextField dniJT) {
        this.altaEstNombreJT = nombreJT;
        this.altaEstApellidoJT = apellidoJT;
        this.altaEstDniJT = dniJT;
    }
    
    public void setInscripcionCarreraComponents(JComboBox<Estudiante> estudianteCB, 
                                               JComboBox<Carrera> carreraCB) {
        this.inscCarrEstudianteCB = estudianteCB;
        this.inscCarrCarreraCB = carreraCB;
    }
    
    public void setVerificacionCarreraComponents(JComboBox<Estudiante> estudianteCB) {
        this.verFinEstudianteCB = estudianteCB;
    }
    
    ///      Métodos para alta de estudiante      ///
    
    public void darAltaEstudiante() {
        try {
            String nombre = altaEstNombreJT.getText().trim();
            String apellido = altaEstApellidoJT.getText().trim();
            String dniStr = altaEstDniJT.getText().trim();

            // Validar que los campos no estén vacíos
            if (nombre.isEmpty() || apellido.isEmpty() || dniStr.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Todos los campos son obligatorios",
                        "Error de validación",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que el DNI sea un número válido
            long dni;
            try {
                dni = Long.parseLong(dniStr);
                if (dni <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "El DNI debe ser un número positivo válido",
                        "Error de validación",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar si el estudiante ya existe
            boolean estudianteExiste = universidad.getEstudiantes().stream()
                    .anyMatch(e -> e.getDni() == dni);

            if (estudianteExiste) {
                JOptionPane.showMessageDialog(null,
                        "Ya existe un estudiante con ese DNI",
                        "Estudiante duplicado",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Crear el estudiante y agregarlo a la universidad
            Estudiante nuevoEstudiante = new Estudiante(nombre, apellido, dni);
            universidad.agregarEstudiante(nuevoEstudiante);

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(null,
                    "Estudiante agregado correctamente:\n" + nuevoEstudiante.toString(),
                    "Alta exitosa",
                    JOptionPane.INFORMATION_MESSAGE);

            // Limpiar campos
            limpiarCamposEstudiante();

            // Mostrar en la consola
            consola.append("Estudiante agregado: " + nuevoEstudiante.toString() + "\n");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al agregar estudiante: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiarCamposEstudiante() {
        altaEstNombreJT.setText("");
        altaEstApellidoJT.setText("");
        altaEstDniJT.setText("");
        altaEstNombreJT.requestFocus();
    }

    ///      Métodos para inscripción a carrera      ///
    
    public void inscribirEstudianteEnCarrera() {
        try {
            Estudiante estudiante = (Estudiante) inscCarrEstudianteCB.getSelectedItem();
            Carrera carrera = (Carrera) inscCarrCarreraCB.getSelectedItem();

            if (estudiante == null || carrera == null) {
                JOptionPane.showMessageDialog(null,
                        "Debe seleccionar un estudiante y una carrera",
                        "Error de validación",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar si el estudiante ya está inscripto en una carrera
            if (estudiante.getCarrera() != null) {
                JOptionPane.showMessageDialog(null,
                        "El estudiante ya está inscripto en una carrera",
                        "Error de inscripción",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Realizar la inscripción
            estudiante.setCarrera(carrera);

            JOptionPane.showMessageDialog(null,
                    "Estudiante inscripto exitosamente en " + carrera.getNombre(),
                    "Inscripción exitosa",
                    JOptionPane.INFORMATION_MESSAGE);

            limpiarCamposInscripcionCarrera();
            consola.append("Estudiante inscripto a carrera: " + estudiante.toString() + " -> " + carrera.toString() + "\n");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al inscribir estudiante: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiarCamposInscripcionCarrera() {
        inscCarrEstudianteCB.setSelectedIndex(-1);
        inscCarrCarreraCB.setSelectedIndex(-1);
    }

    public void actualizarComboBoxesInscripcionCarrera() {
        // Actualizar combo boxes con datos disponibles
        DefaultComboBoxModel<Estudiante> modelEstudiantes = new DefaultComboBoxModel<>();
        universidad.getEstudiantes().forEach(modelEstudiantes::addElement);
        inscCarrEstudianteCB.setModel(modelEstudiantes);

        DefaultComboBoxModel<Carrera> modelCarreras = new DefaultComboBoxModel<>();
        universidad.getCarreras().forEach(modelCarreras::addElement);
        inscCarrCarreraCB.setModel(modelCarreras);
    }

    ///      Métodos para verificación de estado de carrera      ///
    
    public void verificarEstadoCarrera() {
        try {
            Estudiante estudiante = (Estudiante) verFinEstudianteCB.getSelectedItem();

            if (estudiante == null) {
                JOptionPane.showMessageDialog(null,
                        "Debe seleccionar un estudiante",
                        "Error de validación",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (estudiante.getCarrera() == null) {
                JOptionPane.showMessageDialog(null,
                        "El estudiante no está inscripto en ninguna carrera",
                        "Error de verificación",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            PlanDeEstudio plan = estudiante.getCarrera().getPlanEstudio();
            
            // Verificar si la carrera está finalizada
            List<Materia> materiasAprobadas = estudiante.getCursadasInscriptas().stream()
                    .filter(Cursada::isCursadaAprobadaTotal)
                    .map(Cursada::getMateria)
                    .toList();
            
            boolean obligatoriasAprobadas = plan.getMateriasObligatorias().stream()
                    .allMatch(materiasAprobadas::contains);
            
            long optativasAprobadas = plan.getMateriasOptativas().stream()
                    .filter(materiasAprobadas::contains)
                    .count();
            
            boolean optativasSuficientes = optativasAprobadas >= plan.getCantOpcionales();
            
            boolean carreraFinalizada = obligatoriasAprobadas && optativasSuficientes;

            String mensaje = "Estudiante: " + estudiante.toString() + "\n" +
                    "Carrera: " + estudiante.getCarrera().getNombre() + "\n" +
                    "Estado: " + (carreraFinalizada ? "FINALIZADA" : "EN CURSO") + "\n" +
                    "Materias aprobadas: " + materiasAprobadas.size() + "\n" +
                    "Materias obligatorias totales: " + plan.getMateriasObligatorias().size() + "\n" +
                    "Optativas aprobadas: " + optativasAprobadas + "\n" +
                    "Optativas requeridas: " + plan.getCantOpcionales();

            JOptionPane.showMessageDialog(null,
                    mensaje,
                    "Estado de Carrera",
                    carreraFinalizada ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);

            consola.append("Verificación de carrera - Estudiante: " + estudiante.toString() +
                    " - Estado: " + (carreraFinalizada ? "FINALIZADA" : "EN CURSO") + "\n");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al verificar estado: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiarCamposVerificarFinalizada() {
        verFinEstudianteCB.setSelectedIndex(-1);
    }

    public void actualizarComboBoxVerificarFinalizada() {
        // Actualizar combo box solo con estudiantes inscriptos a una carrera
        DefaultComboBoxModel<Estudiante> modelEstudiantes = new DefaultComboBoxModel<>();
        universidad.getEstudiantes().stream()
                .filter(e -> e.getCarrera() != null)
                .forEach(modelEstudiantes::addElement);
        verFinEstudianteCB.setModel(modelEstudiantes);
    }
}
