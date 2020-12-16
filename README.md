# traffic-situations

We need to implement traffic-situations in Java with threads.

## Exercise 1: simple_semaphore

- the solution is straight-forward
- cars enter in intersection, sleep and exit

## Exercise 2: simple_n_roundabout

- just n cars can enter in the intersecection
- a `semaphore` with max_cars_that_can_enter_the roundabout permits solved this problem

## Exercise 3: simple_strict_1_car_roundabout

- I've solved this problem with an ArrayList and wait/notify mechanism
- Firstly, all cars reach the roundabout
- `Main.differentIds` keeps if a car on a lane has already entered in the intersection
- if a car has already entered(the value will be 1), `wait` for the lane to be released
- else set the value to 1 and enter in the roundabout
- after the car exits, `set value` for this lane to `0` and `notifyAll` cars

## Exercise 4: simple_strict_x_car_roundabout

- although this exercise is very similar to the previous one, I've changed the method becase it starts to get complicated
- I've defined an `Array with semaphores`, with `strict_cars_on_lane permits`
- all cars from the roundabout need to be in the same step ==> barriers before and after every write operation
- after all steps, every car must `release the semaphore`
 
## Exercise 5: simple_max_x_car_roundabout

- this requirement is very similar to the previous one
- it uses the same skeleton, but the cars do not need to be in the same step, so we can exclude barriers

## Exercise 6: priority_intersection

- at this exercise, cars with low priority need to wait the high priority cars to finish crossing the intersection
- I've used an `counter`(AtomicInteger) to keep the number of priority cars which are in the intersection
- if the current car is a `high priority car`, enter the intersection and cross it in 2 seconds
- `low priority cars` will be included in a queue, to come out in the same order as they entered
- if the current car is a `low priority car`, wait for high priority cars to exit the intersection
- remove cars from the queue(cross the intersection)

### Problems

- tests which fails: **sometimes**, test 3 fails
- lines 13 are 14 are inverted
- out file:
    ```
    Car 4 with low priority has entered the intersection  
    Car 3 with low priority has entered the intersection
    ```

- ref file:
    ```
    Car 3 with low priority has entered the intersection  
    Car 4 with low priority has entered the intersection
    ```

## Exercise 7: crosswalk

- simulation of a crosswalk
- I've initialized a flag `lastColor` (AtomicInteger) to keep the code of the last color(`0 for green`, `1 for red`)
- while pedestrians have not finished
  - if the pedestrians have green color(cars have red) and last printed message for cars was for `green light`, print `message for red light`
  - if the pedestrians have red color(cars have green) and last printed message for cars was for `red light`, print `message for green light`
- `finished` and `pass` varabiles from `Pedestrians.java` were not safe (becase they weere saved in cache)
  - I've completed with `volatile` atribute

### Problems

- tests which fails: **sometimes**, test 4 fails
- on the first change of the semaphore color -- red color
- just 9 cars print that the semaphore is red
- this happens **JUST** on the second run

## Exercise 8: simple_maintenance

- cars from both lanes will run on a single lane

- `bool` is a flag to check if all cars from the other lane has passed
- `wait` for all cars to reach the bottleneck

- let's say that cars from `lane 0` are high priority cars and cars from `lane 1` - low priority cars

- if the current car is from `lane 0`, it has priority
  - check if there are low priority cars that are in the bottleneck and wait to complete
  - after that, cross the bottleneck
  - wait for all cars that must cross the intersection at a time
- if the current car is a `low priority car`
  - wait for priority cars to cross the bottleneck
  - cross the bottleneck
  - wait for all low priority cars from this round to cross the bottleneck 

- both types of cars have a semaphores, to accept just a limited number of cars
- at the end, release semaphores for all cars, to begin next round

## Exercise 9: complex_maintenance

## Exercise 10: railroad

- this exercise was very similar with 6
- I've used a queue to keep the order of the cars and a barrier to simulate the train
- after the train "passed", the car with `id 0` prints that the train has passed
- extract from the queue cars and print the messages
