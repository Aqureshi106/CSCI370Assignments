abstract class Degrees {
  private final double degrees;

  public double getDegrees() {
    return degrees;
  }

  protected Degrees(double degrees) {
    this.degrees = degrees;
  }

  public String toString() {
    return "" + degrees;
  }
}
final class Fahrenheit extends Degrees {
  public Fahrenheit() { // Default Constructor
    this(-459.67);
  }

  public Fahrenheit(double degrees) { // Constructor
    super(check(degrees));
  }

  public Fahrenheit setDegrees(double degrees) {
    return new Fahrenheit(degrees);
  }

  private static double check(double degrees) {
    if (degrees < -459.67) {
      throw new IllegalArgumentException("Degrees in Fahrenheit cannot be less than -459.67");
    }
    return degrees;
  }

  protected Celsius toCelsius() {
    Celsius x = new Celsius((getDegrees() - 32) * 5/9);
    return x;
  }

  @Override
  public String toString() {
      return getDegrees() + "°F";
  }
}

final class Celsius extends Degrees {
  public Celsius() { // Default Constructor
    this(-273.15);
  }

  private static double check(double degrees) {
    if (degrees < -273.15) {
      throw new IllegalArgumentException("Degrees in Celsius cannot be less than -273.15");
    }
    return degrees;
  }

  public Celsius setDegrees(double degrees) {
    return new Celsius(degrees);
  }

  public Celsius(double degrees) { // Constructor
    super(check(degrees));
  }

  protected Fahrenheit toFahrenheit() {
    Fahrenheit x = new Fahrenheit((getDegrees() * 9/5) + 32);
    return x;
  }

  @Override
  public String toString() {
      return getDegrees() + "°C";
  }
}
public class Main {
  public static void main(String[] args) {
    Fahrenheit f = new Fahrenheit(103.00);
    Celsius c = new Celsius(64.00);
    String c_string = c.toString();
    String f_string = f.toString();
    Fahrenheit c_to_f = c.toFahrenheit();
    Celsius f_to_c = f.toCelsius();
    String c_to_f_string = c_to_f.toString();
    String f_to_c_string = f_to_c.toString();
    Fahrenheit f2 = f.setDegrees(76.00);
    Celsius c2 = c.setDegrees(23.00);
    String f2_str = f2.toString();
    String c2_str = c2.toString();
    System.out.println("Celsius to Fahrenheit: " + c_string + " -> " + c_to_f_string);
    System.out.println("Fahrenheit to Celsius: " + f_string + " -> " + f_to_c_string);
    System.out.println("New Fahrenheit: " + f2_str);
    System.out.println("New Celsius: " + c2_str);
  }
}
