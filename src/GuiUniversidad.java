import Model.*;
import Model.BuilderPlan.PlanBuild;
import Model.InscripcionStrategy.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
    private JComboBox CrearPlanCarreraCB;
    private JButton AltaMateriaButton;
    private JTextField CrearPlNombreJT;
    private JTextField CrearPlIDJT;

    // ATRIBUTOS DE LA UNIVERSIDAD
    private final Universidad universidad;


    public GuiUniversidad() {

        universidad = new Universidad();

        precargarDatos();



        // Inicializar paneles (todos ocultos por defecto)
        inicializarPaneles();

        // BOTONES DEL PANEL PESTAÑA
        AltaEstudianteButton.addActionListener(e -> mostrarPanel(AltaEstudiantePanel));
        AltaMateriaButton.addActionListener(e -> mostrarPanel(AltaMateriaPanel));
        AltaCarreraButton.addActionListener(e -> mostrarPanel(CrearPlanPanel));
        InscripcionEstudianteButton.addActionListener(e -> mostrarPanel(inscripcionCarrera));
        verificarFinalizadaButton.addActionListener(e -> mostrarPanel(VerificarFinalizada));
        InscripcionAMateriaButton.addActionListener(e -> mostrarPanel(InscripcionMateria));

        //Botones de panel de alta Estudiante
        AltaEstdarAltaButton.addActionListener(e -> darAltaEstudiante());
        AltaEstcancelarButton.addActionListener(e -> limpiarCamposEstudiante());

        //Botones de panel de alta Materia
        AltaMatAltaMateriaButton.addActionListener(e ->{
            darAltaMateria();
            actualizarListaCorrelativas();
        });
        AltaMatCancelarMateriaButton.addActionListener(e -> limpiarCamposMateria());

        //Alta de carrera + plan.
        CrearPlCrearButton.addActionListener(e -> crearPlan());
        CrearPlsalirButton.addActionListener(e -> {
            limpiarCamposCrearPlan();
            ocultarTodosLosPaneles();
        });
        AltaCarreraButton.addActionListener(e -> {
            mostrarPanel(CrearPlanPanel);
            actualizarListasCrearPlan();
        });


    }



    ///     Metodos para alta de carrera y plan de estudios.    ///

    // Metodo para actualizar las listas del panel crear plan
    private void actualizarListasCrearPlan() {
        DefaultListModel<Materia> modelObligatorias = new DefaultListModel<>();
        DefaultListModel<Materia> modelOptativas = new DefaultListModel<>();

        // Cargar todas las materias disponibles en ambas listas
        universidad.getMaterias().forEach(materia -> {
            modelObligatorias.addElement(materia);
            modelOptativas.addElement(materia);
        });

        CrearPlMateriaObligatoriaJlist.setModel(modelObligatorias);
        CrearPlMateriaOpcionalJList.setModel(modelOptativas);
    }


    // crear carrera con plan y sus materias, se agrega carrera al list en universidad.
    private void crearPlan() {
        try {
            // Obtener datos básicos
            String nombreCarrera = CrearPlNombreJT.getText().trim();
            String idStr = CrearPlIDJT.getText().trim();
            String cantOptStr = CrearPlCantOpcJT.getText().trim();

            if (nombreCarrera.isEmpty() || idStr.isEmpty() || cantOptStr.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Todos los campos son obligatorios",
                        "Error de validación",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que no haya superposición en las selecciones
            List<Materia> seleccionadasObligatorias = obtenerSeleccionadasObligatorias();
            List<Materia> seleccionadasOptativas = obtenerSeleccionadasOptativas();

            // Chequear superposición
            for (Materia materia : seleccionadasObligatorias) {
                if (seleccionadasOptativas.contains(materia)) {
                    JOptionPane.showMessageDialog(null,
                            "La materia '" + materia.getNombre() + "' no puede ser obligatoria y optativa al mismo tiempo",
                            "Error de validación",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Parsear datos numéricos
            Integer id = Integer.parseInt(idStr);
            int cantOptativas = Integer.parseInt(cantOptStr);

            // Obtener estrategia seleccionada
            String estrategiaSeleccionada = (String) CrearPlEstrategiaCB.getSelectedItem();
            CondicionInscripcion estrategia = obtenerEstrategiaPorNombre(estrategiaSeleccionada);

            // Usar el Builder para crear el plan
            PlanBuild builder = new PlanBuild();

            // Agregar materias obligatorias
            for (Materia materia : seleccionadasObligatorias) {
                builder.agregarMateriaObligatoria(materia);
            }

            // Agregar materias optativas
            for (Materia materia : seleccionadasOptativas) {
                builder.agregarMateriaOpcional(materia);
            }

            // Configurar el plan
            builder.setOptativasMinimas(cantOptativas);
            builder.setEstrategiaInscripcion(estrategia);

            // Obtener el plan de estudio construido
            PlanDeEstudio nuevoPlan = builder.devolverPlan();

            // Crear la carrera y asignarle el plan
            Carrera nuevaCarrera = new Carrera(nombreCarrera, id);
            nuevaCarrera.setPlanEstudio(nuevoPlan);

            // Agregar a la universidad
            universidad.agregarCarrera(nuevaCarrera);

            JOptionPane.showMessageDialog(null,
                    "Carrera '" + nombreCarrera + "' creada exitosamente con " +
                            seleccionadasObligatorias.size() + " materias obligatorias y " +
                            seleccionadasOptativas.size() + " optativas",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            // Limpiar y mostrar en consola
            limpiarCamposCrearPlan();
            Consola.append("Carrera creada: " + nuevaCarrera.toString() + "\n");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "El ID y la cantidad de optativas deben ser números válidos",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al crear carrera: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Metodo auxiliar para obtener la estrategia según el nombre
    private CondicionInscripcion obtenerEstrategiaPorNombre(String nombreEstrategia) {
        if (nombreEstrategia == null) return new DirectorStrategy().getStrategy();

        return switch (nombreEstrategia.toLowerCase()) {
            case "condición a"-> new CondicionA();
            case "condición b" -> new CondicionB();
            case "condición c"-> new CondicionC();
            case "condición d"-> new CondicionD();
            case "condición e" -> new CondicionE();
            default -> new DirectorStrategy().getStrategy();
        };
    }

    // Métodos auxiliares para obtener selecciones
    private List<Materia> obtenerSeleccionadasObligatorias() {
        List<Materia> seleccionadas = new ArrayList<>();
        int[] indices = CrearPlMateriaObligatoriaJlist.getSelectedIndices();
        for (int index : indices) {
            seleccionadas.add((Materia) CrearPlMateriaObligatoriaJlist.getModel().getElementAt(index));
        }
        return seleccionadas;
    }

    private List<Materia> obtenerSeleccionadasOptativas() {
        List<Materia> seleccionadas = new ArrayList<>();
        int[] indices = CrearPlMateriaOpcionalJList.getSelectedIndices();
        for (int index : indices) {
            seleccionadas.add((Materia) CrearPlMateriaOpcionalJList.getModel().getElementAt(index));
        }
        return seleccionadas;
    }

    private void limpiarCamposCrearPlan() {
        CrearPlNombreJT.setText("");
        CrearPlIDJT.setText("");
        CrearPlCantOpcJT.setText("");
        CrearPlMateriaObligatoriaJlist.clearSelection();
        CrearPlMateriaOpcionalJList.clearSelection();
    }




    ///      Métodos nuevos para alta de materia      ///
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
        precargarEstudiantes();
        precargarCarrerasYMaterias();
        actualizarListasCrearPlan(); // Actualizar las listas después de precargar
    }

    private void precargarEstrategias() {
        // Configurar el ComboBox de estrategias
        DefaultComboBoxModel<String> modelEstrategias = new DefaultComboBoxModel<>();
        modelEstrategias.addElement("Condición A");
        modelEstrategias.addElement("Condición B");
        modelEstrategias.addElement("Condición C");
        modelEstrategias.addElement("Condición D");
        modelEstrategias.addElement("Condición E");
        CrearPlEstrategiaCB.setModel(modelEstrategias);
    }

    private void precargarEstudiantes() {
        // Precargar 8 estudiantes
        String[] nombres = {"Juan", "María", "Carlos", "Ana", "Luis", "Sofía", "Diego", "Laura"};
        String[] apellidos = {"García", "Rodríguez", "Martínez", "López", "González", "Pérez", "Sánchez", "Ramírez"};

        for (int i = 0; i < 8; i++) {
            long dni = 30000000L + i * 1000; // DNI: 30000000, 30001000, 30002000, etc.
            Estudiante estudiante = new Estudiante(nombres[i], apellidos[i], dni);
            universidad.agregarEstudiante(estudiante);
        }

        Consola.append("=== Estudiantes precargados ===\n");
        universidad.getEstudiantes().forEach(e -> Consola.append(e.toString() + "\n"));
        Consola.append("\n");
    }

    private void precargarCarrerasYMaterias() {
        // Crear materias base (15 materias para 3 carreras de 5 cada una)
        List<Materia> todasLasMaterias = new ArrayList<>();

        // Materias para Ingeniería (1-5)
        todasLasMaterias.add(new Materia("Matemática I", 101, 1));
        todasLasMaterias.add(new Materia("Física I", 102, 1));
        todasLasMaterias.add(new Materia("Programación I", 103, 1));
        todasLasMaterias.add(new Materia("Álgebra Lineal", 104, 2));
        todasLasMaterias.add(new Materia("Cálculo Diferencial", 105, 2));

        // Materias para Licenciatura en Sistemas (6-10)
        todasLasMaterias.add(new Materia("Base de Datos I", 106, 1));
        todasLasMaterias.add(new Materia("Redes de Datos", 107, 2));
        todasLasMaterias.add(new Materia("Ingeniería de Software", 108, 3));
        todasLasMaterias.add(new Materia("Inteligencia Artificial", 109, 4));
        todasLasMaterias.add(new Materia("Seguridad Informática", 110, 5));

        // Materias para Administración (11-15)
        todasLasMaterias.add(new Materia("Introducción a la Administración", 111, 1));
        todasLasMaterias.add(new Materia("Contabilidad Básica", 112, 1));
        todasLasMaterias.add(new Materia("Economía I", 113, 2));
        todasLasMaterias.add(new Materia("Marketing", 114, 3));
        todasLasMaterias.add(new Materia("Gestión de Proyectos", 115, 4));

        // Agregar correlativas simples (ejemplo)
        // Matemática I -> Cálculo Diferencial
        todasLasMaterias.get(4).getCorrelativas().add(todasLasMaterias.get(0));
        // Programación I -> Ingeniería de Software
        todasLasMaterias.get(7).getCorrelativas().add(todasLasMaterias.get(2));
        // Base de Datos I -> Seguridad Informática
        todasLasMaterias.get(9).getCorrelativas().add(todasLasMaterias.get(5));

        // Agregar todas las materias a la universidad
        for (Materia materia : todasLasMaterias) {
            universidad.agregarMateria(materia);
        }

        // Crear las 3 carreras usando el Builder
        crearCarreraIngenieria(todasLasMaterias.subList(0, 5));
        crearCarreraSistemas(todasLasMaterias.subList(5, 10));
        crearCarreraAdministracion(todasLasMaterias.subList(10, 15));

        Consola.append("=== Carreras y Materias precargadas ===\n");
        universidad.getCarreras().forEach(c -> {
            Consola.append(c.toString() + "\n");
            Consola.append("  Materias: " + c.getPlanEstudio().getMateriasObligatorias().size() + "\n");
        });
        Consola.append("\n");
    }

    private void crearCarreraIngenieria(List<Materia> materias) {
        PlanBuild builder = new PlanBuild();

        // Agregar 3 obligatorias y 2 optativas
        builder.agregarMateriaObligatoria(materias.get(0)); // Matemática I
        builder.agregarMateriaObligatoria(materias.get(1)); // Física I
        builder.agregarMateriaObligatoria(materias.get(2)); // Programación I
        builder.agregarMateriaOpcional(materias.get(3));     // Álgebra Lineal
        builder.agregarMateriaOpcional(materias.get(4));     // Cálculo Diferencial

        builder.setOptativasMinimas(1);
        builder.setEstrategiaInscripcion(new CondicionA());

        PlanDeEstudio plan = builder.devolverPlan();
        Carrera ingenieria = new Carrera("Ingeniería en Sistemas", 1);
        ingenieria.setPlanEstudio(plan);
        universidad.agregarCarrera(ingenieria);
    }

    private void crearCarreraSistemas(List<Materia> materias) {
        PlanBuild builder = new PlanBuild();

        // Agregar 4 obligatorias y 1 optativa
        builder.agregarMateriaObligatoria(materias.get(0)); // Base de Datos I
        builder.agregarMateriaObligatoria(materias.get(1)); // Redes de Datos
        builder.agregarMateriaObligatoria(materias.get(2)); // Ingeniería de Software
        builder.agregarMateriaObligatoria(materias.get(3)); // Inteligencia Artificial
        builder.agregarMateriaOpcional(materias.get(4));     // Seguridad Informática

        builder.setOptativasMinimas(1);
        builder.setEstrategiaInscripcion(new CondicionB());

        PlanDeEstudio plan = builder.devolverPlan();
        Carrera sistemas = new Carrera("Licenciatura en Sistemas", 2);
        sistemas.setPlanEstudio(plan);
        universidad.agregarCarrera(sistemas);
    }

    private void crearCarreraAdministracion(List<Materia> materias) {
        PlanBuild builder = new PlanBuild();

        // Agregar 2 obligatorias y 3 optativas
        builder.agregarMateriaObligatoria(materias.get(0)); // Introducción a la Administración
        builder.agregarMateriaObligatoria(materias.get(1)); // Contabilidad Básica
        builder.agregarMateriaOpcional(materias.get(2));     // Economía I
        builder.agregarMateriaOpcional(materias.get(3));     // Marketing
        builder.agregarMateriaOpcional(materias.get(4));     // Gestión de Proyectos

        builder.setOptativasMinimas(2);
        builder.setEstrategiaInscripcion(new CondicionC());

        PlanDeEstudio plan = builder.devolverPlan();
        Carrera administracion = new Carrera("Licenciatura en Administración", 3);
        administracion.setPlanEstudio(plan);
        universidad.agregarCarrera(administracion);
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
