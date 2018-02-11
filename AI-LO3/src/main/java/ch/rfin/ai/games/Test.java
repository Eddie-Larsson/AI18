package main.java.ch.rfin.ai.games;

import ch.rfin.ai.games.AdversarialSearchAlgorithm;
import ch.rfin.ai.games.MiniMaxTest;
import ch.rfin.ai.games.algorithms.*;

/**
 * Tests your minimax implementation using toy games.
 * You may choose to modify {@link #getAlgorithm()} if you want to.
 *
 * @author Christoffer Fink
 */
public class Test extends MiniMaxTest {

  public static void main(String[] args) {
    new Test().test();
  }

  @Override
  public AdversarialSearchAlgorithm<String,String> getAlgorithm() {
    // You can modify this if you want to, for example to pass in parameters.
    return new MiniMax<>(Integer.MAX_VALUE);
  }

}
