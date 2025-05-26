import java.time.Instant;
import java.util.LinkedList;
import java.util.Queue;

public class SlidingWindowLog {

    private int windowSize;
    private Queue<Long> requestLog;
    private int maxRequestPerWindow;

    SlidingWindowLog(int windowSize, int maxRequestPerWindow ) {
        this.windowSize = windowSize;
        this.maxRequestPerWindow = maxRequestPerWindow;
        requestLog = new LinkedList<>();
    }



    public boolean allowRequest() {
        Long now = Instant.now().getEpochSecond();

        long windowStart = now - windowSize;

        // remove requests that have gone out of window
        while(!requestLog.isEmpty() && requestLog.peek() < windowStart ){
            requestLog.remove(); // removing all elements that have gone out of window start
        }

        if(requestLog.size() < maxRequestPerWindow) { // if requests in the window < max request allowed, allow the request
            requestLog.add(now);
            return true;
        }
        else{
            return false;
        }

    }
}
