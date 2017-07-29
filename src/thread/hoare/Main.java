package thread.hoare;

public class Main 
{

    public static void main(String[] args)
    {
        ChopstickArray chopstickArray = new ChopstickArray(5);
        for(int i = 0; i < 5; i++)
        {
            new Thread(new Philosopher(i, chopstickArray)).start();
        }
    }
}