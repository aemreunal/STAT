package stat.graphics;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Burcu Basak SARIKAYA
 * S000855
 * burcu.sarikaya@ozu.edu.tr
 */

public class SalePage extends Page {

    private JTextField textfieldCustomerName;
    private JTable productTable;
    private JTextField textfieldTotalPrice;
    private JComboBox productBox;

    public SalePage() {
        initPageDesign();
        initBackButton();
        initCustomerNameField();
        initSelectProductField();
        initProductTable();
        initTotalPrice();
        initConfirmButton();
    }

    private void initPageDesign() {
        setLayout(null);
        setBackground(new Color(204, 204, 0));
        setSize(500, 450);
    }

    private void initBackButton() {
        JButton buttonBack = new JButton("BACK");
        buttonBack.setForeground(new Color(204, 51, 51));
        buttonBack.setBackground(new Color(245, 245, 245));
        buttonBack.setFont(new Font("Tahoma", Font.BOLD, 14));
        buttonBack.setBounds(29, 28, 437, 30);
        buttonBack.addActionListener(getBackAction());
        add(buttonBack);
    }

    private ActionListener getBackAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ApplicationWindow appWindow = getApplicationWindow();
                appWindow.setCurrentPage(new MenuPage());
            }
        };
    }

    private void initCustomerNameField() {
        JLabel labelCustomerName = new JLabel("Customer Name : ");
        labelCustomerName.setFont(new Font("Tahoma", Font.BOLD, 13));
        labelCustomerName.setBounds(29, 74, 125, 24);

        textfieldCustomerName = new JTextField();
        textfieldCustomerName.setBounds(164, 77, 201, 20);
        textfieldCustomerName.setColumns(10);

        add(labelCustomerName);
        add(textfieldCustomerName);
    }

    private void initSelectProductField() {
        JLabel labelSelectProduct = new JLabel("Select Product :");
        labelSelectProduct.setFont(new Font("Tahoma", Font.BOLD, 13));
        labelSelectProduct.setBounds(29, 109, 125, 24);

        productBox = new JComboBox(new String[0]);
        productBox.setBounds(164, 111, 201, 22);
        fillProducts();

        JButton buttonAdd = new JButton("ADD");
        buttonAdd.setForeground(new Color(94, 52, 231));
        buttonAdd.setBackground(new Color(245, 245, 245));
        buttonAdd.setFont(new Font("Tahoma", Font.BOLD, 11));
        buttonAdd.setBounds(375, 111, 91, 23);
        buttonAdd.addActionListener(getAddAction());

        add(labelSelectProduct);
        add(productBox);
        add(buttonAdd);
    }

    private void fillProducts() {
        // TODO: implement
    }

    private ActionListener getAddAction() {
        // TODO: implement
        return null;
    }

    private void initProductTable() {
        productTable = new JTable(getTableModel());
        productTable.setBounds(29, 155, 437, 145);
        productTable.addMouseListener(getMouseAdapter());
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBounds(29, 155, 437, 145);
        add(scrollPane);
    }

    private TableModel getTableModel() {
        String[] columnNames = {"Product", "Amount", "Delete"};
        // TODO: Remove static data
        Object[][] data = {{"Elma", 5, "Remove"},{"Armut", 5, "Remove"},{"Avokado", 5, "Remove"} };
        return new DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column){
                return (column == 1);
            }
            public void setValueAt(Object aValue, int row, int column) {
                if(isCellEditable(row, column)) {
                    String regex = "[0-9]+";
                    String value = (String) aValue;
                    if (value.matches(regex))
                        super.setValueAt(aValue, row, column);
                }
            }
        };
    }

    private MouseAdapter getMouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable table = (JTable)e.getSource();
                int row = table.getSelectedRow();
                int column = table.getSelectedColumn();
                if(column == 2) {
                    ((DefaultTableModel)table.getModel()).removeRow(row);
                }
                // TODO: ADD TOTAL UPDATE
            }
        };
    }

    private void initTotalPrice() {
        JLabel labelTotal = new JLabel("Total :");
        labelTotal.setFont(new Font("Tahoma", Font.BOLD, 14));
        labelTotal.setBounds(29, 327, 49, 24);

        textfieldTotalPrice = new JTextField();
        textfieldTotalPrice.setBounds(78, 331, 125, 20);
        textfieldTotalPrice.setColumns(10);
        textfieldTotalPrice.setEditable(false);

        add(labelTotal);
        add(textfieldTotalPrice);
    }

    private void initConfirmButton() {
        JButton buttonConfirm = new JButton("CONFIRM");
        buttonConfirm.setForeground(new Color(0, 153, 51));
        buttonConfirm.setBackground(new Color(245, 245, 245));
        buttonConfirm.setFont(new Font("Tahoma", Font.BOLD, 14));
        buttonConfirm.setBounds(29, 362, 437, 30);
        buttonConfirm.addActionListener(getConfirmAction());
        add(buttonConfirm);
    }

    private ActionListener getConfirmAction() {
        // TODO: implement
        return null;
    }

}