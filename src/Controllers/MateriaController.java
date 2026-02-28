package Controllers;

import Exceptions.MateriaException;
import Exceptions.ValidacionException;
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
            validarDatosAltaMateria();
            
            String idStr = altaMatIdJT.getText().trim();
            String nombre = altaMatNombreJT.getText().trim();
            String cuatrimestreStr = altaMatCuatrimestreJT.getText().trim();

            int id = Integer.parseInt(idStr);
            int cuatrimestre = Integer.parseInt(cuatrimestreStr);

            // Verificar si la materia ya existe
            if (existeMateriaConId(id)) {
                throw new MateriaException("Ya existe una materia con ese ID");
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

        } catch (ValidacionException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
        } catch (MateriaException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Materia duplicada",
                    JOptionPane.WARNING_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "El ID debe ser positivo y el cuatrimestre entre 1 y 10",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al agregar materia: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void validarDatosAltaMateria() throws ValidacionException {
        String idStr = altaMatIdJT.getText().trim();
        String nombre = altaMatNombreJT.getText().trim();
        String cuatrimestreStr = altaMatCuatrimestreJT.getText().trim();

        if (idStr.isEmpty() || nombre.isEmpty() || cuatrimestreStr.isEmpty()) {
            throw new ValidacionException("Todos los campos son obligatorios");
        }

        int id, cuatrimestre;
        try {
            id = Integer.parseInt(idStr);
            cuatrimestre = Integer.parseInt(cuatrimestreStr);
            if (id <= 0 || cuatrimestre <= 0 || cuatrimestre > 10) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            throw new ValidacionException("El ID debe ser positivo y el cuatrimestre entre 1 y 10");
        }
    }
    
    private boolean existeMateriaConId(int id) {
        return universidad.getCarreras().stream()
                .flatMap(carrera -> carrera.getPlanEstudio().getMateriasObligatorias().stream())
                .anyMatch(m -> m.getId().equals(id)) ||
                universidad.getCarreras().stream()
                        .flatMap(carrera -> carrera.getPlanEstudio().getMateriasOptativas().stream())
                        .anyMatch(m -> m.getId().equals(id));
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

            validarDatosInscripcionMateria(estudiante, materia);

            // Verificar si el estudiante tiene carrera
            if (estudiante.getCarrera() == null) {
                throw new MateriaException("El estudiante debe estar inscripto en una carrera para inscribirse a materias");
            }

            // Verificar si la materia pertenece a la carrera del estudiante
            if (!materiaPerteneceACarrera(estudiante, materia)) {
                throw new MateriaException("La materia no pertenece al plan de estudios de la carrera del estudiante");
            }

            // Verificar si ya está inscripto
            if (yaEstaInscripto(estudiante, materia)) {
                throw new MateriaException("El estudiante ya está inscripto en esta materia");
            }

            // Verificar correlativas
            List<Materia> correlativasPendientes = obtenerCorrelativasPendientes(estudiante, materia);
            if (!correlativasPendientes.isEmpty()) {
                throw new MateriaException("No cumple con las correlativas: " + 
                    correlativasPendientes.stream()
                        .map(Materia::getNombre)
                        .reduce((a, b) -> a + ", " + b)
                        .orElse(""));
            }

            // Realizar la inscripción
            Cursada cursada = new Cursada(materia);
            estudiante.getCursadasInscriptas().add(cursada);

            JOptionPane.showMessageDialog(null,
                    "Estudiante inscripto exitosamente en " + materia.getNombre(),
                    "Inscripción exitosa",
                    JOptionPane.INFORMATION_MESSAGE);

            limpiarCamposInscripcionMateria();
            consola.append("ÉXITO: Estudiante " + estudiante.toString() +
                    " inscripto en " + materia.toString() + "\n");

        } catch (ValidacionException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
        } catch (MateriaException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Error de inscripción",
                    JOptionPane.WARNING_MESSAGE);
            
            // Mostrar información detallada y materias disponibles por consola
            Estudiante estudiante = (Estudiante) inscMatAlumnoCB.getSelectedItem();
            if (estudiante != null && estudiante.getCarrera() != null) {
                mostrarMateriasDisponiblesPorConsola(estudiante, estudiante.getCarrera().getPlanEstudio());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al inscribir estudiante: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            consola.append("ERROR EXCEPCIÓN: " + e.getMessage() + "\n");
        }
    }
    
    private void validarDatosInscripcionMateria(Estudiante estudiante, Materia materia) throws ValidacionException {
        if (estudiante == null || materia == null) {
            throw new ValidacionException("Debe seleccionar un estudiante y una materia");
        }
    }
    
    private boolean materiaPerteneceACarrera(Estudiante estudiante, Materia materia) {
        PlanDeEstudio plan = estudiante.getCarrera().getPlanEstudio();
        return plan.getMateriasObligatorias().stream()
                .anyMatch(m -> m.getId().equals(materia.getId())) ||
                plan.getMateriasOptativas().stream()
                        .anyMatch(m -> m.getId().equals(materia.getId()));
    }
    
    private boolean yaEstaInscripto(Estudiante estudiante, Materia materia) {
        return estudiante.getCursadasInscriptas().stream()
                .anyMatch(c -> c.getMateria().getId().equals(materia.getId()));
    }
    
    private List<Materia> obtenerCorrelativasPendientes(Estudiante estudiante, Materia materia) {
        List<Materia> correlativasPendientes = new ArrayList<>();
        for (Materia correlativa : materia.getCorrelativas()) {
            boolean correlativaAprobada = estudiante.getCursadasInscriptas().stream()
                    .filter(c -> c.isCursadaAprobadaTotal())
                    .map(Cursada::getMateria)
                    .anyMatch(m -> m.getId().equals(correlativa.getId()));
            
            if (!correlativaAprobada) {
                correlativasPendientes.add(correlativa);
            }
        }
        return correlativasPendientes;
    }
    
    private void mostrarMateriasDisponiblesPorConsola(Estudiante estudiante, PlanDeEstudio plan) {
        List<Materia> materiasDisponibles = new ArrayList<>();
        
        // Verificar materias obligatorias
        for (Materia materia : plan.getMateriasObligatorias()) {
            if (puedeCursarMateria(estudiante, materia)) {
                materiasDisponibles.add(materia);
            }
        }
        
        // Verificar materias optativas
        for (Materia materia : plan.getMateriasOptativas()) {
            if (puedeCursarMateria(estudiante, materia)) {
                materiasDisponibles.add(materia);
            }
        }
        
        consola.append("\n📋 MATERIAS DISPONIBLES PARA " + estudiante.toString() + ":\n");
        
        if (materiasDisponibles.isEmpty()) {
            consola.append("    No hay materias disponibles para cursar en este momento.\n");
        } else {
            for (int i = 0; i < materiasDisponibles.size(); i++) {
                Materia materia = materiasDisponibles.get(i);
                String tipo = plan.getMateriasObligatorias().contains(materia) ? "[OBLIGATORIA]" : "[OPTATIVA]";
                consola.append("    " + (i + 1) + ". " + materia.toString() + " " + tipo + "\n");
            }
        }
        consola.append("   ----------------------------------------\n\n");
    }

    public void limpiarCamposInscripcionMateria() {
        inscMatAlumnoCB.setSelectedIndex(-1);
        inscMatMateriaCB.setSelectedIndex(-1);
    }

    public void actualizarComboBoxesInscripcionMateria() {
        // Actualizar combo box de estudiantes
        DefaultComboBoxModel<Estudiante> modelEstudiantes = new DefaultComboBoxModel<>();
        universidad.getEstudiantes().stream()
                .filter(e -> e.getCarrera() != null)
                .forEach(modelEstudiantes::addElement);
        inscMatAlumnoCB.setModel(modelEstudiantes);

        // Actualizar combo box de materias según el estudiante seleccionado
        actualizarComboBoxMateriasPorEstudiante();
    }
    
    private void actualizarComboBoxMateriasPorEstudiante() {
        DefaultComboBoxModel<Materia> modelMaterias = new DefaultComboBoxModel<>();
        
        Estudiante estudianteSeleccionado = (Estudiante) inscMatAlumnoCB.getSelectedItem();
        
        if (estudianteSeleccionado != null && estudianteSeleccionado.getCarrera() != null) {
            // Obtener materias de la carrera del estudiante
            PlanDeEstudio plan = estudianteSeleccionado.getCarrera().getPlanEstudio();
            
            // Agregar materias obligatorias disponibles
            for (Materia materia : plan.getMateriasObligatorias()) {
                if (puedeCursarMateria(estudianteSeleccionado, materia)) {
                    modelMaterias.addElement(materia);
                }
            }
            
            // Agregar materias optativas disponibles
            for (Materia materia : plan.getMateriasOptativas()) {
                if (puedeCursarMateria(estudianteSeleccionado, materia)) {
                    modelMaterias.addElement(materia);
                }
            }
            
            // Mostrar información por consola de las materias disponibles
            if (modelMaterias.getSize() > 0) {
                consola.append("\n📋 MATERIAS DISPONIBLES PARA " + estudianteSeleccionado.toString() + ":\n");
                for (int i = 0; i < modelMaterias.getSize(); i++) {
                    Materia materia = modelMaterias.getElementAt(i);
                    String tipo = plan.getMateriasObligatorias().contains(materia) ? "[OBLIGATORIA]" : "[OPTATIVA]";
                    consola.append("    " + (i + 1) + ". " + materia.toString() + " " + tipo + "\n");
                }
                consola.append("   ----------------------------------------\n");
            } else {
                consola.append("\n⚠️ No hay materias disponibles para inscribir a " + estudianteSeleccionado.toString() + "\n");
                consola.append("   (Revise correlativas pendientes o materias ya cursadas)\n");
                consola.append("   ----------------------------------------\n");
            }
        } else {
            // Si no hay estudiante seleccionado, mostrar mensaje
            if (estudianteSeleccionado == null) {
                consola.append("ℹ️ Seleccione un estudiante para ver las materias disponibles\n");
            } else if (estudianteSeleccionado.getCarrera() == null) {
                consola.append("⚠️ El estudiante " + estudianteSeleccionado.toString() + " no está inscripto en ninguna carrera\n");
            }
        }
        
        inscMatMateriaCB.setModel(modelMaterias);
    }
    
    // metodo para configurar el listener que actualiza las materias cuando cambia el estudiante
    public void configurarListenerEstudiante() {
        inscMatAlumnoCB.addActionListener(e -> actualizarComboBoxMateriasPorEstudiante());
    }
    
    private boolean puedeCursarMateria(Estudiante estudiante, Materia materia) {
        // Verificar si ya está inscripto
        if (estudiante.getCursadasInscriptas().stream()
                .anyMatch(c -> c.getMateria().getId().equals(materia.getId()))) {
            return false;
        }

        // Verificar correlativas
        for (Materia correlativa : materia.getCorrelativas()) {
            boolean correlativaAprobada = estudiante.getCursadasInscriptas().stream()
                    .filter(c -> c.isCursadaAprobadaTotal())
                    .map(Cursada::getMateria)
                    .anyMatch(m -> m.getId().equals(correlativa.getId()));
            
            if (!correlativaAprobada) {
                return false;
            }
        }

        return true;
    }
}
