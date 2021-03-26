import java.util.ArrayList;
import java.util.Random;

public class Inspector {
    private int status;
    private ArrayList<Component> components;
    private double ins1Lambda;
    private double ins2Lambda;
    private double ins3Lambda;
    Random ins1RNG;
    Random ins2RNG;
    Random ins3RNG;

    public Inspector(){
        ins1Lambda = 0.09654457;
        ins2Lambda = 0.064363;
        ins3Lambda = 0.048467;
        ins1RNG = new Random();
        ins2RNG = new Random();
        ins3RNG = new Random();
    }
    public void performMaintenance(){
        //Check the inspectors current status then follow the necessary algorithm for
        //adding a component to an open workstation buffer.
    }
    public double ws1RNG(){
        double randomNum = 0, ins1Time = 0;
        // Get next int from respective RNG
        randomNum = ins1RNG.nextInt();
        // Return time value calculated
        return calculateTime(ins1Time, randomNum);
    }
    public double ws2RNG(){
        double randomNum = 0, ins2Time = 0;
        // Get next int from respective RNG
        randomNum = ins2RNG.nextInt();
        // Return time value calculated
        return calculateTime(ins2Time, randomNum);
    }
    public double ws3RNG(){
        double randomNum = 0, ins3Time = 0;
        // Get next int from respective RNG
        randomNum = ins3RNG.nextInt();
        // Return time value calculated
        return calculateTime(ins3Time, randomNum);
    }
    public double calculateTime(double lambda, double randomNum){
        double time, randomD;
        // Calc within bound by dividing by max value
        randomD = randomNum/Integer.MAX_VALUE;
        // CDF calculation for ws1Time
        time = (-1/lambda)*Math.log(1-randomD);
        return time;
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
