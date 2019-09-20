package knyazev.texteditor.gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static knyazev.texteditor.gui.Window.*;

import static knyazev.texteditor.utils.Reader.read;
import static knyazev.texteditor.utils.Writer.write;

public class JMenuFile extends JMenu {

    private JFileChooser fileChooser;

    private JDialog dialog;

    public JMenuFile(JTextArea textArea) {
        setText("File");
        prepareFileChooserForJsonFormat();
        addComponents();
    }

    private void prepareFileChooserForJsonFormat() {
        fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON data object", "json");
        fileChooser.setFileFilter(filter);
    }

    private void addComponents() {
        addJMenuItem("New", createNewFileOption());
        addJMenuItem("Open", createOpenFileOption());
        addSeparator();
        addJMenuItem("Save", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });
        addJMenuItem("Save as...", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFileAs();
            }
        });
        addSeparator();
        addJMenuItem("Exit", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeProgram();
            }
        });
    }

    private void addJMenuItem(String text, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(actionListener);
        this.add(menuItem);
    }

    private ActionListener createNewFileOption() {
        ActionListener open = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!textArea.getText().equals("")) {
                    createDialog("Current file is not empty.\n" +
                        "Save it?", createNewFileOptionPanel());
                }
            }
        };
        return open;
    }

    private void createDialog(String text, JPanel optionPanel) {
        dialog = new JDialog(frame);

        int width = 350;
        int height = 100;

        int x = frame.getLocation().x +  frame.getWidth() / 2 -  width / 2;
        int y = frame.getLocation().y + frame.getHeight() / 2 - height;

        dialog.setLocation(x, y);

        dialog.setLayout(new BorderLayout());

        JPanel panel = createDialogPanel(dialog, text, optionPanel);
        dialog.add(panel, BorderLayout.CENTER);

        dialog.setSize(width, height);
        dialog.setVisible(true);
    }

    private JPanel createDialogPanel(JDialog dialog, String text, JPanel optionPanel) {
        JPanel panel = new JPanel();

        panel.add(new JLabel(text));
        panel.add(optionPanel);

        return panel;
    }

    private void saveFile() {

        if (file != null && file.exists()) {
            try {
                write(textArea.getText(), file);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        } else {
            saveFileAs();
        }

    }

    private void saveFileAs() {

        int status = fileChooser.showSaveDialog(frame);
        if (status == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath() + ".json");
            try {
                file.createNewFile();
                write(textArea.getText(), file);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

    }

    private ActionListener createOpenFileOption() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        };
        return actionListener;
    }

    private void openFile() {

        int status = fileChooser.showOpenDialog(frame);
        if (status == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            if (file.exists()) {
                try {
                    text = read(file);
                    textArea.setText(text);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        }

    }

    private void closeProgram() {
        if (!textArea.getText().equals(text)) {
            createDialog("Current file has been changed.\n" +
                "Save it?", createCloseProgramOptionPanel());
        } else {
            frame.dispose();
        }
    }

    private JPanel createNewFileOptionPanel() {
        JPanel optionPanel = new JPanel();

        List<JButton> list = new ArrayList<>();

        list.add(new JButton("Save"));
        list.add(new JButton("Don't save"));
        list.add(new JButton("Cancel"));

        list.get(0).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                saveFile();
            }
        });

        list.get(1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                textArea.setText("");
            }
        });

        list.get(2).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        });

        list.forEach(button -> {
            button.setPreferredSize(new Dimension(100, 24));
            optionPanel.add(button);
        });

        return optionPanel;
    }

    private JPanel createCloseProgramOptionPanel() {
        JPanel optionPanel = new JPanel();

        List<JButton> list = new ArrayList<>();

        list.add(new JButton("Save"));
        list.add(new JButton("Don't save"));
        list.add(new JButton("Cancel"));

        list.get(0).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                saveFile();
                frame.dispose();
            }
        });

        list.get(1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                frame.dispose();
            }
        });

        list.get(2).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        });

        list.forEach(button -> {
            button.setPreferredSize(new Dimension(100, 24));
            optionPanel.add(button);
        });

        return optionPanel;
    }

}