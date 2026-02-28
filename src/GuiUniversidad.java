import Controllers.CarreraController;
import Controllers.CursadaController;
import Controllers.EstudianteController;
import Controllers.MateriaController;
import Model.Universidad;

import javax.swing.*;

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
    private JButton DemostrarStateButton;

    // ATRIBUTOS DE LA UNIVERSIDAD
    private final Universidad universidad;
    private final MateriaController materiaController;
    private final EstudianteController estudianteController;
    private final CarreraController carreraController;


    public GuiUniversidad() {

        universidad = Universidad.getInstance();
        materiaController = new MateriaController(universidad, Consola);
        estudianteController = new EstudianteController(universidad, Consola);
        carreraController = new CarreraController(universidad, Consola);

        // Inicializar controller con componentes de Interfaz
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
        DemostrarStateButton.addActionListener(e -> {
            new CursadaController().demostracionState();
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
        // Configurar MateriaController con componentes de Interfaz
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
        
        // Configurar listener para actualizar materias según el estudiante seleccionado
        materiaController.configurarListenerEstudiante();
        
        // Configurar EstudianteController con componentes de Interfaz
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
        
        // Configurar CarreraController con componentes de Interfaz
        carreraController.setCrearCarreraComponents(
            CrearPlNombreJT,
            CrearPlIDJT,
            CrearPlCantOpcJT,
            CrearPlMateriaObligatoriaJlist,
            CrearPlMateriaOpcionalJList,
            CrearPlEstrategiaCB
        );
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
