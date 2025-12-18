import javax.swing.*;
import java.awt.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

public class DerivadaApp extends JFrame {

    private JTextArea outputArea;

    // ajuste de conexion oracle 19c
    private static final String DB_URL =
            "jdbc:oracle:thin:@//localhost:1521/orcl";
    private static final String DB_USER = "system";
    private static final String DB_PASSWORD = "Tapiero123";

    public DerivadaApp() {
        setTitle("Cálculo de Derivada - Registro Oracle 19c");
        setSize(520, 380);
        setDefaultCloseOperation(EXIT_ON_CLOSE);    
        setLocationRelativeTo(null);
        
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel(
                "Derivada de f(x) = x³  →  f'(x) = 3x²",
                JLabel.CENTER
        );
        title.setFont(new Font("Arial", Font.BOLD, 16));

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 14));

        JButton calculateBtn = new JButton("Calcular y Registrar");

        calculateBtn.addActionListener(e -> calcularYRegistrar());

        panel.add(title, BorderLayout.NORTH);
        panel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        panel.add(calculateBtn, BorderLayout.SOUTH);

        add(panel);
    }

    private void calcularYRegistrar() {
        int[] valoresX = {-3, -2, -1, 1, 2, 3};
        double[] resultados = new double[6];

        outputArea.setText("");

        for (int i = 0; i < valoresX.length; i++) {
            resultados[i] = 3 * Math.pow(valoresX[i], 2);   
            outputArea.append(
                    "f'(" + valoresX[i] + ") = " + resultados[i] + "\n"
            );
        }
        
        registrarEnOracle(resultados);
    }

    private void registrarEnOracle(double[] r) {
        String sql = "{ CALL SP_GUARDAR_DERIVADA(?, ?, ?, ?, ?, ?, ?) }";

        try (Connection conn = DriverManager.getConnection(
                DB_URL, DB_USER, DB_PASSWORD);
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, "f(x) = x^3");
            stmt.setDouble(2, r[0]);
            stmt.setDouble(3, r[1]);
            stmt.setDouble(4, r[2]);
            stmt.setDouble(5, r[3]);
            stmt.setDouble(6, r[4]);
            stmt.setDouble(7, r[5]);

            stmt.execute();

            outputArea.append("\n✔ Resultados registrados en Oracle 19c");

        } catch (Exception ex) {
            outputArea.append(
                    "\n✖ Error al registrar en BD:\n" + ex.getMessage()
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new DerivadaApp().setVisible(true)
        );
    }
}