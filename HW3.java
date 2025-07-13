abstract class Temperature {
    private double temp;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
      }

    public Temperature(double temp) {
        this.temp = temp;
    }

    public abstract Boolean isFreezing();

    public abstract Boolean isBoiling();

    public abstract String toString();
}

class Fahrenheit extends Temperature {
    public Fahrenheit() { // Default Constructor
        this(-459.67);
    }

    public Fahrenheit(double temp) { // Constructor
        super(check(temp));
    }

    private static double check(double temp) {
        if (temp < -459.67) {
            throw new IllegalArgumentException("Temp in Fahrenheit cannot be less than -459.67.");
        }
        return temp;
    }

    protected Celsius toCelsius() {
        Celsius x = new Celsius((getTemp() - 32) * 5 / 9);
        return x;
    }

    @Override
    public String toString() {
        return getTemp() + "°F";
    }

    public Kelvin toKelvin() {
        Kelvin x = new Kelvin((getTemp() - 32) * 5/9 + 273.15);
        return x;
    }

    @Override
    public Boolean isFreezing() {
        return getTemp() <= 32;
    }

    @Override
    public Boolean isBoiling() {
        return getTemp() >= 212;
    }
}

class Celsius extends Temperature {
    public Celsius() { // Default Constructor
        this(-273.15);
    }

    private static double check(double temp) {
        if (temp < -273.15) {
            throw new IllegalArgumentException("Temp in Celsius cannot be less than -273.15.");
        }
        return temp;
    }

    public Celsius(double temp) { // Constructor
        super(check(temp));
    }

    protected Fahrenheit toFahrenheit() {
        Fahrenheit x = new Fahrenheit((getTemp() * 9 / 5) + 32);
        return x;
    }

    @Override
    public String toString() {
        return getTemp() + "°C";
    }

  public Kelvin toKelvin() {
    Kelvin x = new Kelvin((getTemp() + 273.15));
    return x;
  }

    public Boolean isFreezing() {
        return getTemp() <= 0;
    }

    public Boolean isBoiling() {
        return getTemp() >= 100;
    }
}

class Kelvin extends Temperature {
    public Kelvin() {
        this(0.0);
    }

    public Kelvin(double temp) { // Constructor
        super(check(temp));
    }

    private static double check(double temp) {
      if (temp < 0.0) {
        throw new IllegalArgumentException("Temp in Kelvin cannot be less than 0.0.");
      }
      return temp;
    }

    protected Celsius toCelsius() {
      Celsius x = new Celsius((getTemp() - 273.15));
      return x;
    }

    protected Fahrenheit toFahrenheit() {
      Fahrenheit x = new Fahrenheit((getTemp() - 273.15) * 9 / 5 + 32);
      return x;
    }

        @Override
    public String toString() {
      return getTemp() + "K";
    }

    public Boolean isFreezing() {
        return getTemp() <= 273.15;
    }

    public Boolean isBoiling() {
        return getTemp() >= 373.15;
    }
  }

public class Main {
    protected static double averageTemperature(Temperature [] temps) {
        int size = temps.length;
        double sum = 0.0;
        for (Temperature i: temps) {
            Celsius c;
            if (i instanceof Fahrenheit) {
                c = ((Fahrenheit) i).toCelsius();
                sum += c.getTemp();
            }
            else if (i instanceof Kelvin) {
                c = ((Kelvin) i).toCelsius();
                sum += c.getTemp();
            }
            else if (i instanceof Celsius) {
                c = ((Celsius) i);
                sum += c.getTemp();
            }
            else {
                throw new IllegalArgumentException("Invalid type.");
            }
        }
        return sum / size;
    } 
  public static void main(String[] args) {
    Fahrenheit f = new Fahrenheit(103.00);
    Celsius c = new Celsius(64.00);
    Kelvin k = new Kelvin(35.0);
    String k_string = k.toString();
    String c_string = c.toString();
    String f_string = f.toString();
    Fahrenheit c_to_f = c.toFahrenheit();
    Celsius f_to_c = f.toCelsius();
    Celsius k_to_c = k.toCelsius();
    Fahrenheit k_to_f = k.toFahrenheit();
    Kelvin f_to_k = f.toKelvin();
    Kelvin c_to_k = c.toKelvin();
    String f_to_k_string = f_to_k.toString();
    String c_to_k_string = c_to_k.toString();
    String k_to_f_string = k_to_f.toString();
    String k_to_c_string = k_to_c.toString();
    String c_to_f_string = c_to_f.toString();
    String f_to_c_string = f_to_c.toString();
    System.out.println("Celsius to Fahrenheit: " + c_string + " -> " + c_to_f_string);
    System.out.println("Fahrenheit to Celsius: " + f_string + " -> " + f_to_c_string);
    System.out.println("Celsius to Kelvin: " + c_string + " -> " + c_to_k_string);
    System.out.println("Fahrenheit to Kelvin: " + f_string + " -> " + f_to_k_string);
    System.out.println("Kelvin to Celsius: " + k_string + " -> " + k_to_c_string);
    System.out.println("Kelvin to Fahrenheit: " + k_string + " -> " + k_to_f_string);
    f.setTemp(92.0);
    c.setTemp(31.9);
    k.setTemp(21.2);
    f_string = f.toString();
    c_string = c.toString();
    k_string = k.toString();
    System.out.println("New Fahrenheit: " + f_string);
    System.out.println("New Celsius: " + c_string);
    System.out.println("New Kelvin: " + k_string);
    f.setTemp(13.5);
    c.setTemp(101.2);
    k.setTemp(278.51);
    f_string = f.toString();
    c_string = c.toString();
    k_string = k.toString();
    if (f.isFreezing()) {
        System.out.println("The temperature " + f_string + " is freezing.");
    }
    else if (f.isBoiling()) {
          System.out.println("The temperature " + f_string + " is boiling.");
    }
    else {
        System.out.println("The temperature " + f_string + " is normal.");
    }
    if (c.isFreezing()) {
          System.out.println("The temperature " + c_string + " is freezing.");
      }
    else if (c.isBoiling()) {
            System.out.println("The temperature " + c_string + " is boiling.");
      }
    else {
          System.out.println("The temperature " + c_string + " is normal.");
      }
      if (k.isFreezing()) {
          System.out.println("The temperature " + k_string + " is freezing.");
      }
      else if (k.isBoiling()) {
            System.out.println("The temperature " + k_string + " is boiling.");
      }
      else {
          System.out.println("The temperature " + k_string + " is normal.");
      }
      Fahrenheit a = new Fahrenheit(24);
      Fahrenheit b = new Fahrenheit(96.2);
      Fahrenheit d = new Fahrenheit(54.7);
      Celsius e = new Celsius(3.2);
      Celsius g = new Celsius(36.1);
      Kelvin h = new Kelvin(301.2);
      Temperature [] x = {
          a, b, d, e, g, h
      };
      double average = averageTemperature(x);
      System.out.println("The average of temperature of array x = " + average);
  }
}
