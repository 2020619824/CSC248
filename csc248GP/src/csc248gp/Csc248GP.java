package csc248gp;

/*Yeay dh jadi sorting tu :D
kalau korang ade ape2 nak tambah/ubah buat je mane tahu lgi better.*/

import java.util.Scanner;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Csc248GP 
{
    /*
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        
        Queue inputQ = new Queue();
        Queue tempInputQ = new Queue();
        Queue executeQ = new Queue();
        Queue tempExecuteQ = new Queue();
        Queue waitQ = new Queue();
        Queue doneQ = new Queue();
        Queue toSortQ = new Queue();
        Queue interuptedQ = new Queue();
        
        int numJob=0;
        double tt=0.0;
        double wt=0.0;
        double totalTT=0.0;
        double totalWT=0.0;
        
        //input
        int loop = 0;
        while (loop == 0)
        {
            JPanel panel = new JPanel();
            JTextField jobName = new JTextField(5);
            JTextField arrivalTime = new JTextField(5);
            JTextField burstTime = new JTextField(5);

            panel.add(new JLabel("Job Name:"));
            panel.add(jobName);
            panel.add(new JLabel("Arrival Time :"));
            panel.add(arrivalTime);
            panel.add(new JLabel("Burst Time:"));
            panel.add(burstTime);
            
            JOptionPane.showConfirmDialog(null, panel, "Enter Job Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
            String job = jobName.getText();
            int aTime = Integer.parseInt(arrivalTime.getText());
            int bTime = Integer.parseInt(burstTime.getText());

            while (job.isEmpty() ||aTime == 0 || bTime == 0)
            {
                if (job.isEmpty())
                    JOptionPane.showMessageDialog(null, "Please input name of the Job" ,"Invalid Input", JOptionPane.INFORMATION_MESSAGE);
                if (aTime == 0 || bTime == 0)
                    JOptionPane.showMessageDialog(null, "Time cannot be zero" ,"Invalid Input", JOptionPane.ERROR_MESSAGE);

                JOptionPane.showConfirmDialog(null, panel, "Enter Job Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                job = jobName.getText();
                aTime = Integer.parseInt(arrivalTime.getText());
                bTime = Integer.parseInt(burstTime.getText());
            }

            Job newJob = new Job(job, aTime, bTime);
            inputQ.enqueue(newJob);

            loop = JOptionPane.showConfirmDialog(null, "More Job to Input? ", "", JOptionPane.YES_NO_OPTION);
        }
        
        /*For Robust Testing
        Job A = new Job();
        Job B = new Job();
        Job C = new Job();
        Job D = new Job();
        Job E = new Job();
        
        A.setJob("A");
        B.setJob("B");
        C.setJob("C");
        D.setJob("D");
        E.setJob("E");
        
        A.setArrivalTime(1);
        B.setArrivalTime(3);
        C.setArrivalTime(6);
        D.setArrivalTime(6);
        E.setArrivalTime(9);
        
        A.setBurstTime(10);
        B.setBurstTime(1);
        C.setBurstTime(3);
        D.setBurstTime(1);
        E.setBurstTime(2);
        
        inputQ.enqueue(A);
        inputQ.enqueue(B);
        inputQ.enqueue(C);
        inputQ.enqueue(D);
        inputQ.enqueue(E);
        
        
        Job interupt = new Job();
        //time loop
        int time = 1;
        for (int n=1; n<=time; n++)
        {
            Queue checkInQ = new Queue();
            
            System.out.println("\nTime: " +n);
            
            //check sama ada semua job dh diexecute/belum
            if (executeQ.isEmpty()&&inputQ.isEmpty()&&interuptedQ.isEmpty()) //kalau dah
                System.out.println("End");
            else //kalau belum
                time++;
            
            Job temp;
            while (!inputQ.isEmpty())
            {
                temp = (Job)inputQ.dequeue();
                
                //check sama ade job tu dah boleh diexecute/belum
                if (temp.getArrivalTime()==n) //kalau dh boleh
                {
                    temp.setToExecuteStatus(true);
                    toSortQ.enqueue(temp);
                }
                else //kalau belum
                    tempInputQ.enqueue(temp);
            }
            
            while (!interuptedQ.isEmpty())
            {
                temp = (Job)interuptedQ.dequeue();
                toSortQ.enqueue(temp);
            }
            
            toSortQ = HoldSorting(toSortQ);
            
            while (!toSortQ.isEmpty())
            {
                temp = (Job)toSortQ.dequeue();
                Job temp2;
                
                if (temp.equals(interupt))
                    temp.setExecutingStatus(true);
                else
                {
                    temp2 = (Job)toSortQ.getFront();

                    while (!toSortQ.isEmpty() && executeQ.isEmpty() && temp.getBurstTime()!= temp2.getBurstTime())
                    {
                        temp2 = (Job)toSortQ.dequeue();
                        if (temp2.getBurstTime()<=temp.getBurstTime())
                        {
                            temp2.setExecutingStatus(true);
                        }
                        else if (temp2.equals(interupt))
                            temp2.setExecutingStatus(true);
                        
                        executeQ.enqueue(temp2);
                    }

                    if (executeQ.isEmpty())
                    {
                        temp.setExecutingStatus(true);
                    }
                    else
                    {
                        temp.setExecutingStatus(false);
                    }
                }
                executeQ.enqueue(temp);
            }
            
            while (!executeQ.isEmpty())
            {
                temp = (Job)executeQ.dequeue();
                
                if (temp.getToExecuteStatus() && temp.getExecutingStatus()) //kalau job tu dh sampai pastu takde job yg tengah process, so diye akan buat yg ni
                {
                    System.out.println("Job "+temp.getJob()+" is executing...");
                    if (n==(temp.getBurstTime()+temp.getHoldTime()+temp.getArrivalTime()-1))
                    {
                        temp.setExecutingStatus(false);
                        temp.setCompletionTime(n+1);//set completion time
                        doneQ.enqueue(temp);
                        numJob++;//increment number of job
                    }
                    else
                        tempExecuteQ.enqueue(temp);
                }
                else //kalau job tu dh sampai pastu dah ade job yg tengah process, so diye akan buat yg ni
                {
                    if (n==temp.getArrivalTime())
                    {
                        System.out.println("Job "+temp.getJob()+" has arrived...");
                        temp.setHoldTime(1);
                    }
                    else
                    {
                        temp.setHoldTime(temp.getHoldTime()+1); //increment kan holdtime utk job yg tengah waiting tu
                        System.out.println("Job "+temp.getJob()+" is in hold for "+temp.getHoldTime()+"ms...");
                    }
                    
                    waitQ.enqueue(temp);
                }
            }
            
            while (!tempInputQ.isEmpty()) //utk masukkan balik job ke dlm inputQ yg asal 
            {
                temp = (Job)tempInputQ.dequeue();
                if (temp.getArrivalTime() == time)
                    checkInQ.enqueue(temp);
                inputQ.enqueue(temp);
            }
            
            //sortingkan jobs yg tengah in hold tu
            waitQ = BurstSorting(waitQ); 
            
            while (!waitQ.isEmpty()) 
            {
                temp = (Job)waitQ.dequeue(); 
                
                //check kalau job yg tengah process tadi dah sudah/belum 
                if (tempExecuteQ.isEmpty()) //kalau belum
                {
                    if (checkInQ.isEmpty())
                    {
                        temp.setExecutingStatus(true);
                        tempExecuteQ.enqueue(temp);
                    }
                    else
                    {
                        Job temp2 = (Job)checkInQ.dequeue();
                        Job temp4 = new Job();
                        if (checkInQ.isEmpty())
                            temp4 = temp2;
                        else
                        {
                            while (!checkInQ.isEmpty())
                            {
                                Job temp3 = (Job)checkInQ.dequeue();
                                if (temp3.getBurstTime()<temp2.getBurstTime())
                                    temp4 = temp3;
                                else
                                    temp4 = temp2;
                            }
                        }
                        
                        if (temp.getBurstTime()<=temp4.getBurstTime())
                        {
                            temp.setExecutingStatus(true);
                            tempExecuteQ.enqueue(temp);
                        }
                        else
                        {
                            temp4.setExecutingStatus(true);
                            interupt = temp4;
                            interuptedQ.enqueue(temp);
                        }
                    }
                }
                else 
                     tempExecuteQ.enqueue(temp);
                
            }
            
            while (!tempExecuteQ.isEmpty()) //utk masukkan balik job ke dlm executeQ yg asal
            {
                temp = (Job)tempExecuteQ.dequeue();
                executeQ.enqueue(temp);
            }
            
            
            while(!doneQ.isEmpty()){
                temp=(Job)doneQ.dequeue();
                tt=temp.getCompletionTime()-temp.getArrivalTime();
                wt=temp.getCompletionTime()-(temp.getArrivalTime()+temp.getBurstTime());
                totalTT+=tt;
                totalWT+=wt;
            }
               
        }
        
        //dah dapat TT dgn WT
        System.out.print("\nAverage turn-around time: "+totalTT/numJob);
        System.out.print("\nAverage waiting time: "+totalWT/numJob);
    }*/
    
    ///*
    public static void main(String[] args)
    {
        Queue inputQ = new Queue();
        Queue tempNewQ = new Queue();
        Queue readyQ = new Queue();
        Queue executeQ = new Queue();
        Queue tempExecuteQ = new Queue();
        Queue waitQ = new Queue();
        Queue tempWaitQ = new Queue();
        Queue doneQ = new Queue();
        Queue interruptedQ = new Queue();
        
        Job A = new Job();
        Job B = new Job();
        Job C = new Job();
        Job D = new Job();
        Job E = new Job();
        
        A.setJob("A");
        B.setJob("B");
        C.setJob("C");
        D.setJob("D");
        E.setJob("E");
        
        A.setArrivalTime(1);
        B.setArrivalTime(3);
        C.setArrivalTime(6);
        D.setArrivalTime(6);
        E.setArrivalTime(9);
        
        A.setBurstTime(10);
        B.setBurstTime(1);
        C.setBurstTime(3);
        D.setBurstTime(1);
        E.setBurstTime(2);
        
        inputQ.enqueue(A);
        inputQ.enqueue(B);
        inputQ.enqueue(C);
        inputQ.enqueue(D);
        inputQ.enqueue(E);
        
        boolean hasInterupt = true;
        int numJob=0;
        double tt=0.0;
        double wt=0.0;
        double totalTT=0.0;
        double totalWT=0.0;
        
        boolean firstIsEx = false;
        Job interrupt = new Job();
        int time = 1;
        for (int x=1; x<=time; x++)
        {
            Queue toSortQ = new Queue();
            Queue checkInQ = new Queue();
            Job firstEx = new Job();  
            
            System.out.println("\nTime: "+x);
            Job temp;
            
            if (executeQ.isEmpty()&&readyQ.isEmpty()&&inputQ.isEmpty()&&interruptedQ.isEmpty())
                System.out.println("End");
            else
                time++;
            
            while (!interruptedQ.isEmpty())
            {
                temp = (Job)interruptedQ.dequeue();
                readyQ.enqueue(temp);
            }
            
            if (readyQ.isEmpty()&&executeQ.isEmpty())
                firstIsEx = false;
            
            while (!inputQ.isEmpty())
            {
                temp = (Job)inputQ.dequeue();
                if (temp.getArrivalTime() <= x)
                {
                    temp.setToExecuteStatus(true);
                    toSortQ.enqueue(temp);
                    readyQ.enqueue(temp);
                }
                else
                    tempNewQ.enqueue(temp);
            }
            
            if (!toSortQ.isEmpty() && executeQ.isEmpty() && !firstIsEx)
            {
                toSortQ = BurstSorting(toSortQ);
                firstEx = (Job)toSortQ.getFront();
                firstEx.setExecutingStatus(true);
                executeQ.enqueue(firstEx);
                firstIsEx = true;
            }
            
            while (!readyQ.isEmpty())
            {
                temp = (Job)readyQ.dequeue();
                
                if (!temp.equals(interrupt))
                {
                    if (executeQ.isEmpty() && hasInterupt)
                        temp.setExecutingStatus(true);

                    if (temp.getToExecuteStatus() && temp.getExecutingStatus() && !temp.equals(firstEx))
                        executeQ.enqueue(temp);
                    else if (temp.getToExecuteStatus() && !temp.getExecutingStatus())
                        waitQ.enqueue(temp);
                }
                else
                {
                    temp.setExecutingStatus(true);
                    hasInterupt = true;
                    executeQ.enqueue(temp);
                }
            }
           
            int count=0;
            while (!executeQ.isEmpty())
            {
                temp = (Job)executeQ.dequeue();
                tempExecuteQ.enqueue(temp);
                count++;
            }
            
            for (int i=0; i<count; i++)
            {
                if (count>1)
                {
                    tempExecuteQ.dequeue();
                    count--;
                }
            }
            
            while (!tempExecuteQ.isEmpty())
            {
                temp = (Job)tempExecuteQ.dequeue();
                executeQ.enqueue(temp);
            }
                
            while (!executeQ.isEmpty())
            {
                temp = (Job)executeQ.dequeue();
                System.out.println("Job "+temp.getJob()+" is executing...");
                if (x==(temp.getBurstTime()+temp.getHoldTime()+temp.getArrivalTime()-1))
                {
                    temp.setExecutingStatus(false);
                    temp.setCompletionTime(x+1);
                    doneQ.enqueue(temp);
                    numJob++;
                }
                else
                    tempExecuteQ.enqueue(temp);
            }
            
            
            while (!waitQ.isEmpty())
            {
                temp = (Job)waitQ.dequeue();
                
                if (x==temp.getArrivalTime())
                {
                    System.out.println("Job "+temp.getJob()+" has arrived...");
                    temp.setHoldTime(1);
                }
                else 
                {
                    temp.setHoldTime(temp.getHoldTime()+1);
                    System.out.println("Job "+temp.getJob()+" is in hold for "+temp.getHoldTime()+"ms...");
                }
                
                tempWaitQ.enqueue(temp);
            }
            
            
            while (!tempNewQ.isEmpty())
            {
                temp = (Job)tempNewQ.dequeue();
                if (temp.getArrivalTime()==time)
                    checkInQ.enqueue(temp);
                inputQ.enqueue(temp);
            }
            
            checkInQ = BurstSorting(checkInQ);
            
            tempWaitQ = BurstSorting(tempWaitQ);
            
            Job temp2 = new Job();
            while (!tempWaitQ.isEmpty())
            {
                temp = (Job)tempWaitQ.dequeue();
                
                if (tempExecuteQ.isEmpty())
                {
                    if (!checkInQ.isEmpty())
                    {
                        temp2 = (Job)checkInQ.getFront();

                        if (temp.getBurstTime()<=temp2.getBurstTime())
                        {
                            readyQ.enqueue(temp);
                        }
                        else if (interruptedQ.isEmpty())
                        {
                            interrupt = temp2;
                            hasInterupt = false;
                            interruptedQ.enqueue(temp);
                        }
                    }
                    else
                    {
                        temp.setExecutingStatus(true);
                        tempExecuteQ.enqueue(temp);
                    }
                }
                else
                    readyQ.enqueue(temp);
            }
            
            
            while (!tempExecuteQ.isEmpty())
            {
                temp = (Job)tempExecuteQ.dequeue();
                executeQ.enqueue(temp);
            }
            
            while(!doneQ.isEmpty())
            {
                temp=(Job)doneQ.dequeue();
                tt=temp.getCompletionTime()-temp.getArrivalTime();
                wt=temp.getCompletionTime()-(temp.getArrivalTime()+temp.getBurstTime());
                totalTT+=tt;
                totalWT+=wt;
            }
        }
        //dah dapat TT dgn WT
        System.out.print("\nAverage turn-around time: "+totalTT/numJob);
        System.out.println("\nAverage waiting time: "+totalWT/numJob);
    }
    //*/
    //sorting data yg ada dlm queue tu ikut burst time
    //https://youtu.be/Z0hQsqJQYoc : concept nak sorting queue
    public static Queue BurstSorting(Queue job)
    {
        Queue tempJob = new Queue();
        
        int size = 0;
        while (!job.isEmpty())
        {
            Job temp = (Job)job.dequeue();
            tempJob.enqueue(temp);
            size++;
        }
        
        Job temp;
        
        for (int x=0; x<size; x++)
        {   
            Job a = (Job)tempJob.dequeue();
            while (!tempJob.isEmpty())
            {
                Job b = (Job)tempJob.dequeue();
                if (a.getBurstTime()<=b.getBurstTime())
                {
                    temp = a;
                    a = b;
                    b = temp;
                }
                job.enqueue(b);
            }
            job.enqueue(a);
            while (!job.isEmpty())
            {
                Job curr = (Job)job.dequeue();
                tempJob.enqueue(curr);
            }
        }
        
        return tempJob;
    }
    
     public static Queue HoldSorting(Queue Q)
    {
        Queue tempQ = new Queue();
        
        int size = 0;
        while (!Q.isEmpty())
        {
            Job temp = (Job)Q.dequeue();
            tempQ.enqueue(temp);
            size++;
        }
        
        Job temp;
        
        for (int x=0; x<size; x++)
        {   
            while (!tempQ.isEmpty())
            {
                Job a = (Job)tempQ.dequeue();
                while (!tempQ.isEmpty())
                {
                    Job b = (Job)tempQ.dequeue();
                    if (a.getHoldTime()>=b.getHoldTime() && !a.getExecutingStatus() && !b.getExecutingStatus())
                    {
                        temp = a;
                        a = b;
                        b = temp;
                    }
                    Q.enqueue(b);
                }
                Q.enqueue(a);
            }
            
            while (!Q.isEmpty())
            {
                Job curr = (Job)Q.dequeue();
                tempQ.enqueue(curr);
            }
       }
        
        return tempQ;
    }
}
