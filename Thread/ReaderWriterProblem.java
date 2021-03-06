// EPI
// https://en.wikipedia.org/wiki/Readers%E2%80%93writers_problem
/*
First readers-writers problem
Suppose we have a shared memory area with the basic constraints. It is possible to protect the shared data behind 
a mutual exclusion mutex, in which case no two threads can access the data at the same time. However, this solution is suboptimal, 
because it is possible that a reader R1 might have the lock, and then another reader R2 requests access. It would be foolish for R2 to wait 
until R1 was done before starting its own read operation; instead, R2 should start right away. This is the motivation for the 
first readers-writers problem, in which the constraint is added that no reader shall be kept waiting if the share is currently opened for reading. 
This is also called readers-preference

Solution:
- Read lock
- Write lock
- Track number of concurrent readers

Reader Thread
- Locks the read lock and increment the read counter and releases the read lock
- Read the data
- Locks the read lock and decrement the read counter and releases the read lock and call notify() to any waiting writer thread

Writer Thread
- locks the write lock. Performs the following in an infinite loop
  - It locks the read lock, checks to see if the read counter is 0. If so, then performs its write and releases the read lock and break out of the loop
    and releases the write lock
  - Otherwise, wait()
*/
import java.math.*;
import java.util.*;

public class ReaderWriterProblem{
  public static class RWMonitor{
    // read lock
    public static Object readLock = new Object();
    // write lock
    public static Object writeLock = new Object();
    public static int readCount = 0;
    static String data = new Date().toString();
  }

  public static class Task {
    static Random r = new Random();
    static void doSomeThingElse() {
      BigInteger b = BigInteger.probablePrime(512, r);
      System.out.println(" identified a big prime: " + (b.mod(BigInteger.TEN)));
      try {
        Thread.sleep(r.nextInt(1000));
      } catch (InterruptedException e) {
        // Time to move on.
      }
    }
  }

  public static class Writer extends Thread{
    String name;
    public Writer(String n){
      name = n;
    }
    public void run(){
      while(true){
        // Increment the read counter and start reading
        synchronized(RWMonitor.writeLock){
          boolean done = false;
          while(!done){
            synchronized(RWMonitor.readLock){
              // Number of reader is 0
              if(RWMonitor.readCount == 0){
                // write data
                System.out.println("Writer " + name + " is about to write");                
                RWMonitor.data = new Date().toString();
                done = true;
              }
              else{
                try{
                  // wait
                  while(RWMonitor.readCount != 0){
                    RWMonitor.readLock.wait();
                  }
                }
                catch(InterruptedException e){

                }
              }
            }
          }
        }
        Task.doSomeThingElse();
      }
    }
  }

  public static class Reader extends Thread{
    String name;
    public Reader(String n){
      name = n;
    }
    public void run(){
      while(true){
        // Increment the read counter and start reading
        synchronized(RWMonitor.readLock){
          RWMonitor.readCount++;
        }
        System.out.println("Reader " + name + " is about to read");        
        // print data
        System.out.println(RWMonitor.data);

        // Decrement the read counter and stop reading
        synchronized(RWMonitor.readLock){
          RWMonitor.readCount--;
          // release the read lock
          RWMonitor.readLock.notify();
        }
        Task.doSomeThingElse();
      }
    }
  }

  public static void main(String[] args) throws Exception {
    Thread r0 = new Reader("r0");
    Thread r1 = new Reader("r1");
    Thread w0 = new Writer("w0");
    Thread w1 = new Writer("w1");
    r0.start();
    r1.start();
    w0.start();
    w1.start();

    try {
      Thread.sleep(10000);
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.exit(0);  
  }  
}
