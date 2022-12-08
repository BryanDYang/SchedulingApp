package test;

import dao.ApptDao;
import model.Appt;
import main.Main;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;
import utilities.VerifyAppt;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class OverlappingApptTest {

    Appt newAppt = new Appt("Test Title", "Test Description",
            "Test Location", "Test Type", LocalDateTime.of(2022, 12, 10,8, 0),
            LocalDateTime.of(2022, 12, 10, 9, 0), "Test User", "Test User",
            1, 1, 1);

    Appt overlappingAppt = new Appt("Test Title", "Test Description",
            "Test Location", "Test Type", LocalDateTime.of(2022, 12, 10,8, 30),
            LocalDateTime.of(2022, 12, 10, 9, 0), "Test User", "Test User",
            1, 1, 1);

    Appt notOverlappingAppt = new Appt("Test Title", "Test Description",
            "Test Location", "Test Type", LocalDateTime.of(2022, 12, 10,11, 0),
            LocalDateTime.of(2022, 12, 10, 12, 0), "Test User", "Test User",
            1, 1, 1);

    Appt adjAppt = new Appt("Test Title", "Test Description",
            "Test Location", "Test Type", LocalDateTime.of(2022, 12, 10,9, 0),
            LocalDateTime.of(2022, 12, 10, 10, 0), "Test User", "Test User",
            1, 1, 1);

    //Create test appointment and insert in DB
    private void createTestRecord() throws SQLException {
        ApptDao.create(newAppt);
    }

    @Test
    void isOverlappingAppt() throws SQLException {
        createTestRecord();
        Assertions.assertEquals(true, VerifyAppt.overlappingAppt(overlappingAppt));
    }

    @Test
    void notOverlappingAppointment() throws SQLException {
        createTestRecord();
        Assertions.assertEquals(false, VerifyAppt.overlappingAppt(notOverlappingAppt));
    }

    @Test
    void allowAdjacentAppointment() throws SQLException {
        createTestRecord();
        Assertions.assertEquals(false, VerifyAppt.overlappingAppt(adjAppt));
    }
}
