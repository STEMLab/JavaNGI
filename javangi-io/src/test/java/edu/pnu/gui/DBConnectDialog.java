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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

/**
 * @author Donguk Seo
 *
 */
public class DBConnectDialog extends JDialog {

        private final JPanel contentPanel = new JPanel();
        private JTextField txtPostgis;
        private JTextField textField_URL;
        private JTextField textField_USER;

        private NGI_Sample parent;
        private JTextField textField_PASSWORD;

        /**
         * Create the dialog.
         */
        public DBConnectDialog(NGI_Sample parent) {
                this.parent = parent;
                
                setTitle("Database Connection");
                setBounds(100, 100, 239, 195);
                getContentPane().setLayout(new BorderLayout());
                contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
                getContentPane().add(contentPanel, BorderLayout.CENTER);
                contentPanel.setLayout(null);
                
                JLabel lblDatabase = new JLabel("Database");
                lblDatabase.setBounds(12, 10, 57, 15);
                contentPanel.add(lblDatabase);
                
                txtPostgis = new JTextField();
                txtPostgis.setEditable(false);
                txtPostgis.setText("PostGIS");
                txtPostgis.setBounds(81, 7, 116, 21);
                contentPanel.add(txtPostgis);
                txtPostgis.setColumns(10);
                
                JLabel lblUrl = new JLabel("URL");
                lblUrl.setBounds(12, 35, 57, 15);
                contentPanel.add(lblUrl);
                
                textField_URL = new JTextField();
                textField_URL.setBounds(81, 32, 116, 21);
                contentPanel.add(textField_URL);
                textField_URL.setColumns(10);
                
                JLabel lblUserName = new JLabel("User Name");
                lblUserName.setBounds(12, 60, 64, 15);
                contentPanel.add(lblUserName);
                
                JLabel lblPassword = new JLabel("Password");
                lblPassword.setBounds(12, 85, 57, 15);
                contentPanel.add(lblPassword);
                
                textField_USER = new JTextField();
                textField_USER.setBounds(81, 57, 116, 21);
                contentPanel.add(textField_USER);
                textField_USER.setColumns(10);
                
                textField_PASSWORD = new JTextField();
                textField_PASSWORD.setBounds(81, 82, 116, 21);
                contentPanel.add(textField_PASSWORD);
                textField_PASSWORD.setColumns(10);
                {
                        JPanel buttonPane = new JPanel();
                        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        getContentPane().add(buttonPane, BorderLayout.SOUTH);
                        {
                                JButton okButton = new JButton("OK");
                                okButton.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                                String url = "jdbc:postgresql://" + textField_URL.getText();
                                                String user = textField_USER.getText();
                                                String password = textField_PASSWORD.getText();
                                                
                                                if(url != null && user != null && password != null) {
                                                        getParent().getJavaNGI().connectToDB(url, user, password);
                                                        dispose();
                                                        System.out.println("DB Connected.");
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

        /**
         * @return the parent
         */
        public NGI_Sample getParent() {
                return parent;
        }

        /**
         * @param parent the parent to set
         */
        public void setParent(NGI_Sample parent) {
                this.parent = parent;
        }
}
