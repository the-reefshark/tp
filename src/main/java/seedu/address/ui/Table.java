package seedu.address.ui;

import javafx.beans.property.SimpleStringProperty;

public class Table {

    private SimpleStringProperty action;
    private SimpleStringProperty format;

    /**
     * Creates an entry of the table
     * @param action String under action column of Table
     * @param format String under format column of Table
     */
    public Table(String action, String format) {
        this.action = new SimpleStringProperty(action);
        this.format = new SimpleStringProperty(format);
    }

    public String getAction() {
        return action.get();
    }

    public void setAction(String a) {
        this.action.set(a);
    }

    public String getFormat() {
        return format.get();
    }

    public void setFormat(String f) {
        this.format.set(f);
    }
}
