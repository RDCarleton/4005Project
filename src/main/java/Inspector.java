import java.util.ArrayList;

public class Inspector {
    private int status;
    private ArrayList<Component> components;

    public Inspector(){}
    public void performMaintenance(){}
    public void addComponent(Component c){
        components.add(c);
    }
    public int getStatus(){
        return status;
    }
    public void setStatus(int i){
        status = i;
    }
}
