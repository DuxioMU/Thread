package thread.hoare.philosopher;

public class ChopstickArray {
	
	private Chopstick[] chopsticks;
	public ChopstickArray(){};
	
	public ChopstickArray(int size){
		chopsticks = new Chopstick[size];
		for(int i = 0; i <chopsticks.length;++i ){
			chopsticks[i] =new Chopstick(i);
		}
	}
	public Chopstick getId(int id){
		return chopsticks[id];
	}
	
	public Chopstick getLast(int id){
		if(id == 0){
			return chopsticks[chopsticks.length-1];
		}else{
			return chopsticks[id-1];
		}
	}
	
}
