package gui;

import model.ChatClient;

import javax.swing.table.AbstractTableModel;

public class LoggedUsersTabelModel extends AbstractTableModel {

    ChatClient chatClient;

    public LoggedUsersTabelModel(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "UserName";
            default:
                return "0";
        }
    }

    @Override
    public int getRowCount() {
        return chatClient.getLoggedUsers().size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return chatClient.getLoggedUsers().get(rowIndex);
    }
}
