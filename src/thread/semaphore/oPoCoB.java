package thread.semaphore;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class oPoCoB extends Thread{
	
	int B;
	Semaphore sput; //可以使用的空缓存区数
	Semaphore sget;	//缓冲区内可以使用的产品数
	//sput = 1;		//缓冲区内允许放入一件产品
	//sget = 0;		//缓冲区内没有产品
	public oPoCoB(int sput,int sget){
		this.sput=new Semaphore(sput);
		this.sget=new Semaphore(sget);
	}
	
	class Producer implements Runnable{
		
		@Override
		public void run() {
			while(true){
				try {
					sput.acquire();
					Random r=new Random();
					B=r.nextInt(100);
					System.out.println("生产了产品"+B);
					sleep(1000);
					sget.release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	class Consumer implements Runnable{

		@Override
		public void run() {
			while(true){
				try {
					sget.acquire();
					System.out.println("获得产品"+B);
					sleep(1000);
					sput.release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		oPoCoB pcb=new oPoCoB(1,0);
		new Thread(pcb.new Producer()).start();
		new Thread(pcb.new Consumer()).start();
	}
	
	
	
}
