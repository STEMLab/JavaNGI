package edu.pnu.javangi.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import edu.pnu.javangi.featuretype.FeatureTypeFactory;
import edu.pnu.javangi.featuretype.FeatureType;

public class JavaNGI {
        private ArrayList<FeatureType> featureTypeList;

        private String NGIFilePath;

        private String NDAFilePath;

        private String[] NGICommands = { "<LAYER_START>", "$LAYER_ID", "$END", "$LAYER_NAME",
                        "$END", "<LAYER_END>" };

        private String[] NDACommands = { "<LAYER_START>", "$LAYER_ID", "$END", "$LAYER_NAME",
                        "$END", "<LAYER_END>" };

        private Connection con = null;

        private Statement st = null;

        private ResultSet rs = null;

        public JavaNGI() {
                featureTypeList = new ArrayList<FeatureType>();
        }

        public ArrayList<FeatureType> getFeatureTypeList() {
                return featureTypeList;
        }

        public int getNumFeatureType() {
                return featureTypeList.size();
        }

        public FeatureType getFeatureTypeN(int n) {
                return featureTypeList.get(n);
        }

        public void importDataFromFile(String NGIFilePath, String NDAFilePath)
                        throws ClassNotFoundException, InstantiationException,
                        IllegalAccessException, IOException {
                int layerID;
                String layerName;

                this.NGIFilePath = NGIFilePath;
                this.NDAFilePath = NDAFilePath;

                File NGIFile = new File(NGIFilePath);
                File NDAFile = new File(NDAFilePath);

                Scanner ngiScanner = null;
                Scanner ndaScanner = null;
                try {
                        // Load File
                        ngiScanner = new Scanner(NGIFile);
                        ndaScanner = new Scanner(NDAFile);
                } catch (Exception e) {
                        e.printStackTrace();
                }

                boolean validSequence = true;
                while (validSequence && (ngiScanner.hasNextLine() && ndaScanner.hasNextLine())) {
                        String ngiStr = ngiScanner.nextLine();
                        String ndaStr = ndaScanner.nextLine();

                        if (!(ngiStr.equals(NGICommands[0]) && ndaStr.equals(NDACommands[0]))) { // <LAYER_START>
                                // if(!ndaStr.equals(NDACommands[0])){ // <LAYER_START>
                                validSequence = false;
                                continue;
                        }
                        ngiStr = ngiScanner.nextLine();
                        ndaStr = ndaScanner.nextLine();

                        if (!(ngiStr.equals(NGICommands[1]) && ndaStr.equals(NDACommands[1]))) { // $LAYER_ID
                                // if(!ndaStr.equals(NDACommands[1])){ // $LAYER_ID
                                validSequence = false;
                                continue;
                        }
                        layerID = Integer.valueOf(ngiScanner.nextLine());
                        ndaScanner.nextLine();

                        ngiStr = ngiScanner.nextLine();
                        ndaStr = ndaScanner.nextLine();
                        if (!(ngiStr.equals(NGICommands[2]) && ndaStr.equals(NDACommands[2]))) { // $END
                                // if(!ndaStr.equals(NDACommands[2])){ // $END
                                validSequence = false;
                                continue;
                        }
                        ngiStr = ngiScanner.nextLine();
                        ndaStr = ndaScanner.nextLine();

                        if (!(ngiStr.equals(NGICommands[3]) && ndaStr.equals(NDACommands[3]))) { // $LAYER_NAME
                                // if(!ndaStr.equals(NDACommands[3])){ // $LAYER_NAME
                                validSequence = false;
                                continue;
                        }
                        layerName = ngiScanner.nextLine();
                        ndaScanner.nextLine();

                        ngiStr = ngiScanner.nextLine();
                        ndaStr = ndaScanner.nextLine();
                        if (!(ngiStr.equals(NGICommands[4]) && ndaStr.equals(NDACommands[4]))) { // $END
                                // if(!ndaStr.equals(NDACommands[4])){ // $END
                                validSequence = false;
                                continue;
                        }

                        FeatureType featuretype = FeatureTypeFactory
                                        .createFeatureType(layerName);
                        if (featuretype != null) {
                                featuretype.setLayerID(layerID);
                                featuretype.importDataFromFile(ngiScanner, ndaScanner);
                                featureTypeList.add(featuretype);
                        } else {
                                while (ngiScanner.hasNextLine()) {
                                        ngiStr = ngiScanner.nextLine();

                                        if (ngiStr.equals("<LAYER_END>"))
                                                break;
                                }
                                while (ndaScanner.hasNextLine()) {
                                        ndaStr = ndaScanner.nextLine();

                                        if (ndaStr.equals("<LAYER_END>"))
                                                break;
                                }
                        }
                        /*
                         * //if(!(ngiStr.equals(NGICommands[5]) && ndaStr.equals(NDACommands[5]))){ // <LAYER_END> if(ndaStr.equals(NDACommands[5])){
                         * // <LAYER_END> validSequence = false; continue; }
                         */
                }

                // Close File
                try {
                        ngiScanner.close();
                        ndaScanner.close();
                } catch (Exception e) {
                } finally {
                        ngiScanner = null;
                        ndaScanner = null;
                }
        }

