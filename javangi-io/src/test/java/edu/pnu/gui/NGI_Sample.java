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

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.pnu.javangi.util.JavaNGI;

/**
 * @author Donguk Seo
 *
 */
public class NGI_Sample extends JFrame {

        private JPanel contentPane;
        private JTextField textField_SOURCE;
        private JTextField textField_DEST;
        private JTextArea txtLog = null;
        
        private JavaNGI javaNGI = null;
        private String url = null;
        private String user = null;
        private String password = null;

        /**
         * Launch the application.
         */
        public static void main(String[] args) {
                EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                try {
                                        NGI_Sample frame = new NGI_Sample();
                                        frame.setVisible(true);
                                } catch (Exception e) {
                                        e.printStackTrace();
                                }
                        }
                });
        }

        /**
         * Create the frame.
         */
        public NGI_Sample() {
                setTitle("NGI Convertor sample");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setBounds(100, 100, 374, 253);
                
                JMenuBar menuBar = new JMenuBar();
                setJMenuBar(menuBar);
                
                JMenu mnFile = new JMenu("File");
                menuBar.add(mnFile);
                
                JMenuItem mntmOpenNgiFile = new JMenuItem("Load NGI file");
                mntmOpenNgiFile.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                JFileChooser fileChooser = new JFileChooser();
                                FileNameExtensionFilter filter = new FileNameExtensionFilter("ngi (*.ngi)",
                                                "ngi");
                                        fileChooser.setFileFilter(filter);
                                int returnVal= fileChooser.showOpenDialog(NGI_Sample.this);
                                if(returnVal == JFileChooser.APPROVE_OPTION) {
                                        File ngiFile = fileChooser.getSelectedFile();
                                        String ndaPath = ngiFile.getAbsolutePath();
                                        ndaPath = ndaPath.replace(".ngi", ".nda");
                                        File ndaFile = new File(ndaPath);
                                        
                                        javaNGI = new JavaNGI();
                                        javaNGI.importDataFromNGI(ngiFile, ndaFile);
                                        textField_SOURCE.setText(ndaPath);
                                        addLog(ngiFile.getName() + "(nda)" + "files are loaded.");
                                }
                        }
                });
                mnFile.add(mntmOpenNgiFile);
                
                JSeparator separator = new JSeparator();
                mnFile.add(separator);
                
                JMenuItem mntmExportToshp = new JMenuItem("Export to shp");
                mntmExportToshp.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                if(javaNGI != null) {
                                        JFileChooser fileChooser = new JFileChooser();
                                        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                                        int returnVal= fileChooser.showOpenDialog(NGI_Sample.this);
                                        if(returnVal == JFileChooser.APPROVE_OPTION) {
                                                File dir = fileChooser.getSelectedFile();
                                                
                                                javaNGI.exportToShape(dir);
                                                textField_DEST.setText(dir.getAbsolutePath());
                                                addLog("Convert ngi file to .shp(.shx, .dbf) files.");
                                        }
                                }
                        }
                });
                mnFile.add(mntmExportToshp);
                
                JMenu mnDb = new JMenu("DB");
                //mnFile.add(mnDb);
                
                
                JMenuItem mntmConnect = new JMenuItem("Connect");
                mntmConnect.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                DBConnectDialog dialog = new DBConnectDialog(NGI_Sample.this);
                                dialog.setModal(true);
                                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                dialog.setVisible(true);
                                addLog("Create DB Connectoin");
                        }
                });
                mnDb.add(mntmConnect);
                
                JMenuItem mntmImport = new JMenuItem("Import");
                mntmImport.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                if(javaNGI != null && javaNGI.getConnection() != null) {
                                        DBImportDialog dialog = new DBImportDialog(NGI_Sample.this);
                                        dialog.setModal(true);
                                        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                        dialog.setVisible(true);
                                        addLog("Import NGI data from PostGIS");
                                }
                        }
                });
                
                JMenuItem mntmExport = new JMenuItem("Export");
                mntmExport.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                if(javaNGI != null) {
                                        try {
                                                javaNGI.createSchema();
                                                javaNGI.insertDataToDB();
                                                addLog("Export NGI data to PostGIS");
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
                mnDb.add(mntmExport);
                mnDb.add(mntmImport);
                contentPane = new JPanel();
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                setContentPane(contentPane);
                contentPane.setLayout(null);
                
                JLabel lblNgiFile = new JLabel("NGI File");
                lblNgiFile.setBounds(12, 10, 60, 22);
                contentPane.add(lblNgiFile);
                
                textField_SOURCE = new JTextField();
                textField_SOURCE.setEditable(false);
                textField_SOURCE.setBounds(84, 11, 260, 21);
                contentPane.add(textField_SOURCE);
                textField_SOURCE.setColumns(10);
                
                txtLog = new JTextArea();
                txtLog.setWrapStyleWord(true);
                txtLog.setEditable(false);
                txtLog.setLineWrap(true);
                txtLog.setBounds(12, 75, 332, 109);
                contentPane.add(txtLog);
                
                JLabel lblShapeFile = new JLabel("Shape File");
                lblShapeFile.setBounds(12, 42, 66, 15);
                contentPane.add(lblShapeFile);
                
                textField_DEST = new JTextField();
                textField_DEST.setEditable(false);
                textField_DEST.setBounds(84, 39, 260, 21);
                contentPane.add(textField_DEST);
                textField_DEST.setColumns(10);
        }

        /**
         * @return the javaNGI
         */
        public JavaNGI getJavaNGI() {
                return javaNGI;
        }

        /**
         * @param javaNGI the javaNGI to set
         */
        public void setJavaNGI(JavaNGI javaNGI) {
                this.javaNGI = javaNGI;
        }

        /**
         * @return the url
         */
        public String getUrl() {
                return url;
        }

        /**
         * @param url the url to set
         */
        public void setUrl(String url) {
                this.url = url;
        }

        /**
         * @return the user
         */
        public String getUser() {
                return user;
        }

        /**
         * @param user the user to set
         */
        public void setUser(String user) {
                this.user = user;
        }

        /**
         * @return the password
         */
        public String getPassword() {
                return password;
        }

        /**
         * @param password the password to set
         */
        public void setPassword(String password) {
                this.password = password;
        }
        
        public void addLog(String msg) {
                txtLog.append(msg + "\n");
        }
        
}
