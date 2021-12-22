package csc248gp;

import java.util.Scanner;
public class Csc248GP 
{
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        
        Queue inputQ = new Queue();
        Queue tempInputQ = new Queue();
        Queue executeQ = new Queue();
        Queue tempExecuteQ = new Queue();
        Queue waitQ = new Queue();
        Queue doneQ = new Queue();
        
        //input
        /*char loop = 'Y';
        while (Character.toUpperCase(loop) == 'Y')
        {
            System.out.print("\nEnter Job: ");
            String job = sc.nextLine();
            System.out.print("Enter Arrival Time: ");
            int aTime = Integer.parseInt(sc.nextLine());
            System.out.print("Enter Burst Time: ");
            int bTime = Integer.parseInt(sc.nextLine());
            System.out.print("Do you to input more?: ");
            loop = sc.nextLine().charAt(0);
            
            Job newJob = new Job(job, aTime, bTime);
            inputQ.enqueue(newJob);
        }Penat nak input satu2 ;(*/
        
        //nak senang input siap2
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
        
        //time loop
        boolean executeStatus = false;
        int time = 1;
        for (int n=1; n<=18; n++)
        {
            System.out.println("\nTime: " +n);
            
            Job temp;
            while (!inputQ.isEmpty())
            {
                temp = (Job)inputQ.dequeue();
                
                if (temp.getArrivalTime()==n)
                {
                    executeQ.enqueue(temp);
                    executeStatus = true;
                }
                else 
                    tempInputQ.enqueue(temp);
            }
            if (executeQ.isEmpty())
                System.out.println("End");
            else
                time++;
            
            while (!executeQ.isEmpty())
            {
                temp = (Job)executeQ.dequeue();
                
                if (n<=temp.getBurstTime() && executeStatus)
                {
                    System.out.println("Job "+temp.getJob()+" is executing...");
                    if (n==(temp.getBurstTime()+temp.getHoldTime()+temp.getArrivalTime()))
                    {
                        executeStatus = false;
                        doneQ.enqueue(temp);
                    }
                    else
                        tempExecuteQ.enqueue(temp);
                }
                else 
                {
                    if (n==temp.getArrivalTime())
                        System.out.println("Job "+temp.getJob()+" has arrived...");
                    else
                        System.out.println("Job "+temp.getJob()+" is in hold for "+temp.getHoldTime()+"ms...");
                    
                    waitQ.enqueue(temp);
                }
            }
            
            while (!waitQ.isEmpty())
            {
                temp = (Job)waitQ.dequeue(); 
                
                if (!tempExecuteQ.isEmpty())
                {
                    temp.setHoldTime(temp.getHoldTime()+1);
                }
                
                tempExecuteQ.enqueue(temp);
            }
            
            while (!tempInputQ.isEmpty())
            {
                temp = (Job)tempInputQ.dequeue();
                inputQ.enqueue(temp);
            }
            
            while (!tempExecuteQ.isEmpty())
            {
                temp = (Job)tempExecuteQ.dequeue();
                executeQ.enqueue(temp);
            }
        }
    }
}
