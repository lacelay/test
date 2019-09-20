package knyazev.texteditor.gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Window {

    public static JFrame frame;

    public static JTextArea textArea;

    public static String text;

    private JScrollPane scrollPane;

    public static File file;

    public Window(String title, int width, int height) {
        frame = createFrame(title, width, height, WindowConstants.EXIT_ON_CLOSE);
        createMenu();
        createTextArea();
        text = "";
        frame.setVisible(true);
    }

    private JFrame createFrame(String title, int width, int height, int closeOperation) {

        JFrame frame = new JFrame();

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        int x = dimension.width / 2 - width / 2;
        int y = dimension.height / 2 - height / 2;

        frame.setLocation(x, y);
        frame.setTitle(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(closeOperation);

        return frame;

    }

    private void createMenu() {

        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenuFile(textArea);

        menuBar.add(file);

        frame.setJMenuBar(menuBar);

    }

    private void createTextArea() {

        int vsbPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
        int hsbPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;

        textArea = new JTextArea();
        textArea.setFont(new Font(Font.DIALOG, Font.PLAIN,  10));
        scrollPane = new JScrollPane(textArea, vsbPolicy, hsbPolicy);

        frame.add(scrollPane);

    }

}