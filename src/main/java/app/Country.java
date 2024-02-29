package app;

/**
 * Class represeting a Country from the World database
 * For simplicity this uses public fields
 * You could use private fields with getters to be safer
 *
 * @author Tri Huynh, 2024. email: tri.huynh5@rmit.edu.au
 */
public class Country {
   // Movie id
   public String code;

   // Movie name
   public String name;

   /**
    * Create a Country setting any of the fields,
    * Fields will have default "empty" values
    */
   public Country() { }

   /**
    * Create a movie setting all of the fields
    */
   public Country(String code, String name) {
      this.code = code;
      this.name = name;
   }
}
