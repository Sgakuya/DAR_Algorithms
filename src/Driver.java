/*
  *
  * Class to store a driver
  *
*/

import java.util.*;

public class Driver{
  private String name;
  public int numRequests = 0;
  private int origin;
  public int initOrigin = 0;
  public int currentTime = 0;
  public ArrayList<LinearRequest> requests = new ArrayList<LinearRequest>();
  public ArrayList<LinearRequest> schedule = new ArrayList<LinearRequest>();
  public ArrayList<LinearRequest> unserved = new ArrayList<LinearRequest>();



  public Driver(String s, int origin){
    this.origin = origin;
    this.initOrigin = origin;
    this.name = s;
  }
  
  public void addRequest(LinearRequest l){
    numRequests++;
    requests.add(l);
  }

  public void redRequests(LinearRequest l){
    numRequests--;
    requests.remove(l);
  }

  public String getName(){
    return name;
  }

  public int getOrigin(){
    return origin;
  }
  
  public void setOrigin(int o) {
	  origin = o ;
  }

  public int getNumRequests(){
    return numRequests;
  }
  
  public ArrayList<LinearRequest> getRequests (){
	  return requests;
  }

  public double distToReq(int req){
    return Math.abs(origin - req);
  }

  public boolean equals(Driver B){
    if(this.name.equals(B.name) && this.origin == B.origin && this.numRequests == B.numRequests)
      return true;
    return false;
  }
  
}//