        public void exportDataToFile(String ngiFilePath, String ndaFilePath) {
                BufferedWriter ngibw = null;
                PrintWriter ngiWriter = null;
                BufferedWriter ndabw = null;
                PrintWriter ndaWriter = null;
                try {
                        ngibw = new BufferedWriter(new FileWriter(ngiFilePath));
                        ngiWriter = new PrintWriter(ngibw, true);

                        ndabw = new BufferedWriter(new FileWriter(ndaFilePath));
                        ndaWriter = new PrintWriter(ndabw, true);

                        //ngiWriter.println("test");
                        
                        for (int i = 0; i < featureTypeList.size(); i++) {
                                featureTypeList.get(i).exportDataToFile(ngiWriter, ndaWriter);
                        }
                        
                        ngibw.close();
                        ndabw.close();
                        ngiWriter.close();
                        ndaWriter.close();
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                } finally {
                        ngibw = null;
                        ndabw = null;
                        ngiWriter = null;
                        ndaWriter = null;
                }
        }

        public void connectToDB(String url, String user, String password) {
                try {
                        con = DriverManager.getConnection(url, user, password);
                        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);
                } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }

        public void createSchema() throws ClassNotFoundException, InstantiationException,
                        IllegalAccessException {
                ArrayList<String> schemas = FeatureTypeFactory.getAllFeatureTypeSchema();

                for (int i = 0; i < schemas.size(); i++) {
                        try {
                                st.executeUpdate(schemas.get(i));
                        } catch (SQLException e) {
                                // e.printStackTrace();
                                System.out.println(e.getMessage());
                        }
                }
        }

        public void insertDataToDB() {
                int idx = NGIFilePath.lastIndexOf("\\");
                String fileName = NGIFilePath.substring(idx + 1, NGIFilePath.length());

                for (int i = 0; i < featureTypeList.size(); i++) {
                        String[] querys = featureTypeList.get(i).getInsertQuery(fileName);

                        for (int j = 0; j < querys.length; j++) {
                                try {
                                        st.executeUpdate(querys[j]);
                                } catch (SQLException e) {
                                        e.printStackTrace();
                                        System.out.println(e.getMessage());
                                }
                        }
                }
        }

        public void importDataFromDB(String fileName) throws ClassNotFoundException,
                        InstantiationException, IllegalAccessException {
                String[] groupName = { "traffic", "building", "facility", "boundary", "landform",
                                "note", "vegetation", "watersystem" };
                featureTypeList.clear();

                for (int k = 0; k < groupName.length; k++) {
                        String[] classNames = FeatureTypeFactory.getClassNameArray(groupName[k]);
                        for (int i = 0; i < classNames.length; i++) {
                                String sql = "SELECT COUNT(*) FROM " + classNames[i]
                                                + "metadata WHERE filename = '" + fileName
                                                + ".ngi'";

                                try {
                                        rs = st.executeQuery(sql);
                                        if (rs.next()) {
                                                int count = rs.getInt(1);

                                                if (count > 0) {
                                                        FeatureType featureType = FeatureTypeFactory
                                                                        .createFeatureType(classNames[i]);

                                                        try {
                                                                sql = "SELECT * FROM "
                                                                                + classNames[i]
                                                                                + "metadata WHERE filename = '"
                                                                                + fileName
                                                                                + ".ngi' ORDER BY index ASC";
                                                                rs = st.executeQuery(sql);
                                                                featureType.importDataFromDB(
                                                                                "MetaData", rs);

                                                                sql = "SELECT * FROM "
                                                                                + classNames[i]
                                                                                + "data WHERE filename = '"
                                                                                + fileName
                                                                                + ".ngi' ORDER BY recordid ASC";
                                                                rs = st.executeQuery(sql);
                                                                featureType.importDataFromDB(
                                                                                "Data", rs);
                                                                featureTypeList.add(featureType);
                                                        } catch (SQLException e) {
                                                                e.printStackTrace();
                                                        }
                                                }
                                        }

                                } catch (SQLException e) {
                                        e.printStackTrace();
                                }
                        }
                }
        }

        public void exportToShape() {
                ShapeConvertUtil.convertNGIToShape(null, featureTypeList);
        }
}
