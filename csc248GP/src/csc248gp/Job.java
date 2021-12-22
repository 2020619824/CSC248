
package csc248gp;


public class Job {
    private String job;
    private int arrivalTime;
    private int burstTime;
    private int holdTime;
    
    public Job()
    {
        job=null;
        arrivalTime=0;
        burstTime=0;
        holdTime=0;
    }
    
    public Job(String j,int a,int b){
        job=j;
        arrivalTime=a;
        burstTime=b;
    }
    
    public void setJob (String j){job = j;}
    public void setArrivalTime(int a){arrivalTime = a;}
    public void setBurstTime(int b){burstTime = b;}
    public void setHoldTime(int h){holdTime = h;}
    
    public String getJob(){return job;}
    public int getArrivalTime(){return arrivalTime;}
    public int getBurstTime(){return burstTime;}
    public int getHoldTime(){return holdTime;}
}
