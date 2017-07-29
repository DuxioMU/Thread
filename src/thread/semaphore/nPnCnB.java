package thread.semaphore;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class nPnCnB {
	
	static final int CAPACITY=5;
	int B[]= new int[CAPACITY];
	Semaphore sput;		//����ʹ�õĿջ�������
	Semaphore sget;		//�������ڿ���ʹ�õĲ�Ʒ��
	int sputptr,sgetptr;		//ѭ������ָ��
	Semaphore s1,s2; //����ʹ��putptr��getptr
	
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
					System.out.println("������Ʒ��"+i);
					s1.release();//ͬ��
					sget.release();//���������߿���ȡ����	
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
					System.out.println("ȡ����Ʒ:"+B[sgetptr]);
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
