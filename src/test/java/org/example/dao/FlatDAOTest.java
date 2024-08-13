package org.example.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.example.core.FlatInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FlatDAOTest {

    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
    }

    @Test
    void getAllFlatsSuccessfully() throws SQLException {
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("flat_id")).thenReturn(1, 2);
        when(mockResultSet.getString("building_name")).thenReturn("Building1", "Building2");
        when(mockResultSet.getInt("flat_floor")).thenReturn(1, 2);
        when(mockResultSet.getInt("flat_number")).thenReturn(101, 102);
        when(mockResultSet.getBoolean("flat_elevator")).thenReturn(true, false);
        when(mockResultSet.getFloat("flat_sqm")).thenReturn(50.0f, 60.0f);
        when(mockResultSet.getInt("flat_people")).thenReturn(3, 4);
        when(mockResultSet.getInt("flat_kids")).thenReturn(1, 2);
        when(mockResultSet.getBoolean("flat_pet")).thenReturn(true, false);
        when(mockResultSet.getBoolean("flat_pet_elevator")).thenReturn(false, true);

        FlatDAO flatDAO = new FlatDAO(mockConnection);
        List<FlatInfo> flats = flatDAO.getAllFlats();

        assertNotNull(flats);
        assertEquals(2, flats.size());
        assertEquals(1, flats.get(0).getFlatId());
        assertEquals("Building1", flats.get(0).getBuildingName());
        assertEquals(101, flats.get(0).getFlatNumber());
        assertEquals(1, flats.get(0).getFlatFloor());
        assertTrue(flats.get(0).isFlatElevator());
        assertEquals(50.0f, flats.get(0).getFlatSqft());
        assertEquals(3, flats.get(0).getFlatPeople());
        assertEquals(1, flats.get(0).getFlatKids());
        assertTrue(flats.get(0).isFlatPets());
        assertFalse(flats.get(0).isFlatPetsElevator());
    }

    @Test
    void getAllFlatsThrowsSQLException() throws SQLException {
        when(mockStatement.executeQuery()).thenThrow(new SQLException("SQL error"));

        FlatDAO flatDAO = new FlatDAO(mockConnection);

        SQLException exception = assertThrows(SQLException.class, flatDAO::getAllFlats);

        assertEquals("Error in getting flat info", exception.getMessage());
    }

    @Test
    void getAllFlatsByBuildingSuccessfully() throws SQLException {
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("flat_id")).thenReturn(1);
        when(mockResultSet.getString("building_name")).thenReturn("Building1");
        when(mockResultSet.getInt("flat_floor")).thenReturn(1);
        when(mockResultSet.getInt("flat_number")).thenReturn(101);
        when(mockResultSet.getBoolean("flat_elevator")).thenReturn(true);
        when(mockResultSet.getFloat("flat_sqm")).thenReturn(50.0f);
        when(mockResultSet.getInt("flat_people")).thenReturn(3);
        when(mockResultSet.getInt("flat_kids")).thenReturn(1);
        when(mockResultSet.getBoolean("flat_pet")).thenReturn(true);
        when(mockResultSet.getBoolean("flat_pet_elevator")).thenReturn(false);

        FlatDAO flatDAO = new FlatDAO(mockConnection);
        List<FlatInfo> flats = flatDAO.getAllFlatsByBuilding("Building1");

        assertNotNull(flats);
        assertEquals(1, flats.size());
        assertEquals(1, flats.get(0).getFlatId());
        assertEquals("Building1", flats.get(0).getBuildingName());
        assertEquals(101, flats.get(0).getFlatNumber());
        assertEquals(1, flats.get(0).getFlatFloor());
        assertTrue(flats.get(0).isFlatElevator());
        assertEquals(50.0f, flats.get(0).getFlatSqft());
        assertEquals(3, flats.get(0).getFlatPeople());
        assertEquals(1, flats.get(0).getFlatKids());
        assertTrue(flats.get(0).isFlatPets());
        assertFalse(flats.get(0).isFlatPetsElevator());
    }

    @Test
    void getAllFlatsByBuildingThrowsSQLException() throws SQLException {
        when(mockStatement.executeQuery()).thenThrow(new SQLException("SQL error"));

        FlatDAO flatDAO = new FlatDAO(mockConnection);

        SQLException exception = assertThrows(SQLException.class, () -> flatDAO.getAllFlatsByBuilding("Building1"));

        assertEquals("Error in getting flat info by building", exception.getMessage());
    }

    @Test
    void getAllFlatsByBuildingAndFlatNumSuccessfully() throws SQLException {
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("flat_id")).thenReturn(1);
        when(mockResultSet.getString("building_name")).thenReturn("Building1");
        when(mockResultSet.getInt("flat_floor")).thenReturn(1);
        when(mockResultSet.getInt("flat_number")).thenReturn(101);
        when(mockResultSet.getBoolean("flat_elevator")).thenReturn(true);
        when(mockResultSet.getFloat("flat_sqm")).thenReturn(50.0f);
        when(mockResultSet.getInt("flat_people")).thenReturn(3);
        when(mockResultSet.getInt("flat_kids")).thenReturn(1);
        when(mockResultSet.getBoolean("flat_pet")).thenReturn(true);
        when(mockResultSet.getBoolean("flat_pet_elevator")).thenReturn(false);

        FlatDAO flatDAO = new FlatDAO(mockConnection);
        List<FlatInfo> flats = flatDAO.getAllFlatsByBuildingAndFlatNum("Building1", 101);

        assertNotNull(flats);
        assertEquals(1, flats.size());
        assertEquals(1, flats.get(0).getFlatId());
        assertEquals("Building1", flats.get(0).getBuildingName());
        assertEquals(101, flats.get(0).getFlatNumber());
        assertEquals(1, flats.get(0).getFlatFloor());
        assertTrue(flats.get(0).isFlatElevator());
        assertEquals(50.0f, flats.get(0).getFlatSqft());
        assertEquals(3, flats.get(0).getFlatPeople());
        assertEquals(1, flats.get(0).getFlatKids());
        assertTrue(flats.get(0).isFlatPets());
        assertFalse(flats.get(0).isFlatPetsElevator());
    }

    @Test
    void getAllFlatsByBuildingAndFlatNumThrowsSQLException() throws SQLException {
        when(mockStatement.executeQuery()).thenThrow(new SQLException("SQL error"));

        FlatDAO flatDAO = new FlatDAO(mockConnection);

        SQLException exception = assertThrows(SQLException.class, () -> flatDAO.getAllFlatsByBuildingAndFlatNum("Building1", 101));

        assertEquals("Error in getting flat info by building", exception.getMessage());
    }

    @Test
    void createFlatSuccessfully() throws SQLException {
        when(mockStatement.executeUpdate()).thenReturn(1);

        FlatDAO flatDAO = new FlatDAO(mockConnection);
        FlatInfo flatInfo = new FlatInfo("Building1", 101, 1, true, 50.0f, 3, 1, true, false);
        flatDAO.createFlat(flatInfo);

        verify(mockStatement).setString(1, "Building1");
        verify(mockStatement).setInt(2, 1);
        verify(mockStatement).setInt(3, 101);
        verify(mockStatement).setBoolean(4, true);
        verify(mockStatement).setFloat(5, 50.0f);
        verify(mockStatement).setInt(6, 3);
        verify(mockStatement).setInt(7, 1);
        verify(mockStatement).setBoolean(8, true);
        verify(mockStatement).setBoolean(9, false);
        verify(mockStatement).executeUpdate();
    }

    @Test
    void createFlatThrowsSQLException() throws SQLException {
        doThrow(new SQLException("SQL error")).when(mockStatement).executeUpdate();

        FlatDAO flatDAO = new FlatDAO(mockConnection);
        FlatInfo flatInfo = new FlatInfo("Building1", 101, 1, true, 50.0f, 3, 1, true, false);

        SQLException exception = assertThrows(SQLException.class, () -> flatDAO.createFlat(flatInfo));

        assertEquals("Error in creating flat info", exception.getMessage());
    }

    @Test
    void updateFlatSuccessfully() throws SQLException {
        when(mockStatement.executeUpdate()).thenReturn(1);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("flat_id")).thenReturn(1);
        when(mockResultSet.getString("building_name")).thenReturn("Building1");
        when(mockResultSet.getInt("flat_floor")).thenReturn(1);
        when(mockResultSet.getInt("flat_number")).thenReturn(101);
        when(mockResultSet.getBoolean("flat_elevator")).thenReturn(true);
        when(mockResultSet.getFloat("flat_sqm")).thenReturn(55.0f); // Updated value
        when(mockResultSet.getInt("flat_people")).thenReturn(4); // Updated value
        when(mockResultSet.getInt("flat_kids")).thenReturn(2); // Updated value
        when(mockResultSet.getBoolean("flat_pet")).thenReturn(true);
        when(mockResultSet.getBoolean("flat_pet_elevator")).thenReturn(true); // Updated value

        // Mock the ResultSet for updatedFlatHelper
        when(mockConnection.prepareStatement("SELECT * FROM flat_info WHERE building_name = ? AND flat_number = ?")).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        FlatDAO flatDAO = new FlatDAO(mockConnection);
        FlatInfo updatedFlat = new FlatInfo("Building1", 102, 1, true, 55.0f, 4, 2, true, true);
        FlatInfo result = flatDAO.updateFlat("Building1", 101, updatedFlat);

        assertNotNull(result);
        assertEquals("Building1", result.getBuildingName());
        assertEquals(102, result.getFlatNumber());
        assertEquals(1, result.getFlatFloor());
        assertTrue(result.isFlatElevator());
        assertEquals(55.0f, result.getFlatSqft());
        assertEquals(4, result.getFlatPeople());
        assertEquals(2, result.getFlatKids());
        assertTrue(result.isFlatPets());
        assertTrue(result.isFlatPetsElevator());
    }

    @Test
    void updateFlatThrowsSQLException() throws SQLException {
        doThrow(new SQLException("SQL error")).when(mockStatement).executeUpdate();

        FlatDAO flatDAO = new FlatDAO(mockConnection);
        FlatInfo updatedFlat = new FlatInfo("Building1", 102, 1, true, 55.0f, 4, 2, true, true);

        SQLException exception = assertThrows(SQLException.class, () -> flatDAO.updateFlat("Building1", 101, updatedFlat));

        assertEquals("Error in updating flat", exception.getMessage());
    }

    @Test
    void deleteFlatSuccessfully() throws SQLException {
        when(mockStatement.executeUpdate()).thenReturn(1);

        FlatDAO flatDAO = new FlatDAO(mockConnection);
        boolean result = flatDAO.deleteFlat("Building1", 101);

        assertTrue(result);
    }

    @Test
    void deleteFlatThrowsSQLException() throws SQLException {
        doThrow(new SQLException("SQL error")).when(mockStatement).executeUpdate();

        FlatDAO flatDAO = new FlatDAO(mockConnection);

        SQLException exception = assertThrows(SQLException.class, () -> flatDAO.deleteFlat("Building1", 101));

        assertEquals("Error in deleting flat", exception.getMessage());
    }
}