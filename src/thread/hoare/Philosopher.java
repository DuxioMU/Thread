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
        if(state){ // �����˼��,˵�������ѧ������Ŀ���û��
            chopstickArray.getId(id).setAvailable(true);
            chopstickArray.getLast(id).setAvailable(true);
            System.out.println(this.toString() + "��˼��");
            try{
                Thread.sleep(1000);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        state = false;
    }

    public synchronized void eating(){
        if(!state){ // ��˼��
            if(chopstickArray.getId(id).isAvailable()){ // �����ѧ�����ֱߵĿ��ӿ���
                if(chopstickArray.getLast(id).isAvailable()){ // ������ֱߵĿ���Ҳ����
                    // Ȼ������ܳԷ�����ѧ������Ŀ��Ӷ�����Ϊ������
                    chopstickArray.getId(id).setAvailable(false);
                    chopstickArray.getLast(id).setAvailable(false);
                    System.out.println(this.toString() + "�ڳԷ�");
                    try{
                        Thread.sleep(1000);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }else{
                    // ���ֱߵĿ��ӿ��ã��������ֱߵĲ�����
                    System.out.println(this.toString() + "�ڵȴ�"
                            + chopstickArray.getLast(id));
                    try{
                        wait(new Random().nextInt(100));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }else{
                // �����ѧ�����ֱߵĿ��Ӳ�������ȴ�
                System.out.println(this.toString() + "�ڵȴ�"
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
        return " ��ѧ�� " + id;
    }

    private int id;
    private boolean state;
    ChopstickArray chopstickArray;

}