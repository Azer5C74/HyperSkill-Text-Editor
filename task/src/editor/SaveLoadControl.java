package editor;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;



interface SaveLoadListener {
    enum Actions {
        SAVE_FILE,
        LOAD_FILE
    }

    void onClick(String fileName, final Actions action) throws IOException;
}



class SaveLoadControl extends JPanel {
    ArrayList<SaveLoadListener> buttonListeners = new ArrayList<>();
    private JTextField fileNameField;

    SaveLoadControl() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        fileNameField = new JTextField();
        fileNameField.setName("FilenameField");
        fileNameField.setColumns(30);
        JButton saveButton = new JButton();
        saveButton.setName("SaveButton");
        saveButton.setText("Save");
        saveButton.addActionListener(actionEvent -> {
            try {
                saveFileButtonAction();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        JButton loadButton = new JButton();
        loadButton.setName("LoadButton");
        loadButton.setText("Load");
        loadButton.addActionListener(actionEvent -> {
            try {
                loadFileButtonAction();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.add(fileNameField);
        this.add(saveButton);
        this.add(loadButton);
    }

    void addSaveLoadListener(SaveLoadListener listener) {
        buttonListeners.add(listener);
    }

    private void loadFileButtonAction() throws IOException {
        String fileName = fileNameField.getText();
        if (fileName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "File name is empty!");
        } else {
            for (SaveLoadListener listener : buttonListeners) {
                listener.onClick(fileName, SaveLoadListener.Actions.LOAD_FILE);
            }
        }
    }

    private void saveFileButtonAction() throws IOException {
        String fileName = fileNameField.getText();
        if (fileName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "File name is empty!");
        } else
            for (SaveLoadListener listener : buttonListeners) {
                listener.onClick(fileName, SaveLoadListener.Actions.SAVE_FILE);
            }

    }
}