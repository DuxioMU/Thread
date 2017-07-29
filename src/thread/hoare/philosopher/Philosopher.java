package thread.hoare.philosopher;

import java.util.Random;

public class Philosopher implements Runnable{

	private int id;
	private boolean state;//true 在思考 false 在吃饭
	ChopstickArray chopstickArray;
	
	public Philosopher(){};
	
	public Philosopher(int id,ChopstickArray chopstickArray){
		this.id=id;
		this.chopstickArray = chopstickArray;
	}
	
	public synchronized void thinking() throws InterruptedException{
		if(state){ //如果在思考说明这个哲学家两边的筷子没用
			chopstickArray.getId(id).setAvailable(true);
			chopstickArray.getLast(id).setAvailable(true);
			Thread.sleep(1000);
		}
		state = false;
	}
	public synchronized void eating() throws InterruptedException{
		if(!state){
			if(chopstickArray.getId(id).isAvailable()){//如果哲学家右手的筷子可用
				if(chopstickArray.getLast(id).isAvailable()){//如果左手边的筷子也可以用
					//然后将这个能吃饭的的哲学家两侧的筷子都设置为不可用
					chopstickArray.getId(id).setAvailable(false);
					chopstickArray.getLast(id).setAvailable(false);
					System.out.println(this.toString()+"在吃饭");
					Thread.sleep(1000);
				}else{
					//右手边的筷子可用,但是左手边的不可用
					System.out.println(this.toString()+"在等待"+chopstickArray.getLast(id));
					wait(new Random().nextInt(100));
				}
			}else{
				//如果哲学家右手边的筷子不可用则等待
				System.out.println(this.toString()+"在等待"+chopstickArray.getId(id));
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
		return "哲学家"+id;
	}
	
	public static void main(String[] args){
		ChopstickArray chopstickArray = new ChopstickArray(5);
		for(int i= 0;i<5;i++){
			new Thread(new Philosopher(i,chopstickArray)).start();
		}
	}
	
}
