import java.time.Instant;

public class FixedWindowCounter {

    private int maxRequestsPerWindow;
    private int windowSize; // in seconds
    private Instant windowStartTime;
    private int requestCount;


    FixedWindowCounter(int maxRequestsPerWindow, int windowSize) {
        this.maxRequestsPerWindow = maxRequestsPerWindow;
        this.windowSize = windowSize;
        this.windowStartTime = Instant.now();
        this.requestCount = 0;
    }



    public boolean allowRequest() {
        Instant now = Instant.now();

        if((now.toEpochMilli() - windowStartTime.toEpochMilli())/ 1000 >= windowSize) {
            // new window started
            requestCount = 0;
            windowStartTime = now;
        }

        if(requestCount < maxRequestsPerWindow) {
            requestCount++;
            return true;
        }
        else{
            return false;
        }


    }






}
