package thread.hoare;

/**
 * ��ʾ���ӵ���
 * */
public class Chopstick{
    public Chopstick(){

    }

    public Chopstick(int id){
        this.id = id;
    }

    public boolean isAvailable(){
        return available;
    }

    public void setAvailable(boolean available){
        this.available = available;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    public String toString(){
        return "����" + id;
    }

    /**
     * ��ʾ�����Ƿ����
     * */
    private volatile boolean available = true;
    private int id;
}