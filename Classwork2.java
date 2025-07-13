import org.jetbrains.annotations.NotNull;

import java.util.Random;

enum Prize {
    GOAT, CAR, UNKNOWN
}

class Door {
    private boolean open;
    private final @NotNull Prize prize;

    public Door(@NotNull Prize prize) {
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
        doors = new Door[3];
        int index_car = rand.nextInt(3);
        for (int i = 0; i < doors.length; i++) {
        Prize prize;
        if (i == index_car) {
            prize = Prize.CAR;
        }
        else {
            prize = Prize.GOAT;
        }
        doors[i] = new Door(prize);
        }
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

public class Main {
    public static void main(String[] args) {
        int numWins = 0;
        final int NUM_TRIALS = 1000;
        for (int i = 0; i < NUM_TRIALS; i++) {
            GameshowHost montyHall = new GameshowHost();
            Player player = new KeepingPlayer(montyHall);
            boolean won = player.play();
            if (won)
                numWins++;
        }
        System.out.println("KeepingPlayer: " + (numWins / 1000.0));

        numWins = 0;
        for (int i = 0; i < NUM_TRIALS; i++) {
            GameshowHost montyHall = new GameshowHost();
            Player player = new SwitchingPlayer(montyHall);
            boolean won = player.play();
            if (won)
                numWins++;
        }
        System.out.println("SwitchingPlayer: " + (numWins / 1000.0));
    }
}
