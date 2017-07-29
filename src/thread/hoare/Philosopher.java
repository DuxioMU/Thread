package thread.hoare;

import java.util.Random;

import javax.swing.JTextArea;

public class Philosopher implements Runnable{

    public Philosopher(){

    }

    public Philosopher(int id, ChopstickArray chopstickArray){
        this.id = id;
        this.chopstickArray = chopstickArray;
    }

    public synchronized void thinking(){
        if(state){ // 如果在思考,说明这个哲学家两面的筷子没用
            chopstickArray.getId(id).setAvailable(true);
            chopstickArray.getLast(id).setAvailable(true);
            System.out.println(this.toString() + "在思考");
            try{
                Thread.sleep(1000);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        state = false;
    }

    public synchronized void eating(){
        if(!state){ // 在思考
            if(chopstickArray.getId(id).isAvailable()){ // 如果哲学家右手边的筷子可用
                if(chopstickArray.getLast(id).isAvailable()){ // 如果左手边的筷子也可用
                    // 然后将这个能吃饭的哲学家两侧的筷子都设置为不可用
                    chopstickArray.getId(id).setAvailable(false);
                    chopstickArray.getLast(id).setAvailable(false);
                    System.out.println(this.toString() + "在吃饭");
                    try{
                        Thread.sleep(1000);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }else{
                    // 右手边的筷子可用，但是左手边的不可用
                    System.out.println(this.toString() + "在等待"
                            + chopstickArray.getLast(id));
                    try{
                        wait(new Random().nextInt(100));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }else{
                // 如果哲学家右手边的筷子不可用则等待
                System.out.println(this.toString() + "在等待"
                        + chopstickArray.getId(id));
                try{
                    wait(new Random().nextInt(100));
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        state = true;
    }

    @Override
    public void run(){
        for(int i = 0; i < 10; ++i){
            thinking();
            eating();
        }
    }

    @Override
    public String toString(){
        return " 哲学家 " + id;
    }

    private int id;
    private boolean state;
    ChopstickArray chopstickArray;

}