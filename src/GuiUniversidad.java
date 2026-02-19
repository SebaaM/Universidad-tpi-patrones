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
    private JTextField nombreTextField;
    private JButton darAltaButton;
    private JTextField textField1;
    private JPanel AltaEstudiantePanel;
    private JTextField textField2;
    private JButton cancelarButton;
    private JPanel Salida;
    private JPanel AltaMateriaPanel;
    private JPanel InscripcionCarrera;
    private JPanel inscripcionMateria;
    private JPanel VerificarFinalizada;
    private JList list1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Universidad");
        frame.setContentPane(new GuiUniversidad().JPanelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setSize(500,500);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
