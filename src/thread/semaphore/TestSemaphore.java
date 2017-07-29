package thread.semaphore;

import java.util.concurrent.Semaphore;

/**
 * 1>
	Semaphore semaphore = new Semaphore(1);
	���еĳ�ʼֵ 1 ��ʾ��ǰ���ź�����ǰ��������ʵ��߳�����
	���������ʼֵ 1 �ǿ���ͨ��semaphore.acquire()��semaphore.release()�������иı�ģ�
	�����֮�������ź����ĳ�ʼֵ�ǿ��Խ��иı�ģ��������ź����ĳ�ʼֵ�����ƣ�

	2>
	semaphore.acquire()�ǻ��һ����ɣ����ź����е��������Ϊ0���������������ֵȴ���״����
	���ѻ�ȡ��ɣ�������ź����������Ϊ��һ��
	semaphore.release()���ͷ�һ����ɣ���֮ǰ�ź������������Ϊ0����ô������semaphore.release()
	����ʱ�����ʱ���ź����е�������ͻ��Ϊ1�������֮�����ǽ�����ź����е��������ԭ���Ļ�����
	����һ�����������ź���֮ǰ�ĳ�ʼֵ��ʲô��

	3>
	semaphore.availablePermits()��ʾ�ź���semaphore�˿���ӵ�е�������ĸ���

 * @author Administrator
 *
 */
public class TestSemaphore {

	public static void main(String[] args) {
		/**
		 * �ڶ����������У�
		 * �ڵ�һ��ʵ���Ļ����ϣ���Semaphore�ĳ�ʼ��������1��Ϊ��0
		 * ���������ԣ�
		 * �ڵڶ���ʾ���Ļ����ϣ���semaphore.acquire()��semaphore.release()��λ�û�����һ��
		 */
		
		Semaphore semaphore = new Semaphore(0);
		
		for(int i=0;i<2;i++){
				System.out.println(i+"================="+i);
				System.out.println(semaphore.availablePermits());
				semaphore.release();
				System.out.println(semaphore.availablePermits());
				System.out.println(i+"================="+i);
		}
		try {
			System.out.println("===============");
			System.out.println(semaphore.availablePermits());
			semaphore.acquire();
			System.out.println(semaphore.availablePermits());
			System.out.println("=================");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
