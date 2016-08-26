package app;

import app.models.*;
import app.models.Driver;

import java.sql.*;
import java.util.*;
import java.util.List;


/**
 * Created by anthonymace on 10/24/15.
 */
public class SqlDriver {
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement ps;
    private static ResultSet rs;

    private static String DB_NAME = "jdbc:sqlite:test.db";
    private static String LIBRARY = "org.sqlite.JDBC";

    public void createTestDBFile() {
        try {
            Class.forName(LIBRARY);
            connection = DriverManager.getConnection(DB_NAME);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static void insertRecord(Object obj) {
        try {
            Class.forName(LIBRARY);
            connection = DriverManager.getConnection(DB_NAME);

            if (obj instanceof Driver && !isRecord(obj)) {
                ps = connection.prepareStatement(
                        "INSERT INTO DRIVERS  (FIRST_NAME, LAST_NAME, USERNAME, PASSWORD, CHANNEL, RADIO_VOLUME, STATION, PHONE_VOLUME, MILES_REMAINING, AVERAGE_SPEED, MAX_SPEED) " +
                                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                );
                ps.setString(1, ((Driver) obj).getFirstName());
                ps.setString(2, ((Driver) obj).getLastName());
                ps.setString(3, ((Driver) obj).getUsername());
                ps.setString(4, ((Driver) obj).getPassword());
                ps.setString(5,  ((Driver) obj).getChannel());
                ps.setInt(6, ((Driver) obj).getRadioVolume());
                ps.setInt(7,  ((Driver) obj).getStation());
                ps.setInt(8,  ((Driver) obj).getPhoneVolume());
                ps.setDouble(9, ((Driver) obj).getMilesRemaining());
                ps.setDouble(10, ((Driver) obj).getAverageSpeed());
                ps.setDouble(11,  ((Driver) obj).getMaxSpeed());
            } else if (obj instanceof RadioHistory) {
                ps = connection.prepareStatement(
                        "INSERT INTO RADIO_HISTORIES  (DRIVER_ID, NAME, STATION, DATE, TIME, DURATION) " +
                        "VALUES(?, ?, ?, ?, ?, ?)"
                );
                ps.setInt(1,  ((RadioHistory) obj).getdriverID());
                ps.setString(2, ((RadioHistory) obj).getName());
                ps.setString(3, ((RadioHistory) obj).getStation());
                ps.setString(4, ((RadioHistory) obj).getDate());
                ps.setString(5,  ((RadioHistory) obj).getTime());
                ps.setDouble(6, ((RadioHistory) obj).getDuration());
            }  else if (obj instanceof DriverHistory) {
                ps = connection.prepareStatement(
                        "INSERT INTO DRIVER_HISTORIES (DRIVER_ID, NAME, DATE, DURATION, AVERAGE_SPEED, MAX_SPEED) " +
                                "VALUES(?, ?, ?, ?, ?, ?)"
                );
                ps.setInt(1, ((DriverHistory) obj).getDriverID());
                ps.setString(2, ((DriverHistory) obj).getName());
                ps.setString(3, ((DriverHistory) obj).getDate());
                ps.setDouble(4, ((DriverHistory) obj).getDuration());
                ps.setDouble(5, ((DriverHistory) obj).getAvgSpeed());
                ps.setDouble(6, ((DriverHistory) obj).getMaxSpeed());
            }   else if (obj instanceof PhoneHistory) {
                ps = connection.prepareStatement(
                        "INSERT INTO PHONE_HISTORIES (DRIVER_ID, NAME, PHONE_NUMBER, DATE, TIME, DURATION) " +
                                "VALUES(?, ?, ?, ?, ?, ?)"
                );
                ps.setInt(1, ((PhoneHistory) obj).getDriverID());
                ps.setString(2, ((PhoneHistory) obj).getName());
                ps.setString(3, ((PhoneHistory) obj).getNumber());
                ps.setString(4, ((PhoneHistory) obj).getDate());
                ps.setString(5, ((PhoneHistory) obj).getTime());
                ps.setDouble(6, ((PhoneHistory) obj).getDuration());

            } else if (obj instanceof Contact) {
                ps = connection.prepareStatement(
                        "INSERT INTO CONTACTS   (DRIVER_ID, PHONE_NUMBER) " + "VALUES(?, ?)"
                );
                ps.setInt(1, ((Contact) obj).getdriverID());
                ps.setString(2, ((Contact) obj).getPhoneNumber());
            }

            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        if (obj instanceof Driver) {
            setDriverIDFromRecord((Driver) obj);
        }
    }

    public static List<String> findBy(String table, String column, Object value) {
        if (Main.testing) return null;
        List results = new ArrayList<String>();
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DB_NAME);
            ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + column + " = ?");

            if (value instanceof String) {
                ps.setString(1, value.toString());
            } else {
                ps.setInt(1, Integer.parseInt(value.toString()));
            }

            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsData = rs.getMetaData();
            int columnsInRow = rsData.getColumnCount();
            while(rs.next()) {
                String row = "";
                for (int i = 1; i <= columnsInRow; i++) {
                    row += rs.getString(i) + "  ";
                }

                results.add(row);
            }
            rs.close();
            ps.close();
            connection.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return results;
    }

    public static List<String> getRecords(String table) {
        if (Main.testing) return null;
        String select = "SELECT * FROM " + table.toUpperCase();
        List results = new ArrayList<String>();
        try {
            Class.forName(LIBRARY);
            connection = DriverManager.getConnection(DB_NAME);
            stmt = connection.createStatement();
            rs =stmt.executeQuery(select);
            ResultSetMetaData rsData = rs.getMetaData();
            int columnsInRow = rsData.getColumnCount();
            while (rs.next()) {
                String row = "";
                for (int i = 1; i <= columnsInRow; i++) {
                    row += rs.getString(i) + " ";
                }
                results.add(row);
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return results;
    }

    public static void updateRecord(String table, String column, int ID, Object value) {
        if (Main.testing) return;
        try {
            Class.forName(LIBRARY);
            connection = DriverManager.getConnection(DB_NAME);

            ps = connection.prepareStatement(
                "UPDATE " + table.toUpperCase() + " " +
                "SET " + column.toUpperCase() + " = ? " +
                "WHERE ID = ?"
            );

            if (value instanceof String) {
                ps.setString(1, value.toString());
            } else if(value instanceof Integer) {
                ps.setInt(1, Integer.parseInt(value.toString()));
            } else if(value instanceof Double) {
                ps.setDouble(1, Double.parseDouble(value.toString()));
            }
            ps.setInt(2, ID);

            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static void updateAllRecords(String table, String column, Object value) {
        if (Main.testing) return;
        try {
            Class.forName(LIBRARY);
            connection = DriverManager.getConnection(DB_NAME);

            ps = connection.prepareStatement(
                    "UPDATE " + table.toUpperCase() + " " + "SET " + column.toUpperCase() + " = ? "
            );

            if (value instanceof String) {
                ps.setString(1, value.toString());
            } else if(value instanceof Integer) {
                ps.setInt(1, Integer.parseInt(value.toString()));
            } else if(value instanceof Double) {
                ps.setDouble(1, Double.parseDouble(value.toString()));
            }

            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    private static void setDriverIDFromRecord(Driver driver) {
        if (Main.testing) return;
        try {
            Class.forName(LIBRARY);
            connection = DriverManager.getConnection(DB_NAME);
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT ID FROM DRIVERS WHERE USERNAME = '" + driver.getUsername() + "'");
            while (rs.next()) {
                int id = rs.getInt("ID");
                driver.setID(id);
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    private static boolean isRecord(Object obj) {
        if (Main.testing) return false;
        String select = "";
        if (obj instanceof Driver) {
            select = "SELECT ID FROM DRIVERS WHERE USERNAME = '" + ((Driver) obj).getUsername() + "'";
        } else if (obj instanceof DriverHistory) {
            select = "SELECT ID FROM DRIVER_HISTORIES WHERE DRIVER_ID = '" + ((DriverHistory) obj).getDriverID() + "'";
        }
        try {
            Class.forName(LIBRARY);
            connection = DriverManager.getConnection(DB_NAME);
            stmt = connection.createStatement();
            rs =stmt.executeQuery(select);
            if (rs.isBeforeFirst()) {
                rs.close();
                stmt.close();
                connection.close();
                return true;
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return false;
    }

}
