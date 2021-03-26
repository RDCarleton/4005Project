import java.util.ArrayList;

public class Workstation {
    private ArrayList<Component> buffers;
    private int productNumber;

    public Workstation(){}
    public void setProductNumber(int number){
        productNumber = number;
    }

    public void addToBuffer(Component c){
        buffers.add(c);
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
