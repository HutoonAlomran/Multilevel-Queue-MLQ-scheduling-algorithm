import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
public class P_MLQ {
	

		int choice;
		int numOfProcesses;
		
		PCB Array[]; // Temporary Array
		PCB Q1[]; 
		PCB Q2[];
		
		int SystemTime , finishedProcesses , counter , Q1_counter , Q2_counter , currentProcess = 0 ;
		PCB scheduledProcesses[] ;
		int sched_counter = 0 ;
		
		public static void main(String[] args) {
		
			P_MLQ obj = new P_MLQ() ;
			obj.Menu() ;

		}
		
		// Displaying the menu
		public void Menu() {
			 System.out.println("1. Enter processes information. ");
			 System.out.println("2. Report detailed information about each process. ");
			 System.out.println("3. Report the average turnaround time, waiting time, and response time. ");
			 System.out.println("4. Exit the program");
			
			 System.out.println("Enter your choice number (1 to 4) : ");
			 Scanner input = new Scanner(System.in); // Create a Scanner object
			 choice = input.nextInt(); // Read choice from the user
			 
			 while (choice > 4 || choice < 1) { // To ensure that the choice is valid
			 System.out.println("Enter your choice number (1 to 4) : ");
			 choice = input.nextInt();
			 }
			
			 switch (choice) {
			 case 1:
				 
				 int C_Q1 = 0; // Counter to count the size of processes in Q1
				 int C_Q2 = 0; // Counter to count the size of processes in Q2
				 
				 System.out.println("Enter number of processes :");
				 numOfProcesses = input.nextInt(); // Read number of processes
				 Array = new PCB[numOfProcesses];
				 
				 for(int i=0; i<numOfProcesses; i++) // Read information of the process
				 {
				 System.out.println("Enter arrival time for process #" + (i+1));
				 int arrivalTime = input.nextInt();
				 
				 System.out.println("Enter CPU burst for process #" + (i+1));
				 int CPU = input.nextInt();
				 
				 System.out.println("Enter process priorty for process #" + (i+1));
				 int priorty = input.nextInt();
				 
				 while (priorty>2 || priorty<1) { // To ensure that priorities range from 1 to 2
				 System.out.println("Enter process priorty for process #" + (i+1));
				 priorty = input.nextInt();
				 } 
				 
				 // Increment the size of Q1 or Q2 based on priority
				 if(priorty==1){
					 C_Q1++; 
				 }
				 else if (priorty==2){
					 C_Q2++; 
				 }
				 
				 String processId = ""+(i+1); // Process in the form of “PN”, where N represents the process number
				 Array[i] = new PCB(processId,priorty,arrivalTime,CPU);
				 }
				 
				 // Create two arrays of type PCB 
				 Q1 = new PCB[C_Q1];
				 Q2 = new PCB[C_Q2];
				 
				 // Counter for Q1 and Q2
				 int q1 = 0 ; 
				 int q2 = 0 ;
				 
				 // Loop to add processes to Q1 and Q2 from the Temporary array
				 for(int i = 0 ; i < Array.length ; i++) {
					 if(Array[i].getPriority() == 1)
						 Q1[q1++] = Array[i] ;
					 else
						 Q2[q2++] = Array[i] ; 
				 }
				 
				 Menu();
				 break;
			
			 case 2:
				 try {
					 
					//open file
					 File outfile = new File("Report1.txt"); 
					 FileOutputStream sf = new FileOutputStream(outfile); 
					 PrintWriter pf = new PrintWriter(sf);
					 					 
					//write 
					 for(int i=0 ; i < Array.length ; i++) {
						 if(Array[i] != null) {
							 System.out.println(Array[i].toString());
							 pf.println(Array[i].toString()); 
						 }
					 }
						
					//close
					pf.close();	
						
					Menu();
				    break;
							 
					 } 
				    catch (IOException ex) {
						 System.out.println("File Error");
						 }
			 
			 case 3:
				 
			 scheduledProcesses = new PCB[numOfProcesses] ;
             System.out.println("************Scheduale Processes*************");
             System.out.print("[");
             
             if (Q1.length >= 1)
            	 SJF();
             if (Q2.length >= 1)
            	 FCFS();
             
             System.out.println(" ]");
             
			 try {
				 
				 double Avg_WaitingTime = 0 , Avg_TurnArroundTime = 0 , Avg_ResponseTime = 0 ;
				 int Sum_WaitingTime = 0 , Sum_TurnArroundTime = 0 , Sum_ResponseTime = 0 ;
				 
				//open file
				 File outfile = new File("Report2.txt"); 
				 FileOutputStream sf = new FileOutputStream(outfile); 
				 PrintWriter pf = new PrintWriter(sf);
				 					 
				//write 
				 for(int i=0 ; i < scheduledProcesses.length ; i++) {
					 if(scheduledProcesses[i] != null) {
						 Sum_WaitingTime = Sum_WaitingTime + scheduledProcesses[i].getWaitingTime() ;
						 Sum_TurnArroundTime = Sum_TurnArroundTime +  scheduledProcesses[i].getTurnArroundTime() ;
						 Sum_ResponseTime = Sum_ResponseTime +	 scheduledProcesses[i].getResponseTime() ;	 
						 
						 Avg_WaitingTime = Sum_WaitingTime / scheduledProcesses.length ;
                         Avg_TurnArroundTime = Sum_TurnArroundTime / scheduledProcesses.length ;
                         Avg_ResponseTime = Sum_ResponseTime / scheduledProcesses.length ;
						 
						 System.out.println(scheduledProcesses[i].toString());
						 pf.println(scheduledProcesses[i].toString()); 
					 }					 
				 }
				 
				 System.out.println("Avg_WaitingTime = "+ Avg_WaitingTime + " | Avg_TurnArroundTime = " + Avg_TurnArroundTime + " | Avg_ResponseTime = " + Avg_ResponseTime);
				 pf.println("Avg_WaitingTime = "+ Avg_WaitingTime + " , Avg_TurnArroundTime = " + Avg_TurnArroundTime + " , Avg_ResponseTime = " + Avg_ResponseTime);
					
				//close
				pf.close();	
					
				Menu();
			    break;
						 
				 } 
			    catch (IOException ex) {
					 System.out.println("File Error");
					 }
			 
			 case 4:
			 System.out.println("Good bye");
			 break;
			 default:
			 System.out.println("Invalid choice");
			
			 } // end switch
			 } //end menu
				
