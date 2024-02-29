package app;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class JDBCConnection {

    // Name of database file (contained in database folder)
    private static final String DATABASE = "jdbc:sqlite:database/world.db";

    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    /**
     * Get all of the Movies in the database.
     * @return
     *    Returns an ArrayList of Movie objects
     */
    public ArrayList<Country> getCountries() {
        // Create the ArrayList to return - this time of Movie objects
        ArrayList<Country> countries = new ArrayList<Country>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query =  """
                            SELECT code, name 
                            FROM Country
                            """;
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            // The "results" variable is similar to an array
            // We can iterate through all of the database query results
            while (results.next()) {
                // Create a Movie Object
                Country country = new Country();

                // Lookup the columns we want, and set the movie object field
                // BUT, we must be careful of the column type!
                country.code    = results.getString("Code");
                country.name  = results.getString("Name");

                // Add the movie object to the array
                countries.add(country);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the movies
        return countries;
    }

    public ArrayList<City> getCityNamesByCountry(String countryCode) {
        // Create the ArrayList to return - this time of Movie objects
        ArrayList<City> citiesOfACountry = new ArrayList<City>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get all cities of a selected country code
            // We can replace %s by %$1s to indicate the position of the parameter
            // in the String.format() call
            String query = 
                String.format("""
                    SELECT name, population 
                    FROM City
                    WHERE CountryCode = '%s'
                """, 
                countryCode
            );
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            // The "results" variable is similar to an array
            // We can iterate through all of the database query results
            while (results.next()) {
                // Create a Movie Object
                City city = new City();

                // Lookup the columns we want, and set the movie object field
                // BUT, we must be careful of the column type!
                city.name  = results.getString("Name");
                city.population  = results.getInt("Population");

                // Add the movie object to the array
                citiesOfACountry.add(city);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the movies
        return citiesOfACountry;
    }

    // TODO: Keep adding more methods here to answer all of the questions from the Studio Class activities
}
