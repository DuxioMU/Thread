package thread.hoare.philosopher;

import java.util.concurrent.Semaphore;
/**
 * 使用信号量实现哲学家问题
 * @author Administrator
 *
 */
public class OS_Philosopher {

	static int chop[]=new int [7];
	static Semaphore []sem=new Semaphore[7];
	
	public static void main(String[] args) throws InterruptedException {
		for(int i=0;i<=6;i++){
			sem[i]=new Semaphore(1);
		}
		Thread.sleep(1000);
		class philosopher implements Runnable{
			int i;
			public philosopher(int i){
				this.i=i;
			}
			@Override
			public void run() {
				for(int j=0;j<20;j++){
					try{
						synchronized(this){
							sem[i].acquire();
							sem[(i+1)%5].acquire();
						}
					System.out.println("Phi"+i+"is Eating");
					Thread.sleep(2000);
					sem[i].release();
					sem[(i+1)%5].release();
					System.out.println("Phi"+i+"is Thinking");
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			}
		}
		philosopher t1 = new philosopher(1);
		philosopher t2 = new philosopher(2);
		philosopher t3 = new philosopher(3);
		philosopher t4 = new philosopher(4);
		philosopher t5 = new philosopher(5);
		new Thread(t1).start();
		new Thread(t2).start();
		new Thread(t3).start();
		new Thread(t4).start();
		new Thread(t5).start();
		
	}
}
