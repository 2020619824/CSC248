package csc248gp;

/*
-------------------------------------------------
     ASSESSMENT #2: CSC248-DATA STRUCTURES
-------------------------------------------------
PREPARED FOR: ZAWAWI BIN ISMAIL @ ABD WAHAB

PREPARED BY:
2020450714 - WAN MARZUQI AMRIN BIN WAN MANSOR
2020469254 - NADIAH HUMAIRA BINTI AHMAD RAZILLAH
2020619824 - AHMAD NASRUDDIN BIN AHMAD NARASHID
2020894454 - AISYAH BINTI AZMAN
-------------------------------------------------
*/

import java.awt.BorderLayout;
import java.awt.Font;
import java.text.DecimalFormat;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class Csc248GP 
{   
    public static void main(String[] args)
    {
        //object instantiations of Queue
        Queue inputQ = new Queue();
        Queue tempInputQ = new Queue();
        Queue readyQ = new Queue();
        Queue executeQ = new Queue();
        Queue tempExecuteQ = new Queue();
        Queue waitQ = new Queue();
        Queue tempWaitQ = new Queue();
        Queue doneQ = new Queue();
        Queue interruptedQ = new Queue();
        Queue printInputQ = new Queue();
        Queue printOutputQ = new Queue();
        
        boolean run = true; //flag that indicate to run or to exit the program 
        run = input(inputQ, printInputQ); //called the function to ask user to input Job(s) and its details
        
        //condition that determine whether the program should run or not
        if (run)
        {
            boolean hasInterupt = false; //flag that indicate the Job that should execute next has interrupt or not
            int numJob=0; //store number of total Jobs
            double totalTT=0.0; //store total Turnaround Time
            double totalWT=0.0; //store total Waiting Time

            boolean firstIsEx = false; //flag that indicate there is first Job will execute or not
            Job interrupt = new Job(); //store Job that interupt the other Job that should execute next  
            int time = 1; //store maximum CPU time
            
            //looping the CPU time until all Job(s) has executed
            for (int x=1; x<=time; x++)
            {
                Queue toSortQ = new Queue();
                Queue checkInQ = new Queue();
                Job firstEx = new Job(); //store Job that will execute first

                System.out.println("\nTime: "+x);
                
                Job temp; //store Job that is being process in Queue

                //condition that determine whether to stop loop the CPU time or not
                if (executeQ.isEmpty()&&readyQ.isEmpty()&&inputQ.isEmpty()&&interruptedQ.isEmpty())
                    System.out.println("End");
                else
                    time++;

                //process interruptedQ
                while (!interruptedQ.isEmpty())
                {
                    temp = (Job)interruptedQ.dequeue();
                    readyQ.enqueue(temp);
                }

                //condition that indicate there is no Job that ready to be executed in previous CPU time
                if (readyQ.isEmpty()&&executeQ.isEmpty())
                    firstIsEx = false;

                //process inputQ
                while (!inputQ.isEmpty())
                {
                    temp = (Job)inputQ.dequeue();
                    //condition that check whether the Job(s) ready to execute or not
                    if (temp.getArrivalTime() <= x) 
                    {
                        temp.setToExecuteStatus(true); //set the Job to be ready to execute
                        toSortQ.enqueue(temp);
                        readyQ.enqueue(temp);
                    }
                    else
                        tempInputQ.enqueue(temp);
                }
                
                //condition that process toSortQ or not
                if (!toSortQ.isEmpty() && executeQ.isEmpty() && !firstIsEx)
                {
                    toSortQ = BurstSorting(toSortQ); //sort Jobs in toSortQ based on Burst Time
                    firstEx = (Job)toSortQ.getFront(); //get a Job with smallest Burst Time
                    firstEx.setExecutingStatus(true); //set the Job to execute in current CPU Time
                    executeQ.enqueue(firstEx);
                    firstIsEx = true; //set there is already Job that should be execute
                }

                //process readyQ
                while (!readyQ.isEmpty())
                {
                    temp = (Job)readyQ.dequeue();

                    //condition that check whether the Job that interrupt is here  
                    if (!temp.equals(interrupt))
                    {
                        //condition that indicate the Job that previously execute has done or not
                        if (executeQ.isEmpty() && !hasInterupt)
                            temp.setExecutingStatus(true); //set the Job to execute in current CPU Time

                        //condition that determine whether the Job(s) should execute or wait
                        if (temp.getToExecuteStatus() && temp.getExecutingStatus() && !temp.equals(firstEx))
                            executeQ.enqueue(temp);
                        else if (temp.getToExecuteStatus() && !temp.getExecutingStatus())
                            waitQ.enqueue(temp);
                    }
                    else
                    {
                        temp.setExecutingStatus(true); //set the Job to execute in current CPU Time
                        hasInterupt = false; //set there is no Job that will interupt 
                        executeQ.enqueue(temp);
                    }
                }

                int count=0; //store number of Jobs in executeQ
                
                //process executeQ to get total number of Jobs
                while (!executeQ.isEmpty())
                {
                    temp = (Job)executeQ.dequeue();
                    tempExecuteQ.enqueue(temp);
                    count++; //increment number of Job(s)
                }
                
                //looping the count until only one Job is in tempExecuteQ 
                for (int i=0; i<count; i++)
                {
                    //condition that check whether there is Job(s) more than one
                    if (count>1)
                    {
                        tempExecuteQ.dequeue(); //remove unnecessary Job(s)
                        count--; //increment number of Job(s)
                    }
                }
                
                //process tempExecuteQ
                while (!tempExecuteQ.isEmpty())
                {
                    temp = (Job)tempExecuteQ.dequeue();
                    executeQ.enqueue(temp);
                }

                //process executeQ
                while (!executeQ.isEmpty())
                {
                    temp = (Job)executeQ.dequeue();
                    System.out.println("Job "+temp.getJob()+" is executing...");
                    
                    //condition that determine whether the Job that currently executing has done or not 
                    if (x==(temp.getBurstTime()+temp.getHoldTime()+temp.getArrivalTime()-1))
                    {
                        temp.setExecutingStatus(false); //set the Job to stop execute in next CPU Time
                        temp.setCompletionTime(x+1); //increment Completion Time of the Job(s)
                        doneQ.enqueue(temp);
                        numJob++; //increment total Jobs that has been executed
                    }
                    else
                        tempExecuteQ.enqueue(temp);
                }

                //process waitQ
                while (!waitQ.isEmpty())
                {
                    temp = (Job)waitQ.dequeue();
                    
                    //condition that determine the Job(s) that currently waiting has arrived or already arrived
                    if (x==temp.getArrivalTime())
                    {
                        System.out.println("Job "+temp.getJob()+" has arrived...");
                        temp.setHoldTime(1); //set Hold Time only to 1
                    }
                    else 
                    {
                        temp.setHoldTime(temp.getHoldTime()+1); //increment Hold Time of the Job(s)
                        System.out.println("Job "+temp.getJob()+" is in hold for "+temp.getHoldTime()+"ms...");
                    }

                    tempWaitQ.enqueue(temp);
                }
                
                //process tempInput
                while (!tempInputQ.isEmpty())
                {
                    temp = (Job)tempInputQ.dequeue();
                    
                    //condition that check whether the Jobs that is ready to execute in next CPU time
                    if (temp.getArrivalTime()==time)
                        checkInQ.enqueue(temp);
                    inputQ.enqueue(temp);
                }

                checkInQ = BurstSorting(checkInQ); //sort Jobs in checkInQ based on Burst Time

                tempWaitQ = BurstSorting(tempWaitQ); ////sort Jobs in tempWaitQ based on Burst Time

                Job temp2 = new Job(); //store other Job that is being process in Queue
                
                //process tempWaitQ
                while (!tempWaitQ.isEmpty())
                {
                    temp = (Job)tempWaitQ.dequeue();
                    
                    //condition to check whether the Job that currently execute has done or not
                    if (tempExecuteQ.isEmpty())
                    {
                        //condition to check whether there is the Jobs that is ready to execute in next CPU time
                        if (!checkInQ.isEmpty())
                        {
                            temp2 = (Job)checkInQ.getFront();

                            /*condition to determine wheter the Job that currently wait or 
                            the Job that is ready to execute in next CPU time should execute next*/
                            if (temp.getBurstTime()<=temp2.getBurstTime())
                            {
                                readyQ.enqueue(temp);
                            }
                            else if (interruptedQ.isEmpty())
                            {
                                interrupt = temp2;
                                hasInterupt = true; //set there is Job that will interupt 
                                interruptedQ.enqueue(temp);
                            }
                        }
                        else
                        {
                            temp.setExecutingStatus(true); //set the Job to execute in next CPU Time
                            tempExecuteQ.enqueue(temp);
                        }
                    }
                    else
                        readyQ.enqueue(temp);
                }

                //process tempExecuteQ
                while (!tempExecuteQ.isEmpty())
                {
                    temp = (Job)tempExecuteQ.dequeue();
                    executeQ.enqueue(temp);
                }

                //process doneQ
                while(!doneQ.isEmpty())
                {
                    temp=(Job)doneQ.dequeue();
                    temp.setTurnaroundTime(temp.getCompletionTime()-temp.getArrivalTime()); //set Turnaround Time of the Job(s)
                    temp.setWaitingTime(temp.getCompletionTime()-(temp.getArrivalTime()+temp.getBurstTime())); //set Waiting Time of the Job(s)
                    totalTT+=temp.getTurnaroundTime(); //total Turnaround Time of all Jobs
                    totalWT+=temp.getWaitingTime(); //total Waiting Time of all Jobs
                    printOutputQ.enqueue(temp); 
                }
            }
            //control the decimal point of TT & WT when be displayed
            DecimalFormat df = new DecimalFormat("0.0");
            System.out.print("\nAverage turn-around time: "+df.format(totalTT/numJob)+"ms"); //calculate average TT and display it
            System.out.println("\nAverage waiting time: "+df.format(totalWT/numJob)+"ms"); //calculate average WT and display it

            printOutputQ = JobSorting(printOutputQ); //sort Jobs in printOutputQ alphabetically
            printOutput(printOutputQ,numJob,totalTT,totalWT); //called the function to output the Job(s) details by tabulation
        }
    }
    
    //function that sorting Job(s) in Queue based on Burst Time
    public static Queue BurstSorting(Queue sortQ)
    {
        Queue tempQ = new Queue(); //Queue that store temporary data from sortQ
        
        int size = 0; //store total number of Jobs in sortQ
        
        //process sortQ
        while (!sortQ.isEmpty())
        {
            Job temp = (Job)sortQ.dequeue();
            tempQ.enqueue(temp);
            size++; //increment number of Job(s)
        }
        
        Job temp; //store Job that is being process in Queue
        
        //looping that sorting all the Job(s) by comparing each Job with other Job(s) 
        for (int x=0; x<size; x++)
        {   
            Job a = (Job)tempQ.dequeue();
            
            //looping that sorting the current selected Job by comparing it with other Job(s)
            while (!tempQ.isEmpty())
            {
                Job b = (Job)tempQ.dequeue();
                
                //condition that determine whether the selected Job has lower or same Burst Time than the other
                if (a.getBurstTime()<=b.getBurstTime())
                {
                    //swapping the Jobs
                    temp = a;
                    a = b;
                    b = temp;
                }
                sortQ.enqueue(b);
            }
            sortQ.enqueue(a);
            
            //process sortQ again
            while (!sortQ.isEmpty())
            {
                Job curr = (Job)sortQ.dequeue();
                tempQ.enqueue(curr);
            }
        }
        
        return tempQ;
    }
    
    //function that sorting Job(s) in Queue alphabetically
    public static Queue JobSorting(Queue sortQ)
    {
        Queue tempQ = new Queue(); //Queue that store temporary data from sortQ
        
        int size = 0; //store total number of Jobs in sortQ
        
        //process sortQ
        while (!sortQ.isEmpty())
        {
            Job temp = (Job)sortQ.dequeue();
            tempQ.enqueue(temp);
            size++; //increment number of Job(s)
        }
        
        Job temp; //store Job that is being process in Queue
        
        //looping that sorting all the Job(s) by comparing each Job with other Job(s)
        for (int x=0; x<size; x++)
        {   
            Job a = (Job)tempQ.dequeue();
            
            //looping that sorting the current selected Job by comparing it with other Job(s)
            while (!tempQ.isEmpty())
            {
                Job b = (Job)tempQ.dequeue();
                
                //condition that determine whether the selected Job has lower or same Burst Time than the other
                if (a.getJob().compareTo(b.getJob())<=0)
                {
                    //swapping the Jobs
                    temp = a;
                    a = b;
                    b = temp;
                }
                sortQ.enqueue(b);
            }
            sortQ.enqueue(a);
            
            //process sortQ again
            while (!sortQ.isEmpty())
            {
                Job curr = (Job)sortQ.dequeue();
                tempQ.enqueue(curr);
            }
       }
        
        return tempQ;
    }
    
    //function that ask the user to input Job(s) and its details
    public static boolean input(Queue inputQ, Queue printInputQ)
    {
        int confirmInput = 1; //store number that indicate to stop the loop or not
        //looping until user want to proceed with the input
        while (confirmInput == 1) 
        {
            int loop = 0; //store number that indicate to stop the loop or not
            //looping until there is no more Job want to input by user
            while (loop == 0)
            {
                JPanel panel = new JPanel(); //object instantiation of JPanel
                //object instantiations of JTextField
                JTextField jobName = new JTextField(5);
                JTextField arrivalTime = new JTextField(5);
                JTextField burstTime = new JTextField(5);
                
                //add required labels and textFields in the panel
                panel.add(new JLabel("Job Name:"));
                panel.add(jobName);
                panel.add(new JLabel("Arrival Time :"));
                panel.add(arrivalTime);
                panel.add(new JLabel("Burst Time:"));
                panel.add(burstTime);
                
                //show the Input Dialog
                int end = JOptionPane.showConfirmDialog(null, panel, "Enter Job Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                
                //condition that check if user want to cancel or close the Input Dialog
                if (end == 2 || end == -1)
                    return false;
                
                //retrieve and store the input data
                String job = jobName.getText();
                String aTime = arrivalTime.getText();
                String bTime = burstTime.getText();

                //looping until user enter valid data
                while (job.isBlank() || aTime.isBlank() || bTime.isBlank() || Integer.parseInt(aTime)<=0 || Integer.parseInt(bTime)<=0)
                {
                    //condition that check if user enter invalid data or leave textField(s) blank and show Message(s) 
                    if (job.isBlank())
                        JOptionPane.showMessageDialog(null, "Please input name of the Job" ,"Invalid Input", JOptionPane.INFORMATION_MESSAGE);
                    if (aTime.isBlank()  ||  bTime.isBlank() || Integer.parseInt(aTime)<=0 || Integer.parseInt(bTime)<=0)
                        JOptionPane.showMessageDialog(null, "Time cannot be zero or less" ,"Invalid Input", JOptionPane.ERROR_MESSAGE);
                    
                    //show the Input Dialog again
                    end = JOptionPane.showConfirmDialog(null, panel, "Enter Job Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    
                    //condition that check again if user want to cancel or close the Input Dialog 
                    if (end == 2 || end == -1)
                        return false;

                    //retrieve and store the input data again
                    job = jobName.getText();
                    aTime = arrivalTime.getText();
                    bTime = burstTime.getText();;
                }

                //object instantiation of Job and store the data in it
                Job newJob = new Job(job, Integer.parseInt(aTime), Integer.parseInt(bTime));
                inputQ.enqueue(newJob);
                printInputQ.enqueue(newJob);
                
                //show a Message to ask user wheter want to input more or not
                loop = JOptionPane.showConfirmDialog(null, "More Job to Input? ", "", JOptionPane.YES_NO_OPTION);
            }

            JFrame frame = printInput(printInputQ); //called the function to output the Job(s) details by tabulation

            while (frame.isVisible()){} //looping to keep showing the Input Frame

            //show a Message to ask the user confirmation about the data that has been inputted
            confirmInput = JOptionPane.showConfirmDialog(null, "Proceed with the input?", "User Confirmation", JOptionPane.YES_NO_OPTION);
            frame.setVisible(false); //hide the Input Frame when user close it
            
            //condition that check whether user want to proceed with the input data or not
            if (confirmInput==1)
            {
                //looping to clear all data stored in inputQ & printInputQ
                while (!inputQ.isEmpty())
                {
                    inputQ.dequeue();
                }
                while (!printInputQ.isEmpty())
                {
                    printInputQ.dequeue();
                }
            }
        }
        return true;
    }
    
    //function that output the Input Frame
    public static JFrame printInput(Queue printQ)
    {
        JFrame frame = new JFrame("Input"); //object instantiation of JFrame
        
        //static array that store name of the columns
        String[] columns = {"Job","Arrival Time","CPU (Burst) Time"};
        
        Queue tempQ = new Queue(); //Queue that store temporary data from printQ
        Job temp; //store Job that is being process in Queue
        
        int size = 0; //store number of total Jobs in printQ  
        //process printQ
        while(!printQ.isEmpty()) 
        {
            temp = (Job)printQ.dequeue();
            tempQ.enqueue(temp);
            size++; //increment number of Job(s) in printQ  
        }
        
        //static array that store data of the table
        Object[][] data = new Object [size][columns.length];
        
        //looping to transfer all needed data from the Queue to the static array
        for (int i=0; i<size; i++)
        {
            temp = (Job)tempQ.dequeue();
            data[i][0] = temp.getJob();
            data[i][1] = temp.getArrivalTime();
            data[i][2] = temp.getBurstTime();
        }
        
        JTable table = new JTable(data, columns); //object instantiation of JTable
        JScrollPane scrollPane = new JScrollPane(table); //object instantiation of JScrollPane
        
        //set whether or not this table is always made large enough to fill the height of an enclosing viewport
        table.setFillsViewportHeight(true); 
        
        //set alignment of each data in selected column(s) to Center
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i=0; i<columns.length; i++)
        {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        //create a label and put it at the bottom of the Frame
        JLabel lblHeading = new JLabel("Please close first to continue...", SwingConstants.RIGHT);
        lblHeading.setFont(new Font("Arial", Font.TRUETYPE_FONT,16));
        
        //create and set the layout for the scroll pane and label in the Frame
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(lblHeading, BorderLayout.PAGE_END);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        //set what should happen after the user close the Frame
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        frame.setSize(320,160); //set size of the Frame
        frame.setLocationRelativeTo(null); //set location where the Frame should pop up
        frame.setVisible(true); //unhide the Frame
        
        return frame;
    }
    
    //function that output the Output Frame
    public static void printOutput(Queue printQ, int num, double tt, double wt)
    {
        JFrame frame = new JFrame("Output"); //object instantiation of JFrame
        
        //static array that store name of the columns
        String[] columns = {"Job","Arrival Time","CPU (Burst) Time","Completion Time","TT","WT"};
        
        Queue tempQ = new Queue(); //Queue that store temporary data from printQ
        Job temp; //store Job that is being process in Queue
        
        int size = 0; //store number of total Jobs in printQ
        //process printQ
        while(!printQ.isEmpty())
        {
            temp = (Job)printQ.dequeue();
            tempQ.enqueue(temp);
            size++; //increment number of Job(s) in printQ
        }
        
        //static array that store data of the table
        Object[][] data = new Object [size+2][columns.length];
        
        //looping to transfer all needed data from the Queue to the static array
        for (int i=0; i<size; i++)
        {
            temp = (Job)tempQ.dequeue();
            data[i][0] = temp.getJob();
            data[i][1] = temp.getArrivalTime();
            data[i][2] = temp.getBurstTime();
            data[i][3] = temp.getCompletionTime();
            data[i][4] = temp.getTurnaroundTime();
            data[i][5] = temp.getWaitingTime();
        }
        
        //control the decimal point of TT & WT when be displayed
        DecimalFormat df = new DecimalFormat("0.0");
        
        //store other details about TT & WT
        data[size][3] = "Total";
        data[size][4] = (int)tt;
        data[size][5] = (int)wt;
        data[size+1][3] = "Average";
        data[size+1][4] = df.format(tt/num);
        data[size+1][5] = df.format(wt/num);
                
        
        JTable table = new JTable(data, columns); //object instantiation of JTable
        JScrollPane scrollPane = new JScrollPane(table); //object instantiation of JScrollPane
        
        //set whether or not this table is always made large enough to fill the height of an enclosing viewport
        table.setFillsViewportHeight(true);
        
        //set alignment of each data in selected column(s) to Center
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i=0; i<columns.length; i++)
        {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
         //create and set the layout for the scroll pane in the Frame
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        //set what should happen after the user close the Frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setSize(600,175); //set size of the Frame
        frame.setLocationRelativeTo(null); //set location where the Frame should pop up
        frame.setVisible(true); //unhide the Frame
    }
}
