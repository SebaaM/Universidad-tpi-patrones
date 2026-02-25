package Controllers;

import Model.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para gestionar las operaciones relacionadas con materias
 * incluyendo alta de materias e inscripción de estudiantes
 */
public class MateriaController {
    
    private Universidad universidad;
    private JTextArea consola;
    
    // para alta de materia
    private JTextField altaMatIdJT;
    private JTextField altaMatNombreJT;
    private JTextField altaMatCuatrimestreJT;
    private JList<Materia> altaMatCorrelativasJList;
    
    // Componentes UI para inscripción a materia
    private JComboBox<Estudiante> inscMatAlumnoCB;
    private JComboBox<Materia> inscMatMateriaCB;
    
    public MateriaController(Universidad universidad, JTextArea consola) {
        this.universidad = universidad;
        this.consola = consola;
    }
    
    // Configuración de componentes UI
    public void setAltaMateriaComponents(JTextField idJT, JTextField nombreJT, 
                                         JTextField cuatrimestreJT, JList<Materia> correlativasJList) {
        this.altaMatIdJT = idJT;
        this.altaMatNombreJT = nombreJT;
        this.altaMatCuatrimestreJT = cuatrimestreJT;
        this.altaMatCorrelativasJList = correlativasJList;
    }
    
    public void setInscripcionMateriaComponents(JComboBox<Estudiante> alumnoCB, 
                                               JComboBox<Materia> materiaCB) {
        this.inscMatAlumnoCB = alumnoCB;
        this.inscMatMateriaCB = materiaCB;
    }
    
    ///      Métodos para alta de materia      ///
    
