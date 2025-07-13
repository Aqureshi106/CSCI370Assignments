import java.util.*;

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

  public boolean play(StateGame state) {
    selectFirstTime();
    state.setSelection(selection);
    host.hearFirstSelection(selection);
    selectSecondTime();
    state.setSelection(selection);
    host.hearSecondSelection(selection);
    Door[] doors = host.getDoors();
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
    Door[] doors = host.getDoors();

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
  private final Door[] doors;

  public GameshowHost() {
    Random rand = new Random();
    doors = new Door[] { new Door(Prize.GOAT), new Door(Prize.GOAT), new Door(Prize.GOAT) };
    doors[rand.nextInt(doors.length)] = new Door(Prize.CAR);
  }

  public Door[] getDoors() {
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

// TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
  public static void main(String[] args) {
    int numWins = 0;
    final int NUM_TRIALS = 1000;
    for (int i = 0; i < NUM_TRIALS; i++) {
      GameshowHost montyHall = new GameshowHost();
      Door[] doors = montyHall.getDoors();
      StateGame state = new StateGame(doors, -1);
      Player player = new KeepingPlayer(montyHall);
      MementoGame save = state.mementoSave();
      boolean won = player.play(state);
      state.MementoRestore(save);
      if (won)
        numWins++;
    }
    System.out.println("KeepingPlayer: " + (numWins / 1000.0));

    numWins = 0;
    for (int i = 0; i < NUM_TRIALS; i++) {
      GameshowHost montyHall = new GameshowHost();
      Door[] doors = montyHall.getDoors();
      StateGame state = new StateGame(doors, -1);
      Player player = new SwitchingPlayer(montyHall);
      MementoGame save = state.mementoSave();
      boolean won = player.play(state);
      state.MementoRestore(save);
      if (won)
        numWins++;
    }
    System.out.println("SwitchingPlayer: " + (numWins / 1000.0));

  }
}

class MementoGame {
  private final Prize[] prizes;
  private final boolean[] openStates;
  private final int selection;

  public MementoGame(Prize[] prizes, boolean[] openStates, int selection) {
    this.prizes = Arrays.copyOf(prizes, prizes.length);
    this.openStates = Arrays.copyOf(openStates, openStates.length);
    this.selection = selection;
  }

  public Prize[] getPrizes() {
    return prizes;
  }

  public boolean[] getOpenStates() {
    return openStates;
  }

  public int getSelection() {
    return selection;
  }
}

class StateGame {
  private Door[] doors;
  private int playerChoice;

  public StateGame(Door[] doors, int playerChoice) {
    this.doors = doors;
    this.playerChoice = playerChoice;
  }

  public MementoGame mementoSave() {
    Prize[] prizes = new Prize[doors.length];
    boolean[] openStates = new boolean[doors.length];
    for (int i = 0; i < doors.length; i++) {
      prizes[i] = doors[i].peek();
      openStates[i] = doors[i].isOpen();
    }
    return new MementoGame(prizes, openStates, playerChoice);
  }

  public void MementoRestore(MementoGame memento) {
    Prize[] prizes = memento.getPrizes();
    boolean openStates[] = memento.getOpenStates();
    Door[] restoredDoors = new Door[prizes.length];
    for (int i = 0; i < prizes.length; i++) {
      restoredDoors[i] = new Door(prizes[i]);
      if (openStates[i]) {
        restoredDoors[i].open();
      }
    }
      this.doors = restoredDoors;
      this.playerChoice = memento.getSelection();
  }

  public int getSelection() {
    return playerChoice;
  }

  public void setSelection(int playerChoice) {
    this.playerChoice = playerChoice;
  }

  public Door[] getDoors() {
    return doors;
  }
}
