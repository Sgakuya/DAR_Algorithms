# DAR_ALGORITHMS_TESTS

These files contain multiple functions that were used in the development of a near-optimal scheduling algorithm for Vermont's Tri-Valley transit organization's Dial-A-Ride service.

## AUTHORS
Smith Gakuya  
Prof. Ananya Das - Middlebury College Computer Science Dept.

## PROJECT DESCRIPTION 

Rides are simulated on a singular plane (1D) and code for creation/comparison of requests is in ***LinearRequest.java***
Drivers who take up the various requests have functions that are declared in ***Driver.java***

The bulk of the greedy algorithms implemented and tested are located in ***WeightFunction.java***
The weight function that we ended up using primarily takes the following into consideration:
  1) _x1: the distance from the current position of the driver to the pickup location of the request_
  2) _x2: the desired pickup time_
  3) _x3: the travel time between the drop-off location of the request to the shortest travel time to the pickup location of another request._
Other features used include ride sharing for rides headed in the same general direction and around the same time.

Formatting, printing and comparisons of the different algorithms are done in ***Comparison.java***
