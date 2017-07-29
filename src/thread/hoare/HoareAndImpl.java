package thread.hoare;

import java.util.concurrent.Semaphore;
/**
 * �ź������������������Ͼ���һ�����̶���
 * @author Administrator
 * 
 *@see ͨ�������ź���������̹ܳ��̵Ļ����
 * ������,�����������ȴ����̶���,������һ����
 * ׼�ĳ�����ƽ�����˳��Ŀ��.����ʵ����wait
 * ��signal�������򵥵ĳ�����̡�
 */
public class HoareAndImpl {

	private Semaphore mutex;//���ù̹ܳ���ǰʹ�õĻ����ź���
	//����signal�Ľ��̹����Լ�(��ø����ȼ��Ľ���)���ź���
	private Semaphore next;
	private int next_count;//ֻnext�ϵȴ��Ľ�����(�����ȼ��ȴ�����)
	//�����̵ܳ��е���������
	//@SuppressWarnings("unused")
	//����Դ��ص��ź���(��Ϊ��Դ�ò������������ȴ���Դ��������)
	private Semaphore x_sem;
	//@SuppressWarnings("unused")
	private int x_count;//��x_sem�ϵȴ��Ľ�����
	
	
	public HoareAndImpl(){
		this.mutex=new Semaphore(1);
		this.next=new Semaphore(0);
		this.next_count=0;
		this.x_sem=new Semaphore(8);
		this.x_count=0;
		
	}
	
	/**
	 * ������û����̵ܳĿ��
	 * 
	 * @see �������ȼ��ȴ��Ľ��̶����н���������0ʱ,
	 * �����ͷŸ����ȼ��ĵȴ�����,�����ͷŵ����ȼ��ȴ�����
	 */
	public void method(){
		try {
			mutex.acquire();
			
			//������
			
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
	 * �����̵ܳ�wait����
	 * @throws InterruptedException 
	 * 
	 * @see ����wait ʱ �ȴ������������(����)�Ľ�����Ҫ
	 * ��һ,�����ͷŸ����ȼ�����(����)(next�����ȼ��ȴ���
	 * �̶���),û�о��ͷŵ����ȼ����̶���(mutex�ȴ����̶�
	 * ��),�ͷ�����Լ�����. �ȴ��Լ����ͷ����ڽ��ȴ���
	 * ���������Ľ�������һ.
	 * 
	 * IM���Բ��� ����
	 */
	public void wait(Semaphore x_sem,int x_count,HoareAndImpl IM) 
			throws InterruptedException{
		
		x_count=x_count+1;//x_sem�����ȴ���������һ
		
		if(IM.next_count>0){
			IM.next.release();//�ͷŸ����ȼ��ȴ�����
		}else{
			IM.mutex.release();//�ͷŽ���ܳ���ͨ����ȴ�����
		}
		x_sem.acquire();//���Լ�������������ȴ����̶���(�ȴ����ͷ�)
		x_count=x_count-1;//(���ͷ��������ȴ���������һ)
	}
	
	
	/**
	 * �����̵ܳ�signal����
	 * @throws InterruptedException 
	 * 
	 * @see �����ж���û�еȴ���������(����)�Ľ���,���û��
	 * signal����һ���ղ���,����еȴ����̶���(������)��һ,
	 * Ȼ������������Ľ����ͷŵ�,�ٽ��Լ�����(����ȴ���
	 * �ù̵ܳĶ���),���Լ�������һ��Ҳ�ᱻ�ͷŵ�ʱ�����Ҫ
	 * ��һ.
	 * 
	 */
	public void signal(Semaphore x_sem,int x_count,HoareAndImpl IM)
			throws InterruptedException{
		if(x_count>0){
			IM.next_count=IM.next_count+1;//�ȴ���������һ
			x_sem.release();
			/*
			 * ����ȴ����õĶ���(����ǻ����ܳ�����
			 * �ŵ����Լ�����ȴ�ֱ�����ͷŽ�����
			 * ���̻ܳ�ȴ���һ������)
			 */
			IM.next.acquire();
			IM.next_count=IM.next_count-1;//�ȴ���������һ����˳��ܳ�
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
