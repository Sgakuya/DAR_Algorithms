/* Program that generates the random requests and computes the algorithm's schedule and optimal schedule */


// package Comp;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Comparison{
	
	public static int iterations;
	static List<ArrayList<LinearRequest>> combinations = new ArrayList<ArrayList<LinearRequest>>();
	static int index = 0;
	public static int sumOPT = 0, multiSumOPT;
	public static int sumBest = 0, multiSumBest = 0;
	public static int sumFix = 0, multiSumFix = 0, avgMultiSumFix = 0;
	public static int timeOPT = 0, multiTimeOPT = 0;
	public static int timeBest = 0, multiTimeBest = 0;
	public static int timeFix = 0, multiTimeFix = 0;
	public static int bestTh = 0, bestOutput = 0, bTh = 0, bOt = 0, avgOut = 0, avgTh = 0;
	public static int totalOld = 0, totalEarliest=0, totalFval=0;
	
	
	public static void main(String[] args)throws IOException {
		Scanner sc = new Scanner(System.in);
		//System.out.print("What types of request do you want have? Random(1), Text Input(2): ");
		int choice = 1;
		if(choice == 1) {
//			System.out.println("How mamy random requests do you want to have: ");
			
			WeightFunction wf = new WeightFunction();
			wf.numRequest = 20;
			int numDrivers = 5;
			
			
			//System.out.print("How many iterations do you want: ");
			iterations = 100;
			
			for(int i=0;i<iterations;i++) {
//				System.out.println("######################### START OF ITERATION " + (i + 1) + " #########################");
				List<LinearRequest> rl = wf.creatRandomRequest(); //creates numRequest number of random requests
				List<LinearRequest> temp1 = new ArrayList<LinearRequest>(); 
				List<LinearRequest> temp2 = new ArrayList<LinearRequest>();
				List<LinearRequest> temp3 = new ArrayList<LinearRequest>();
				List<LinearRequest> temp4 = new ArrayList<LinearRequest>();
				List<LinearRequest> temp5 = new ArrayList<LinearRequest>();


				List<Driver> dList = wf.createRandomDrivers(numDrivers);  //creates numDrivers number of drivers
				List<Driver> copy1 = new ArrayList<Driver>();
				List<Driver> copy2 = new ArrayList<Driver>();
				List<Driver> copy3 = new ArrayList<Driver>();
				List<Driver> copy4 = new ArrayList<Driver>();

				//adds random drivers to temp copies
				for (Driver d : dList) {
					copy1.add(new Driver(d.getName(), d.getOrigin()));
					copy2.add(new Driver(d.getName(), d.getOrigin()));
					copy3.add(new Driver(d.getName(), d.getOrigin()));
					copy4.add(new Driver(d.getName(), d.getOrigin()));
				}
				
				//adds random requests just created to temp1, temp2
				for(LinearRequest lr: rl) {
					temp1.add(new LinearRequest(lr.startPos,lr.finishPos,lr.pickTime));
					temp2.add(new LinearRequest(lr.startPos,lr.finishPos,lr.pickTime));
					temp3.add(new LinearRequest(lr.startPos,lr.finishPos,lr.pickTime));
					temp4.add(new LinearRequest(lr.startPos,lr.finishPos,lr.pickTime));
					temp5.add(new LinearRequest(lr.startPos,lr.finishPos,lr.pickTime));

				}
				
//				//print the randLIst
//				System.out.println("The List of RandomRequests:");
//				for(LinearRequest r : rl) {
//					System.out.println(r.toString());
//				}

				/*
				System.out.println("\nRound " + (i+1));
				//Get The RunTime of OPT
				long startTime = System.nanoTime();
				int OPT = getOptimal(rl).size();
				long endTime = System.nanoTime();
				System.out.println("OPT takes "+ (endTime - startTime)/100000.0 + " Millisecond");
				sumOPT += OPT;
				timeOPT += (endTime - startTime)/100000.0;
				*/
		        

        
				//Version1(a): RunTime of ALL Combination of weights - Single Driver
				//No other features
//				long startTime = System.nanoTime();
//				wf.set.clear();
//				int s = wf.runAlg1(rl,0);							
//				long endTime = System.nanoTime();
//				System.out.println("(SingleDriver)ALG with best combination takes "+ (endTime - startTime)/100000.0 + " Millisecond");
//				sumBest += s;
//				timeBest += (endTime - startTime)/100000.0;
				
				
				//Version2(a): RunTime of fixed weight (0,1,1) - Single Driver
//				startTime = System.nanoTime();
//				wf.set.clear();
//				wf.runAlg(temp1,0,0,0,1,1);			
//				int s2 = wf.set.size();				
//				endTime = System.nanoTime();
//				System.out.println("(SingleDriver)ALG with fixed combination takes "+ (endTime - startTime)/100000.0 + " Millisecond");
//				sumFix += s2;
//				timeFix += (endTime - startTime)/100000.0;

				//Version2(b): RunTime of fixed weight (0,1,1) - Multi Driver
//				startTime = System.nanoTime();
//				wf.set.clear();
//		        System.out.println("---------UNASSIGNED REQUESTS => INITIAL LIST----------");
//		        for (LinearRequest l: temp3){
//		          System.out.println(l.toString());
//		        }
//		        System.out.println();
//		        wf.unServed.clear();
//		        
//		        //our stuff starts here
//		        for (int n = 1; n<2 ; n++) {
//			        int s3 = 0;
//		        	wf.MultiDriver(temp3, dList, n);
//			        System.out.println("==================REQUESTS SERVED & UNSERVED==================");
//			        System.out.println("Current Threshold = "+ n);
//
//			        for (Driver d : dList){
//						wf.runAlg(d.getRequests(),0,d.getOrigin(),0,1,1);
//						//keep track of current no of served requests
//						s3 += wf.set.size();
//
//						System.out.println("Driver: "+ d.getName() + "; Driver origin: " + d.getOrigin() + "; Requests assigned: " + d.getNumRequests() + "; Requests served: " + wf.set.size());
//						System.out.println("--------served reqs driver " + d.getName() +"--------");
//						for (LinearRequest l : wf.set){
//						    System.out.println(l.toString());
//						}
//						wf.set.clear();
//						System.out.println("--------unserved reqs driver " + d.getName() +"--------");
//						for (LinearRequest l : wf.unServed) {
//						   	System.out.println(l.toString());
//						}
//						wf.unServed.clear();
//						System.out.println();
//						System.out.println("==============");
//						d.numRequests = 0;
//
//			        }
//					
//					//if current no of reqs is more than those before, update best threshold value
//					if (s3 > bestOutput) {
//						bestTh = n;
//						bestOutput = s3;
//					}
//					
//			        System.out.println("Total requests served: " + s3);
//			        System.out.println();
//			        
//			        //refresh list to ensure all values are reset for next iteration
//			        temp3.clear();
//			        for (LinearRequest l: temp2)
//			        	temp3.add(l.copy());
//					avgMultiSumFix += s3;
//
//		        }
//
//		        System.out.println("Max served this iteration: "+ bestOutput + "\nBest theshold for " + wf.numRequest + 
//		        		" requests is: "+ bestTh + "\n");
//		        
//		        avgMultiSumFix /= wf.numRequest;
//		        multiSumFix += avgMultiSumFix;
//		        
//		        avgOut += bestOutput;
//		        avgTh += bestTh;
//		        
//		        if (bestOutput > bOt) {
//		        	bOt = bestOutput;
//		        	bTh = bestTh;
//		        }
//		        
//		        bestTh = 0;
//		        bestOutput = 0;
		        
		        
		        //NewMultiDriver algorithm
		        
		
		        //not being used right now
//		     	endTime = System.nanoTime();
//				System.out.println("(MultiDriver)ALG with fixed combination takes "+ (endTime - startTime)/100000.0 + " Millisecond");
//				multiTimeFix += (endTime - startTime)/100000.0;
				
			
				//RunTime of ALG (single driver)
//				startTime = System.nanoTime();
//				wf.set.clear();
//				wf.runAlg2(rl,0,0,0,1,1);
//				int size = wf.set.size();
//				endTime = System.nanoTime();
//				System.out.println("(Single Driver)ALG takes "+ (endTime - startTime)/100000.0 + " Millisecond");
//				
//				System.out.println("ALG List:");
//				for(LinearRequest r : wf.set) {
//					System.out.println(r.toString());
//				}
				
				
							
//				System.out.println("---------UNASSIGNED REQUESTS => INITIAL LIST----------");
//		        temp3.clear();
//		        temp4.clear();
//		        LinearRequest L = new LinearRequest(0, 0, 0);
//		        temp3.add(L = new LinearRequest(10, 20, 3));
//		        temp3.add(L = new LinearRequest(15, 20, 4));
//		        temp3.add(L = new LinearRequest(5, 10, 1));
//		        temp3.add(L = new LinearRequest(3, 7, 2));
//		        temp3.add(L = new LinearRequest(20, 3, 8));
//		        temp3.add(L = new LinearRequest(4, 5, 9));
//		        temp3.add(L = new LinearRequest(34, 20, 65));
//		        temp3.add(L = new LinearRequest(13, 27, 54));
////		        temp3.add(L = new LinearRequest(15, 20, 4));
////		        temp3.add(L = new LinearRequest(15, 20, 4));
//       
//		        for (LinearRequest lt : temp3) {
//		        	temp4.add(lt);
//		        }
				
				List<LinearRequest> hardcoded = new ArrayList<LinearRequest>();		
		        List<Driver> hc = new ArrayList<Driver>();
		        Driver dr = new Driver("1", 19);
		        hc.add(dr);
		        
		        hardcoded.add(new LinearRequest(34,16,48));
		        hardcoded.add(new LinearRequest(3,-13,86));
		        hardcoded.add(new LinearRequest(6,-9,6));
		        hardcoded.add(new LinearRequest(45,30,51));
		        hardcoded.add(new LinearRequest(2,14,54));
		        hardcoded.add(new LinearRequest(29,44,60));
		        hardcoded.add(new LinearRequest(55,67,64));
		        hardcoded.add(new LinearRequest(57,52,64));
		        hardcoded.add(new LinearRequest(55,61,65));
		        hardcoded.add(new LinearRequest(62,58,68));
		        hardcoded.add(new LinearRequest(24,33,69));
		        hardcoded.add(new LinearRequest(74,73,77));
		        hardcoded.add(new LinearRequest(58,67,79));
		        hardcoded.add(new LinearRequest(76,73,79));
		        hardcoded.add(new LinearRequest(70,67,82));
		        hardcoded.add(new LinearRequest(73,82,87));
		        hardcoded.add(new LinearRequest(4,-6,91));
		        hardcoded.add(new LinearRequest(96,80,101));
		        hardcoded.add(new LinearRequest(23,39,102));
		        hardcoded.add(new LinearRequest(40,32,107));
		        hardcoded.add(new LinearRequest(107,89,110));
		        hardcoded.add(new LinearRequest(79,62,111));
		        hardcoded.add(new LinearRequest(74,91,113));
		        hardcoded.add(new LinearRequest(3,1,114));
		        hardcoded.add(new LinearRequest(88,98,127));
		        hardcoded.add(new LinearRequest(52,62,130));
		        hardcoded.add(new LinearRequest(107,114,133));
		        hardcoded.add(new LinearRequest(117,133,139));
		        hardcoded.add(new LinearRequest(120,103,140));
		        
				
				
				//RunTime of ALG2 (multi driver - 30 min buffer)
//				startTime = System.nanoTime();
				wf.set.clear();
//				System.out.println("==================Old MultiDriver Alg==================");
//				System.out.println("---------UNASSIGNED REQUESTS => INITIAL LIST----------");
//		        for (LinearRequest l: temp3){
//		          System.out.println(l.toString());
//		        }
//		        System.out.println();
		        wf.unServed.clear();
		        
		   
				
		        for (int n = 1; n<2; n++) {
					int s4 = 0;
					wf.MultiDriver(temp4, copy1, n);
//					System.out.println("==================REQUESTS SERVED & UNSERVED==================");
//			        System.out.println("Current Threshold = "+ n);
			        
			        //keep track of unserved previous requests to add to requests of next drivers
					List<LinearRequest> uns = new ArrayList<LinearRequest>();

			        for (Driver d : copy1) {
						wf.runAlg(d.getRequests(), 0, d.getOrigin(), 0, 1, 1);
						d.numRequests = 0;
						s4+=wf.set.size();
						for (LinearRequest lr: wf.set)
							d.addRequest(lr);
						wf.set.clear();						
						for (LinearRequest l : wf.unServed)
							uns.add(l);
						wf.unServed.clear();						
					}
//			        System.out.println("We served the following number of requests: " + s4);
//					System.out.println("==============");					
			        s4 = 0;
			        for (Driver d: copy1) {
			        	for (LinearRequest l : uns)
			        		d.addRequest(l);
						wf.runAlg(d.getRequests(), 0, d.getOrigin(), 0, 1, 1);
						s4 += wf.set.size();
//						System.out.println("Driver: "+ d.getName() + "; Driver origin: " + d.getOrigin() + "; Requests assigned: " + d.getNumRequests() + "; Requests served: " + wf.set.size());
//						System.out.println("--------served reqs driver " + d.getName() +"--------");
//						for (LinearRequest l : wf.set){
//						    System.out.println(l.toString());
//						}
						d.numRequests = 0;
						wf.set.clear();
						
//						System.out.println("--------unserved reqs driver " + d.getName() +"--------");
//						for (LinearRequest l : wf.unServed) {
//						   	System.out.println(l.toString());
//						}
						uns.clear();
						for (LinearRequest l : wf.unServed)
							uns.add(l);
						wf.unServed.clear();

			        }
//					System.out.println("Unserved at the end: " + uns.size());					

			      //if current no of reqs is more than those before, update best threshold value
					if (s4 > bestOutput) {
						bestTh = n;
						bestOutput = s4;
					}
//					System.out.println("Total requests served: " + s4);
//			        System.out.println();
			        
			        //refresh list to ensure all values are reset for next iteration
			        temp4.clear();
			        for (LinearRequest l: temp2)
			        	temp4.add(l.copy());
					avgMultiSumFix += s4;
			        totalOld += s4;

		        }
		        

		        
//		        System.out.println("Max served this iteration: "+ bestOutput + "\nBest theshold for " + wf.numRequest + 
//		        		" requests is: "+ bestTh + "\n");
		        
		        
		        avgMultiSumFix /= wf.numRequest;
		        multiSumFix += avgMultiSumFix;
		        
		        avgOut += bestOutput;
		        avgTh += bestTh;
		        
		        if (bestOutput > bOt) {
		        	bOt = bestOutput;
		        	bTh = bestTh;
		        }
		        
		        bestTh = 0;
		        bestOutput = 0;
		        
		        
		        
		        
		        //New MultiDriver Algorithms
		        totalEarliest += wf.multiDriverNew(copy4, temp3);
		        totalFval += wf.multiDriverNew2(copy3, temp5);
		        
		        
//		        wf.multiDriverNew2(hc, hardcoded);


		        
				
//				endTime = System.nanoTime();
//				System.out.println("(MultiDriver)ALG2 takes "+ (endTime - startTime)/100000.0 + " Millisecond");
				
				
				
//				//RunTime of ShareRides
//				startTime = System.nanoTime();
//				ShareRide sr = new ShareRide();
//				sr.set.clear();
//				sr.overlap.clear();
//				sr.shareRides.clear();
//				sr.shareAlg(temp2);	
//				int shareSize = sr.set.size() + sr.shareRides.size();				
//				endTime = System.nanoTime();
//				System.out.println("Pure Share Rides takes "+ (endTime - startTime)/100000.0 + " Millisecond");
//				
//				System.out.println("\nPath List:");
//				for(LinearRequest r : sr.set) {
//					System.out.println(r.toString());
//				}
//				
//				System.out.println("\nShareRides List:");
//				for(LinearRequest r : sr.shareRides) {
//					System.out.println(r.toString());
//				}
//				
//				
//	
				index = 0;
				combinations.clear();
				
//				System.out.println("######################### END OF ITERATION " + (i + 1) + " #########################");
//				System.out.println();

				
				//System.out.println("\nThe Max Size from OPT is: " + OPT + "\nThe Max Size from ALG with best combination is: " + s + "\nThe Max Size from ALG with fixed combination: " + s2);
				//System.out.println("\nThe Max Size from ALG with best combination is: " + s + "\nThe Max Size from ALG is(30 in advance & sharerides): " + size + "\nThe Max Size from Share Ride: " + shareSize);
				
			}
//			System.out.println("Average max output = " + avgOut/iterations);
//			System.out.println("Average best threshold = " + avgTh/iterations);
//			System.out.println("Best max output in " + iterations + " iterations= " + bOt);
//			System.out.println("Best threshold in " + iterations + " iterations= " + bTh);



			
			System.out.println("\nFor " + iterations + " iterations of " + wf.numRequest + " Requests");
			System.out.println("Avg requests for Old alg: " + totalOld / iterations);
			System.out.println("Avg requests for Earliest alg: " + totalEarliest / iterations);
			System.out.println("Avg requests for Fvalue alg: " + totalFval / iterations);

			
////			System.out.println("Average Size for OPT is: " + sumOPT/(double)iterations);
////			System.out.println("Average Size for MultiDriver OPT is: " + multiSumOPT/(double)iterations);
//			System.out.println("Average Size for Best Combination is: " + sumBest/(double)iterations);
////			System.out.println("Average Size for MultiDriver Best Combination is: " + multiSumBest/(double)iterations);
//			System.out.println("Average Size for Fix Combination is: " + sumFix/(double)iterations);
//			System.out.println("Average Size for MultiDriver Fix Combination is: " + multiSumFix/(double)iterations);
////			System.out.println("Average run-time for OPT is: " + timeOPT/(double)iterations);
////			System.out.println("Average run-time for MultiDriver OPT is: " + multiTimeOPT/(double)iterations);
//			System.out.println("Average run-time for Best Combination is: " + timeBest/(double)iterations);
////			System.out.println("Average run-time for MultiDriver Best Combination is: " + multiTimeBest/(double)iterations);
//			System.out.println("Average run-time for Fix Combination is: " + timeFix/(double)iterations);
//			System.out.println("Average run-time for MultiDriver Fix Combination is: " + multiTimeFix/(double)iterations);
			System.out.println("No of requests: " + wf.numRequest + " no of drivers: "+ numDrivers);

			
			
		}else{//Text Scan
	        File file = new File("/Users/haiyiluo/eclipse-workspace/Project/src/input.txt");
	            Scanner reader = new Scanner(file);
	            
	            List<LinearRequest> rl = new ArrayList<LinearRequest>();
	            while(reader.hasNextLine()) {
	            	int s = reader.nextInt();
	            	int d = reader.nextInt();
	            	int f = reader.nextInt();
	            	rl.add(new LinearRequest(s,d,f));
	            }
	            
	            reader.close();
	            
	            System.out.println("\nOur Request Input: ");
	            
				for(LinearRequest r : rl) {
					System.out.println(r.toString());
				}
				
//				ShareRide sr = new ShareRide();

				
				//RunTime of ALG
//				sr.alg(rl,0,0);
//				
//
//				
//				System.out.println("\noverlap ");
//				for(LinearRequest r : sr.overlap) {
//					System.out.println(r.toString());
//				}
//			
//				sr.shareAlg(rl);
//				
//				System.out.println("\nShare ");
//				for(LinearRequest r : sr.shareRides) {
//					System.out.println(r.toString());
//				}
				
				//List<LinearRequest> s = sr.shareEnd();
				
//				System.out.println("\nShareEnds ");
//				for(LinearRequest r : s) {
//					System.out.println(r.toString());
//				}
	        
		}
	}
	

	
	
	/*For computing the optimal schedule*/
	public static void permute(List<LinearRequest> lrl, int k) {
        if (k == lrl.size()) {
        	ArrayList<LinearRequest> indivSeq = new ArrayList<LinearRequest>();
        		for (int i = 0; i < lrl.size(); i++) {
        			indivSeq.add(lrl.get(i));
//        			System.out.print(" [" + lrl.get(i).toString() + "] ");
                
            }
        		combinations.add(index, indivSeq);
        		index++;
//        		System.out.println();
        } 
        else {
        	//recursively swapping one thing each time
            for (int i = k; i < lrl.size(); i++) {
            	Collections.swap(lrl, i, k);
                permute(lrl, k + 1);
                Collections.swap(lrl, i, k);
            }
        }
 
	}

  /*For computing the optimal schedule*/
	public static List<LinearRequest> getOptimal (List<LinearRequest> lrl){


		//gives us a list of list of requests, in every combination possible
        permute(lrl, 0);
        for(int j = 0; j < combinations.size(); j++) {
        	int time = 0;
        	int origin = 0;
        	for(int i = 0; i< combinations.get(j).size(); i++) {
        		if(combinations.get(j).get(i).pickTime < time) {
        			combinations.get(j).remove(i);
        			i--;
        			continue;
        		}
        		if(time + Math.abs(combinations.get(j).get(i).startPos - origin) > combinations.get(j).get(i).pickTime) {
        			combinations.get(j).remove(i);
        			i--;
        		}
        		time = combinations.get(j).get(i).finishTime;
    			origin = combinations.get(j).get(i).finishPos;
        	}
        }

        int maxIndex = -1;
        int maxSize = Integer.MIN_VALUE;

        for(int i =0; i< combinations.size(); i++) {
        	List<LinearRequest> l = combinations.get(i);
        	if (l.size() > maxSize) {
        		maxSize = l.size();
        		maxIndex = i;
        	}
        }


        return combinations.get(maxIndex);
	}
	
	
}