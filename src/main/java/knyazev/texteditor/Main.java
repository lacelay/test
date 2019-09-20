package knyazev.texteditor;

import knyazev.texteditor.gui.Window;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        Window window = new Window("JSON Editor", 960, 640);
    }
}
