/**
 * Himanshu Chaudhary
 *
 * This class contains two objects of FibonacciThread which calculates fibonacci series separately
 * the main class then stops the threads and prints the value at each step
 * After 10 loops, main stops both threads and exits
 */
public class FibonacciUsingThreads
{

  FibonacciThread t1 = new FibonacciThread("Fibonacci_Thread1");
  FibonacciThread t2 = new FibonacciThread("Fibonacci_Thread2");

  void printNumbers(FibonacciThread thread)
  {
    /**
     * This syncronized block locks FibonacciThread and prints the values then resumes the thread
     */
    synchronized (thread)
    {
      System.out.println(thread.getNAME() + "(" + thread.getStep() + ") " + thread.getX() + ", " +
      thread.getY() + ", " + thread.getZ());

    }
  }


  /**
   * @param args This runs in the main thread , it starts the two FibonacciThread classes and stops after 2 seconds
   *             then prints the value while the other threads pause. After 10 loops, it kills the thread and exits
   */
  public static void main(String[] args)
  {
    FibonacciUsingThreads main = new FibonacciUsingThreads();
    main.t1.start();
    main.t2.start();
    for (int i = 0; i < 10; i++)
    {
      try
      {
        Thread.sleep(2000);
        main.printNumbers(main.t1);
        main.printNumbers(main.t2);
      } catch (InterruptedException e)
      {

      }
    }
    main.t1.killProcess();   /**calls a syncronized block in FibonnaciThread to stop the thread*/
    main.t2.killProcess();
    /** will check until both the threads are dead and the exit the program*/
    while (true)
    {
      if (!main.t1.isAlive() && !main.t2.isAlive())
      {
        System.out.print("Program Exit");
        System.exit(0);
      }
    }
  }
}


/**
 * This class calculates fibonacci thread and stores three different steps in x,y and z along with strp number
 * This thread can be stopped by execution of killProcess() method which can be called from main
 */
class FibonacciThread extends Thread
{

  private final String NAME;  //Name of thread
  private long step = 0;//Steps since start.
  private long z;             //fib(step)
  private long y = 1;//fib(step-1)
  private long x = 1;//fib(step-2
  private boolean isAlive;
  private long temp;

  /**
   *
   * @param threadName
   * It is a constructor that sets the thread name to a given name and
   * also initialized isAlive value to true
   */
  FibonacciThread(String threadName)
  {
    this.NAME = threadName;
    this.isAlive = true;
  }

  /**\
   *
   * @return the Name of the thread
   */
  String getNAME()
  {
    return this.NAME;
  }

  /**
   *
   * @return the z value (current value) of the Fibonnaci series
   */
  long getZ()
  {
    return this.z;
  }

  /**
   *
   * @return the x value (step-2) value of the Fibonnaci series
   */
  long getX()
  {
    return this.x;
  }

  /**
   *
   * @return the current step number of the Fibonacci series
   */
  long getStep()
  {
    return this.step;
  }

  /**
   *
   * @return the z value (step-1) value of the Fibonacci series
   */
  long getY()
  {
    return this.y;
  }

  /**
   * This synchronized block calculates the fibonacci series until isAlive is set False
   */
  synchronized void runFibo()
  {
    /** if z becomes to large( as mentioned is spec) the values are initialized*/
    if (z == 7540113804746346429L)
    {
      x = 1;
      y = 1;
      z = x + y;
    }
    /** stores the fibonacci values stepwise in x,y,z*/
    temp = z;
    z = z + y;
    x = y;  /**sets the value of y before changing value of y to z*/
    y = temp;
    step++;
  }


  public void run()
  {
    while (isAlive)
    {
      runFibo();
    }
    /** prints the exit message and the thread closes as there is no more code to execute*/
    System.out.println("Thread " + this.NAME + " signing out at step " + step);
  }

  /** this method marks the process to be dead so that it can stop its calculation and close*/
  void killProcess()
  {
    this.isAlive = false;
  }

}



