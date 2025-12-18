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
    }
}