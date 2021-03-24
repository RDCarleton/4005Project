import java.util.ArrayList;

public class Inspector {
    private int status;
    private ArrayList<Component> components;

    public Inspector(){}
    public void performMaintenance(){
        //Check the inspectors current status then follow the necessary algorithm for
        //adding a component to an open workstation buffer.
    }
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
