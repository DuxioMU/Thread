package thread.hoare;

import java.util.concurrent.Semaphore;

public class ReaderWriter {
	
	static Semaphore sem =new Semaphore(1);
	static Semaphore sem_wrt = new Semaphore(1);
	static int readercount=0;
	static String a="hello world!";
	public static void main(String args[]){
		class reader implements Runnable{
			public reader(){
				
			}

			@Override
			public void run() {
				try {
					sem.acquire();
					readercount++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(readercount==1){
					try {
						sem_wrt.acquire();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				sem.release();
				System.out.println("Reading"+a);
				
				try {
					sem.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				readercount--;
				if(readercount==0){
					sem_wrt.release();
				}
				sem.release();
			}
		}
		class writer implements Runnable{
			public writer(){
				
			}

			@Override
			public void run() {
				try {
					sem_wrt.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				a=a+"I's Jerrry";
				System.out.println("Wriing"+a);
				sem_wrt.release();
			}
		}
		for(int i=1;i<=10;i++){
			new Thread(new writer()).start();
			new Thread(new reader()).start();
			
		}
		
	}
	
	
	
}
