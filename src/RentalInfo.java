import java.util.HashMap;

public class RentalInfo {

  public String statement(Customer customer) {
    HashMap<String, Movie> movies = new HashMap<>();
    movies.put("F001", new Movie("You've Got Mail", "regular"));
    movies.put("F002", new Movie("Matrix", "regular"));
    movies.put("F003", new Movie("Cars", "childrens"));
    movies.put("F004", new Movie("Fast & Furious X", "new"));

    double totalAmount = 0;
    int frequentEnterPoints = 0;
    StringBuilder result = new StringBuilder("Rental Record for " + customer.getName() + "\n");
    for (MovieRental r : customer.getRentals()) {
      double thisAmount = calculateAmount(movies, r);

      //add frequent bonus points
      frequentEnterPoints++;
      // add bonus for a two day new release rental
      if (movies.get(r.getMovieId()).getCode().equals("new") && r.getDays() > 2) frequentEnterPoints++;

      //print figures for this rental
      result.append("\t").append(movies.get(r.getMovieId()).getTitle()).append("\t").append(thisAmount).append("\n");
      totalAmount += thisAmount;
    }
    // add footer lines
    result.append("Amount owed is ").append(totalAmount).append("\n");
    result.append("You earned ").append(frequentEnterPoints).append(" frequent points\n");

    return result.toString();
  }

  private double calculateAmount(HashMap<String, Movie> movies, MovieRental rental) {
    double thisAmount = 0;
    switch (movies.get(rental.getMovieId()).getCode()) {
      case "regular":
        thisAmount = 2;
        if (rental.getDays() > 2) {
          thisAmount += ((rental.getDays() - 2) * 1.5);
        }
        break;
      case "new":
        thisAmount = rental.getDays() * 3;
        break;
      case "childrens":
        thisAmount = 1.5;
        if (rental.getDays() > 3) {
          thisAmount += ((rental.getDays() - 3) * 1.5);
        }
        break;
    }
    return thisAmount;
  }
}
