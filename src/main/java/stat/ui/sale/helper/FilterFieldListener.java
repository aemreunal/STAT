package stat.ui.sale.helper;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class FilterFieldListener implements DocumentListener {

    private TableRowSorter tableSorter;
    private int columnIndex;

    public FilterFieldListener(TableRowSorter tableSorter, int columnIndex) {
        this.tableSorter = tableSorter;
        this.columnIndex = columnIndex;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        filterTable(getText(e));
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        filterTable(getText(e));
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        filterTable(getText(e));
    }

    private String getText(DocumentEvent event) {
        try {
            Document document = event.getDocument();
            return document.getText(0, document.getLength());
        } catch (BadLocationException e1) {
            System.err.println("Unable too get text, will not filter.");
            return "";
        }
    }

    private void filterTable(String filterText) {
        tableSorter.setRowFilter(RowFilter.regexFilter(filterText, columnIndex));
    }

}
