package bankapp;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;

public class BankAccount implements ActionListener {
    double balance;
    double amount;
    String accountNumber;
    Frame mainWindow;
    Button checkBalButton,
            withdrawButton,
            depositButton,
            checkPrevTransactionButton,
            logOutButton,
            goBackButton,
            submitWithdrawButton,
            submitDepositButton;
    Label result;
    Label enterAmount;
    TextField amountInput;
    final NumberFormat inr = NumberFormat.getCurrencyInstance();
    SQLImport sqlImport;

    BankAccount(Frame mainWindow,String accountNumber) {
        this.mainWindow = mainWindow;
        this.accountNumber = accountNumber;
        connectDatabase();
        mainMenu();
    }

    private void connectDatabase() {
        try {
            sqlImport = new SQLImport(accountNumber);
            setBalance();
        }
        catch (Exception e) {
            result.setText("Error: " + e.getMessage());
        }
    }
    private void mainMenu() {
        mainWindow.setSize(400,400);
        mainWindow.setLayout(new GridLayout(7,1,0,10));
        Label welcomeMessage = new Label("Welcome to MKSG Bank");
        welcomeMessage.setAlignment(Label.CENTER);
        mainWindow.add(welcomeMessage);

        checkBalButton = new Button("Check Balance");
        checkBalButton.addActionListener(this);
        mainWindow.add(checkBalButton);

        withdrawButton = new Button("Withdraw");
        withdrawButton.addActionListener(this);
        mainWindow.add(withdrawButton);

        depositButton =  new Button("Deposit");
        depositButton.addActionListener(this);
        mainWindow.add(depositButton);

        checkPrevTransactionButton = new Button("Check Previous Transaction");
        checkPrevTransactionButton.addActionListener(this);
        mainWindow.add(checkPrevTransactionButton);

        logOutButton = new Button("Log Out");
        logOutButton.addActionListener(this);
        mainWindow.add(logOutButton);

        result = new Label("");
        result.setAlignment(Label.CENTER);
        mainWindow.add(result);
        try {
            mainWindow.setTitle(new SQLImport(accountNumber).getCustomerName() + " " + accountNumber);
        }
        catch (Exception e) {
            result.setText("Error: " + e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == checkBalButton)
            checkBal();
        else if (e.getSource() == withdrawButton)
            withdrawWindow();
        else if (e.getSource() == depositButton)
            depositWindow();
        else if (e.getSource() == checkPrevTransactionButton)
            getPreviousTransactionMessage();
        else if (e.getSource() == goBackButton) {
            mainWindow.removeAll();
            mainMenu();
        }
        else if (e.getSource() == submitWithdrawButton || e.getSource() == submitDepositButton) {
            result.setText("Processing . . .");
            if (amountInput.getText().equals("")) {
                result.setText("Enter amount");
                return;
            }
            amount = Double.parseDouble(amountInput.getText());
            if (e.getSource() == submitWithdrawButton)
                withdraw(amount);
            else
                deposit(amount);
        }
        else if (e.getSource() == logOutButton) {
            mainWindow.removeAll();
            new LogIn(mainWindow);
        }
    }

    private void setBalance() {
        try {
            balance = sqlImport.getBalance();
        } catch (Exception e) {
            result.setText("Error: Please try again" + e.getMessage());
        }
    }

    private void checkBal() {
        result.setText("Balance: " + inr.format(balance));
    }

    private void withdrawWindow() {
        mainWindow.removeAll();
        mainWindow.setLayout(new GridLayout(3,2,10,10));
        mainWindow.setSize(400,150);
        enterAmount = new Label("Enter Amount to Withdraw:");
        mainWindow.add(enterAmount);

        amountInput = new TextField();
        amountValidation(amountInput);
        mainWindow.add(amountInput);

        goBackButton = new Button("Go Back");
        goBackButton.addActionListener(this);
        mainWindow.add(goBackButton);

        submitWithdrawButton = new Button("Submit");
        submitWithdrawButton.addActionListener(this);
        mainWindow.add(submitWithdrawButton);

        result = new Label();
        result.setAlignment(Label.CENTER);
        mainWindow.add(result);
    }

    private void depositWindow() {
        mainWindow.removeAll();
        mainWindow.setLayout(new GridLayout(3,2,10,10));
        mainWindow.setSize(400,150);
        enterAmount = new Label("Enter Amount to Deposit:");
        mainWindow.add(enterAmount);

        amountInput = new TextField();
        amountValidation(amountInput);
        mainWindow.add(amountInput);

        goBackButton = new Button("Go Back");
        goBackButton.addActionListener(this);
        mainWindow.add(goBackButton);

        submitDepositButton = new Button("Submit");
        submitDepositButton.addActionListener(this);
        mainWindow.add(submitDepositButton);

        result = new Label();
        result.setAlignment(Label.CENTER);
        mainWindow.add(result);
    }

    private void amountValidation(TextField amtInput) {
        amtInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if ( !(e.getKeyChar() >= '0' && e.getKeyChar() <= '9')
                        && e.getKeyCode() != KeyEvent.VK_BACK_SPACE
                        && e.getKeyChar() != '.')
                    e.consume();
            }
        });
    }

    private void withdraw(double amount) {
        if (amount == 0) {
            result.setText("Enter a valid amount");
        }
        else if (amount > balance)
            result.setText("Insufficient Balance");
        else {
            balance -= amount;
            try {
                sqlImport.setBalance(balance);
                result.setText(inr.format(amount) + " Withdrawn");
                sqlImport.setPreviousTransaction(-amount);
            }
            catch (Exception e) {
                result.setText("Error: Please Try Again " + e.getMessage());
            }
        }
    }

    private void deposit(double amount) {
        if (amount == 0) {
            result.setText("Enter a valid amount");
            return;
        }
        balance += amount;
        try {
            sqlImport.setBalance(balance);
            result.setText(inr.format(amount) + " Deposited");
            sqlImport.setPreviousTransaction(amount);
        }
        catch (Exception e) {
            result.setText("Error: Please Try Again " + e.getMessage());
        }
    }

    private void getPreviousTransactionMessage() {
        result.setText("Processing . . .");
        try {
            amount = new SQLImport(accountNumber).getPreviousTransaction();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (amount == 0)
            result.setText("No Previous Transaction");
        else if (amount < 0)
            result.setText(inr.format(Math.abs(amount)) + " was withdrawn");
        else
            result.setText(inr.format(Math.abs(amount)) + " was deposited");
    }
}
