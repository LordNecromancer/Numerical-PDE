import org.tc33.jheatchart.HeatChart;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;


public class Laplace {
    static Grid theGrid = new Grid(20, 10); //Defines resolution of grid
    private final static double TOLERANCE = Math.pow(10,-15);

     private final static double w=1;


    public static void main(String[] kittens) throws IOException{
        System.out.println("SOR Finite Difference Method for Laplace's Equation");
        initializeGrid();
        double max=0;
        int n=0;
      do{
          max=0;
          n++;
            for(int i = 1; i < theGrid.getWidth()-1; i++){
                for (int j = 1; j < theGrid.getHeight()-1; j++){

                    double temp=theGrid.getPoint(i,j).getVoltage();
                    theGrid.getPoint(i, j).setVoltage((1-w)*theGrid.getPoint(i,j).getVoltage()+(0.25) *w* ( theGrid.getPoint(i, j).getNorth().getVoltage() + theGrid.getPoint(i, j).getSouth().getVoltage() + theGrid.getPoint(i, j).getEast().getVoltage() + theGrid.getPoint(i, j).getWest().getVoltage()));
                    if(Math.abs(theGrid.getPoint(i,j).getVoltage()-temp)>max){
                        max=Math.abs(theGrid.getPoint(i,j).getVoltage()-temp);
                    }

                }
            }
        } while (max>TOLERANCE &&n<Math.pow(10,6));

        System.out.println(n);
        printGrid();
        HeatChart map = new HeatChart(theGrid.getVoltageArray());
        map.setTitle("Voltage Distribution, #1");
        map.setXAxisLabel("X Axis");
        map.setYAxisLabel("Y Axis");

        map.saveToFile(new File("laplace.png"));
    }

    public static void initializeGrid(){
        for(int i = 0; i < theGrid.getWidth(); i++){

            for(int j = 0; j < theGrid.getHeight(); j++){
                theGrid.getPoint(0,j).setVoltage(10);

                if (theGrid.getPoint(i, j).isCorner()){
                    theGrid.getPoint(i, j).setVoltage(0);
                }

            }
        }



    }

    public static void printGrid(){

        DecimalFormat df = new DecimalFormat("#.##");

        for(int i = 0; i < theGrid.getWidth(); i++){
            for(int j = 0; j < theGrid.getHeight(); j++){
                System.out.print(df.format(theGrid.getPoint(i, j).getVoltage()) + "  \t ");
            }
            System.out.println();
        }
    }

}
