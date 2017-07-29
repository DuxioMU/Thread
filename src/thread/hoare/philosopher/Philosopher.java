package thread.hoare.philosopher;

import java.util.Random;

public class Philosopher implements Runnable{

	private int id;
	private boolean state;//true ��˼�� false �ڳԷ�
	ChopstickArray chopstickArray;
	
	public Philosopher(){};
	
	public Philosopher(int id,ChopstickArray chopstickArray){
		this.id=id;
		this.chopstickArray = chopstickArray;
	}
	
	public synchronized void thinking() throws InterruptedException{
		if(state){ //�����˼��˵�������ѧ�����ߵĿ���û��
			chopstickArray.getId(id).setAvailable(true);
			chopstickArray.getLast(id).setAvailable(true);
			Thread.sleep(1000);
		}
		state = false;
	}
	public synchronized void eating() throws InterruptedException{
		if(!state){
			if(chopstickArray.getId(id).isAvailable()){//�����ѧ�����ֵĿ��ӿ���
				if(chopstickArray.getLast(id).isAvailable()){//������ֱߵĿ���Ҳ������
					//Ȼ������ܳԷ��ĵ���ѧ������Ŀ��Ӷ�����Ϊ������
					chopstickArray.getId(id).setAvailable(false);
					chopstickArray.getLast(id).setAvailable(false);
					System.out.println(this.toString()+"�ڳԷ�");
					Thread.sleep(1000);
				}else{
					//���ֱߵĿ��ӿ���,�������ֱߵĲ�����
					System.out.println(this.toString()+"�ڵȴ�"+chopstickArray.getLast(id));
					wait(new Random().nextInt(100));
				}
			}else{
				//�����ѧ�����ֱߵĿ��Ӳ�������ȴ�
				System.out.println(this.toString()+"�ڵȴ�"+chopstickArray.getId(id));
					wait(new Random().nextInt(100));
			}
		}
		state=true;
	}
	
	@Override
	public void run() {
		for(int i=0; i< 10;++i)	{
			try {
				thinking();
				eating();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String toString(){
		return "��ѧ��"+id;
	}
	
	public static void main(String[] args){
		ChopstickArray chopstickArray = new ChopstickArray(5);
		for(int i= 0;i<5;i++){
			new Thread(new Philosopher(i,chopstickArray)).start();
		}
	}
	
}
