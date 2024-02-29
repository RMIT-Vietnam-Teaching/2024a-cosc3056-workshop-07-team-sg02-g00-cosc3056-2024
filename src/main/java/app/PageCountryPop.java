package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Tri Huynh, 2024. email: tri.huynh5@rmit.edu.vn
 */
public class PageCountryPop implements Handler {

    // URL of this page relative to http://localhost:7001/countryPop
    public static final String URL = "/countryPop";

    // Name of the Thymeleaf HTML template page in the resources folder
    private static final String TEMPLATE = ("countryPop.html");

    @Override
    public void handle(Context context) throws Exception {
        // The model of data to provide to Thymeleaf.
        // In this example the model will be filled with:
        //  - Array List of all Countries in the database
        //  - Array list of all cities from the selected country

        Map<String, Object> model = new HashMap<String, Object>();

        String chosenCountryCode = context.formParam("targetCountry");
        String chosenCity = context.formParam("targetCity");

        System.out.println("Chosen Country " + chosenCountryCode);

        // Add in title for the h1 tag to the model
        model.put("title", new String("Find Population By City"));

        // Look up some information from JDBC
        // First we need to use your JDBCConnection class
        JDBCConnection jdbc = new JDBCConnection();

        // Next we will ask this *class* for the countries
        ArrayList<Country> countries = jdbc.getCountries();

        // Finally put all of these countries into the model
        model.put("countries", countries);

        // Record the country that the user selected previously
        model.put("selectedCountry", chosenCountryCode);

        // Find all cities by a country
        if (chosenCountryCode != null) {

            ArrayList<City> cities = jdbc.getCityNamesByCountry(chosenCountryCode);

            model.put("cities", cities);

        }

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage using Thymeleaf
        context.render(TEMPLATE, model);
    }

}
