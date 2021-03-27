import java.util.Random;


//Top level class that starts the simulation and tracks data
public class Control {

	static Inspector inspector1 = new Inspector();
	static Inspector inspector2 = new Inspector();
	static Workstation workstation1 = new Workstation(1);
	static Workstation workstation2 = new Workstation(2);
	static Workstation workstation3 = new Workstation(3);
	
	static double sim_time_sec = 0 ;
	static double sim_time_min = 0;
	
	private static int inspector1_totalIdleTime;
	private static int inspector2_totalIdleTime;
	
	private final static double ins1Lambda = 0.09654457;
	private final static double ins2Lambda = 0.064363;
	private final static double ins3Lambda = 0.048467;
	private static final double ws1Lambda = 0.2171;
	private static final double ws2Lambda = 0.2172;
	private static final double ws3Lambda = 0.09015;
	
	public static void main(String args[]) {
		
		System.out.println("Beginning simulation");
		
	
		inspector1.setStatus(0);
		inspector2.setStatus(0);
		inspector1_totalIdleTime = 0;
		inspector2_totalIdleTime = 0;
		
		//discrete time loop
		// set the simulation time to an 8 hour shift
	while(sim_time_min < 480) {
		
		//Advance time by 1 sec
		sim_time_sec++;
		sim_time_min = sim_time_sec / 60;
		//Print current system time
		
		if (sim_time_min %1 ==0) {
			System.out.println("---------------");
			System.out.println("time : " + sim_time_min);
			System.out.println("inspector1 status : " + inspector1.getStatus() + ", inspector 2 status: " + inspector2.getStatus());
			System.out.println("inspector1 total idle time : " + inspector1_totalIdleTime/60 + ", inspector2 total idle time : " + inspector2_totalIdleTime/60 + "\n");
		}
		
		//Check status of inspector 
		
		//Check status of inspectors
		if (inspector1.getStatus() == 0) {
			
			
			//Get component and set status
			//Inspector 1 only deals with component 1
			inspector1.addComponent(new Component(1));
			//Set inspectors status as busy
			inspector1.setStatus(1);
			//Set a time to work on this component 
			inspector1.setRemainingTime((int) (calculateInspectionTime(1) * 60));
			System.out.println("["+ sim_time_min +"] Inspector 1 is getting a component, inspection time: " + inspector1.getRemainingTime()/60);
		} 
		
		if (inspector1.getStatus() == 1) {
			//Inspector is inspecting, set remaining time
			int time_left = inspector1.getRemainingTime();
			//System.out.println("Inspector 1 is inspecting a component. Remaining time: " +time_left);
			if (time_left == 0) {
				//inspector has finished an inspection
				inspector1.setRemainingTime(0);
				finishComponent(inspector1, inspector1.getComponent()); //this method will change the status of the inspector
			} else {
				inspector1.setRemainingTime(time_left-1);
			}
			
		}  
		// May or may not exist as an else if??
		if (inspector1.getStatus() == 2) {
			//Inspector1 is blocked at this time
			//Attempt to finish component, if blocked add to block time
			finishComponent(inspector1, inspector1.getComponent());
			if (inspector1.getStatus() == 2) {
				int idleTime = inspector1.getIdleTime_ms();
				inspector1.setIdleTime_ms(idleTime+1);
				inspector1_totalIdleTime++;
			}
		}
		
		
		
		//Inspector has finished inspecting a component. Inspector sends finished component to a buffer.
		//If buffer is full, inspector must wait
		if (inspector2.getStatus() == 0) {	
			//Get component and set status
			//Inspector 2 will get component 2 or 3 randomly
			int randComponent = (int)(Math.random()*(3-2+1)+2);  
			inspector2.addComponent(new Component(randComponent));
			//Set inspectors status as busy
			inspector2.setStatus(1);
			//Set a time to work on this component 
			inspector2.setRemainingTime((int) (calculateInspectionTime(randComponent) * 60));
			System.out.println("["+ sim_time_min +"] Inspector 2 is getting a component, inspection time: " + inspector2.getRemainingTime()/60);
		}
		

		if (inspector2.getStatus() == 1) {
			//Inspector is inspecting, set remaining time
			int time_left = inspector2.getRemainingTime();
			//System.out.println("Inspector 2 is inspecting a component. Remaining time: " +time_left);
			if (time_left == 0) {
				//inspector has finished an inspection
				inspector2.setRemainingTime(0);
				finishComponent(inspector2, inspector2.getComponent()); //this method will change the status of the inspector
			} else {
				inspector2.setRemainingTime(time_left-1);
			}
			
		}
		
		if (inspector2.getStatus() == 2) {
			//Inspector2 is blocked at this time
			//Attempt to finish component, if blocked add to block time
			finishComponent(inspector2, inspector2.getComponent());
			if (inspector2.getStatus() == 2) {
				int idleTime = inspector2.getIdleTime_ms();
				inspector2.setIdleTime_ms(idleTime+1);
				inspector2_totalIdleTime++;
			}
			//check full flag to make product

			
		}
		
		//Check if workstations can begin to process a product
		
		if (workstation1.getStatus() == 0) {
			if (workstation1.canMake(1)) {
				workstation1.setStatus(1);
				workstation1.setProcessingTime((int) (calculateProcessingTime(1) * 60));
				System.out.println("["+ sim_time_min +"]["+ sim_time_min +"] WS1 is beginning to process an item at time " + sim_time_min +", time remaining: " + workstation1.getProcessTime());
				System.out.println("["+ sim_time_min +"]["+ sim_time_min +"] WS1 buffer size: " + workstation1.getBufferSize(1));
			}
		} else if (workstation1.getStatus() == 1) {
			int Ptime = workstation1.getProcessTime();
			if (Ptime > 0) {
				workstation1.setProcessingTime(Ptime-1);
			} else if (Ptime == 0) {
				System.out.println("["+ sim_time_min +"]["+ sim_time_sec +"] WS1 has finished creating a product at time " + sim_time_min);
				System.out.println("["+ sim_time_min +"] WS1 buffer size (before finishing): " + workstation1.getBufferSize(1));
				workstation1.finishProduct(1); //this method sets status back to 0, and removes comps from buffer
				
			}
		}
		
		if (workstation2.getStatus() == 0 && workstation2.canMake(2)) {
			workstation2.setStatus(1);
			workstation2.setProcessingTime((int) (calculateProcessingTime(2) * 60));
			System.out.println("["+ sim_time_min +"] WS2 is beginning to process an item at time " + sim_time_min +", time remaining: " + workstation2.getProcessTime());
			System.out.println("["+ sim_time_min +"] WS2 buffer1 size: "+ workstation2.getBufferSize(1) +", WS2 buffer2 size: " + workstation2.getBufferSize(2));
		} else if (workstation2.getStatus() == 1) {
			int Ptime = workstation2.getProcessTime();
			if (Ptime > 0) {
				workstation2.setProcessingTime(Ptime-1);
			} else if (Ptime == 0) {
				System.out.println("["+ sim_time_min +"] WS2 has finished creating a product at time " + sim_time_min);
				System.out.println("["+ sim_time_min +"] WS2 buffer1 size (before finishing): " + workstation2.getBufferSize(1) + ", WS2 buffer2 size:" + workstation2.getBufferSize(2));
				workstation2.finishProduct(2); //this method sets status back to 0, and removes comps from buffer
				
			}
		}
		
		
		if (workstation3.getStatus() == 0 && workstation3.canMake(3)) {
			workstation3.setStatus(1);
			workstation3.setProcessingTime((int) (calculateProcessingTime(3) * 60));
			System.out.println("["+ sim_time_min +"] WS3 is beginning to process an item at time " + sim_time_min +", time remaining: " + workstation3.getProcessTime());
			System.out.println("["+ sim_time_min +"] WS3 buffer1 size: "+ workstation3.getBufferSize(1) +", WS3 buffer3 size: " + workstation3.getBufferSize(3));
		} else if (workstation3.getStatus() == 1) {
			int Ptime = workstation3.getProcessTime();
			if (Ptime > 0) {
				workstation3.setProcessingTime(Ptime-1);
			} else if (Ptime == 0) {
				System.out.println("["+ sim_time_min +"] WS3 has finished creating a product at time " + sim_time_min);
				System.out.println("["+ sim_time_min +"] WS3 buffer1 size (before finishing): " + workstation3.getBufferSize(1) + ", WS3 buffer3 size:" + workstation3.getBufferSize(3));
				workstation3.finishProduct(3); //this method sets status back to 0, and removes comps from buffer
				
			}
		}

		
		//End of simulation time period
		}
	
	
	
	}
	
