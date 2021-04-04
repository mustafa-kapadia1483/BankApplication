package bankapp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LogIn implements ActionListener {
    String cusName, accountNumber, password;
    Frame mainWindow;
    TextField cusNameField,accountNumberField,passwordField;
    Button logInButton, clearButton;

    LogIn() {
        mainWindow = new Frame("Log In");

        mainWindow.setSize(600, 200);
        mainWindow.setLayout(new GridLayout(4, 2, 10, 10));

        mainWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainWindow.dispose();
            }
        });

        Label cusNameLabel = new Label("Name: ");
        cusNameLabel.setAlignment(Label.RIGHT);
        mainWindow.add(cusNameLabel);

        cusNameField = new TextField();
        mainWindow.add(cusNameField);

        Label accountNumberLabel = new Label("Account Number:");
        accountNumberLabel.setAlignment(Label.RIGHT);
        mainWindow.add(accountNumberLabel);

        accountNumberField = new TextField();
        mainWindow.add(accountNumberField);

        Label passwordLabel = new Label("Password: ");
        passwordLabel.setAlignment(Label.RIGHT);
        mainWindow.add(passwordLabel);

        passwordField = new TextField();
        passwordField.setEchoChar('*');
        mainWindow.add(passwordField);

        logInButton = new Button("Log In");
        logInButton.addActionListener(this);
        mainWindow.add(logInButton);

        clearButton = new Button("Clear");
        clearButton.addActionListener(this);
        mainWindow.add(clearButton);

        mainWindow.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logInButton) {
            cusName = cusNameField.getText();
            accountNumber = accountNumberField.getText();
            password = passwordField.getText();
            mainWindow.removeAll();
            mainWindow.setTitle(cusName + " " + accountNumber);
            new BankAccount(mainWindow);
        }

        else if (e.getSource() == clearButton) {
            cusNameField.setText("");
            accountNumberField.setText("");
            passwordField.setText("");
        }
    }
}