    public void darAltaMateria() {
        try {
            String idStr = altaMatIdJT.getText().trim();
            String nombre = altaMatNombreJT.getText().trim();
            String cuatrimestreStr = altaMatCuatrimestreJT.getText().trim();

            if (idStr.isEmpty() || nombre.isEmpty() || cuatrimestreStr.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Todos los campos son obligatorios",
                        "Error de validación",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id, cuatrimestre;

            // Validación de datos numéricos
            try {
                id = Integer.parseInt(idStr);
                cuatrimestre = Integer.parseInt(cuatrimestreStr);
                if (id <= 0 || cuatrimestre <= 0 || cuatrimestre > 10) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "El ID debe ser positivo y el cuatrimestre entre 1 y 10",
                        "Error de validación",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar si la materia ya existe
            boolean materiaExiste = universidad.getCarreras().stream()
                    .flatMap(carrera -> carrera.getPlanEstudio().getMateriasObligatorias().stream())
                    .anyMatch(m -> m.getId().equals(id)) ||
                    universidad.getCarreras().stream()
                            .flatMap(carrera -> carrera.getPlanEstudio().getMateriasOptativas().stream())
                            .anyMatch(m -> m.getId().equals(id));

            if (materiaExiste) {
                JOptionPane.showMessageDialog(null,
                        "Ya existe una materia con ese ID",
                        "Materia duplicada",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            List<Materia> correlativas = obtenerCorrelativasSeleccionadas();
            Materia nuevaMateria = new Materia(nombre, id, cuatrimestre);
            nuevaMateria.setCorrelativas(correlativas);

            // Agregar la materia a la universidad
            universidad.agregarMateria(nuevaMateria);

            JOptionPane.showMessageDialog(null,
                    "Materia agregada correctamente:\n" + nuevaMateria.toString(),
                    "Alta exitosa",
                    JOptionPane.INFORMATION_MESSAGE);

            limpiarCamposMateria();
            actualizarListaCorrelativas();
            consola.append("Materia agregada: " + nuevaMateria.toString() + "\n");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al agregar materia: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiarCamposMateria() {
        altaMatIdJT.setText("");
        altaMatNombreJT.setText("");
        altaMatCuatrimestreJT.setText("");
        altaMatIdJT.requestFocus();
    }

    public void actualizarListaCorrelativas() {
        DefaultListModel<Materia> model = new DefaultListModel<>();

        universidad.getMaterias().forEach(model::addElement);

        altaMatCorrelativasJList.setModel(model);
    }

    public List<Materia> obtenerCorrelativasSeleccionadas() {
        List<Materia> correlativas = new ArrayList<>();

        int[] selectedIndices = altaMatCorrelativasJList.getSelectedIndices();
        for (int index : selectedIndices) {
            Materia materia = (Materia) altaMatCorrelativasJList.getModel().getElementAt(index);
            correlativas.add(materia);
        }

        return correlativas;
    }

    ///      Métodos para inscripción a materia      ///
    
    public void inscribirEstudianteEnMateria() {
        try {
            Estudiante estudiante = (Estudiante) inscMatAlumnoCB.getSelectedItem();
            Materia materia = (Materia) inscMatMateriaCB.getSelectedItem();

            if (estudiante == null || materia == null) {
                JOptionPane.showMessageDialog(null,
                        "Debe seleccionar un estudiante y una materia",
                        "Error de validación",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar si el estudiante tiene carrera
            if (estudiante.getCarrera() == null) {
                JOptionPane.showMessageDialog(null,
                        "El estudiante debe estar inscripto en una carrera para inscribirse a materias",
                        "Error de inscripción",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verificar si la materia pertenece a la carrera del estudiante
            PlanDeEstudio plan = estudiante.getCarrera().getPlanEstudio();
            if (!plan.getMateriasObligatorias().contains(materia) && !plan.getMateriasOptativas().contains(materia)) {
                JOptionPane.showMessageDialog(null,
                        "La materia no pertenece al plan de estudios de la carrera del estudiante",
                        "Error de inscripción",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verificar si ya está inscripto
            if (estudiante.getCursadasInscriptas().stream().anyMatch(c -> c.getMateria().equals(materia))) {
                JOptionPane.showMessageDialog(null,
                        "El estudiante ya está inscripto en esta materia",
                        "Error de inscripción",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verificar correlativas
            for (Materia correlativa : materia.getCorrelativas()) {
                if (!estudiante.getCursadasInscriptas().stream()
                        .filter(c -> c.isCursadaAprobadaTotal())
                        .map(Cursada::getMateria)
                        .toList()
                        .contains(correlativa)) {
                    JOptionPane.showMessageDialog(null,
                            "No cumple con las correlativas: " + correlativa.getNombre(),
                            "Error de inscripción",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            // Realizar la inscripción
            Cursada cursada = new Cursada(materia);
            estudiante.getCursadasInscriptas().add(cursada);

            JOptionPane.showMessageDialog(null,
                    "Estudiante inscripto exitosamente en " + materia.getNombre(),
                    "Inscripción exitosa",
                    JOptionPane.INFORMATION_MESSAGE);

            limpiarCamposInscripcionMateria();
            consola.append("Estudiante inscripto a materia: " + estudiante.toString() + " -> " + materia.toString() + "\n");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al inscribir estudiante: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiarCamposInscripcionMateria() {
        inscMatAlumnoCB.setSelectedIndex(-1);
        inscMatMateriaCB.setSelectedIndex(-1);
    }

    public void actualizarComboBoxesInscripcionMateria() {
        // Actualizar combo boxes con datos disponibles
        DefaultComboBoxModel<Estudiante> modelEstudiantes = new DefaultComboBoxModel<>();
        universidad.getEstudiantes().stream()
                .filter(e -> e.getCarrera() != null)
                .forEach(modelEstudiantes::addElement);
        inscMatAlumnoCB.setModel(modelEstudiantes);

        DefaultComboBoxModel<Materia> modelMaterias = new DefaultComboBoxModel<>();
        universidad.getMaterias().forEach(modelMaterias::addElement);
        inscMatMateriaCB.setModel(modelMaterias);
    }
}
