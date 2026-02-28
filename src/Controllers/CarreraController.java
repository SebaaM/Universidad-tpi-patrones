package Controllers;

import Exceptions.ValidacionException;
import Model.BuilderPlan.PlanBuild;
import Model.Carrera;
import Model.InscripcionStrategy.*;
import Model.Materia;
import Model.PlanDeEstudio;
import Model.Universidad;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para gestionar las operaciones relacionadas con carreras
 * incluyendo creación de carreras, gestión de planes de estudio e inscripciones
 */
public class CarreraController {
    
    private Universidad universidad;
    private JTextArea consola;
    
    // Componentes UI para creación de carrera y plan
    private JTextField crearPlNombreJT;
    private JTextField crearPlIDJT;
    private JTextField crearPlCantOpcJT;
    private JList<Materia> crearPlMateriaObligatoriaJlist;
    private JList<Materia> crearPlMateriaOpcionalJList;
    private JComboBox<String> crearPlEstrategiaCB;
    
    public CarreraController(Universidad universidad, JTextArea consola) {
        this.universidad = universidad;
        this.consola = consola;
    }
    
    // Configuración de componentes UI
    public void setCrearCarreraComponents(JTextField nombreJT, JTextField idJT, 
                                         JTextField cantOpcJT, JList<Materia> obligatoriasJList,
                                         JList<Materia> optativasJList, JComboBox<String> estrategiaCB) {
        this.crearPlNombreJT = nombreJT;
        this.crearPlIDJT = idJT;
        this.crearPlCantOpcJT = cantOpcJT;
        this.crearPlMateriaObligatoriaJlist = obligatoriasJList;
        this.crearPlMateriaOpcionalJList = optativasJList;
        this.crearPlEstrategiaCB = estrategiaCB;
    }
    
    ///      Métodos para creación de carrera y plan de estudios      ///
    
    public void actualizarListasCrearPlan() {
        DefaultListModel<Materia> modelObligatorias = new DefaultListModel<>();
        DefaultListModel<Materia> modelOptativas = new DefaultListModel<>();

        // Cargar todas las materias disponibles en ambas listas
        universidad.getMaterias().forEach(materia -> {
            modelObligatorias.addElement(materia);
            modelOptativas.addElement(materia);
        });

        crearPlMateriaObligatoriaJlist.setModel(modelObligatorias);
        crearPlMateriaOpcionalJList.setModel(modelOptativas);
    }

