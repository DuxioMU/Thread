package thread.hoare;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 参考:http://blog.csdn.net/maitianshouwei/article/details/51519150
 * @author Administrator
 *
 * @param <E>
 */
public class BlockingQueue<E> {
	
	
	private ReentrantLock lock;//全局锁
	private Condition notFull;//条件变量,用来监控队列是否已满
	private Condition notEmpty;//条件变量,用来监控队列是否为空
	private final int capacity;//容量,这是一个定容的队列
	private int head;//队列头部指针
	private int tail;//队列尾部指针
	private int count;//当前元素个数
	private E[] data ;//保存元素的数组
	
	/*
	 * 初始化阻塞队列
	 */
	@SuppressWarnings("unchecked")
	public BlockingQueue(int c){
		this.lock=new ReentrantLock();
		this .notEmpty=lock.newCondition();
		this.notFull=lock.newCondition();
		this.capacity=c;
		this.count=0;
		this.head=0;
		this.tail=0;
		data=(E[])new Object[c+1];
	}
	/*
	 * 向队列中添加一个元素
	 */
	public void put(E e){
		lock.lock();
		try {
			while(this.count==this.capacity){//判断队列是否已满
				notFull.await();//照成当前线程在接到信号或被中断之前一直处在等待状态
			}
			tail++;
			count++;
			if(tail==this.capacity+1)
				tail =0;
			data[tail]=e;//插入元素
			notEmpty.signal();//通知其他线程,队列不为空
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}finally{
			lock.unlock();
		}
		
	}
	/*
	 * 获取队列容量
	 */
	public int getCap(){
		return this.capacity;
	}
	/*
	 * 判读队列是否为空
	 */
	public boolean isEmpty(){
		lock.lock();
		try{
			return count==0?true:false;
		}finally{
			lock.unlock();
		}
	}
	
	/*
	 * 判断队列是否已满
	 */
	public boolean isFull(){
		lock.lock();
		try{
			return count==capacity?true:false;
		}finally{
			lock.unlock();
		}
	}
	/*
	 * 从队列中取出一个元素
	 */
	public E take() throws InterruptedException{
		lock.lock();
		try{
			while(this.count==0)//
				notEmpty.await();//阻塞队列
			head++;
			count--;
			if(head==this.capacity+1)
				head=0;
			E x = data[head];//获得头部元素
			notFull.signal();//通知其他线程,队列未满
			return x;
		}finally{
			lock.unlock();
		}
	}
	/*
	 * 在上述所有的操作中,几乎每个操作都在如下的结构体中进行,为了防止代码
	 * 异常退出而导致锁没有被释放,必须使用finally关键字确保锁的释放.
	 * lock.lock();
	 * try{
	 * 	return count==capacity?true:false;
	 * }Finally{
	 * 	lock.unlock();
	 * }
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
