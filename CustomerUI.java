package Customer;

import Connection.DataConnection;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

class DetailCustomer extends JFrame {
    private JPanel panelId, panelName, panelPhone, panelBt;
    private JTextArea id, name, phone;
    private JLabel lbId, lbName, lbPhone;
    private JButton updateBt, closeBt;

    public void initActionClose() {
        closeBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                setVisible(false);
            }
        });
    }

    public void initActionUpdate(PanelCustomer panelCustomer) {
        updateBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (id.getText().compareTo("") == 0) {
                    Customer obj = new Customer();
                    Random rand = new Random();
                    String tmp = "KH" + rand.nextInt(10000);
                    obj.setId(tmp);
                    obj.setName(name.getText());
                    obj.setPhone(phone.getText());
                    CustomerBusiness.addCustomer(obj);
                    panelCustomer.loadData();
                }

                else {
                    Customer obj = new Customer();
                    obj.setId(id.getText());
                    obj.setName(name.getText());
                    obj.setPhone(phone.getText());
                    CustomerBusiness.updateCustomer(obj);
                    panelCustomer.loadData();
                }
                
                setVisible(false);
            }
        });
    }

    public DetailCustomer(PanelCustomer panelCustomer) {
        setTitle("Customer information");
        setSize(600, 300);
        setLayout(new GridLayout(4, 1));

        panelId = new JPanel();
        panelId.setLayout(new FlowLayout(FlowLayout.LEFT));
        lbId = new JLabel("ID");
        id = new JTextArea(2, 35);
        panelId.add(lbId);
        panelId.add(id);

        panelName = new JPanel();
        panelName.setLayout(new FlowLayout(FlowLayout.LEFT));
        lbName = new JLabel("Name");
        name = new JTextArea(2, 35);
        panelName.add(lbName);
        panelName.add(name);

        panelPhone = new JPanel();
        panelPhone.setLayout(new FlowLayout(FlowLayout.LEFT));
        lbPhone = new JLabel("Phone");
        phone = new JTextArea(2, 35);
        panelPhone.add(lbPhone);
        panelPhone.add(phone);

        panelBt = new JPanel();
        panelBt.setLayout(new FlowLayout(FlowLayout.RIGHT));
        updateBt = new JButton("Update");
        closeBt = new JButton("Close");
        panelBt.add(updateBt);
        panelBt.add(closeBt);

        add(panelId);
        add(panelName);
        add(panelPhone);
        add(panelBt);

        initActionClose();
        initActionUpdate(panelCustomer);
        setLocationRelativeTo(null);
    }

    public JTextArea getTxtId() {
        return id;
    }

    public JTextArea getTxtName() {
        return name;
    }

    public JTextArea getTxtPhone() {
        return phone;
    }

    public JButton getUpdateBt() {
        return updateBt;
    }

    public JButton getCloseBt() {
        return closeBt;
    }
}

class SearchPanel extends JPanel {
    private JButton searchBt;
    private JTextArea text;
    private JLabel label;

    public void initActionSearch(PanelCustomer panelCustomer) {
        searchBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                panelCustomer.getDtm().setRowCount(0);
                String name = text.getText();

                Connection conn = null;

                try {
                    conn = DataConnection.setConnect();
                    
                    String sql = "select * from customer where name = ?";
                    PreparedStatement psm = conn.prepareStatement(sql);
                    psm.setString(1, name);

                    ResultSet rs = psm.executeQuery();
                    while (rs.next()) {
                        Object[] row = {rs.getString("idCustomer"), rs.getString("name"), rs.getString("phone")};
                        panelCustomer.getDtm().addRow(row);
                    }

                } catch(SQLException e) {
                    Logger.getLogger(CustomerBusiness.class.getName()).log(Level.SEVERE, null, e);
                } finally {
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                    } catch(SQLException e) {
                        System.out.println("Cannot close connection");
                    }
                }
            }
        });
    }

    public SearchPanel(PanelCustomer panelCustomer) {
        setBorder(new TitledBorder("Enter search information"));
        setLayout(new FlowLayout(FlowLayout.LEFT));

        text = new JTextArea(1, 25);
        label = new JLabel("Key word");
        searchBt = new JButton("Search");

        add(label);
        add(text);
        add(searchBt);

        initActionSearch(panelCustomer);

        setVisible(true);
    }

    public JButton getSearchBt() {
        return searchBt;
    }

    public JTextArea getText() {
        return text;
    }
}

