package thread.hoare;

import java.util.concurrent.Semaphore;
/**
 * 信号量和条件变量本质上就是一个进程队列
 * @author Administrator
 * 
 *@see 通过两个信号量来管理管程过程的互斥调
 * 用问题,建立了两个等待进程对列,给出了一个标
 * 准的程序设计进入和退出的框架.并且实现了wait
 * 和signal的两个简单的程序过程。
 */
public class HoareAndImpl {

	private Semaphore mutex;//调用管程过程前使用的互斥信号量
	//发出signal的进程挂起自己(获得高优先级的进程)的信号量
	private Semaphore next;
	private int next_count;//只next上等待的进程数(高优先级等待进程)
	//霍尔管程当中的条件变量
	//@SuppressWarnings("unused")
	//与资源相关的信号量(因为资源得不到满足而进入等待资源条件队列)
	private Semaphore x_sem;
	//@SuppressWarnings("unused")
	private int x_count;//在x_sem上等待的进程数
	
	
	public HoareAndImpl(){
		this.mutex=new Semaphore(1);
		this.next=new Semaphore(0);
		this.next_count=0;
		this.x_sem=new Semaphore(8);
		this.x_count=0;
		
	}
	
	/**
	 * 互斥调用霍尔管程的框架
	 * 
	 * @see 当高优先级等待的进程队列中进程数大于0时,
	 * 优先释放高优先级的等待进程,否则释放低优先级等待进程
	 */
	public void method(){
		try {
			mutex.acquire();
			
			//过程体
			
			if(next_count>0){
				next.release();
			}else{
				mutex.release();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 霍尔管程的wait过程
	 * @throws InterruptedException 
	 * 
	 * @see 调用wait 时 等待这个条件变量(队列)的进程数要
	 * 加一,首先释放高优先级队列(进程)(next高优先级等待进
	 * 程队列),没有就释放低优先级进程队列(mutex等待进程队
	 * 列),释放完后将自己挂起. 等待自己被释放完在将等待该
	 * 条件变量的进程数减一.
	 * 
	 * IM可以不用 本身
	 */
	public void wait(Semaphore x_sem,int x_count,HoareAndImpl IM) 
			throws InterruptedException{
		
		x_count=x_count+1;//x_sem条件等待进程数加一
		
		if(IM.next_count>0){
			IM.next.release();//释放高优先级等待进程
		}else{
			IM.mutex.release();//释放进入管程普通互斥等待进程
		}
		x_sem.acquire();//将自己挂起放入条件等待进程队列(等待被释放)
		x_count=x_count-1;//(被释放完条件等待进程数减一)
	}
	
	
	/**
	 * 霍尔管程的signal过程
	 * @throws InterruptedException 
	 * 
	 * @see 首先判断有没有等待条件变量(队列)的进程,如果没有
	 * signal就是一个空操作,如果有等待进程队列(进程数)加一,
	 * 然后将条件变量里的进程释放掉,再将自己挂起(进入等待调
	 * 用管程的队列),将自己挂起有一天也会被释放到时候计数要
	 * 减一.
	 * 
	 */
	public void signal(Semaphore x_sem,int x_count,HoareAndImpl IM)
			throws InterruptedException{
		if(x_count>0){
			IM.next_count=IM.next_count+1;//等待进程数加一
			x_sem.release();
			/*
			 * 进入等待调用的队列(这就是霍尔管程中释
			 * 放掉后将自己挂起等待直到被释放进程退
			 * 出管程或等待另一个条件)
			 */
			IM.next.acquire();
			IM.next_count=IM.next_count-1;//等待进程数减一完成退出管程
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