	//Gets time (in s) from distribution to set time for workstations and inspectors
	private static double calculateInspectionTime(int inspector) {
		
	        Random RandomGen = new Random();

	        double randomNum;
	        double time, x;
	        double randomD;
	        randomNum = RandomGen.nextInt();
	        // Calc within bound by dividing by max value
	        randomD = Math.abs(randomNum/Integer.MAX_VALUE);
	        
	        switch (inspector) {
	        	case 1:	
	        		 	// CDF calculation for inspection times
	        			x = (-1/ins1Lambda)*Math.log(1-randomD);
	        			time = (-1/ins1Lambda)*Math.log(1-randomD);
	        			time = Math.round(time*100.0)/100.0;

	        			break;
	        	case 2:	
        		 	// CDF calculation for inspection times
        			x = (-1/ins2Lambda)*Math.log(1-randomD);
        			time = (-1/ins2Lambda)*Math.log(1-randomD);
					time = Math.round(time*100.0)/100.0;
					break;
	        	case 3:	
        		 	// CDF calculation for inspection times
        			x = (-1/ins3Lambda)*Math.log(1-randomD);
        			time = (-1/ins3Lambda)*Math.log(1-randomD);
					time = Math.round(time*100.0)/100.0;
					break;
        		default : x = 0; time = 0;
	        }
	        	        
		return time;
	}
	
