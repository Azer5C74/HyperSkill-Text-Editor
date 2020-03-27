package editor;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class TextEditor extends JFrame implements SaveLoadListener {
    JTextArea textArea;

    public TextEditor() {
        setTitle("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        textArea = new JTextArea(200, 200);
        textArea.setName("TextArea");
        JScrollPane scrollableTextArea = new JScrollPane(textArea);
        scrollableTextArea.setName("ScrollPane");
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        BorderFactory.setMargin(scrollableTextArea, 10, 10, 10, 10);
        add(scrollableTextArea, BorderLayout.CENTER);
        SaveLoadControl fileSelection = new SaveLoadControl();
        fileSelection.addSaveLoadListener(this);
        BorderFactory.setMargin(fileSelection, 5, 0, 0, 5);
        add(fileSelection, BorderLayout.NORTH);
        setVisible(true);

    }

    @Override
    public void onClick(String fileName, Actions action) throws IOException {
        File file = new File(fileName);
        switch (action) {
            case LOAD_FILE:
                if (file.exists()) loadTextFromFile(file);
                else textArea.setText("");
                break;
            case SAVE_FILE:
                saveTextToFile(file);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }
    }

    void loadTextFromFile(File file) throws IOException {
        Path path = Paths.get(file.getPath());
        String text = new String(Files.readAllBytes(path));
        textArea.setText(text);
    }

    void saveTextToFile(File file) {

        String text = textArea.getText();
        Path path = Paths.get(file.getPath());
        try {
            Files.write(path, text.getBytes());
            JOptionPane.showMessageDialog(null,"Saved");        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
