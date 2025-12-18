# -_aplicacion_java-_calculo_de_derivadas_con_persistencia_en_Oracle19C_- :.  
# Aplicación Java – Cálculo de Derivadas con Persistencia en Oracle 19c .

<img width="1024" height="1024" alt="image" src="https://github.com/user-attachments/assets/a5b7ef47-23f8-4a4c-9f9d-2a5719495457" />  

Solución completa, profesional y lista para ejecutar en **IntelliJ IDEA**, que cumple exactamente con los siguientes requerimientos:

* Aplicación Java con **interfaz gráfica (Swing)**
* Cálculo de una **derivada simple**
* Generación y visualización de **6 soluciones numéricas**
* Registro de resultados en **Oracle Database 19c** mediante **Stored Procedure**

---

## 1. Enfoque matemático (Derivada)

Para mantener un enfoque claro, académico y determinista, se utiliza la siguiente función:

```text
f(x) = x³
```

La derivada correspondiente es:

```text
f'(x) = 3x²
```

La derivada se evalúa en los siguientes valores de `x`:

```text
x = -3, -2, -1, 1, 2, 3
```

Esto produce **seis resultados numéricos distintos**, los cuales:

* Se muestran en la interfaz gráfica
* Se almacenan en la base de datos Oracle

---

## 2. Stored Procedure en Oracle 19c

Ejecute previamente el siguiente script en Oracle Database 19c:

```sql
CREATE TABLE DERIVADAS_RESULTADO (
    ID NUMBER GENERATED ALWAYS AS IDENTITY,
    FECHA_REGISTRO DATE,
    FUNCION VARCHAR2(100),
    RESULTADO1 NUMBER,
    RESULTADO2 NUMBER,
    RESULTADO3 NUMBER,
    RESULTADO4 NUMBER,
    RESULTADO5 NUMBER,
    RESULTADO6 NUMBER
);

CREATE OR REPLACE PROCEDURE SP_GUARDAR_DERIVADA (
    P_FUNCION    IN VARCHAR2,
    P_R1         IN NUMBER,
    P_R2         IN NUMBER,
    P_R3         IN NUMBER,
    P_R4         IN NUMBER,
    P_R5         IN NUMBER,
    P_R6         IN NUMBER
) AS
BEGIN
    INSERT INTO DERIVADAS_RESULTADO (
        FECHA_REGISTRO,
        FUNCION,
        RESULTADO1,
        RESULTADO2,
        RESULTADO3,
        RESULTADO4,
        RESULTADO5,
        RESULTADO6
    ) VALUES (
        SYSDATE,
        P_FUNCION,
        P_R1,
        P_R2,
        P_R3,
        P_R4,
        P_R5,
        P_R6
    );

    COMMIT;
END;
/
```

---

## 3. Programa Java (Swing + Oracle JDBC)

**Archivo único:** `DerivadaApp.java`

```java
import javax.swing.*;
import java.awt.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

public class DerivadaApp extends JFrame {

    private JTextArea outputArea;

    // Ajuste de conexión Oracle 19c
    private static final String DB_URL =
            "jdbc:oracle:thin:@//localhost:1521/orcl";
    private static final String DB_USER = "USUARIO";
    private static final String DB_PASSWORD = "PASSWORD";

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
```

---

## 4. Requisitos para ejecución

* **JDK 17 o superior**
* **IntelliJ IDEA**
* **Oracle Database 19c**
* **Oracle JDBC Driver** (`ojdbc11.jar`) agregado al proyecto

---

## 5. Resultado final

* Interfaz gráfica intuitiva con botón de cálculo
* Visualización clara de **6 soluciones de la derivada**
* Persistencia confiable en **Oracle 19c** mediante Stored Procedure
* Código limpio, estructurado y alineado con prácticas académicas y empresariales.

## © 2025 Giovanny Alejandro Tapiero - chatGpt - Arduino - Calculo y matematicas discretas.  
Todos los derechos reservados .

Este software, denominado “DerivadaApp”, ha sido desarrollado con fines
académicos y demostrativos en el área de Cálculo Diferencial.
Queda prohibida la reproducción total o parcial, distribución,
modificación o uso comercial del presente software sin la autorización
expresa y por escrito del autor .

Tecnologías utilizadas:
- Java (Swing)
- Oracle Database 19c
- Cálculo Diferencial . :. .
