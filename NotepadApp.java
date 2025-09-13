import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;

public class NotepadApp extends JFrame {

    private JTextArea textArea;
    private JFileChooser fileChooser;

    public NotepadApp() {
        // *******Frame setup
        setTitle("Simple Notepad");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ******Text Area
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane);

        // ******File Chooser
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));

        // ******Menu Bar
        JMenuBar menuBar = new JMenuBar();

        // ******File Menu
        JMenu fileMenu = new JMenu("File");

        JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(e -> textArea.setText(""));

        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(e -> openFile());

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> saveFile());

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        //  ******Edit Menu
        JMenu editMenu = new JMenu("Edit");

        JMenuItem cutItem = new JMenuItem("Cut");
        cutItem.addActionListener(e -> textArea.cut());

        JMenuItem copyItem = new JMenuItem("Copy");
        copyItem.addActionListener(e -> textArea.copy());

        JMenuItem pasteItem = new JMenuItem("Paste");
        pasteItem.addActionListener(e -> textArea.paste());

        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);

        // *******Format Menu (Optional Font/Color)
        JMenu formatMenu = new JMenu("Format");

        JMenuItem colorItem = new JMenuItem("Change Text Color");
        colorItem.addActionListener(e -> {
            Color color = JColorChooser.showDialog(this, "Choose Text Color", textArea.getForeground());
            if (color != null) {
                textArea.setForeground(color);
            }
        });

        JMenuItem fontItem = new JMenuItem("Change Font");
        fontItem.addActionListener(e -> changeFont());

        formatMenu.add(colorItem);
        formatMenu.add(fontItem);

        // ******** Help Menu
        JMenu helpMenu = new JMenu("Help");

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Created by: Your Name\nID: Your ID",
                        "About Notepad",
                        JOptionPane.INFORMATION_MESSAGE)
        );

        helpMenu.add(aboutItem);

        // ********Add menus to MenuBar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    // ******** File Open Method
    private void openFile() {
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                textArea.read(reader, null);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error opening file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // *********File Save Method
    private void saveFile() {
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                textArea.write(writer);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //****** Font Change Method
    private void changeFont() {
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        String font = (String) JOptionPane.showInputDialog(this, "Choose Font:",
                "Font Selector", JOptionPane.PLAIN_MESSAGE, null, fonts, textArea.getFont().getFamily());

        if (font != null) {
            textArea.setFont(new Font(font, Font.PLAIN, 16));
        }
    }

    // ********* Main Method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NotepadApp app = new NotepadApp();
            app.setVisible(true);
        });
    }
}
