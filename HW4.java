import org.junit.Test;
import static org.junit.Assert.*;

public class SwitchingPlayerUnitTest {
  @Test 
  public void testFirstChoiceIsValidAndClosed() {
    GameshowHost host = new GameshowHost();
    SwitchingPlayer player = new SwitchingPlayer(host);
    player.selectFirstTime();
    int firstChoice = player.selection;
    Door[] doors = host.getDoors();
    assertTrue(firstChoice >= 0 && firstChoice <= 2);
    assertFalse(doors[firstChoice].isOpen());
  }

  public void testSecondChoiceIsValidAndClosed() {
    GameshowHost host = new GameshowHost();
    SwitchingPlayer player = new SwitchingPlayer(host);
    player.selectFirstTime();
    int firstChoice = player.selection;
    host.hearFirstSelection(firstChoice);
    player.selectSecondTime();
    int secondChoice = player.selection;
    Door[] doors = host.getDoors();
    assertTrue(secondChoice >= 0 && secondChoice <= 2);
    assertFalse(doors[secondChoice].isOpen());
    assertNotEquals(firstChoice, secondChoice);
  }
}
