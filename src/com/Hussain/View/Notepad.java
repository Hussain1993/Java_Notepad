package com.Hussain.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Hussain
 * Date: 03/06/2013
 * Time: 13:13
 * Project Name: Notepad
 */
public class Notepad extends JFrame {
    private static boolean saved;

    private static JTextArea textArea;

    private static JMenuBar menuBar;
    private static JMenu fileMenu;
    private static JMenu editMenu;
    private static JMenuItem openMenuItem;
    private static JMenuItem saveMenuItem;
    private static JMenuItem closeMenuItem;
    private static JMenuItem cutMenuItem;
    private static JMenuItem copyMenuItem;
    private static JMenuItem pasteMenuItem;

    public Notepad(){
        setTitle("Untitled");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(new Dimension(600,400));
        initWidgets();
        setLayout();
        addWidgets();
        actionListerners();
        saved = false;
        windowListener();
    }

    private void initWidgets(){
        textArea = new JTextArea();
        textArea.setLineWrap(true);

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");

        openMenuItem = new JMenuItem("Open");
        saveMenuItem = new JMenuItem("Save");
        closeMenuItem = new JMenuItem("Close");

        cutMenuItem = new JMenuItem("Cut");
        copyMenuItem = new JMenuItem("Copy");
        pasteMenuItem = new JMenuItem("Paste");

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(closeMenuItem);

        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);

    }

    private void setLayout(){
        setLayout(new BorderLayout());
    }

    private void addWidgets(){
         add(new JScrollPane(textArea),BorderLayout.CENTER);
        this.setJMenuBar(menuBar);
    }

    private void actionListerners(){
        /*
        This is the action listener for the open button
         */
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser open = new JFileChooser();
                int option = open.showOpenDialog(null);
                if(option == JFileChooser.APPROVE_OPTION)
                {
                    textArea.setText("");
                    try{
                        Scanner scan = new Scanner(new FileReader(open.getSelectedFile().getPath()));
                        Notepad.this.setTitle(open.getSelectedFile().getName());
                        while(scan.hasNext())
                        {
                            textArea.append(scan.nextLine());
                        }
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        /*
        This is the action listener for the save button
         */
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser save = new JFileChooser();
                save.addChoosableFileFilter(new TxtFileFilter());
                int option = save.showSaveDialog(null);
                if(option == JFileChooser.APPROVE_OPTION)
                {
                    String ext = "";
                    String extension = save.getFileFilter().getDescription();
                    if(extension.equals("*.txt"))
                    {
                        ext = ".txt";
                    }
                    try{
                        BufferedWriter writer = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath()+ext));
                        writer.write(textArea.getText());
                        writer.close();
                        saved = true;
                        Notepad.this.setTitle(save.getSelectedFile().getName());
                    }
                    catch(Exception exception){
                        System.out.println(exception.getMessage());
                    }
                }
            }
        });

        /*
        This is the action listener for the close button
         */
        closeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(saved)
                {
                    System.exit(0);
                }
                else
                {
                    showSaveDialog();
                }
            }
        });

        /*
        This is the action listener for the cut button
         */
        cutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textArea.cut();
            }
        });

        /*
        This is the action listener for the copy button
         */
        copyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textArea.copy();
            }
        });

        /*
        This is the action listener for the paste button
         */
        pasteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textArea.paste();
            }
        });
    }//end method actionListerners

    private void windowListener(){
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if(saved)
                {
                    super.windowClosing(windowEvent);
                }
                else
                {
                    showSaveDialog();
                }
            }
        });
    }

    private void showSaveDialog(){
        int choice = JOptionPane.showConfirmDialog(null,"You have not saved the file, are you sure you want to close the application?","Save before Quitting",JOptionPane.YES_NO_OPTION);
        if(choice == 0)
        {
            System.exit(0);
        }
    }
}
