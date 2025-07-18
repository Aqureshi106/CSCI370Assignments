import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

class SimpleFactoryPlayer {
  public Player create(String type, GameshowHost host) {
    type = type.toLowerCase();
    if (type.equals("keep")) {
      return new KeepingPlayer(host);
    }
    else if (type.equals("switch")) {
      return new SwitchingPlayer(host);
    }
    else {
      throw new IllegalArgumentException("Type " + type + " cannot be accepted.");
    }
  }
}

enum Prize {
  GOAT, CAR, UNKNOWN
}

class Door {
  private boolean open;
  private Prize prize;

  public Door(Prize prize) {
    this.prize = prize;
  }

  public void open() {
    this.open = true;
  }

  public void close() {
    this.open = false;
  }

  public boolean isOpen() {
    return open;
  }

  public Prize look() {
    if (!open)
      return Prize.UNKNOWN;
    else
      return prize;
  }

  public Prize peek() {
    return prize;
  }
}


abstract class Player {
  protected int selection;
  protected GameshowHost host;

  public Player(GameshowHost host) {
    this.host = host;
  }

  public void selectFirstTime() {
    // random number between 0 and 2 inclusive
    Random rand = new Random();
    selection = rand.nextInt(3);
  }

  abstract void selectSecondTime();

  public boolean play() {
    selectFirstTime();
    host.hearFirstSelection(selection);
    selectSecondTime();
    host.hearSecondSelection(selection);
    Door [] doors = host.getDoors();
    return doors[selection].look() == Prize.CAR;
  }
}

class KeepingPlayer extends Player {
  public KeepingPlayer(GameshowHost host) {
    super(host);
  }

  @Override
  public void selectSecondTime() {
    // no change to selection; do nothing
  }
}

class SwitchingPlayer extends Player {
  public SwitchingPlayer(GameshowHost host) {
    super(host);
  }

  @Override
  public void selectSecondTime() {
    // get access to the Doors
    Door [] doors = host.getDoors();

    // for loop through doors.
    // skip over door from first selection
    // skip over door if it is open and the Prize is a Goat
    int newSelection = 0;
    for (int i = 0; i < doors.length; i++)
      if (i != selection && doors[i].look() == Prize.UNKNOWN)
        newSelection = i;

    selection = newSelection;
  }
}

class GameshowHost {
  private final Door [] doors;

  public GameshowHost() {
    Random rand = new Random();
    doors = new Door[] { new Door(Prize.GOAT), new Door(Prize.GOAT), new Door(Prize.GOAT)};
    doors[rand.nextInt(doors.length)] = new Door(Prize.CAR);
  }

  public Door [] getDoors() {
    return doors;
  }

  public void hearFirstSelection(int selection) {
    // respond by looping through doors, opening
    // the one which is NOT the selection but which contains a Goat
    for (int i = 0; i < doors.length; i++) {
      if (i != selection && doors[i].peek() == Prize.GOAT) {
        doors[i].open();
        return;
      }
    }
  }

  public void hearSecondSelection(int selection) {
    // just open that door
    doors[selection].open();
  }
}

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
  public static void main(String[] args) {
    int numWins = 0;
    final int NUM_TRIALS = 1000;
    SimpleFactoryPlayer fact = new SimpleFactoryPlayer();
    for (int i = 0; i < NUM_TRIALS; i++) {
      GameshowHost montyHall = new GameshowHost();
      Player player = fact.create("keep", montyHall);
      boolean won = player.play();
      if (won)
        numWins++;
    }
    System.out.println("KeepingPlayer: " + (numWins / 1000.0));

    numWins = 0;
    for (int i = 0; i < NUM_TRIALS; i++) {
      GameshowHost montyHall = new GameshowHost();
      Player player = fact.create("switch", montyHall);
      boolean won = player.play();
      if (won)
        numWins++;
    }
    System.out.println("SwitchingPlayer: " + (numWins / 1000.0));


  }
}
