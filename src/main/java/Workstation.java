import java.util.ArrayList;

public class Workstation {
	//Buffer of size 2 holds components 
    private ArrayList<Component> buffer1;
    private ArrayList<Component> buffer2;
    private ArrayList<Component> buffer3;
    //flags 1 and 2 signal a component is available at this station. Full flags signal buffer is full
    private int flag1, flag2, flag3;
    private int full_flag1, full_flag2, full_flag3;
    //WorkstationNumber is assigned to each workstation
    private int workstationNumber;
    
    //status: 0=idle, 1=creating a product
    private int status;
    //
    private int full_flag;
    //Each workstation will process a product for a set period of time
    private int remainingProcessingTime;
    //Constructor for workstation. Each workstation makes its respective product (WS1 makes product 1)
    public Workstation(int workstationNum){
    	workstationNumber = workstationNum;
    	
    	//Set unused buffers to null, initialize flags
    	if (workstationNum == 1) {
    		buffer1 = new ArrayList<Component>();
    		buffer2 = null;
    		buffer3 = null;
    		full_flag1 = 0;
    		full_flag2 = 1;
    		full_flag3 = 1;
    		flag1 = 0;
    		flag2 = 1;
    		flag3 = 1;
    	} else if (workstationNum == 2 ){ 
    		buffer1 = new ArrayList<Component>();
    		buffer2 = new ArrayList<Component>();
    		buffer3 = null;
    		full_flag1 = 0;
    		full_flag2 = 0;
    		full_flag3 = 1;
    		flag1 = 0;
    		flag2 = 0;
    		flag3 = 1;
    	} else if (workstationNum == 3) {
    		buffer1 = new ArrayList<Component>();
    		buffer2 = null;
    		buffer3 = new ArrayList<Component>();
    		full_flag1 = 0;
    		full_flag2 = 1;
    		full_flag3 = 0;
    		flag1 = 0;
    		flag2 = 1;
    		flag3 = 0;
    	}
    }
    
    //Check and return the size of the buffer
    public int getBufferSize (int bufferNum) {
    	int bufferSize = 0;
        switch (bufferNum) {
	        case 1: if (buffer1 != null) { bufferSize = buffer1.size(); } 
	        		break;
	        case 2: if (buffer2 != null) { bufferSize = buffer2.size(); } 
					break; 
	        case 3: if (buffer3 != null) { bufferSize = buffer3.size(); } 
	        		break;
	        default: bufferSize = -1;
	        		break;
        }
        return bufferSize;
    }
    

    //Add a component to the buffer
    public void addToBuffer(Component comp){
        int compNum = comp.getComponentNum();
        //Add component to the right buffer, based on component#
        switch (compNum) {
	        case 1: if (buffer1.size() <2 ) {buffer1.add(comp);} 
	        		break;
	        case 2: if (buffer2.size() <2 ) {buffer2.add(comp);} 
					break; 
	        case 3:  if (buffer3.size() <2 ) {buffer3.add(comp);} 
	        		break;
	        default: break;
        }
    }

    
    //returns true if a product can be made
    public boolean canMake(int workstation) {
    	
    	if (workstation == 1) {
    		if (buffer1 != null && buffer1.size() > 0) {
    			flag1 = 1 ;
    		} else {
    			flag1 = 0;
    		}
    	} else if (workstation == 2) {
    		if (buffer1 != null && buffer1.size() > 0) {
    			flag1 =1 ;
    		} else {
    			flag1 = 0;
    		}
    		if (buffer2 != null && buffer2.size() > 0) {
    			flag2 = 1;
    		} else {
    			flag2 = 0;
    		}
    		
    	} else if (workstation == 3) {
    		if (buffer1 != null && buffer1.size() > 0) {
    			flag1 =1 ;
    		} else {
    			flag1 = 0;
    		}
    		if (buffer3 != null && buffer3.size() > 0) {
    			flag3 = 1;
    		} else {
    			flag3 = 0;
    		}
    		
    	}
    	
    	
    	if (flag1 == 1 && flag2 == 1 && flag3 == 1) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    
    public void finishProduct(int product) {
    	
    	if (product == 1) {
    		buffer1.remove(buffer1.size()-1);
    	} else if (product ==2) {
    		buffer1.remove(buffer1.size()-1);
    		buffer2.remove(buffer2.size()-1);
    	} else if (product == 3) {
    		buffer1.remove(buffer1.size()-1);
    		buffer3.remove(buffer3.size()-1);
    	}
    	status = 0;
    }
    
    public void setProcessingTime (int time_ms) {
    	remainingProcessingTime = time_ms;
    }
    
    //Time in ms. 
    public int getProcessTime () {
    	return remainingProcessingTime;
    }
    
    public int getStatus() {
    	return status;
    }
    
    public void setStatus(int status) {
    	this.status = status;
    }
}
