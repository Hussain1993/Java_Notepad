package com.Hussain.View;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Hussain
 * Date: 03/06/2013
 * Time: 15:25
 * Project Name: Notepad
 */
public class TxtFileFilter extends FileFilter {
    @Override
    public boolean accept(File file) {
        if(file.isDirectory())
        {
            return false;
        }
        String s = file.getName();
        return s.endsWith(".txt");
    }

    @Override
    public String getDescription() {
        return "*.txt";
    }
}
