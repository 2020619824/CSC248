
package csc248gp;


public class Job {
    private String job;
    private int arrivalTime;
    private int burstTime;
    
    public Job(String j,int a,int b){
        job=j;
        arrivalTime=a;
        burstTime=b;
    }
    
    public String getJob(){return job;}
    public int getArrivalTime(){return arrivalTime;}
    public int getBurstTime(){return burstTime;}
}