class PanelCustomer extends JPanel {
    private JTable tableCustomer;
    private DefaultTableModel dtm;

    public PanelCustomer() {
        setLayout(new GridLayout(1, 1));

        tableCustomer = new JTable();
        String[] nameColumns = { "idCustomer", "name", "phone" };
        dtm = new DefaultTableModel(nameColumns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        loadData();

        tableCustomer.setModel(dtm);
        tableCustomer.setRowSelectionAllowed(true);
        add(new JScrollPane(tableCustomer));
        
        setVisible(true);
    }

    public void loadData() {
        dtm.setRowCount(0);

        Connection conn = null;
        try {
            conn = DataConnection.setConnect();

            String sql = "select * from customer";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                Object[] row = {rs.getString("idCustomer"), rs.getString("name"), rs.getString("phone")};
                dtm.addRow(row);
            }
        } catch(SQLException e) {
            System.out.println("Error in showing table customer");
        }
    }

    public JTable getTableCustomer() {
        return tableCustomer;
    }

    public DefaultTableModel getDtm() {
        return dtm;
    }
}

class PanelButton extends JPanel {
    private JButton addBt, delBt, editBt;

    public PanelButton(PanelCustomer panelCustomer) {
        setLayout(new FlowLayout(FlowLayout.RIGHT));

        addBt = new JButton("Add");
        delBt = new JButton("Delete");
        editBt = new JButton("Edit");

        add(addBt);
        add(delBt);
        add(editBt);

        addBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                DetailCustomer formEdit = new DetailCustomer(panelCustomer);
                formEdit.getTxtId().setEnabled(false);
                formEdit.setVisible(true);
            }
        });

        delBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int row = panelCustomer.getTableCustomer().getSelectedRow();
                String id = String.valueOf(panelCustomer.getTableCustomer().getValueAt(row, 0));
                CustomerBusiness.deleteCustomer(id); 
                panelCustomer.loadData();
            }
        });

        editBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                DetailCustomer formEdit = new DetailCustomer(panelCustomer);
                int row = panelCustomer.getTableCustomer().getSelectedRow();
                String id = String.valueOf(panelCustomer.getTableCustomer().getValueAt(row, 0));
                Customer obj = CustomerBusiness.showCustomerDetail(id);

                formEdit.getTxtId().setEnabled(false);
                formEdit.getTxtId().setText(obj.getId());
                formEdit.getTxtName().setText(obj.getName());
                formEdit.getTxtPhone().setText(obj.getPhone());
                formEdit.setVisible(true);
            }
        });

        setVisible(true);
    }

    public JButton getAddBt() {
        return addBt;
    }

    public JButton getDelBt() {
        return delBt;
    }

    public JButton getEditBt() {
        return editBt;
    }
}

public class CustomerUI {

    public static void showMenu() {
        JFrame menuCustomer = new JFrame("Customer");
        menuCustomer.setSize(600, 600);
        menuCustomer.setLayout(new BorderLayout());

        PanelCustomer panelCustomer = new PanelCustomer();
        SearchPanel searchPanel = new SearchPanel(panelCustomer);
        panelCustomer.loadData();
        PanelButton panelButton = new PanelButton(panelCustomer);

        menuCustomer.add(searchPanel, BorderLayout.NORTH);
        menuCustomer.add(panelCustomer, BorderLayout.CENTER);
        menuCustomer.add(panelButton, BorderLayout.SOUTH);

        menuCustomer.setResizable(true);
        menuCustomer.setLocationRelativeTo(null);
        menuCustomer.setVisible(true);
    }
}