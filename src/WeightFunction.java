/* 
  * Code that runs two variations of the algorithm.
  * (Will probably not need this code)
 * In this program, we will experiment the different values for the weight and will 
 * attain the combination of the weight function has the greatest percentage correctness
 * 
 * We need to attain the optimal subsets by our selves
 */


import java.util.*;

public class WeightFunction{
	
	//public double[] origin;
	public static int numRequest;
	public static List<LinearRequest> set = new ArrayList<LinearRequest>();
	public static List<LinearRequest> unServed = new ArrayList<LinearRequest>();
	public static int iterations;
	public static int Count = 0;


	//array for storing percent and weight values
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		//The mode of evaluation by approximation rate
		if(args.length == 1 && args[0].equals("approximate")) {
			double w1,w2,w3; 
			System.out.print("w1 = ");
			w1 = sc.nextDouble();
			System.out.print("w2 = ");
			w2 = sc.nextDouble();
			System.out.print("w3 = ");
			w3 = sc.nextDouble();
			
			System.out.print("How many random requests do you want to have: ");
			numRequest = sc.nextInt();
			
			System.out.print("How many rounds do you want to run: ");
			iterations = sc.nextInt();
			
			int count = 0;
			
			for(int i=0;i<iterations;i++) {
				//creates random request
				List<LinearRequest> rl = creatRandomRequest();
				
				//Evaluation
				List<LinearRequest> temp = new ArrayList<LinearRequest>();
				//make a deep copy of rl
				for(LinearRequest lr: rl) {
					temp.add(lr);
				}
				
        //we think the last parameter here is the location of the origin (0)
        assignf(temp,w1,w2,w3,0);
				runAlg(temp,0,0,w1,w2,w3);
				int ourSet = set.size();
				
				set.clear();
				
				List<Test> t = noRandSimulate(rl);
				
				int maxSet = t.get(0).setSize;
				if(ourSet == maxSet) count++;
				
			}
			
			double approxRate = ((double)count)/((double)iterations);
			System.out.println("The Approximation Rate of (" + w1 + "," + w2 + "," + w3 + ") is " + approxRate*100 + "% correct" );
		}
		//Otherwise, run the iteration (functional comparison) mode
		else {
			//Input
			System.out.print("How many requests do you want: ");
			numRequest = sc.nextInt();
			System.out.print("How many iterations do you want: ");
			iterations = sc.nextInt();
			
			//The array that calculate the grant average
			double[] calculatedAvg = new double[3];
		

			
			//run the iterations
			for(int i = 0;i<iterations;i++) {
				List<Test> res = simulate();
				double[] avgWeights = new double[3];
				avgWeights = calcAvg(res);
				System.out.println("iteration " + (i+1) + " w1: " + avgWeights[0] + " w2: " + avgWeights[1] + " w3: " + avgWeights[2]);
				
				//calculate the average
				calculatedAvg[0] += avgWeights[0];
				calculatedAvg[1] += avgWeights[1];
				calculatedAvg[2] += avgWeights[2];
			}
			calculatedAvg[0] = calculatedAvg[0]/iterations;
			calculatedAvg[1] = calculatedAvg[1]/iterations;
			calculatedAvg[2] = calculatedAvg[2]/iterations;
			
			System.out.println("Final Averages: w1: " + calculatedAvg[0] + " w2: " + calculatedAvg[1] + " w3: " + calculatedAvg[2]);
			System.out.println("Test Round: ");
			
			//creates random request
			List<LinearRequest> rl = creatRandomRequest();

			List<Test> result = new ArrayList<Test>();
					
			//simulates random request
			for(double i = 0; i<=10; i++) {
				for(double j = 0; j<=10; j++) {
					for(double k = 0; k<=10; k++) {
						List<LinearRequest> temp = new ArrayList<LinearRequest>();
							//make a deep copy of rl
							for(LinearRequest lr: rl) {
								temp.add(lr);
							}
							assignf(temp,i,j,k,0);
							runAlg(temp,0,0,i,j,k);
							
							int subSetSize = set.size();
							
							result.add(new Test(subSetSize,i,j,k));
								
								
							set.clear();
						}
					}
				}
					
			//Sort the list result
			Collections.sort(result);
					
			System.out.println("The max size = " + result.get(0).setSize);
			
			//Evaluation
			List<LinearRequest> temp = new ArrayList<LinearRequest>();
			List<LinearRequest> temp2 = new ArrayList<LinearRequest>();
			//make a deep copy of rl
			for(LinearRequest lr: rl) {
				temp.add(lr);
				temp2.add(lr);
			}
			
			
			//print random request list
			for(LinearRequest lr : temp) {
				System.out.println(lr.toString());
			}
			System.out.println("\n\n");
			
			//Run alg with our calculated average for comparisons
			runAlg(temp,0,0,calculatedAvg[0],calculatedAvg[1],calculatedAvg[2]);
			
			System.out.println("The Max size gained by the calculated average weight values = " + set.size());
			
			//Print the max size subset from our calculatedAvg weights
			for(LinearRequest s : set) {
				System.out.println(s.toString());
			}
			set.clear();
			
			//Run the algorithm that eliminate some dead time
			runAlg2(temp2,0,0,0,1,1);
			System.out.println("The Max size gained by the allow 30 min earlier = " + set.size());
			for(LinearRequest s : set) {
				System.out.println(s.toString());
			}
		}
	}
	
	//Assign the f-value to a given linear request list
	public static void assignf(List<LinearRequest> lrl, double weight1, double weight2, double weight3, double origin) {
		for(LinearRequest lr: lrl) {
			lr.setf(weight1,weight2,weight3,Math.abs((double)lr.startPos - origin),lr.finishTime,lr.getX3(lrl));
		}
	}
	

	
	//simulate the running of the algorithm
	public static void runAlg(List<LinearRequest> lrl, double time, int origin, double w1, double w2, double w3){
		
		//remove all illegal requests
		for(int i = 0; i<lrl.size(); i++) {
			//if finish time minus start time is greater than big T
			if((lrl.get(i).finishTime - lrl.get(i).pickTime) > (12*6)) {
				//keep track of unserved request
				unServed.add(lrl.get(i));
				lrl.remove(i);
				i--;
				continue;
			}
			if(lrl.get(i).pickTime < time) {
				//keep track of unserved request
				unServed.add(lrl.get(i));
				lrl.remove(i);
				i--;
				continue;
			}
			//if arrival time is greater than the time desired for pickup
			if(Math.abs(lrl.get(i).startPos-origin)+ time > lrl.get(i).pickTime) {
				//keep track of unserved request
				unServed.add(lrl.get(i));
				lrl.remove(i);
				i--;
			}
		}
		
		//Assign f-value after removing all illegal requests
		assignf(lrl,w1,w2,w3,origin);
		//sorts list based on f value
		Collections.sort(lrl);;
		
		//adds first element of lrl to our set
		if (lrl.size()>0) {
			set.add(lrl.get(0));
			
			//update time & origin
			time = lrl.get(0).finishTime;
			origin = lrl.get(0).finishPos;
			lrl.remove(0);
		
			runAlg(lrl,time,origin,w1,w2,w3);//Recursive call
		}
		
		return;
	}
	
	
	/*
	 * The version of runAlg that will allow for pickups to be at most 30 mins earlier
	 */
	public static void runAlg2(List<LinearRequest> lrl, double time, int origin, double w1, double w2, double w3){
		//remove all illegal requests
		for(int i = 0; i<lrl.size(); i++) {
			//if finish time minus start time is greater than big T
			if((lrl.get(i).finishTime - lrl.get(i).pickTime) > (12*6)) {
				unServed.add(lrl.get(i));
				lrl.remove(i);
				i--;
				continue;
			}
			//If the pickTime is passed
			if(lrl.get(i).pickTime < time) {
				unServed.add(lrl.get(i));
				lrl.remove(i);
				i--;
				continue;
			}
			//if arrival time is greater than the time desired for pickup
			if(Math.abs(lrl.get(i).startPos-origin)+ time > lrl.get(i).pickTime) {
				unServed.add(lrl.get(i));
				lrl.remove(i);
				i--;
			}
		}
		
		//Assign f-value after removing all illegal requests
		assignf(lrl,w1,w2,w3,origin);
		
		//sorts list based on f value
		Collections.sort(lrl);;
		
		//Pick the first legal greedy request
		if(lrl.size()>0) {
			
			//Allow for pickups to be at most 30 mins earlier (eliminate the dead time)
			if(Math.abs(lrl.get(0).startPos - origin) <= (lrl.get(0).pickTime - time - 3)) {
				lrl.get(0).pickTime -= 3;
				lrl.get(0).finishTime -= 3;
			}else if (Math.abs(lrl.get(0).startPos - origin) == (lrl.get(0).pickTime - time - 2)) {
				lrl.get(0).pickTime -= 2;
				lrl.get(0).finishTime -= 2;
			}else if (Math.abs(lrl.get(0).startPos - origin) == (lrl.get(0).pickTime - time - 1)) {
				lrl.get(0).pickTime -= 1;
				lrl.get(0).finishTime -= 1;
			}
			
			//adds first element of lrl to our set
			//set.add(lrl.get(0));
			LinearRequest r1 = lrl.get(0);
			lrl.remove(0);
			List<LinearRequest> shareRiders = shareRide(lrl,r1);
			
					
		
			
			for(LinearRequest lr : shareRiders) {
				//if(shareRiders.size() >1 ) System.out.println("RideShare : " + lr.toString());
				set.add(lr);
				//update the time and origin
				time = lr.finishTime;
				origin = lr.finishPos;
			}
			
			
			//update the time and origin
//			time = shareRiders.get(0).finishTime;
//			origin = shareRiders.get(0).finishPos;
			
			
			
			runAlg2(lrl,time,origin,w1,w2,w3);//Recursive call
		}
		return;
	}
	
	//Generate random Driver List of n drivers
	public static List<Driver> createRandomDrivers(int n){
		//arbitrarily set
		int  maxDrivers = 25; 
		
		ArrayList<Driver> dList = new ArrayList<Driver>();		
		Random random = new Random();
	    //keep track of previous driver locations
	    boolean[] prevLocs = new boolean[maxDrivers];
	    for (int i=0; i<maxDrivers; i++){
	      prevLocs[i] = false;
	    }
		int temp = n;
    
		while (n > 0) {
			String dName = String.valueOf(temp-n+1);
	        //choose origin that hasn't been used
	        int origin = 0;
	        while(true){
		        origin = random.nextInt(maxDrivers);
		        if (!prevLocs[origin]) {
		        	prevLocs[origin] = true;
		            break;
		        }
        }
		Driver d = new Driver(dName, origin);
			dList.add(d);
			n--;
		}
		
		return dList;
	}
	
	//Generate Random Request List
	public static List<LinearRequest> creatRandomRequest() {
		/*
		 * Let the maximum driving time to be T = 10, we can remove all the requests that finishTime - pickTime > 10
		 * We also use speed = 3 to stimulate the speed so that it can be more convenient
		 * rList.get(0) = new Request({0,0}, {1,1}, 1);
		 * rList.get(1) = new Request({1,9}, {1,17}, 9);
		 * rList.get(2) = new Request({30,30}, {11,19}, 3);
		 */
		
		/*
		 * Using Linear Request, then the pickTime should always be less than the pickup Location if we are using int
		 * r.List.get(0) = new Request(1,10,1);
		 * r.List.get(1) = new Request(2,4,0);
		 * r.List.get(2) = new Request(7,10,3);
		 */
		List<LinearRequest> rList = new ArrayList<LinearRequest>();
		
		
		Random random = new Random();
		
		//Generate numRequest amount of requests and insert into the list(with negative coords)
		for(int i=0; i<numRequest;i++) {
			
			int prob = random.nextInt(10)+1;
			int p;
			
			if(prob == 1) {
				p = random.nextInt(37);
			}
			
			else if(prob == 10) {
				p = random.nextInt(30) + 115;
			}
			
			else{
				p = random.nextInt(78) + 37;
			}
				
			//s can only be less than or equal to p
			int s = random.nextInt(p+1);
			//the destination is no more than 3 units of time away from the starting position
			int d = s + random.nextInt(37)-18;
			//prevent s and d from being the same #
			while(s==d) {
				d = s + random.nextInt(37)-18;
			}
			
			
			if(d-s <= 18) {
				rList.add(new LinearRequest(s,d,p));
			}
			else {
				i--;
			}
		}
		
		return rList;
	}

	
	//This simulate one entire iteration
	public static List<Test> simulate() {
		
		//creates random request
		List<LinearRequest> rl = creatRandomRequest();
		List<Test> result = new ArrayList<Test>();
		
		//simulates random request
		for(double i = 0; i<=10; i++) {
			for(double j = 0; j<=10; j++) {
				for(double k = 0; k<=10; k++) {
					List<LinearRequest> temp = new ArrayList<LinearRequest>();
					//make a deep copy of rl
					for(LinearRequest lr: rl) {
						temp.add(lr);
					}
					assignf(temp,i,j,k,0);
					runAlg(temp,0,0,i,j,k);
					
//					double percent = (((double)set.size())/((double)rl.size()))*100;
					int subSetSize = set.size();
//					System.out.println(subSetSize);
					
					result.add(new Test(subSetSize,i,j,k));
					
					
					set.clear();
				}
			}
		}
		
		//Sort the list result
		Collections.sort(result);
				
		return result;
	}
	
	//Run the normal algorithm but try all possible combinations and return the maxSize
	public static int runAlg1(List<LinearRequest> rl, int driverLoc) {
		
		int maxSize = Integer.MIN_VALUE;
		
		//simulates random request
		for(double i = 0; i<=10; i++) {
			for(double j = 0; j<=10; j++) {
				for(double k = 0; k<=10; k++) {
					List<LinearRequest> temp = new ArrayList<LinearRequest>();
					//make a deep copy of rl
					for(LinearRequest lr: rl) {
						temp.add(lr);
					}
					set.clear();
					
					runAlg(temp,0,driverLoc,i,j,k);
					
					int subSetSize = set.size();
					if(subSetSize > maxSize) maxSize = subSetSize;			
					
				}
			}
		}
		
				
		return maxSize;
	}
	
	
	/* The shareRide method will return the List that can share ride with r1
	 * The List lrl will not include r1 because we will remove it first
	 */
	public static List<LinearRequest> shareRide(List<LinearRequest> lrl, LinearRequest r1){
		List<LinearRequest> sr = new ArrayList<LinearRequest>();
		sr.add(r1);
		
		//if(Case1(r1.startPos,r2.startPos,r1.finishPos,r2.finishPos))
		
		
		//Case 1 & Case 5 (Inclusive)
		for(LinearRequest r2 : lrl) {
			if(Case1(r1.startPos,r2.startPos,r1.finishPos,r2.finishPos) || Case5(r1.startPos,r2.startPos,r1.finishPos,r2.finishPos)) {
				//T1 is the Arrival time fpr S2 from s1 from p1
				int T1 = r1.pickTime + Math.abs(r2.startPos - r1.startPos);
				if(T1 <= r2.pickTime && (r2.pickTime - T1) <= 3) {
					//Make r2 get earlier no more than 30 mins
					r2.pickTime = T1;
					r2.finishTime =  r2.pickTime + r2.weight;
					sr.add(r2);
				}
			}
			else if(Case3(r1.startPos,r2.startPos,r1.finishPos,r2.finishPos) || Case7(r1.startPos,r2.startPos,r1.finishPos,r2.finishPos)) {
				int T1 = r1.pickTime + Math.abs(r2.startPos - r1.startPos);
				if(T1 <= r2.pickTime && (r2.pickTime - T1) <= 3) {
					//Make r2 get earlier no more than 30 mins
					r2.pickTime = T1;
					r2.finishTime =  r2.pickTime + r2.weight;
					sr.add(r2);
				}
//				sr.get(0).finishTime = r2.finishTime;
//				sr.get(0).finishPos = r2.finishPos;
			}
				
			
		}
		
		return sr;
	}
	
	//The simulate function that not create the random request List
	public static List<Test> noRandSimulate(List<LinearRequest> rl) {
		
		List<Test> result = new ArrayList<Test>();
		
		//simulates random request
		for(double i = 0; i<=10; i++) {
			for(double j = 0; j<=10; j++) {
				for(double k = 0; k<=10; k++) {
					List<LinearRequest> temp = new ArrayList<LinearRequest>();
					//make a deep copy of rl
					for(LinearRequest lr: rl) {
						temp.add(lr);
					}
					
					runAlg(temp,0,0,i,j,k);
					

					int subSetSize = set.size();
					System.out.println(set.size());
					
					result.add(new Test(subSetSize,i,j,k));
					
					
					set.clear();
				}
			}
		}
		
		//Sort the list result
		Collections.sort(result);
		
				
		return result;
	}
	
	//Case 1, inclusive positive
	//0,s1,s2,d2,d1
	public static boolean Case1(int s1, int s2,int d1,int d2) {
		if(s2 >= s1 && s2 <= d1) {
			if(d2 > s2 && d2 <= d1) {
				return true;
			}
		}
		return false;
	}
	
	//Case 3: 0,s1,s2,d1,d2
	public static boolean Case3(int s1, int s2,int d1,int d2) {
		if(s2 >= s1 && s2 < d1) {
			if(d2 > s2 && d2 >= d1) {
				if(d2-d1 <= 6) {//Make sure that the r2 trip is not super long
					return true;
				}
			}
		}
		return false;
	}
	
	
	//Case 5, inclusive negative
	public static boolean Case5(int s1, int s2,int d1,int d2) {
		if(s2 <= s1 && s2 >=d1) {
			if(d2 < s2 && d2 >= d1) {
				return true;
			}
		}
		return false;
	}
	
	//Case 7
	public static boolean Case7(int s1, int s2, int d1, int d2) {
		if(s2 <= s1 && s2 > d1) {
			if(d2 < s2 && d2 <= d1) {
				if(Math.abs(d2-d1) <= 6) {
					return true;
				}
			}
		}
		return false;
	}
	
	//The method that calculate the average
	public static double[] calcAvg(List<Test> t) {
		
		double[] avg = new double[3];
		
		double counter = 0;
		int i = 0;
		
		while(t.get(0).setSize == t.get(i).setSize) {
			avg[0] += t.get(i).weight1;
			avg[1] += t.get(i).weight2;
			avg[2] += t.get(i).weight3;
			counter ++;
			i++;
		}
		
		avg[0] = avg[0]/counter;
		avg[1] = avg[1]/counter;
		avg[2] = avg[2]/counter;
		
		
		return avg;
	}
	 
  public static int crossOver(int array[], int low, int high, int x){
    if (array[high] <= x) // x is greater than all
      return high;
    if (array[low] > x)  // x is smaller than all
      return low;
 
    // Find the middle point
    int mid = (low + high)/2;  /* low + (high - low)/2 */
 
    /* If x is same as middle element, then return mid */
    if (array[mid] <= x && array[mid+1] > x)
      return mid;
 
    /* If x is greater than arr[mid], then either arr[mid + 1]
    is ceiling of x or ceiling lies in arr[mid+1...high] */
    if(array[mid] < x)
      return crossOver(array, mid+1, high, x);
 
    return crossOver(array, low, mid - 1, x);
  }

  //x - driver's origin
  //k - threshold
  //n - number of requests
  public static List<LinearRequest> kclosest(List<LinearRequest> lr, int x, int k, int n){

    List<LinearRequest> finalreqs = new ArrayList<LinearRequest>();
    ArrayList<Integer> startPoints = new ArrayList<Integer>();
    
    for(LinearRequest l: lr){
      startPoints.add(l.startPos);
    }
    
    int[] startP_arr = new int[startPoints.size()];
    for (int i = 0; i < startP_arr.length; i++) {
//    	System.out.println(startP_arr[i]);
    }
    
    for(int i = 0; i < startPoints.size(); i++) 
    	startP_arr[i] = startPoints.get(i);
    
    ArrayList<Integer> points = new ArrayList<Integer>();
    
    int l = crossOver(startP_arr, 0, n-1, x);
    int r = l+1;   // Right index to search
    int count = 0; // To keep track of count of elements already printed
 
    // If x is present in arr[], then reduce left index
    // Assumption: all elements in arr[] are distinct
    if (startP_arr[l] == x) l--;
 
    // Compare elements on left and right of crossover
    // point to find the k closest elements
    while (l >= 0 && r < n && count < k)
    {
      if (x - startP_arr[l] < startP_arr[r] - x)
        points.add(startP_arr[l--]);
        //System.out.print(array[l--]+" ");
      else
        points.add(startP_arr[r++]);
        //System.out.print(array[r++]+" ");
      count++;
    }
 
    // If there are no more elements on right side, then
    // print left elements
    while (count < k && l >= 0)
    {
      //System.out.print(arr[l--]+" ");
      points.add(startP_arr[l--]);
      count++;
    }
 
 
    // If there are no more elements on left side, then
    // print right elements
    while (count < k && r < n)
    {
      //System.out.print(array[r++]+" ");
      points.add(startP_arr[r++]);
      count++;
    }
    for(int i: points){
      for(LinearRequest req: lr){
        if (i == req.startPos){
          finalreqs.add(req);
        }
      }
    }
    return finalreqs;
  }
  
  /*
   * New MultiDriver function, uses f value to assign requests to drivers only if we hadn't tried them 
   * before
   */
  public static int multiDriverNew2(List<Driver> drivers, List<LinearRequest> globalReqs) {
	  int threshold = globalReqs.size() / drivers.size() + 2;
	  int k = globalReqs.size();
	  int size = globalReqs.size();
	  
	  
	  for(LinearRequest l: globalReqs) {
		  for(int i=0; i<drivers.size(); i++){
			  l.servable.add(i, true);
		  }
	  }
	  
	  
	  while(size > 0 ) {
		  for(Driver d: drivers) {
			  if(d.schedule.size() < threshold) {
				  k = globalReqs.size();
				  List<LinearRequest> cClosest = kClosest(d, globalReqs, k);
				  LinearRequest best = lowestFVal(cClosest, d);
				  if(best == null) continue;
				  if(best.driverList.size() < 1) {
					  d.requests.add(best);
					  best.driverList.add(d);
				  }
			  }
			  schedulingFunc(d.requests, d, globalReqs);

		  }
		  size--;

	  }
//	  System.out.println("==================New Multi Driver Algorithm - Fvalue ==================");
//	  System.out.println("==================REQUESTS ASSIGNED==================");
	  int totalServed = 0;
      for(Driver d : drivers) {
    	  totalServed += d.schedule.size();
//	  	  if (d.schedule.size() == 0)
//			  System.out.println("Driver: " + d.getName() + " has no served requests!");
//		  else {
//	    	  System.out.println("Driver: " + d.getName() + " Origin: " + d.initOrigin + " served the following " + d.schedule.size() + " request(s):");
//	    	  for(LinearRequest l : d.schedule)
//	    		  System.out.println(l.toString());
//		  }
//	      System.out.println("------------");
//	      
//	      if (d.unserved.size() == 0)
//			  System.out.println("Driver: " + d.getName() + " has no unserved requests!");
//		  else {
//	    	  System.out.println("Driver: " + d.getName() + " didn't serve the following " + d.unserved.size() + " request(s):");
//	    	  for(LinearRequest l : d.unserved)
//	    		  System.out.println(l.toString());
//		  }
//	      System.out.println("==============");
      }

//	  System.out.println("Total requests served: " + totalServed);
//      System.out.println("-----------------------------------------------------------------");
//      
//      System.out.println("Global requests: ");
//      for(LinearRequest l : globalReqs) {
//		  System.out.println(l.toString());
//      }
      return totalServed;



  }
  
  
  /*
   * New MultiDriver function, assigns closest, earliest requests to drivers only if we hadn't tried them 
   * before
   */
  public static int multiDriverNew(List<Driver> drivers, List<LinearRequest> globalReqs) {
	  int threshold = globalReqs.size() / drivers.size() + 2;
	  int k = globalReqs.size();
	  int size = globalReqs.size();
	  
	  
	  for(LinearRequest l: globalReqs) {
		  for(int i=0; i<drivers.size(); i++){
			  l.servable.add(i, true);
		  }
	  }
	  
	  
	  while(size > 0 ) {
		  for(Driver d: drivers) {
			  if(d.schedule.size() < threshold) {
				  k = globalReqs.size();
				  List<LinearRequest> cClosest = kClosest(d, globalReqs, k);
				  LinearRequest best = earliest(cClosest, d);
//				  LinearRequest best = closestUntried(cClosest, d);
				  if(best == null) continue;
				  if(best.driverList.size() < 1) {
					  d.requests.add(best);
					  best.driverList.add(d);
				  }
			  }
			  schedulingFunc(d.requests, d, globalReqs);

		  }
		  size--;

	  }
	  
	  //Print each driver's schedule
//	  System.out.println("==================New Multi Driver Algorithm - Closest ==================");
//	  System.out.println("==================REQUESTS ASSIGNED==================");
	  int totalServed = 0;
      for(Driver d : drivers) {
    	  totalServed += d.schedule.size();
//	  	  if (d.schedule.size() == 0)
//			  System.out.println("Driver: " + d.getName() + " has no served requests!");
//		  else {
//	    	  System.out.println("Driver: " + d.getName() + " Origin: " + d.initOrigin + " served the following " + d.schedule.size() + " request(s):");
//	    	  for(LinearRequest l : d.schedule)
//	    		  System.out.println(l.toString());
//		  }
//	      System.out.println("------------");
//	      
//	      if (d.unserved.size() == 0)
//			  System.out.println("Driver: " + d.getName() + " has no unserved requests!");
//		  else {
//	    	  System.out.println("Driver: " + d.getName() + " didn't serve the following " + d.unserved.size() + " request(s):");
//	    	  for(LinearRequest l : d.unserved)
//	    		  System.out.println(l.toString());
//		  }
//	      System.out.println("==============");
      }

//	  System.out.println("Total requests served: " + totalServed);
//      System.out.println("-----------------------------------------------------------------");
//      
//      System.out.println("Global requests: ");
//      for(LinearRequest l : globalReqs) {
//		  System.out.println(l.toString());
//      }
      
      return totalServed;

	  
  }
  /*
   * Return earliest request
   */
  public static LinearRequest earliest(List<LinearRequest> cClosest, Driver d) {
	//find earliest
	  int earliestTime = 1000000000;
	  LinearRequest earliest = null;
	  
	  //get earliest request
	  for(LinearRequest l: cClosest) {
		  if(l.pickTime < earliestTime && l.servable.get(Integer.parseInt(d.getName())- 1)) {
			  earliestTime = l.pickTime;
			  earliest = l;
		  }
	  }
	  
	  
	  return earliest;
  }
  
  /*
   * Returns the closest  element that hasn't already been tried by this driver
   * or null
   */
  public static LinearRequest closestUntried(List<LinearRequest> cClosest, Driver d) {
	  LinearRequest closestUntried = null;
	  
	  //get closest untested request
	  for(int i=0;i<cClosest.size(); i++) {
		  if(cClosest.get(i).servable.get(Integer.parseInt(d.getName())- 1)) {
			  closestUntried = cClosest.get(i);
			  break;
		  }
	  }
	  
	  
	  return closestUntried;
  } 
  
  /*
   * Returns request with lowest f value that hasn't already been attempted by this driver
   * Returns null if all have been attempted
   */
  public static LinearRequest lowestFVal(List<LinearRequest> lrl, Driver d) {
		//Assign f-value after removing all illegal requests
		assignf(lrl,0,1,1,d.getOrigin());
		//sorts list based on f value
		Collections.sort(lrl);
		for(int i=0; i<lrl.size(); i++) {
			LinearRequest best = lrl.get(i);
			if(best.servable.get(Integer.parseInt(d.getName())- 1)) {
				return best;
			}
		}
		return null;
  }
  
  /*
   * Scheduling function
   */
  public static void schedulingFunc(List<LinearRequest> lrl, Driver d, List<LinearRequest> global) {
	    if(lrl.size() < 1) return;
		if(lrl.get(0).pickTime < d.currentTime) {
			int in = global.indexOf(lrl.get(0));
			global.get(in).servable.add(Integer.parseInt(d.getName()) - 1, Boolean.valueOf(false));
			global.get(in).driverList.clear();
			d.unserved.add(lrl.get(0));
			d.requests.clear();
			return;
		}
		
		//if arrival time is greater than the time desired for pickup
		if(Math.abs(lrl.get(0).startPos-d.getOrigin())+ d.currentTime > lrl.get(0).pickTime) {
			int in = global.indexOf(lrl.get(0));
			global.get(in).servable.add(Integer.parseInt(d.getName()) - 1, Boolean.valueOf(false));
			global.get(in).driverList.clear();
			d.unserved.add(lrl.get(0));
			d.requests.clear();
			return;
		}
		
		d.currentTime = lrl.get(0).finishTime;
		d.setOrigin(lrl.get(0).finishPos);
		d.schedule.add(lrl.get(0));
		global.remove(lrl.get(0));
		d.requests.clear();

		
		
		
  }


  /*
  * Method for multi-drivers. Should return the number of requests served. 
  * To Do: method should also take as parameter a list of the driver start locations.
  */ 
  public static List<LinearRequest> MultiDriver(List<LinearRequest> lr, List<Driver> drivers, int thresh) {    
    //figure out what threshold should be
    double threshold = thresh;
    
    //assign to each driver d, n/k closest requests to them
    for(Driver d : drivers){
      int ori = d.getOrigin();
      int th = (lr.size() / drivers.size());
      List<LinearRequest> kClosestReq = new ArrayList<LinearRequest>();
      List<LinearRequest> kClosestReq2 = new ArrayList<LinearRequest>();

      
      kClosestReq2 = kclosest(lr, ori, th, lr.size());
      kClosestReq = kClosest(d, lr, th);

//      System.out.println("Driver: "+ d.getName() + " initially has the following 3 reqs");
      for (LinearRequest l : kClosestReq){
        l.driverList.add(d);
        d.addRequest(l);
//        System.out.println(l.toString());
      }
//      Systemfu.out.println("-----------------------------------------------------------------");

      
    }
        
    for(LinearRequest l: lr){
      //if request not assigned, find closest driver and assign to them if not past
      //threshold requests
      if (l.driverList.size() == 0){
        Driver closest = null;
        double minDist = 1000000;
        for (Driver d: drivers){
          //if driver is not past threshold request number
          if (d.getNumRequests() < (lr.size() / drivers.size() + threshold)){
            //if driver is closer than the minimum distance seen before
            if (d.distToReq(l.startPos) < minDist) {
              closest = d;
              minDist = d.distToReq(l.startPos);
            }
          }
        }
        //add the closest driver to the request's list and increment that driver's request number
        if (closest != null) {
        	l.driverList.add(closest);
            closest.addRequest(l);
        }
      }
      //if request has more than 1 driver assigned
      else if (l.driverList.size() > 1){
        Driver bestDriver = null;
        //Vermont area is about 10,000 miles^2
        double minDist = 1000000;
        //find out which driver has the least distance to each request
        for(Driver Dr: l.driverList){
           if (Dr.distToReq(l.startPos) < minDist){
             bestDriver = Dr;
             minDist = Dr.distToReq(l.startPos);
           } 
        }
        //Subtract this request from the number of requests that the 
        //drivers that weren't chosen have
        for(Driver Dr : l.driverList) {
          if (!Dr.equals(bestDriver))
            Dr.redRequests(l);
        }
        //take out all drivers, add the one with the least requests
        if (bestDriver != null) {
        	l.driverList.clear();
            l.driverList.add(bestDriver);
        }
      }
    }
    //for debugging
//    System.out.println("==================REQUESTS ASSIGNED==================");
//    for(Driver d : drivers) {
//		if (d.getRequests().size() == 0)
//			System.out.println("Driver: " + d.getName() + " has no requests!");
//		else {
//	    	System.out.println("Driver: " + d.getName() + " now has the following " + d.getNumRequests() + " request(s):");
//	    	for(LinearRequest l : d.getRequests())
//	    		System.out.println(l.toString());
//		}
//	    System.out.println("==============");
//    }
//    System.out.println("-----------------------------------------------------------------");


    return lr;
  }
  
  public static List<LinearRequest> kClosest (Driver d, List<LinearRequest> lr, int k){
	  HashMap<LinearRequest, Double> distMap = new HashMap<LinearRequest, Double>();
      for (LinearRequest l : lr) {
    	  distMap.put(l, d.distToReq(l.startPos));
      }
      List<Map.Entry<LinearRequest, Double>> list = new ArrayList<Map.Entry<LinearRequest, Double>>(distMap.entrySet());
      
      Collections.sort(list, new Comparator<Map.Entry<LinearRequest, Double>>() {
    		  public int compare(Map.Entry<LinearRequest, Double> o1, Map.Entry<LinearRequest, Double> o2)
    		  {
    			  return (o1.getValue()).compareTo(o2.getValue());
    		  }
      });
      
      List<LinearRequest> output = new ArrayList<LinearRequest>();
      for (int i=0; i<k; i++) {
    	  output.add(list.get(i).getKey());
      }
    		  
	  
	  return output;
  }

  
	
	
}

