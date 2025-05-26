import java.time.Instant;
import java.util.LinkedList;
import java.util.Queue;

public class LeakyBucket {

    int capacity;
    int currentSize;
    Queue<Instant> bucket; // It will store the requests
    Instant lastLeakTime;
    double leakRate; // how much requests are leaked per min. (req/minute)



    LeakyBucket(int capacity) {
        this.capacity = capacity;
        this.currentSize = 0;
        this.bucket = new LinkedList<>(); // implementation of queue
        lastLeakTime = Instant.now();
    }



    public boolean allowRequest() {
        leak();

        // check if bucket size < capacity
        if(currentSize < capacity) {
            // add req to the bucket
            bucket.add(Instant.now());

            return true;
        }
        else {
            return false;
        }

    }

    private void leak() {

        // identify the count of requests, that have been processed by the server
        // these need to be removed from the queue
        Instant now = Instant.now();

        double noOfRequestLeaked = (now.toEpochMilli() - lastLeakTime.toEpochMilli()) * leakRate / 60 * 1000;

        for(int i=0; i<noOfRequestLeaked && !bucket.isEmpty(); i++) {
            // remove the request from the bucket ( queue ) from front
            // remove until the requests that should be removed are not removed && the till the bucket is not empty
            bucket.remove();
            currentSize--;
        }

        if(noOfRequestLeaked > 0) lastLeakTime = Instant.now();
    }




}
