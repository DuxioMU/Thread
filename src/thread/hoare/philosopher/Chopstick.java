package thread.hoare.philosopher;
/**
 * ʹ�ùܳ�ʵ����ѧ������ 
 *  ��ʾ������
 * @author Administrator
 *
 */
public class Chopstick {
	
	private int id;					//����id
	private volatile boolean available=true;//��ʾ�����Ƿ����
	
	public Chopstick(){};
	
	public Chopstick(int id){
		this.id=id;
	}
	
	public boolean isAvailable(){
		return available;
	}
	
	public void setAvailable(boolean available){
		this.available=available;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id=id;
	}
	public String toString(){
		return "����"+id;
	}
	
	
}
