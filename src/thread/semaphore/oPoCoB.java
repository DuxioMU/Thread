package thread.semaphore;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class oPoCoB extends Thread{
	
	int B;
	Semaphore sput; //����ʹ�õĿջ�������
	Semaphore sget;	//�������ڿ���ʹ�õĲ�Ʒ��
	//sput = 1;		//���������������һ����Ʒ
	//sget = 0;		//��������û�в�Ʒ
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
					System.out.println("�����˲�Ʒ"+B);
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
					System.out.println("��ò�Ʒ"+B);
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
