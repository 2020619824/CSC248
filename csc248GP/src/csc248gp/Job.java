
package csc248gp;


public class Job {
    private String job;
    private int arrivalTime;
    private int burstTime;
    private int holdTime;
    private boolean toExecuteStatus;
    private boolean executingStatus;
    private int completeTime;
    private int turnaroundTime;
    private int waitingTime;
    
    public Job()
    {
        job=null;
        arrivalTime=0;
        burstTime=0;
        holdTime=0;
        toExecuteStatus = false;
        executingStatus = false;
        completeTime=0;
        turnaroundTime = 0;
        waitingTime = 0;
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
    public void setToExecuteStatus(boolean t){toExecuteStatus = t;}
    public void setExecutingStatus(boolean e){executingStatus = e;}
    public void setCompletionTime(int c){completeTime=c;}
    public void setTurnaroundTime(int t){turnaroundTime = t;}
    public void setWaitingTime(int w){waitingTime = w;}
    
    public String getJob(){return job;}
    public int getArrivalTime(){return arrivalTime;}
    public int getBurstTime(){return burstTime;}
    public int getHoldTime(){return holdTime;}
    public boolean getToExecuteStatus(){return toExecuteStatus;}
    public boolean getExecutingStatus(){return executingStatus;}
    public int getCompletionTime(){return completeTime;}
    public int getTurnaroundTime(){return turnaroundTime;}
    public int getWaitingTime(){return waitingTime;}
}
