package thread.hoare;

import java.util.concurrent.Semaphore;

/**
 * 霍尔管程实现哲学家问题
 * @author Administrator
 *
 */
public class DiningPhilosophers extends Thread {

	private String[] state=new String[5];
	private Semaphore[] s;
	private int[] s_count=new int[5];
	
	public DiningPhilosophers(){
				
	}
	
	public void test(int k){
		if(state[(k-1)%5]!="eating"&&state[k]=="hungry"&&state[(k+1)%5]!="eating"){
			state[k]="eating";
			try {
				signal(s[k],s_count[k]);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void pickup(int i){
		state[i]="hungry";
		test(i);
		if (state[i]=="eating"){
			try {
				wait(s[i],i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void putdown(int i){
		state[i]="thinking";
		test((i-1)%5);
		test((i+1)%5);
	}
	
	public void think(){
		for(int i=0;i<5;i++){
			state[i]="thinking";
			
		}
	}
	
	public void wait(Semaphore s,int k) 
			throws InterruptedException{
		
		s_count[k]=s_count[k]+1;//x_sem条件等待进程数加一
		
		if(s_count[k]>0){
			s.release();//释放高优先级等待进程
		}else{
			s.release();//释放进入管程普通互斥等待进程
		}
		s.acquire();//将自己挂起放入条件等待进程队列(等待被释放)
		s_count[k]=s_count[k]-1;//(被释放完条件等待进程数减一)
	}
	
	public void signal(Semaphore x_sem,int k)
			throws InterruptedException{
		if(s_count[k]>0){
			s_count[k]=s_count[k]+1;//等待进程数加一
			x_sem.release();
			/*
			 * 进入等待调用的队列(这就是霍尔管程中释
			 * 放掉后将自己挂起等待直到被释放进程退
			 * 出管程或等待另一个条件)
			 */
			s[k].acquire();
			s_count[k]=s_count[k]-1;//等待进程数减一完成退出管程
		}
	}
	
	public void run(){
		
		think();
		
		
	}
	
	
	
	

}
