/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2016, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package edu.pnu.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.pnu.javangi.util.JavaNGI;

/**
 * @author Donguk Seo
 *
 */
public class DBImportDialog extends JDialog {

        private final JPanel contentPanel = new JPanel();
        private JTextField textField_SOURCE;
        private JTextField textField_DEST;

        private NGI_Sample parent;
        /**
         * Create the dialog.
         */
        public DBImportDialog(NGI_Sample parent) {
                this.parent = parent;
                
                setTitle("Import from DB");
                setBounds(100, 100, 406, 146);
                getContentPane().setLayout(new BorderLayout());
                contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
                getContentPane().add(contentPanel, BorderLayout.CENTER);
                contentPanel.setLayout(null);
                {
                        JLabel lblFileName = new JLabel("Source File Name");
                        lblFileName.setBounds(12, 10, 109, 15);
                        contentPanel.add(lblFileName);
                }
                {
                        textField_SOURCE = new JTextField();
                        textField_SOURCE.setBounds(133, 7, 157, 21);
                        contentPanel.add(textField_SOURCE);
                        textField_SOURCE.setColumns(10);
                }
                
                JLabel lblDestinationFile = new JLabel("Destination File");
                lblDestinationFile.setBounds(12, 35, 109, 15);
                contentPanel.add(lblDestinationFile);
                
                textField_DEST = new JTextField();
                textField_DEST.setBounds(133, 32, 157, 21);
                contentPanel.add(textField_DEST);
                textField_DEST.setColumns(10);
                
                JButton btnNewButton = new JButton("Open");
                btnNewButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                JFileChooser fileChooser = new JFileChooser();
                                FileNameExtensionFilter filter = new FileNameExtensionFilter("ngi (*.ngi)",
                                                "ngi");
                                        fileChooser.setFileFilter(filter);
                                int returnVal= fileChooser.showOpenDialog(DBImportDialog.this);
                                if(returnVal == JFileChooser.APPROVE_OPTION) {
                                        File destFile = fileChooser.getSelectedFile();
                                                                               
                                        textField_DEST.setText(destFile.getAbsolutePath());
                                }
                                
                        }
                });
                btnNewButton.setBounds(302, 31, 77, 23);
                contentPanel.add(btnNewButton);
                {
                        JPanel buttonPane = new JPanel();
                        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        getContentPane().add(buttonPane, BorderLayout.SOUTH);
                        {
                                JButton okButton = new JButton("OK");
                                okButton.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                                if(textField_SOURCE.getText() != null && textField_DEST.getText() != null
                                                                && getParent().getJavaNGI().getConnection() != null) {
                                                        String sourceName = textField_SOURCE.getText();
                                                        if(sourceName.contains(".ngi")) {
                                                                sourceName = sourceName.substring(0, sourceName.length() - 4);
                                                        }
                                                                        
                                                        try {
                                                                getParent().getJavaNGI().importDataFromDB(sourceName);
                                                                String ngiPath = textField_DEST.getText();
                                                                String ndaPath = ngiPath.replace(".ngi", ".nda");
                                                                File ngiFile = new File(ngiPath);
                                                                File ndaFile = new File(ndaPath);
                                                                
                                                                getParent().getJavaNGI().exportDataToNGIFile(ngiFile, ndaFile);

                                                                System.out.println("Import PostGIS data to NGI");
                                                                dispose();
                                                        } catch (ClassNotFoundException e1) {
                                                                // TODO Auto-generated catch block
                                                                e1.printStackTrace();
                                                        } catch (InstantiationException e1) {
                                                                // TODO Auto-generated catch block
                                                                e1.printStackTrace();
                                                        } catch (IllegalAccessException e1) {
                                                                // TODO Auto-generated catch block
                                                                e1.printStackTrace();
                                                        }
                                                        
                                                }
                                        }
                                });
                                okButton.setActionCommand("OK");
                                buttonPane.add(okButton);
                                getRootPane().setDefaultButton(okButton);
                        }
                        {
                                JButton cancelButton = new JButton("Cancel");
                                cancelButton.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                                dispose();
                                        }
                                });
                                cancelButton.setActionCommand("Cancel");
                                buttonPane.add(cancelButton);
                        }
                }
        }
        
        public NGI_Sample getParent() {
                return parent;
        }
}
