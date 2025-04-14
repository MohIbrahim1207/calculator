import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorGUI extends JFrame implements ActionListener {
    private JTextField display;
    private double firstNumber = 0;
    private String currentOperator = "";
    private boolean newNumber = true;

    public CalculatorGUI() {
        setTitle("Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(this);
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            buttonPanel.add(button);
        }

        JButton clearButton = new JButton("C");
        clearButton.addActionListener(e -> {
            display.setText("");
            firstNumber = 0;
            currentOperator = "";
            newNumber = true;
        });
        clearButton.setFont(new Font("Arial", Font.PLAIN, 18));

        add(buttonPanel, BorderLayout.CENTER);
        add(clearButton, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if (command.matches("[0-9.]")) {
            if (newNumber) {
                display.setText("");
                newNumber = false;
            }
            display.setText(display.getText() + command);
        }
        else if (command.matches("[+\\-*/]")) {
            firstNumber = Double.parseDouble(display.getText());
            currentOperator = command;
            newNumber = true;
        }
        else if (command.equals("=")) {
            if (!currentOperator.isEmpty()) {
                double secondNumber = Double.parseDouble(display.getText());
                double result = calculate(firstNumber, secondNumber, currentOperator);
                display.setText(String.valueOf(result));
                currentOperator = "";
                newNumber = true;
            }
        }
    }

    private double calculate(double a, double b, String operator) {
        switch (operator) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) {
                    JOptionPane.showMessageDialog(this, "Cannot divide by zero!", "Error", JOptionPane.ERROR_MESSAGE);
                    return 0;
                }
                return a / b;
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorGUI calculator = new CalculatorGUI();
            calculator.setVisible(true);
        });
    }
}