	//Gets time (in s) from distribution to set time for workstations and inspectors
	private static double calculateProcessingTime(int workstation) {
		
	        Random RandomGen = new Random();

	        double randomNum;
	        double time, x;
	        double randomD;
	        randomNum = RandomGen.nextInt();
	        // Calc within bound by dividing by max value
	        randomD = Math.abs(randomNum/Integer.MAX_VALUE);
	        
	        switch (workstation) {
	        	case 1:	
	        		 	// CDF calculation for inspection times
	        			x = (-1/ws1Lambda)*Math.log(1-randomD);
	        			time = (-1/ws1Lambda)*Math.log(1-randomD);
						time = Math.round(time*100.0)/100.0;

						System.out.println("WS1: x = " + x+ ", time = " + time);
	        			break;
	        	case 2:	
        		 	// CDF calculation for inspection times
        			x = (-1/ws2Lambda)*Math.log(1-randomD);
        			time = (-1/ws2Lambda)*Math.log(1-randomD);
					time = Math.round(time*100.0)/100.0;
					break;
	        	case 3:	
        		 	// CDF calculation for inspection times
        			x = (-1/ws3Lambda)*Math.log(1-randomD);
        			time = (-1/ws3Lambda)*Math.log(1-randomD);
					time = Math.round(time*100.0)/100.0;
					break;
        		default : time = 0;
	        }
	        	        
		return time;
	}
	
	//This method runs when an inspector has finished processing a component
	private static void finishComponent (Inspector inspector, Component comp) {
		if (comp.getComponentNum() == 1){
			//Component 1 is routed to the buffer with smallest number of components in waiting
			//For ties, priority is: W1 > W2 > W3
			//set status back to idle
			inspector.setStatus(0);
			//Check where to send component
			int bufferSize1 = workstation1.getBufferSize(1);
			int bufferSize2 = workstation2.getBufferSize(1);
			int bufferSize3 = workstation3.getBufferSize(1);
			
			//send component to the shortest buffer in ranked priority
			//if buffers are full, inspector is blocked
				if (bufferSize1 == 0) {
					workstation1.addToBuffer(comp);
					System.out.println("["+ sim_time_min +"] Component 1 has been inspected, sent to WS1, buffer size: " + workstation1.getBufferSize(1));
				} else if (bufferSize2 == 0) {
					workstation2.addToBuffer(comp);
					System.out.println("["+ sim_time_min +"] Component 1 has been inspected, sent to WS2, buffer size: " + workstation2.getBufferSize(1));
				} else if (bufferSize3 == 0) {
					workstation3.addToBuffer(comp);
					System.out.println("["+ sim_time_min +"] Component 1 has been inspected, sent to WS3, buffer size: " + workstation3.getBufferSize(1));
				} else if (bufferSize1 == 1) {
					workstation1.addToBuffer(comp);
					System.out.println("["+ sim_time_min +"] Component 1 has been inspected, sent to WS1, buffer size: " + workstation1.getBufferSize(1));
				} else if (bufferSize2 == 1) {
					workstation2.addToBuffer(comp);
					System.out.println("["+ sim_time_min +"] Component 1 has been inspected, sent to WS2, buffer size: " + workstation2.getBufferSize(1));
				} else if (bufferSize3 == 1) {
					workstation3.addToBuffer(comp);
					System.out.println("["+ sim_time_min +"] Component 1 has been inspected, sent to WS3, buffer size: " + workstation3.getBufferSize(1));
				} else {
					//System.out.println("["+sim_time_ms+"] Inspector 1 is blocked!");
					//set status to block
					inspector.setStatus(2);
				}
			
		} else if (comp.getComponentNum() == 2) {
			//Component 2 has finished being inspected
			//There is only one place to send components
			//temporarily set status back to idle
			inspector.setStatus(0);
			//There is only 1 place to send component 2
			int bufferSize = workstation2.getBufferSize(2);
			
			//if buffers are full, inspector is blocked
				if (bufferSize < 2 ) {
					workstation2.addToBuffer(comp);
					System.out.println("["+ sim_time_min +"] Component 2 has been inspected, sent to WS2, buffer size: " + workstation2.getBufferSize(2));
				} else {
					//System.out.println("["+sim_time_ms+"] Inspector 2 is blocked with component 2!");
					//set status to block
					inspector.setStatus(2);
				}
				
		} else if (comp.getComponentNum() == 3) {
		
			//component 3 has finished inspection
			inspector.setStatus(0);
			int bufferSize = workstation3.getBufferSize(3);
			
			if (bufferSize < 2 ) {
				workstation3.addToBuffer(comp);
				System.out.println("["+ sim_time_min +"] Component 3 has been inspected, sent to WS3, buffer size: " + workstation3.getBufferSize(3));
			} else {
				//System.out.println("["+sim_time_ms+"] Inspector 2 is blocked with component 3!");
				//set status to block
				inspector.setStatus(2);
			}
		}
	}
	
}
