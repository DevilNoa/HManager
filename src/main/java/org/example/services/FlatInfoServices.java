package org.example.services;


import org.example.core.FlatInfo;
import org.example.dao.FlatDAO;

import java.sql.SQLException;
import java.util.List;

public class FlatInfoServices {

    static FlatDAO flatDAO;

    public FlatInfoServices(FlatDAO flatDAO) {
        FlatInfoServices.flatDAO = flatDAO;
    }


    //Get all Flats
    public List<FlatInfo> getAllFlats() throws SQLException {
        return flatDAO.getAllFlats();
    }
    //Get all Flats by building
    public List<FlatInfo> getAllFlatsByBuilding(String buildingName) throws SQLException {
        return flatDAO.getAllFlatsByBuilding(buildingName);
    }
    //Get all Flats by building and apartment number
    public List<FlatInfo> getAllFlatsByBuildingAndFlatNum(String buildingName, int apartmentNumber) throws SQLException {
        return flatDAO.getAllFlatsByBuildingAndFlatNum(buildingName, apartmentNumber);
    }
    //Create a new Flat
    public void addFlat(FlatInfo newFlat) throws SQLException {
         flatDAO.createFlat(newFlat);
    }
    //Update a Flat
    public FlatInfo updateFlat(String buildingName, int flatNumber, FlatInfo updatedFlat) throws SQLException {
        return flatDAO.updateFlat(buildingName, flatNumber, updatedFlat);
    }


    //Delete a Flat
    public static boolean deleteFlat(String buildingName, int flatNumber) throws SQLException{
        return  flatDAO.deleteFlat(buildingName, flatNumber);
    }
}
