package JavaApp;
import Customer.CustomerUI;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuUI {
    public static void main(String args[]) {
        JFrame menu = new JFrame("Menu");
        menu.setSize(600, 600);
        menu.setLayout(new GridLayout(3, 1));

        JButton customerBt = new JButton("Customer");
        menu.add(customerBt);
        customerBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                CustomerUI.showMenu();
            }
        });

        JButton productBt = new JButton("Product");
        menu.add(productBt);

        JButton invoiceBt = new JButton("Invoice");
        menu.add(invoiceBt);

        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setResizable(true);
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
    }
}