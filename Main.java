package bankapp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;

class LogIn implements ActionListener {
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

class BankAccount implements ActionListener{
    double balance = Math.random() * 100000;
    double amt, prevTransaction;
    Frame mainWindow;
    Button checkBalBut, withdrawBut, depositBut, checkPrevTransactionBut,goBackBut, submitWithdrawBut, submitDepositBut;
    Label result, enterAmt;
    TextField amtInput;
    NumberFormat inr = NumberFormat.getCurrencyInstance();

    BankAccount(Frame mainWindow) {
        this.mainWindow = mainWindow;
        mainMenu();
    }
    void mainMenu() {
        mainWindow.setSize(400,400);
        mainWindow.setLayout(new GridLayout(6,1,0,10));
        Label welcomeMessage = new Label("Welcome to ACACA Bank");
        welcomeMessage.setAlignment(Label.CENTER);
        mainWindow.add(welcomeMessage);

        checkBalBut = new Button("Check Balance");
        checkBalBut.addActionListener(this);
        mainWindow.add(checkBalBut);

        withdrawBut = new Button("Withdraw");
        withdrawBut.addActionListener(this);
        mainWindow.add(withdrawBut);

        depositBut =  new Button("Deposit");
        depositBut.addActionListener(this);
        mainWindow.add(depositBut);

        checkPrevTransactionBut = new Button("Check Previous Transaction");
        checkPrevTransactionBut.addActionListener(this);
        mainWindow.add(checkPrevTransactionBut);

        result = new Label("");
        result.setAlignment(Label.CENTER);
        mainWindow.add(result);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == checkBalBut)
            checkBal();
        else if (e.getSource() == withdrawBut)
            withdraw();
        else if (e.getSource() == depositBut)
            deposit();
        else if (e.getSource() == checkPrevTransactionBut)
            prevTransactionFunc();
        else if (e.getSource() == goBackBut) {
            mainWindow.removeAll();
            mainMenu();
        }
        else if (e.getSource() == submitWithdrawBut) {
            amt = Double.parseDouble(amtInput.getText());
            if (amt > balance)
                result.setText("Insufficient Balance");
            else {
                balance -= amt;
                result.setText(inr.format(amt) + " Withdrawn");
                prevTransaction = -amt;
            }
        }
        else if (e.getSource() == submitDepositBut) {
            amt = Double.parseDouble(amtInput.getText());
            balance += amt;
            prevTransaction = amt;
            result.setText(inr.format(amt) + " Deposited");
        }
    }

    void checkBal() {
        result.setText("Balance: " + inr.format(balance));
    }

    void withdraw() {
        mainWindow.removeAll();
        mainWindow.setLayout(new GridLayout(3,2,10,10));
        mainWindow.setSize(400,150);
        enterAmt = new Label("Enter Amount to Withdraw:");
        mainWindow.add(enterAmt);

        amtInput = new TextField();
        mainWindow.add(amtInput);

        goBackBut = new Button("Go Back");
        goBackBut.addActionListener(this);
        mainWindow.add(goBackBut);

        submitWithdrawBut = new Button("Submit");
        submitWithdrawBut.addActionListener(this);
        mainWindow.add(submitWithdrawBut);

        result = new Label();
        mainWindow.add(result);
    }

    void deposit() {
        mainWindow.removeAll();
        mainWindow.setLayout(new GridLayout(3,2,10,10));
        mainWindow.setSize(400,150);
        enterAmt = new Label("Enter Amount to Deposit:");
        mainWindow.add(enterAmt);

        amtInput = new TextField();
        mainWindow.add(amtInput);

        goBackBut = new Button("Go Back");
        goBackBut.addActionListener(this);
        mainWindow.add(goBackBut);

        submitDepositBut = new Button("Submit");
        submitDepositBut.addActionListener(this);
        mainWindow.add(submitDepositBut);

        result = new Label();
        mainWindow.add(result);
    }

    void prevTransactionFunc() {
        if (amt == 0)
            result.setText("No Previous Transaction");
        else if (amt > 0)
            result.setText(inr.format(Math.abs(amt)) + " was withdrawn");
        else
            result.setText(inr.format(Math.abs(amt)) + " was deposited");
    }
}

public class Main {

    public static void main(String[] args) {
	new LogIn();
    }
}
