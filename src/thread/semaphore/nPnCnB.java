package thread.semaphore;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class nPnCnB {
	
	static final int CAPACITY=5;
	int B[]= new int[CAPACITY];
	Semaphore sput;		//可以使用的空缓冲区数
	Semaphore sget;		//缓冲区内可以使用的产品数
	int sputptr,sgetptr;		//循环队列指针
	Semaphore s1,s2; //互斥使用putptr，getptr
	
	public nPnCnB(){
		this.sput=new Semaphore(CAPACITY);
		this.sget=new Semaphore(0);
		this.sputptr=0;
		this.sgetptr=0;
		this.s1=new Semaphore(1);
		this.s2=new Semaphore(1);
	}
	
	class Producer implements Runnable{
		@Override
		public void run() {
			for(int i=0;i<100;i++){
				try {
					sput.acquire();
					s1.acquire();
					B[sputptr]=i;
					sputptr=(sputptr+1)%CAPACITY;
					System.out.println("生产产品："+i);
					s1.release();//同步
					sget.release();//告诉消费者可以取货了	
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
					s2.acquire();
					System.out.println("取出产品:"+B[sgetptr]);
					sgetptr=(sgetptr+1)%CAPACITY;
					Thread.sleep(2000);
					s2.release();
					sput.release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		nPnCnB pcb=new nPnCnB();
		new Thread(pcb.new Producer()).start();
		new Thread(pcb.new Consumer()).start();
	}
	
	
	
	
	
}
