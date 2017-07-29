package thread.semaphore;

import java.util.concurrent.Semaphore;

public class OrangeAndApple {

	int plate;
	Semaphore sp;	//���ӿ��Էż���ˮ��
	Semaphore sg1;	//�������м�������
	Semaphore sg2;	//��������ƻ��
	public OrangeAndApple(){
		this.sp=new Semaphore(1);
		this.sg1=new Semaphore(0);
		this.sg2=new Semaphore(0);
	}
	
	class Father implements Runnable{

		@Override
		public void run() {
			for(int i=1;i<10;i++){
				try {
					sp.acquire();
					plate=i;
					System.out.println("������һ��ƻ����"+i);
					sg2.release();
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	class Mother implements Runnable{
		
		@Override
		public void run() {
			for(int i=10;i<20;i++){
				try {
					sp.acquire();
					plate=i;
					System.out.println("������һ�����ӣ�"+i);
					Thread.sleep(1000);
					sg1.release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	class Son implements Runnable{

		@Override
		public void run() {
			try {
				while(true){
					sg1.acquire();
					System.out.println("����һ�����ӣ�"+plate);
					sp.release();
					Thread.sleep(2000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	class Daughter implements Runnable{

		@Override
		public void run() {
			try {
				while(true){
					sg2.acquire();
					System.out.println("����һ��ƻ����"+plate);
					sp.release();
					Thread.sleep(5000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		OrangeAndApple oaa=new OrangeAndApple();
		new Thread(oaa.new Father()).start();
		new Thread(oaa.new Mother()).start();
		new Thread(oaa.new Son()).start();
		new Thread(oaa.new Daughter()).start();
	}
}
