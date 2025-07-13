class Degrees {
  private double degrees;

  public double getDegrees() {
    return degrees;
  }

  public void setDegrees(double degrees) {
    this.degrees = degrees;
  }
  
  public String toString() {
    return "" + degrees;
  }
}
class Fahrenheit extends Degrees {
  public Fahrenheit() { // Default Constructor
    super();
    setDegrees(-459.67);
  }

  public Fahrenheit(double degrees) { // Constructor
    if (degrees < -459.67) {
      throw new IllegalArgumentException("Degrees in Fahrenheit cannot be less than -459.67");
    }
    setDegrees(degrees);
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

class Celsius extends Degrees {
  public Celsius() { // Default Constructor
    setDegrees(-273.15);
  }

  public Celsius(double temp) { // Constructor
    if (temp < -273.15) {
      throw new IllegalArgumentException("Degrees in Celsius cannot be less than -273.15");
    }
    setDegrees(temp);
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
    System.out.println("Celsius to Fahrenheit: " + c_string + " -> " + c_to_f_string);
    System.out.println("Fahrenheit to Celsius: " + f_string + " -> " + f_to_c_string);
  }
}