    public void crearPlan() {
        try {
            // Obtener datos básicos
            String nombreCarrera = crearPlNombreJT.getText().trim();
            String idStr = crearPlIDJT.getText().trim();
            String cantOptStr = crearPlCantOpcJT.getText().trim();

            // Validar datos básicos
            validarDatosCrearPlan(nombreCarrera, idStr, cantOptStr);

            // Obtener selecciones de materias
            List<Materia> seleccionadasObligatorias = obtenerSeleccionadasObligatorias();
            List<Materia> seleccionadasOptativas = obtenerSeleccionadasOptativas();

            // Validar selecciones de materias
            validarSeleccionesMaterias(seleccionadasObligatorias, seleccionadasOptativas);

            // Parsear datos numéricos
            Integer id = Integer.parseInt(idStr);
            int cantOptativas = Integer.parseInt(cantOptStr);
            
            // Validar datos numéricos
            validarDatosNumericos(id, cantOptativas, seleccionadasOptativas.size());

            // Obtener estrategia seleccionada
            String estrategiaSeleccionada = (String) crearPlEstrategiaCB.getSelectedItem();
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

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(null,
                    "Carrera '" + nombreCarrera + "' creada exitosamente con " +
                            seleccionadasObligatorias.size() + " materias obligatorias y " +
                            seleccionadasOptativas.size() + " optativas",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            // Limpiar campos y mostrar en consola
            limpiarCamposCrearPlan();
            consola.append("Carrera creada: " + nuevaCarrera.toString() + "\n");
            consola.append("  - Materias obligatorias: " + seleccionadasObligatorias.size() + "\n");
            consola.append("  - Materias optativas: " + seleccionadasOptativas.size() + "\n");
            consola.append("  - Optativas requeridas: " + cantOptativas + "\n");

        } catch (ValidacionException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "El ID debe ser un número positivo y la cantidad de optativas debe ser un número válido",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al crear carrera: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void validarDatosCrearPlan(String nombreCarrera, String idStr, String cantOptStr) throws ValidacionException {
        // Validar campos obligatorios
        if (nombreCarrera.isEmpty() || idStr.isEmpty() || cantOptStr.isEmpty()) {
            throw new ValidacionException("Todos los campos son obligatorios");
        }
    }
    
    private void validarSeleccionesMaterias(List<Materia> seleccionadasObligatorias, List<Materia> seleccionadasOptativas) throws ValidacionException {
        // Validar que haya materias seleccionadas
        if (seleccionadasObligatorias.isEmpty() && seleccionadasOptativas.isEmpty()) {
            throw new ValidacionException("Debe seleccionar al menos una materia (obligatoria u optativa)");
        }

        // Validar que no haya superposición entre obligatorias y optativas
        for (Materia materia : seleccionadasObligatorias) {
            if (seleccionadasOptativas.contains(materia)) {
                throw new ValidacionException("La materia '" + materia.getNombre() + "' no puede ser obligatoria y optativa al mismo tiempo");
            }
        }
    }
    
    private void validarDatosNumericos(Integer id, int cantOptativas, int cantidadOptativasSeleccionadas) throws ValidacionException {
        if (id <= 0) {
            throw new ValidacionException("El ID debe ser un número positivo");
        }
        
        if (cantOptativas < 0) {
            throw new ValidacionException("La cantidad de optativas debe ser un número válido");
        }
        
        // Validar que la cantidad de optativas no sea mayor que las optativas seleccionadas
        if (cantOptativas > cantidadOptativasSeleccionadas) {
            throw new ValidacionException("La cantidad de optativas requeridas no puede ser mayor que las optativas seleccionadas");
        }
    }

    // Método auxiliar para obtener la estrategia según el nombre
    private CondicionInscripcion obtenerEstrategiaPorNombre(String nombreEstrategia) {
        if (nombreEstrategia == null) return new DirectorStrategy().getStrategy();

        return switch (nombreEstrategia.toLowerCase()) {
            case "condicion a"-> new CondicionA();
            case "condicion b" -> new CondicionB();
            case "condicion c"-> new CondicionC();
            case "condicion d"-> new CondicionD();
            case "condicion e" -> new CondicionE();
            default -> new DirectorStrategy().getStrategy();
        };
    }

    // Métodos auxiliares para obtener selecciones
    private List<Materia> obtenerSeleccionadasObligatorias() {
        List<Materia> seleccionadas = new ArrayList<>();
        int[] indices = crearPlMateriaObligatoriaJlist.getSelectedIndices();
        for (int index : indices) {
            seleccionadas.add((Materia) crearPlMateriaObligatoriaJlist.getModel().getElementAt(index));
        }
        return seleccionadas;
    }

    private List<Materia> obtenerSeleccionadasOptativas() {
        List<Materia> seleccionadas = new ArrayList<>();
        int[] indices = crearPlMateriaOpcionalJList.getSelectedIndices();
        for (int index : indices) {
            seleccionadas.add((Materia) crearPlMateriaOpcionalJList.getModel().getElementAt(index));
        }
        return seleccionadas;
    }

    public void limpiarCamposCrearPlan() {
        crearPlNombreJT.setText("");
        crearPlIDJT.setText("");
        crearPlCantOpcJT.setText("");
        crearPlMateriaObligatoriaJlist.clearSelection();
        crearPlMateriaOpcionalJList.clearSelection();
    }
}
