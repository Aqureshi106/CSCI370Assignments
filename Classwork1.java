import java.math.BigInteger;
final class RationalImmutable {
  protected final BigInteger numerator;
  protected final BigInteger denominator;

  public RationalImmutable() {
    this.numerator = BigInteger.ZERO;
    this.denominator = BigInteger.ONE;
  }

  public RationalImmutable(BigInteger numerator, BigInteger denominator) {
    if (denominator.equals(BigInteger.ZERO)) {
      throw new IllegalArgumentException("Denominator cannot be zero");
    }
    this.numerator = numerator;
    this.denominator = denominator;
  }

  public BigInteger getNumerator() {
    return numerator;
  }

  public BigInteger getDenominator() {
    return denominator;
  }

  public RationalImmutable plus(RationalImmutable r) {
    BigInteger newNumerator = this.numerator.multiply(r.denominator)
      .add(r.numerator.multiply(this.denominator));
    BigInteger newDenominator = this.denominator.multiply(r.denominator);
    return new RationalImmutable(newNumerator, newDenominator);
  }

  public RationalImmutable multi(RationalImmutable r) {
    BigInteger newNumerator = this.numerator.multiply(r.numerator);
    BigInteger newDenominator = this.denominator.multiply(r.denominator);
    return new RationalImmutable(newNumerator, newDenominator);
  }

  public RationalImmutable divide(RationalImmutable r) {
    BigInteger newNumerator = this.numerator.multiply(r.denominator);
    BigInteger newDenominator = this.denominator.multiply(r.numerator);
    return new RationalImmutable(newNumerator, newDenominator);
  }
}

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello world!");
    RationalImmutable r1 = new RationalImmutable(BigInteger.valueOf(53), BigInteger.valueOf(52));
    RationalImmutable r2 = new RationalImmutable(BigInteger.valueOf(1), BigInteger.valueOf(2));
    System.out.println("The numerator of r1: " + r1.getNumerator() + "\n" + "The denominator of r1: " + r1.getDenominator());
    System.out.println("The numerator of r2: " + r2.getNumerator() + "\n" + "The denominator of r2: " + r2.getDenominator());
   RationalImmutable r1_plus_r2 = r1.plus(r2);
    System.out.println("The numerator of r1_plus_r2: " + r1_plus_r2.getNumerator() + "\n" + "The denominator of r1_plus_r2: " + r1_plus_r2.getDenominator());
    RationalImmutable r1_multi_r2 = r1.multi(r2);
    System.out.println("The numerator of r1_multi_r2: " + r1_multi_r2.getNumerator() + "\n" + "The denominator of r1_multi_r2: " + r1_multi_r2.getDenominator());
    RationalImmutable r1_divide_r2 = r1.divide(r2);
    System.out.println("The numerator of r1_divide_r2: " + r1_divide_r2.getNumerator() + "\n" + "The denominator of r1_divide_r2: " + r1_divide_r2.getDenominator());
  }
}
