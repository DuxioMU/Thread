package thread.semaphore;

import java.util.concurrent.Semaphore;

/**
 * 1>
	Semaphore semaphore = new Semaphore(1);
	其中的初始值 1 表示当前的信号量当前所允许访问的线程数；
	并且这个初始值 1 是可以通过semaphore.acquire()或semaphore.release()函数进行改变的，
	简而言之，就是信号量的初始值是可以进行改变的，不会因信号量的初始值而限制；

	2>
	semaphore.acquire()是获得一个许可，若信号量中的许可数已为0，则这个函数会出现等待的状况；
	若已获取许可，则这个信号量的许可数为减一；
	semaphore.release()是释放一个许可，若之前信号量的许可数已为0，那么当调用semaphore.release()
	函数时，则此时的信号量中的许可数就会变为1；简而言之，就是将这个信号量中的许可数在原来的基础上
	增加一个，而无论信号量之前的初始值是什么；

	3>
	semaphore.availablePermits()表示信号量semaphore此刻所拥有的许可数的个数

 * @author Administrator
 *
 */
public class TestSemaphore {

	public static void main(String[] args) {
		/**
		 * 第二个测试试列：
		 * 在第一个实例的基础上，将Semaphore的初始允许数由1改为了0
		 * 第三个测试：
		 * 在第二个示例的基础上，将semaphore.acquire()和semaphore.release()的位置互换了一下
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
