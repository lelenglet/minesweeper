import java.io.Serializable;

/**
 * This class is used to measure the time elapsed during a game.
 */
public class Timer implements Serializable {
  private long elapsedTime;
  private long begin;
  private long end;

  Timer() {
    elapsedTime = 0;
  }

  /**
   * Start the timer.
   */
  public void start() {
    begin = System.currentTimeMillis();
  }

  /**
   * Stop the timer.
   */
  public void stop() {
    end = System.currentTimeMillis();
    elapsedTime += (long) (end - begin);
  }

  /**
   * Get the elapsed time.
   * 
   * @return the elapsed time
   */
  public long getElapsedTime() {
    this.stop();
    return elapsedTime / 1000;
  }
}
