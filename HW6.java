import java.util.*;

enum Prize {
  GOAT, CAR, UNKNOWN
}

interface Observer {
  void update(Door door);
}

class Door {
  private boolean open = false;
  private boolean chosen = false;
  private final List<Observer> observers = new ArrayList<Observer>();
  private final Prize prize;

  public Door(Prize prize) {
    this.prize = prize;
  }

  public void open() {
    if (open == false) {
      this.open = true;
      notifyObservers();
    }
  }

  public void close() {
    if (open == true) {
      this.open = false;
      notifyObservers();
    }
  }

  public void chose() {
    if (chosen == false) {
      this.chosen = true;
      notifyObservers();
    }
  }

  public void notChosen() {
    if (chosen == true) {
      this.chosen = false;
      notifyObservers();
    }
  }

  public boolean isChosen() {
    return chosen;
  }

  public void appendObserver(Observer i) {
    observers.add(i);
  }

  private void notifyObservers() {
    for (Observer i : observers) {
      i.update(this);
    }
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

abstract class Player implements Observer {
  protected Door[] doors;
  protected Door chosenDoor;
  protected boolean firstChosenComplete = false;
  protected static int countWin = 0;

  public Player(Door[] doors) {
    this.doors = doors;
    for (Door d : doors) d.appendObserver(this);
  }

  public void selectFirstTime() {
    // random number between 0 and 2 inclusive
    Random rand = new Random();
    chosenDoor = doors[rand.nextInt(doors.length)];
    chosenDoor.chose();
    firstChosenComplete = true;
  }

  abstract void selectSecondTime();

  public static void restartWins() {
    countWin = 0;
  }

  @Override
  public void update(Door door) {
      if (door == chosenDoor && door.isOpen() && door.peek() == Prize.CAR) {
          countWin++;
      }
  }

  public static int getWins() {
    return countWin;
  }
}

class KeepingPlayer extends Player {
  public KeepingPlayer(Door[] doors) {
    super(doors);
  }

  @Override
  public void selectSecondTime() {
    chosenDoor.chose();
  }
}

class SwitchingPlayer extends Player {
  public SwitchingPlayer(Door[] doors) {
    super(doors);
  }

  @Override
  public void update(Door door) {
    super.update(door);
    if (door.isOpen() && firstChosenComplete && door != chosenDoor && door.peek() == Prize.GOAT) {
      chosenDoor.notChosen();
      for (Door d : doors) {
        if (!d.isOpen() && d != chosenDoor) {
          chosenDoor = d;
          d.chose();
          break;
        }
      }
    }
    if (door == chosenDoor && door.isOpen() && door.peek() == Prize.CAR) {
      countWin++;
    }
  }

  @Override
  public void selectSecondTime() {
      chosenDoor.chose();
  }
}

 class GameshowHost implements Observer {
  private final Door [] doors;
  private int countSelection = 0;

  public GameshowHost() {
    doors = new Door[3];
    List<Prize> prizes = new ArrayList<Prize>(Arrays.asList(Prize.GOAT, Prize.GOAT, Prize.CAR));
    Collections.shuffle(prizes);
    for (int i = 0; i < 3; i++) {
      doors[i] = new Door(prizes.get(i));
    }
    for (Door d : doors) d.appendObserver(this);
  }

  public Door [] getDoors() {
    return doors;
  }

  public void update(Door door) {
    if (door.isChosen()) {
      countSelection++;
      if (countSelection == 1) {
        for (Door d : doors) {
          if (d != door && d.peek() == Prize.GOAT) {
            d.open();
            break;
          }
        }
      }
      else if (countSelection == 2) {
        for (Door d : doors) {
        if (d.isChosen()) {
        d.open();
        break;
      }
    }
  }
    }
  }
 }

public class Main {
  public static void main(String[] args) {
    final int NUM_TRIALS = 1000;
    Player.restartWins();
    for (int i = 0; i < NUM_TRIALS; i++) {
      GameshowHost montyHall = new GameshowHost();
      Player player = new KeepingPlayer(montyHall.getDoors());
      player.selectFirstTime();
      player.selectSecondTime();
      player.chosenDoor.open();
    }
  System.out.println("KeepingPlayer: " + (Player.getWins() / (double) NUM_TRIALS));
    Player.restartWins();
    for (int i = 0; i < NUM_TRIALS; i++) {
      GameshowHost montyHall = new GameshowHost();
      Player player = new SwitchingPlayer(montyHall.getDoors());
      player.selectFirstTime();
      player.selectSecondTime();
      player.chosenDoor.open();
    }
    System.out.println("SwitchingPlayer: " + (Player.getWins() / (double) NUM_TRIALS));
  }
}