		public void SJF() {
			
			if(finishedProcesses != numOfProcesses) {
				int SJF_counter	= 0 ;
				while(SJF_counter < Q1.length) {
					
					boolean SJF_check = false ; 
					int min_process = 10000 ; 
					
					for(int i = 0 ; i < Q1.length ; i++) {
						
						if(Q1[i].getArrivalTime() <= SystemTime && !Q1[i].isCheck() && Q1[i].getCPU_burst() < min_process) {
							SJF_check = true ;
							min_process = Q1[i].getCPU_burst() ;
							currentProcess = i ;
						} // end if
						
					} // end for
					
					if(SJF_check) {
						System.out.print(" | " + Q1[currentProcess].getPId());
						
						Q1[currentProcess].setCheck(true) ;						
						Q1[currentProcess].setStartTime(SystemTime);
						Q1[currentProcess].setTerminationTime(SystemTime + Q1[currentProcess].getCPU_burst());
						Q1[currentProcess].setTurnArroundTime(Q1[currentProcess].getTerminationTime() - Q1[currentProcess].getArrivalTime());
						Q1[currentProcess].setWaitingTime(Q1[currentProcess].getTurnArroundTime() - Q1[currentProcess].getCPU_burst());
						Q1[currentProcess].setResponseTime(Q1[currentProcess].getStartTime() - Q1[currentProcess].getArrivalTime());
						
						SystemTime = SystemTime + Q1[currentProcess].getCPU_burst() ;
						SJF_counter++ ;
						
						Q1_counter++ ;
						finishedProcesses++ ;
						
						scheduledProcesses[sched_counter++] = Q1[currentProcess] ;
						
					////////////////////////////////////////////////
						if(finishedProcesses == numOfProcesses) {
							SJF_counter = Q1.length ;
							break ;
						} // end if
				   ////////////////////////////////////////////////
						
					} // end if
					else {
						
						if(finishedProcesses != numOfProcesses) {
							
							if(Q2_counter != Q2.length) {
								FCFS();
							}
							else {
								SystemTime++ ;
								SJF_counter = 0 ;
							}
							
						} // end if
						
					} //end else
					
				} // end while
				
			} // end if(finishedProcesses != numOfProcesses)
			
		} // end sjf
		
		public void FCFS() {
			
			if(finishedProcesses != numOfProcesses) {
				int FCFS_counter	= 0 ;
				while(FCFS_counter < Q2.length) {
					
					boolean FCFS_check = false ; 
					int min_process = 10000 ; 
					
					for(int i = 0 ; i < Q2.length ; i++) {
						
						if(Q2[i].getArrivalTime() <= SystemTime && !Q2[i].isCheck() && Q2[i].getArrivalTime() < min_process) {
							FCFS_check = true ;
							min_process = Q2[i].getArrivalTime() ;
							currentProcess = i ;
						} // end if
						
					} // end for
					
					if(FCFS_check) {
						System.out.print(" | " + Q2[currentProcess].getPId());
						
						Q2[currentProcess].setCheck(true) ;						
						Q2[currentProcess].setStartTime(SystemTime);
						Q2[currentProcess].setTerminationTime(SystemTime + Q2[currentProcess].getCPU_burst());
						Q2[currentProcess].setTurnArroundTime(Q2[currentProcess].getTerminationTime() - Q2[currentProcess].getArrivalTime());
						Q2[currentProcess].setWaitingTime(Q2[currentProcess].getTurnArroundTime() - Q2[currentProcess].getCPU_burst());
						Q2[currentProcess].setResponseTime(Q2[currentProcess].getStartTime() - Q2[currentProcess].getArrivalTime());
						
						SystemTime = SystemTime + Q2[currentProcess].getCPU_burst() ;
						FCFS_counter++ ;
						
						Q2_counter++ ;
						finishedProcesses++ ;
						
						scheduledProcesses[sched_counter++] = Q2[currentProcess] ;
						
					////////////////////////////////////////////////
						if(finishedProcesses == numOfProcesses) {
							FCFS_counter = Q2.length ;
							break ;
						} // end if
				   ////////////////////////////////////////////////
						
					} // end if
					else {
						
						SystemTime++ ;
						
						if(finishedProcesses != numOfProcesses) {
							
							if(Q1_counter != Q1.length) {
								SJF();
								FCFS_counter = 0 ;
							}

						} // end if
						
					} //end else
					
				} // end while
				
			} // end if(finishedProcesses != numOfProcesses)
			
		} // end fcfs
		
	} // end class P_MLQ
