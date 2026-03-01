import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankGUI extends JFrame {

    BankAccount account;

    JTextField txtAccNumber;
    JTextField txtAccName;
    JTextField txtAmount;

    JTextArea txtTransactions;

    JButton btnCreateAccount;
    JButton btnDeposit;
    JButton btnWithdraw;
    JButton btnCheckBalance;
    JButton btnViewTransactions;

    public BankGUI() {
        setTitle("Bank Account System");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        setupGUI();
    }

    private void setupGUI() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4, 2, 5, 5));

        topPanel.add(new JLabel("Account Num:"));
        txtAccNumber = new JTextField();
        topPanel.add(txtAccNumber);

        topPanel.add(new JLabel("Name:"));
        txtAccName = new JTextField();
        topPanel.add(txtAccName);

        topPanel.add(new JLabel("Amount:"));
        txtAmount = new JTextField();
        topPanel.add(txtAmount);

        btnCreateAccount = new JButton("Create New Account");
        topPanel.add(new JLabel(""));
        topPanel.add(btnCreateAccount);

        add(topPanel, BorderLayout.NORTH);

        JPanel midPanel = new JPanel();

        btnDeposit = new JButton("Deposit");
        btnWithdraw = new JButton("Withdraw");
        btnCheckBalance = new JButton("Balance");
        btnViewTransactions = new JButton("History");

        btnDeposit.setEnabled(false);
        btnWithdraw.setEnabled(false);
        btnCheckBalance.setEnabled(false);
        btnViewTransactions.setEnabled(false);

        midPanel.add(btnDeposit);
        midPanel.add(btnWithdraw);
        midPanel.add(btnCheckBalance);
        midPanel.add(btnViewTransactions);

        add(midPanel, BorderLayout.CENTER);

        txtTransactions = new JTextArea(12, 35);
        txtTransactions.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtTransactions);

        JPanel botPanel = new JPanel();
        botPanel.add(scroll);

        add(botPanel, BorderLayout.SOUTH);

        btnCreateAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String num = txtAccNumber.getText();
                String name = txtAccName.getText();

                if (num.equals("") || name.equals("")) {
                    JOptionPane.showMessageDialog(null, "Enter all details please!");
                    return;
                }

                account = new BankAccount(num, name);

                btnDeposit.setEnabled(true);
                btnWithdraw.setEnabled(true);
                btnCheckBalance.setEnabled(true);
                btnViewTransactions.setEnabled(true);

                txtAccNumber.setEditable(false);
                txtAccName.setEditable(false);
                btnCreateAccount.setEnabled(false);

                JOptionPane.showMessageDialog(null, "Account created!");
                btnViewTransactions.doClick();
            }
        });

        btnDeposit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double amt = Double.parseDouble(txtAmount.getText());
                    account.deposit(amt);
                    JOptionPane.showMessageDialog(null, "Deposit successful!");
                    txtAmount.setText("");
                    btnViewTransactions.doClick();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Enter a real number!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        btnWithdraw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double amt = Double.parseDouble(txtAmount.getText());
                    account.withdraw(amt);
                    JOptionPane.showMessageDialog(null, "Withdrawal successful!");
                    txtAmount.setText("");
                    btnViewTransactions.doClick();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Enter a real number!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        btnCheckBalance.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Balance is: $" + account.getBalance());
            }
        });

        btnViewTransactions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtTransactions.setText("");
                txtTransactions.append("History for: " + account.getAccountHolderName() + "\n");
                txtTransactions.append("Current Bal: $" + account.getBalance() + "\n");

                for (int i = 0; i < account.getTransactionHistory().size(); i++) {
                    Transaction t = account.getTransactionHistory().get(i);
                    txtTransactions.append(t.getType() + " - $" + t.getAmount() + "\n");
                }
            }
        });
    }

    public static void main(String[] args) {
        BankGUI gui = new BankGUI();
        gui.setVisible(true);
    }
}
