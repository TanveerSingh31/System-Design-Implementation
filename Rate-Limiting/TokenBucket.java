import java.time.Instant;

public class TokenBucket {

    int bucketCapacity;
    long tokenCount; // current no. of tokens present in bucket
    int fillRate; // store the rate at which tokens are added to the bucket -> 10 token / min
    Instant lastRefillTime; // This will store time-stamp, last time the bucket is refilled


    TokenBucket(int bucketCapacity, int tokenCount, int fillRate) {
        this.bucketCapacity = bucketCapacity;
        this.tokenCount = tokenCount;
        this.fillRate = fillRate;
        lastRefillTime = Instant.now();
    }


    // this method will tell, whether request should be allowed or not.
    public boolean allowRequest() {
        refill();

        // check if any tokens present in bucket
        // If yes, accept the request
        // If No, reject the request

        if(tokenCount > 0) {
            tokenCount--;
            return true;
        }
        else {
            return false;
        }
    }



    private void refill() {
        Instant now = Instant.now();

        long tokenToAdd = (now.toEpochMilli() - lastRefillTime.toEpochMilli()) * fillRate / 60 * 1000; // tokens to be added in bucket, based on time elapsed, since last time token added

        this.tokenCount = Math.min( bucketCapacity, tokenToAdd); // we can at max add tokens equal to capacity of bucket
        if(tokenToAdd > 0) this.lastRefillTime = now; // since when we add token in the bucket, only then update the lastRefillTime
    }


}
