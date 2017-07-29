package thread.hoare;

import java.util.concurrent.Semaphore;

/**
 * �����ܳ�ʵ����ѧ������
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
		
		s_count[k]=s_count[k]+1;//x_sem�����ȴ���������һ
		
		if(s_count[k]>0){
			s.release();//�ͷŸ����ȼ��ȴ�����
		}else{
			s.release();//�ͷŽ���ܳ���ͨ����ȴ�����
		}
		s.acquire();//���Լ�������������ȴ����̶���(�ȴ����ͷ�)
		s_count[k]=s_count[k]-1;//(���ͷ��������ȴ���������һ)
	}
	
	public void signal(Semaphore x_sem,int k)
			throws InterruptedException{
		if(s_count[k]>0){
			s_count[k]=s_count[k]+1;//�ȴ���������һ
			x_sem.release();
			/*
			 * ����ȴ����õĶ���(����ǻ����ܳ�����
			 * �ŵ����Լ�����ȴ�ֱ�����ͷŽ�����
			 * ���̻ܳ�ȴ���һ������)
			 */
			s[k].acquire();
			s_count[k]=s_count[k]-1;//�ȴ���������һ����˳��ܳ�
		}
	}
	
	public void run(){
		
		think();
		
		
	}
	
	
	
	

}
