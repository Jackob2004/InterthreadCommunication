class Q{
    int n;
    boolean wait = false;
    synchronized int get(){
        while(!wait){
            try{
                wait();
            }catch (InterruptedException e){
                System.out.println(e);
            }
        }
        System.out.println("Value is: " + n);
        notify();
        wait = false;
        return n;
    }
    synchronized void put(int n){
        while (wait){
            try{
                wait();
            }catch (InterruptedException e){
                System.out.println(e);
            }
        }
        this.n = n;
        System.out.println("Setting the value of n to: " + n);
        notify();
        wait = true;
    }
}
class Producer implements Runnable{
    Q g; //with variable reference we can access methods that are in Q class
    Thread t;
    Producer(Q g){
        this.g =g;
        t = new Thread(this,"The producer thread");

    }
    @Override
    public void run() {
        int i = 0;
        while (true){
            g.put(i++);
        }
    }
}
class Consumer implements Runnable{
    Q g;
    Thread t;
    Consumer(Q g){
        this.g = g;
        t = new Thread(this,"Consumer thread");
    }

    @Override
    public void run() {
        while (true) {
            g.get();
        }
    }
}
public class Main {
    public static void main(String[] args) {
        Q g = new Q();
        Producer p = new Producer(g);
        Consumer c = new Consumer(g);
        p.t.start();
        c.t.start();


    }
}