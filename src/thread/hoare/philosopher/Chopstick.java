package thread.hoare.philosopher;
/**
 * 使用管程实现哲学家问题 
 *  表示筷子类
 * @author Administrator
 *
 */
public class Chopstick {
	
	private int id;					//筷子id
	private volatile boolean available=true;//表示筷子是否可用
	
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
		return "筷子"+id;
	}
	
	
}
