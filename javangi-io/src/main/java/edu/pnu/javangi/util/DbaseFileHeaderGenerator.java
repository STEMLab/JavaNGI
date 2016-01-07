package edu.pnu.javangi.util;

import org.geotools.data.shapefile.dbf.DbaseFileException;
import org.geotools.data.shapefile.dbf.DbaseFileHeader;

import edu.pnu.javangi.featuretype.AbstractFeatureType;
import edu.pnu.javangi.featuretype.boundary.AdministrationBoundary;
import edu.pnu.javangi.featuretype.boundary.EtcBoundary;
import edu.pnu.javangi.featuretype.boundary.WaterwardBoundary;
import edu.pnu.javangi.featuretype.building.Building;
import edu.pnu.javangi.featuretype.building.Wall;
import edu.pnu.javangi.featuretype.facility.AmusementFacility;
import edu.pnu.javangi.featuretype.facility.Beach;
import edu.pnu.javangi.featuretype.facility.BulletinBoard;

public class DbaseFileHeaderGenerator {
        public static DbaseFileHeader createDefaultDbaseHeader(AbstractFeatureType feature) {
                DbaseFileHeader header = new DbaseFileHeader();
                try {
                        header.addColumn("LAYER_ID", 'N', 9, 0);
                        header.addColumn("LAYER_NAME", 'C', 254, 0);
                        header.addColumn("RECORD_ID", 'N', 9, 0);
                        header.addColumn("UFID", 'C', 254, 0);
                } catch (DbaseFileException e) {
                        e.printStackTrace();
                }

                return header;
        }

        public static DbaseFileHeader createDbaseHeaderWithTextColumn(AbstractFeatureType feature) {
                DbaseFileHeader header = createDbaseHeader(feature);

                try {
                        header.addColumn("TEXT", 'C', 254, 0);
                } catch (DbaseFileException e) {
                        e.printStackTrace();
                }

                return header;
        }

        public static DbaseFileHeader createDbaseHeader(AbstractFeatureType feature) {
                DbaseFileHeader header = createDefaultDbaseHeader(feature);

                try {
                        if(feature instanceof AdministrationBoundary) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("구분", 'C', 254, 0);
                        }else if(feature instanceof EtcBoundary) {
                                header.addColumn("용도", 'C', 254, 0);
                        }else if(feature instanceof WaterwardBoundary) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("형태", 'C', 254, 0);
                        }else if(feature instanceof Building) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("구분", 'C', 254, 0);
                                header.addColumn("종류", 'C', 254, 0);
                                header.addColumn("용도", 'C', 254, 0);
                                header.addColumn("주기", 'C', 254, 0);
                                header.addColumn("층수", 'N', 9, 0);
                        }else if(feature instanceof Wall) {
                                header.addColumn("구분", 'C', 254, 0);
                                header.addColumn("재질", 'C', 254, 0);
                        }else if(feature instanceof AmusementFacility) {
                                header.addColumn("명칭", 'C', 254, 0);
                                header.addColumn("구분", 'C', 254, 0);
                        }else if(feature instanceof Beach) {
                                header.addColumn("명칭", 'C', 254, 0);
                        }else if(feature instanceof BulletinBoard) {
                                header.addColumn("구분", 'C', 254, 0);
                        }
                } catch (DbaseFileException e) {
                        e.printStackTrace();
                }
                
                return header;
        }
}
