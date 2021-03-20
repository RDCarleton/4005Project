import java.util.ArrayList;

public class Workstation {
    private ArrayList<Component> buffers;
    private Product productNumber;

    public Workstation(){}
    public void setProductNumber(int number){

    }

    public void addToBuffer(Component c){
        buffers.add(c);
    }
}
