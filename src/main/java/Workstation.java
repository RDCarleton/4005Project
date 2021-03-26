import java.util.ArrayList;
import java.util.Random;

public class Workstation {
    private ArrayList<Component> buffers;
    private int productNumber;
    private double ws1Lambda;
    private double ws2Lambda;
    private double ws3Lambda;
    Random ws1RNG;
    Random ws2RNG;
    Random ws3RNG;

    public Workstation(){
        // Setup all lambda values and initialize all RNGs
        ws1Lambda = 0.2171;
        ws2Lambda = 0.2172;
        ws3Lambda = 0.09015;
        ws1RNG = new Random();
        ws2RNG = new Random();
        ws3RNG = new Random();
    }
    public void setProductNumber(int number){
        productNumber = number;
    }

    public void addToBuffer(Component c){
        buffers.add(c);
    }

    public double ws1RNG(){
        double randomNum = 0, ws1Time = 0;
        // Get next int from respective RNG
        randomNum = ws1RNG.nextInt();
        // Return time value calculated
        return calculateTime(ws1Time, randomNum);
    }
    public double ws2RNG(){
        double randomNum = 0, ws2Time = 0;
        // Get next int from respective RNG
        randomNum = ws2RNG.nextInt();
        // Return time value calculated
        return calculateTime(ws2Time, randomNum);
    }
    public double ws3RNG(){
        double randomNum = 0, ws3Time = 0;
        // Get next int from respective RNG
        randomNum = ws3RNG.nextInt();
        // Return time value calculated
        return calculateTime(ws3Time, randomNum);
    }
    public double calculateTime(double lambda, double randomNum){
        double time, randomD;
        // Calc within bound by dividing by max value
        randomD = randomNum/Integer.MAX_VALUE;
        // CDF calculation for ws1Time
        time = (-1/ws1Lambda)*Math.log(1-randomD);
        return time;
    }
    public int createProduct(){
        int flag1 = 0, flag2 = 0;
        int index1 = 0, index2 = 0;
        // handle P1
        if(productNumber==1){
            for(Component c : buffers){
                if (c.getComponentType()==1){
                    index1 = buffers.indexOf(c);
                    buffers.remove(index1);
                    return 1;
                }
            }
        }
        // handle P2
        else if(productNumber==2){
            // check if at least 1 C1 and C2 exist, then track index to remove items and create product
            for(Component c : buffers){
                if (c.getComponentType()==1){
                    flag1 = 1;
                    index1 = buffers.indexOf(c);
                }
                if (c.getComponentType()==2){
                    flag2 = 1;
                    index2 = buffers.indexOf(c);
                }
            }
        }
        // handle P3
        else if(productNumber==3){
            // check if at least 1 C1 and C3 exist, then track index to remove items and create product
            for(Component c : buffers){
                if (c.getComponentType()==1){
                    flag1 = 1;
                    index1 = buffers.indexOf(c);
                }
                if (c.getComponentType()==3){
                    flag2 = 1;
                    index2 = buffers.indexOf(c);
                }
            }
        }
        // if necessary components exist then remove from buffer and return new product
        if (flag1==1 && flag2==1){
            buffers.remove(index1);
            buffers.remove(index2);
            return 1;
        }
        return 0;
    }
}
