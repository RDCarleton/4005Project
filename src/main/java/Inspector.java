import java.util.ArrayList;

public class Inspector {
	
	//Status 0 = idle, 1 = inspecting component;  2 = blocked
    private int status;
    private int remaining_inspection_time_ms; //in ms
    private int idlingTime_ms;
    private Component held_component;

    public Inspector(){}
    
    public void performMaintenance(double time_sec, Component component){
        //Check the inspectors current status then follow the necessary algorithm for
        //adding a component to an open workstation buffer.
    	
    }
    
  //Changed c to component
    public void addComponent(Component component){ 
    	held_component = component;
    }
    
    public Component getComponent() {
    	return held_component;
    }
    
    public int getStatus(){
        return status;
    }
    public void setStatus(int i){
        status = i;
    }
    
    public int getRemainingTime() {
    	return remaining_inspection_time_ms;
    }
    
    public void setRemainingTime(int remaining_time) {
    	remaining_inspection_time_ms = remaining_time;
 
    }
    
    public void setIdleTime_ms(int time) {
    	idlingTime_ms = time;
    }
    
    public int getIdleTime_ms() {
    	return idlingTime_ms;
    }
    
}
