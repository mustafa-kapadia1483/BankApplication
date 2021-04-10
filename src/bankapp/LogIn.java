package bankapp;

import java.awt.*;
import java.awt.event.*;

public class LogIn implements ActionListener {
    String accountNumber, password;
    Frame mainWindow;
    TextField accountNumberField, passwordField;
    Button logInButton, clearButton;
    Label message;

    LogIn() {
        mainWindow = new Frame();
        logInMenu(mainWindow);
    }

    LogIn(Frame mainWindow) {
        logInMenu(mainWindow);
    }

    public void logInMenu(Frame mainWindow) {
        this.mainWindow = mainWindow;
        mainWindow.setTitle("Log In");

        mainWindow.setSize(600, 200);
        mainWindow.setResizable(false);
        mainWindow.setLayout(new GridLayout(4, 2, 10, 10));

        mainWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainWindow.dispose();
            }
        });

        Label accountNumberLabel = new Label("Account Number:");
        accountNumberLabel.setAlignment(Label.RIGHT);
        mainWindow.add(accountNumberLabel);

        accountNumberField = new TextField();
        accountNumberField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!((e.getKeyChar() >= 'A' && e.getKeyChar() <= 'Z')
                        || e.getKeyChar() > 'a' && e.getKeyChar() < 'z'
                        || e.getKeyChar() >= '0' && e.getKeyChar() <= '9'))
                    e.consume();
            }
        });
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

        message = new Label("Please Enter your Account Number and Password");
        mainWindow.add(message);

        mainWindow.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logInButton) {
            authenticateLogIn();
        } else if (e.getSource() == clearButton) {
            accountNumberField.setText("");
            passwordField.setText("");
        }
    }

    private void authenticateLogIn() {
        accountNumber = accountNumberField.getText();
        password = passwordField.getText();

        if (accountNumber.length() == 10) {
            message.setText("Validating Details . . . . ");

            try {
                var sqlImport = new SQLImport(accountNumber);
                if (sqlImport.authenticate(password)) {
                    mainWindow.removeAll();
                    new BankAccount(mainWindow, accountNumber);
                } else
                    message.setText("Incorrect Account Number or Incorrect Password");
            } catch (Exception e) {
                message.setText("Error: " + e.getMessage());
            }
        } else if (accountNumber.length() == 0)
            message.setText("PLease Enter the Account Number");
        else if (password.length() == 0)
            message.setText("Please Enter the password");
        else
            message.setText("Enter a valid Account Number");
    }
}
