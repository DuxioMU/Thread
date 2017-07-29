package thread.semaphore;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class oPoCnB {
	int B[]; 	//�����������У�
	Semaphore sput;	//����ʹ�õĿջ��������
	Semaphore sget;	//�������ڿ���ʹ�õĲ�Ʒ��
	int putptr,getptr;	//ѭ������ָ��
	int k;
	public oPoCnB(int k){
		this.k=k;
		B=new int[k];
		this.sput=new Semaphore(k);
		this.sget=new Semaphore(0);
		this.putptr=0;
		this.getptr=0;
	}
	
	class Producer implements Runnable{

		@Override
		public void run() {
			while(true){
				try {
					int b=new Random().nextInt(100);
					sput.acquire();
					B[putptr]=b;
					System.out.println("�����ˣ�"+b);
					putptr=(putptr+1)%k;
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
					System.out.println("��ò�Ʒ��"+B[getptr]);
					getptr=(getptr+1)%k;
					Thread.sleep(2000);
					sput.release();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		oPoCnB pcb = new oPoCnB(5);
		new Thread(pcb.new Producer()).start();
		new Thread(pcb.new Consumer()).start();
	}

	
}
