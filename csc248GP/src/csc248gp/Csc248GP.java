package csc248gp;
//GIT PASSWORD: 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

import java.util.Scanner;
public class Csc248GP 
{
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        
        Queue inputQ = new Queue();
        Queue executeQ = new Queue();
        Queue waitQ = new Queue();
        
        //input
        char loop = 'Y';
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
        }
        
        //time loop
        int time = 1;
        for (int n=1; n<=time; n++)
        {
            System.out.println("\nTime: " +n);
            
            Job temp;
            while (!inputQ.isEmpty())
            {
                temp = (Job)inputQ.dequeue();
                
                //if (temp)
            }
            
            time++;
        }
    }
}
