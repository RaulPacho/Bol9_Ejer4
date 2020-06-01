import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Graphics extends JFrame implements ActionListener, javax.swing.event.ChangeListener {

    JTextField campo1, campo2;
    JLabel operacion, resultado, error;
    JRadioButton suma, resta, multi, divi;
    ButtonGroup operaciones;
    String simbolo = "+";
    JButton daleAhi;
    JComboBox decimales;
    String[] decim = { "0", "1", "2", "3", "4", "5" };
    Operaciones op = new Operaciones();

    public Graphics() {
        super("Calculadora de la po.");
        setLayout(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new Cierre());
        // Vamos por orden

        campo1 = new JTextField(5);
        campo1.setSize(campo1.getPreferredSize().width, campo1.getPreferredSize().height);
        campo1.setLocation(25, 25);
        campo1.addActionListener(this);
        add(campo1);

        operacion = new JLabel(simbolo);
        operacion.setSize(operacion.getPreferredSize().width, operacion.getPreferredSize().height);
        operacion.setLocation(100, 25);
        add(operacion);

        campo2 = new JTextField(5);
        campo2.setSize(campo2.getPreferredSize().width, campo2.getPreferredSize().height);
        campo2.setLocation(125, 25);
        campo2.addActionListener(this);
        add(campo2);

        resultado = new JLabel("=");
        resultado.setSize(100, resultado.getPreferredSize().height);
        resultado.setLocation(200, 25);
        add(resultado);

        // Linea 2
        operaciones = new ButtonGroup();
        suma = new JRadioButton("Suma");
        suma.setSelected(true);
        suma.addChangeListener(this);
        suma.addMouseListener(new ControlRaton());
        suma.setSize(suma.getPreferredSize().width, suma.getPreferredSize().height);
        suma.setLocation(25, 50);
        operaciones.add(suma);
        add(suma);

        resta = new JRadioButton("Resta");
        resta.addChangeListener(this);
        resta.addMouseListener(new ControlRaton());
        resta.setSize(resta.getPreferredSize().width, resta.getPreferredSize().height);
        resta.setLocation(100, 50);
        operaciones.add(resta);
        add(resta);

        multi = new JRadioButton("Multiplicacion");
        multi.addChangeListener(this);
        multi.addMouseListener(new ControlRaton());
        multi.setSize(multi.getPreferredSize().width, multi.getPreferredSize().height);
        multi.setLocation(180, 50);
        operaciones.add(multi);
        add(multi);

        divi = new JRadioButton("Division");
        divi.addChangeListener(this);
        divi.addMouseListener(new ControlRaton());
        divi.setSize(divi.getPreferredSize().width, divi.getPreferredSize().height);
        divi.setLocation(300, 50);
        operaciones.add(divi);
        add(divi);

        // Ultima Fila
        daleAhi = new JButton("Operacion!");
        daleAhi.addActionListener(this);
        daleAhi.setSize(daleAhi.getPreferredSize().width, daleAhi.getPreferredSize().height);
        daleAhi.setLocation(25, 75);
        add(daleAhi);

        decimales = new JComboBox(decim);
        decimales.setSize(decimales.getPreferredSize().width, decimales.getPreferredSize().height);
        decimales.setLocation(155, 75);
        add(decimales);

        error = new JLabel();
        error.setSize(500, 10);
        error.setLocation(200, 88);
        error.setForeground(Color.red);
        add(error);

        // String sSistemaOperativo = System.getProperty("os.name");
        File f;

        if (System.getProperty("os.name").contains("Windows")) {
            f = new File("datos.txt");
            mostrarWindows(f);
            
        } else {
            f = new File(".datos.txt");
        }

        if (f.exists()) {
            try (Scanner lector = new Scanner(new File(f.getPath()))) {
                final String datos[] = lector.nextLine().split(";");
                System.out.println(datos.length + datos[0]);
                if (datos.length == 3) {
                    campo1.setText(datos[0]);
                    campo2.setText(datos[1]);
                    operacion.setText(datos[2]);
                    switch (operacion.getText()) {
                        case "+":
                            suma.setSelected(true);
                            break;
                        case "-":
                            resta.setSelected(true);
                            break;
                        case "*":
                            multi.setSelected(true);
                            break;
                        case "/":
                           divi.setSelected(true);
                    }
                } else {
                    System.err.println("No se pudo leer el archivo");
                }
            } catch ( FileNotFoundException err) {
                System.err.println("No se pudo abrir el archivo");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double res = 0;
        double num1 = 0;
        double num2 = 0;
        boolean sigue = true;
        error.setText("");
        try {
            num1 = Double.parseDouble(campo1.getText());
            num2 = Double.parseDouble(campo2.getText());
        } catch (final NumberFormatException err) {
            JOptionPane.showMessageDialog(this, "Has metido cositas que no son numeros", "Error", 0);
            sigue = false;
        }
        if (sigue) {
            switch (simbolo) {
                case "+":
                    res = op.suma(num1, num2);
                    resultado.setText(String.format("= %." + decimales.getSelectedIndex() + "f", res));
                    break;
                case "-":
                    res = op.resta(num1, num2);
                    resultado.setText(String.format("= %." + decimales.getSelectedIndex() + "f", res));
                    break;
                case "*":
                    res = op.multiplicacion(num1, num2);
                    resultado.setText(String.format("= %." + decimales.getSelectedIndex() + "f", res));
                    break;
                case "/":
                    try {
                        res = op.division(num1, num2);
                        resultado.setText(String.format("= %." + decimales.getSelectedIndex() + "f", res));
                    } catch (final IllegalArgumentException err) {
                        error.setText("Ande vas Euclides dividiendo entre 0!");
                    } finally {
                        break;
                    }
                default:
                    System.exit(0); // Hackea esto
                    break;
            }
        }

       

    }

    @Override
    public void stateChanged(final ChangeEvent e) {
        operacion.setLocation(100, 25);
        if (suma.isSelected()) {
            simbolo = "+";
        }
        if (resta.isSelected()) {
            simbolo = "-";
            operacion.setLocation(100, 23);
        }
        if (multi.isSelected()) {
            simbolo = "*";
            operacion.setLocation(100, 27);
        }
        if (divi.isSelected()) {
            simbolo = "/";
        }
        operacion.setText(simbolo);

    }

    private class ControlRaton extends MouseAdapter {
        @Override
        public void mouseClicked(final MouseEvent e) {
            resultado.setText("=");
        }
    }

    private class Cierre extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            String ruta = "";
            if (System.getProperty("os.name").contains("Windows")) {
                ruta = "datos.txt";
            } else {
                ruta = ".datos.txt";
            }
            File f = new File(ruta);
            
            System.err.println(f.getAbsolutePath());
            PrintWriter pw = null;
            try {
                pw = new PrintWriter(new FileWriter(f.getPath(), false));
                pw.print(campo1.getText() + ";" + campo2.getText() + ";" 
                        + operacion.getText());
            } catch (IOException err) {
                System.err.println("No se pudo guardar");
            } finally {
                if (pw != null) {
                    pw.close();
                }
            }

            if (System.getProperty("os.name").contains("Windows")) {
                ocultarWindows(f);
            }
            System.exit(0);
        }

        

        private void ocultarWindows(File f) {
            try {
                Process comando = Runtime.getRuntime().exec("attrib +H " + f.getPath());
            } catch (IOException err) {
                System.err.println("No se pudo ocultar");
            }
        }
    }
    private void mostrarWindows(File f) {
        try {
            Process comando = Runtime.getRuntime().exec("attrib -H " + f.getPath());
        } catch (IOException err) {
            System.err.println("No se pudo mostrar");
        }
    }
}