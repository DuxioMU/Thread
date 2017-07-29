package thread.hoare;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
/**
 * �ο�:http://blog.csdn.net/maitianshouwei/article/details/51519150
 * @author Administrator
 *
 * @param <E>
 */
public class BlockingQueue<E> {
	
	
	private ReentrantLock lock;//ȫ����
	private Condition notFull;//��������,������ض����Ƿ�����
	private Condition notEmpty;//��������,������ض����Ƿ�Ϊ��
	private final int capacity;//����,����һ�����ݵĶ���
	private int head;//����ͷ��ָ��
	private int tail;//����β��ָ��
	private int count;//��ǰԪ�ظ���
	private E[] data ;//����Ԫ�ص�����
	
	/*
	 * ��ʼ����������
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
	 * ����������һ��Ԫ��
	 */
	public void put(E e){
		lock.lock();
		try {
			while(this.count==this.capacity){//�ж϶����Ƿ�����
				notFull.await();//�ճɵ�ǰ�߳��ڽӵ��źŻ��ж�֮ǰһֱ���ڵȴ�״̬
			}
			tail++;
			count++;
			if(tail==this.capacity+1)
				tail =0;
			data[tail]=e;//����Ԫ��
			notEmpty.signal();//֪ͨ�����߳�,���в�Ϊ��
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}finally{
			lock.unlock();
		}
		
	}
	/*
	 * ��ȡ��������
	 */
	public int getCap(){
		return this.capacity;
	}
	/*
	 * �ж������Ƿ�Ϊ��
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
	 * �ж϶����Ƿ�����
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
	 * �Ӷ�����ȡ��һ��Ԫ��
	 */
	public E take() throws InterruptedException{
		lock.lock();
		try{
			while(this.count==0)//
				notEmpty.await();//��������
			head++;
			count--;
			if(head==this.capacity+1)
				head=0;
			E x = data[head];//���ͷ��Ԫ��
			notFull.signal();//֪ͨ�����߳�,����δ��
			return x;
		}finally{
			lock.unlock();
		}
	}
	/*
	 * ���������еĲ�����,����ÿ�������������µĽṹ���н���,Ϊ�˷�ֹ����
	 * �쳣�˳���������û�б��ͷ�,����ʹ��finally�ؼ���ȷ�������ͷ�.
	 * lock.lock();
	 * try{
	 * 	return count==capacity?true:false;
	 * }Finally{
	 * 	lock.unlock();
	 * }
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
