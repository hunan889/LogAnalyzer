package com.duowan.loganalyzer.ui;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * @author carlosliu on 2016/10/31.
 */
public class LogColumn extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
    JTextField mTextField;
    
    LogColumn() {
        mTextField = new JTextField();
    }
    
    @Override
    public Object getCellEditorValue() {
        return null;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
//        mTextField.setText(value);
        return mTextField;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return null;
    }
}
