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
    private JButton Crearplan;
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
    private JComboBox CrearPlanCarreraCB;
    private JButton CrearPlCrearButton;


    public GuiUniversidad() {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Universidad");
        frame.setContentPane(new GuiUniversidad().JPanelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800,400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }



}
