import Model.*;
import Model.BuilderPlan.PlanBuild;
import Model.InscripcionStrategy.*;
import Controllers.MateriaController;
import Controllers.EstudianteController;
import Controllers.CarreraController;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GuiUniversidad {

    private JPanel JPanelPrincipal;
    private JPanel JPanelPestania;
    private JButton AltaCarreraButton;
    private JButton InscripcionAMateriaButton;
    private JButton verificarFinalizadaButton;
    private JButton InscripcionEstudianteButton;
    private JTextArea Consola;
    private JButton AltaEstudianteButton;
    private JTextField AltaEstNombreJT;
    private JButton AltaEstdarAltaButton;
    private JTextField AltaEstApellidoJT;
    private JPanel AltaEstudiantePanel;
    private JTextField AltaEstDniJT;
    private JButton AltaEstcancelarButton;
    private JPanel Salida;
    private JPanel AltaMateriaPanel;
    private JPanel InscripcionMateria;
    private JPanel inscripcionCarrera;
    private JPanel VerificarFinalizada;
    private JComboBox InscMatAlumnoCB;
    private JComboBox InscMatMateriaCB;
    private JList AltaMatCorrelativasJList;
    private JTextField AltaMatNumCuatrimestreJT;
    private JTextField AltaMatnombreJT;
    private JTextField AltaMatIdMateriaJT;
    private JButton AltaMatAltaMateriaButton;
    private JButton AltaMatCancelarMateriaButton;
    private JComboBox InscCarrCarreraCB;
    private JComboBox InscCarrEstudianteCB;
    private JButton InscCarrInscribirButton;
    private JButton InscCarrCancelarButton;
    private JButton InscMatAltaButton;
    private JButton InscMatCancelarButton;
    private JButton VerFinEstadoButton;
    private JButton VerFinSalirButton;
    private JComboBox VerFinEstudianteCB;
    private JPanel ContentPanel;
    private JPanel CrearPlanPanel;
    private JButton CrearPlsalirButton;
    private JList CrearPlMateriaObligatoriaJlist;
    private JList CrearPlMateriaOpcionalJList;
    private JComboBox CrearPlEstrategiaCB;
    private JTextField CrearPlCantOpcJT;
    private JButton CrearPlCrearButton;
    private JButton AltaMateriaButton;
    private JTextField textField1;
    private JTextField CrearPlNombreJT;
    private JTextField CrearPlIDJT;

    // ATRIBUTOS DE LA UNIVERSIDAD
    private final Universidad universidad;
    private final MateriaController materiaController;
    private final EstudianteController estudianteController;
    private final CarreraController carreraController;


    public GuiUniversidad() {

        universidad = new Universidad();
        materiaController = new MateriaController(universidad, Consola);
        estudianteController = new EstudianteController(universidad, Consola);
        carreraController = new CarreraController(universidad, Consola);

        // Inicializar controller con componentes UI
        inicializarControllerComponents();

        precargarDatos();

        // Inicializar paneles (todos ocultos por defecto)
        inicializarPaneles();

        // BOTONES DEL PANEL PESTAÑA
        AltaEstudianteButton.addActionListener(e -> mostrarPanel(AltaEstudiantePanel));
        AltaMateriaButton.addActionListener(e -> {
            materiaController.actualizarListaCorrelativas();
            mostrarPanel(AltaMateriaPanel);
        });
        AltaCarreraButton.addActionListener(e -> {
            carreraController.actualizarListasCrearPlan();
            mostrarPanel(CrearPlanPanel);
        });
        InscripcionEstudianteButton.addActionListener(e -> {
            estudianteController.actualizarComboBoxesInscripcionCarrera();
            mostrarPanel(inscripcionCarrera);
        });
        verificarFinalizadaButton.addActionListener(e -> {
            estudianteController.actualizarComboBoxVerificarFinalizada();
            mostrarPanel(VerificarFinalizada);
        });
        InscripcionAMateriaButton.addActionListener(e -> {
            materiaController.actualizarComboBoxesInscripcionMateria();
            mostrarPanel(InscripcionMateria);
        });

        //Botones de panel de alta Estudiante
        AltaEstdarAltaButton.addActionListener(e -> estudianteController.darAltaEstudiante());
        AltaEstcancelarButton.addActionListener(e -> estudianteController.limpiarCamposEstudiante());

        //Botones de panel de alta Materia
        AltaMatAltaMateriaButton.addActionListener(e ->{
            materiaController.darAltaMateria();
            materiaController.actualizarListaCorrelativas();
        });
        AltaMatCancelarMateriaButton.addActionListener(e -> materiaController.limpiarCamposMateria());

        //Alta de carrera + plan.
        CrearPlCrearButton.addActionListener(e -> carreraController.crearPlan());
        CrearPlsalirButton.addActionListener(e -> {
            carreraController.limpiarCamposCrearPlan();
            ocultarTodosLosPaneles();
        });

        //Inscripcion a carrera
        InscCarrInscribirButton.addActionListener(e -> estudianteController.inscribirEstudianteEnCarrera());
        InscCarrCancelarButton.addActionListener(e -> estudianteController.limpiarCamposInscripcionCarrera());

        // Inscripcion estudiante a Materia.
        InscMatAltaButton.addActionListener(e -> materiaController.inscribirEstudianteEnMateria());
        InscMatCancelarButton.addActionListener(e -> materiaController.limpiarCamposInscripcionMateria());

        //Botones verificar fin de carrera
        VerFinEstadoButton.addActionListener(e -> estudianteController.verificarEstadoCarrera());
        VerFinSalirButton.addActionListener(e -> {
            estudianteController.limpiarCamposVerificarFinalizada();
            ocultarTodosLosPaneles();
        });


    }


    private void inicializarControllerComponents() {
        // Configurar MateriaController con componentes UI
        materiaController.setAltaMateriaComponents(
            AltaMatIdMateriaJT,
            AltaMatnombreJT,
            AltaMatNumCuatrimestreJT,
            AltaMatCorrelativasJList
        );
        
        materiaController.setInscripcionMateriaComponents(
            InscMatAlumnoCB,
            InscMatMateriaCB
        );
        
        // Configurar EstudianteController con componentes UI
        estudianteController.setAltaEstudianteComponents(
            AltaEstNombreJT,
            AltaEstApellidoJT,
            AltaEstDniJT
        );
        
        estudianteController.setInscripcionCarreraComponents(
            InscCarrEstudianteCB,
            InscCarrCarreraCB
        );
        
        estudianteController.setVerificacionCarreraComponents(
            VerFinEstudianteCB
        );
        
        // Configurar CarreraController con componentes UI
        carreraController.setCrearCarreraComponents(
            CrearPlNombreJT,
            CrearPlIDJT,
            CrearPlCantOpcJT,
            CrearPlMateriaObligatoriaJlist,
            CrearPlMateriaOpcionalJList,
            CrearPlEstrategiaCB
        );
    }


    ///      Inscripción a carrera      ///
    
    private void inscribirEstudianteEnCarrera() {
        try {
            Estudiante estudiante = (Estudiante) InscCarrEstudianteCB.getSelectedItem();
            Carrera carrera = (Carrera) InscCarrCarreraCB.getSelectedItem();

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

            // Inscribir al estudiante
            estudiante.setCarrera(carrera);

            JOptionPane.showMessageDialog(null,
                    "Estudiante inscripto exitosamente en " + carrera.getNombre(),
                    "Inscripción exitosa",
                    JOptionPane.INFORMATION_MESSAGE);

            limpiarCamposInscripcionCarrera();
            Consola.append("Estudiante inscripto a carrera: " + estudiante.toString() + " -> " + carrera.toString() + "\n");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al inscribir estudiante: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCamposInscripcionCarrera() {
        InscCarrEstudianteCB.setSelectedIndex(-1);
        InscCarrCarreraCB.setSelectedIndex(-1);
    }

    private void actualizarComboBoxesInscripcionCarrera() {
        // se actualiza los combo box.
        DefaultComboBoxModel<Estudiante> modelEstudiantes = new DefaultComboBoxModel<>();
        universidad.getEstudiantes().forEach(modelEstudiantes::addElement);
        InscCarrEstudianteCB.setModel(modelEstudiantes);

        DefaultComboBoxModel<Carrera> modelCarreras = new DefaultComboBoxModel<>();
        universidad.getCarreras().forEach(modelCarreras::addElement);
        InscCarrCarreraCB.setModel(modelCarreras);
    }

    ///      Verificación de estado de carrera      ///
    
    private void verificarEstadoCarrera() {
        try {
            Estudiante estudiante = (Estudiante) VerFinEstudianteCB.getSelectedItem();

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
                    .filter(c -> c.isCursadaAprobadaTotal())
                    .map(Cursada::getMateria)
                    .collect(java.util.stream.Collectors.toList());
            
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

            Consola.append("Verificación de carrera - Estudiante: " + estudiante.toString() +
                    " - Estado: " + (carreraFinalizada ? "FINALIZADA" : "EN CURSO") + "\n");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al verificar estado: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCamposVerificarFinalizada() {
        VerFinEstudianteCB.setSelectedIndex(-1);
    }

    private void actualizarComboBoxVerificarFinalizada() {
        // solo estudiantes inscriptos a una carrera.
        DefaultComboBoxModel<Estudiante> modelEstudiantes = new DefaultComboBoxModel<>();
        universidad.getEstudiantes().stream()
                .filter(e -> e.getCarrera() != null)
                .forEach(modelEstudiantes::addElement);
        VerFinEstudianteCB.setModel(modelEstudiantes);
    }




    private void darAltaMateria() {
        try {
            String idStr = AltaMatIdMateriaJT.getText().trim();
            String nombre = AltaMatnombreJT.getText().trim();
            String cuatrimestreStr = AltaMatNumCuatrimestreJT.getText().trim();

            if (idStr.isEmpty() || nombre.isEmpty() || cuatrimestreStr.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Todos los campos son obligatorios",
                        "Error de validación",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id, cuatrimestre;

            // chequeo de integer
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


            // Agregar la materia a la universidad (independiente de carreras)
            universidad.agregarMateria(nuevaMateria);

            JOptionPane.showMessageDialog(null,
                    "Materia agregada correctamente:\n" + nuevaMateria.toString(),
                    "Alta exitosa",
                    JOptionPane.INFORMATION_MESSAGE);

            limpiarCamposMateria();
            actualizarListaCorrelativas();
            Consola.append("Materia agregada: " + nuevaMateria.toString() + "\n");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al agregar materia: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private void limpiarCamposMateria() {
        AltaMatIdMateriaJT.setText("");
        AltaMatnombreJT.setText("");
        AltaMatNumCuatrimestreJT.setText("");
        AltaMatIdMateriaJT.requestFocus();
    }

    private void actualizarListaCorrelativas() {
        DefaultListModel<Materia> model = new DefaultListModel<>();

        universidad.getMaterias().forEach(model::addElement);

        AltaMatCorrelativasJList.setModel(model);
    }

    private List<Materia> obtenerCorrelativasSeleccionadas() {
        List<Materia> correlativas = new java.util.ArrayList<>();

        int[] selectedIndices = AltaMatCorrelativasJList.getSelectedIndices();
        for (int index : selectedIndices) {
            Materia materia = (Materia) AltaMatCorrelativasJList.getModel().getElementAt(index);
            correlativas.add(materia);
        }

        return correlativas;
    }







    ///      Metodos de alta de estudiante.       ///
    private void darAltaEstudiante() {
        try {

            String nombre = AltaEstNombreJT.getText().trim();
            String apellido = AltaEstApellidoJT.getText().trim();
            String dniStr = AltaEstDniJT.getText().trim();

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
            Consola.append("Estudiante agregado: " + nuevoEstudiante.toString() + "\n");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al agregar estudiante: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // limpiar los campos del formulario de estudiante
    private void limpiarCamposEstudiante() {
        AltaEstNombreJT.setText("");
        AltaEstApellidoJT.setText("");
        AltaEstDniJT.setText("");
        AltaEstNombreJT.requestFocus();
    }



    ///      Metodos para el control de los paneles principales (pestaña)    ///

    // mostrar un panel específico y ocultar los demás
    private void mostrarPanel(JPanel panelAMostrar) {
        // Ocultar todos los paneles
        AltaEstudiantePanel.setVisible(false);
        AltaMateriaPanel.setVisible(false);
        CrearPlanPanel.setVisible(false);
        inscripcionCarrera.setVisible(false);
        VerificarFinalizada.setVisible(false);
        InscripcionMateria.setVisible(false);

        // Mostrar el panel solicitado y siempre Salida
        panelAMostrar.setVisible(true);
        Salida.setVisible(true);

        // Refrescar
        ContentPanel.revalidate();
        ContentPanel.repaint();
    }

    // ocultar todos los paneles
    private void ocultarTodosLosPaneles() {
        AltaEstudiantePanel.setVisible(false);
        AltaMateriaPanel.setVisible(false);
        CrearPlanPanel.setVisible(false);
        inscripcionCarrera.setVisible(false);
        VerificarFinalizada.setVisible(false);
        InscripcionMateria.setVisible(false);
        Salida.setVisible(true);  // Siempre visible
    }

    // inicializar paneles
    private void inicializarPaneles() {
        AltaEstudiantePanel.setVisible(false);
        AltaMateriaPanel.setVisible(false);
        CrearPlanPanel.setVisible(false);
        inscripcionCarrera.setVisible(false);
        VerificarFinalizada.setVisible(false);
        InscripcionMateria.setVisible(false);
        Salida.setVisible(true);  // Siempre visible desde el inicio
    }








    ///  PRECARGA DE DATOS
    ///

    // Agregar este mEtodo en GuiUniversidad
    private void precargarDatos() {
        precargarEstrategias();
        PrecargaMain.precargarDatos(universidad, Consola);
        carreraController.actualizarListasCrearPlan(); // Actualizar las listas después de precargar
    }

    private void precargarEstrategias() {
        // Configurar el ComboBox de estrategias
        DefaultComboBoxModel<String> modelEstrategias = new DefaultComboBoxModel<>();
        modelEstrategias.addElement("Condicion A");
        modelEstrategias.addElement("Condicion B");
        modelEstrategias.addElement("Condicion C");
        modelEstrategias.addElement("Condicion D");
        modelEstrategias.addElement("Condicion E");
        CrearPlEstrategiaCB.setModel(modelEstrategias);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Universidad");
        frame.setContentPane(new GuiUniversidad().JPanelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        //frame.setSize(800,400);
        frame.setLocationRelativeTo(null);
        //frame.setResizable(false);
        frame.setVisible(true);
    }



}
