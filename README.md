## Starting the application

To start the application, you can use the following command:

Go to the Docker compose directory and run:

```shell script
  cd .\Docker\
```

And then run:

```shell script
  docker compose up --build
```

This will start the backend and the frontend in Docker containers.

## User Interface

The frontend is an Angular application that provides a user interface for interacting with the backend API.

You can access the application UI at: [http://localhost:4200](http://localhost:4200).

If you want to start the frontend separately, you can navigate to the `frontend` directory:

```shell script
  cd .\Frontend\
```

And then run:

```shell script
  docker build -t lab_seq_fe .
```

```shell script
  docker run -p 4200:4200 lab_seq_fe
```

Alternatively, you can run the frontend using npm:

```shell script
  npm install
```

```shell script
  ng serve
```

## Backend

The backend is a Quarkus application that provides a RESTful API for managing users and their associated data.

You can access the backend API at: [http://localhost:8080/lab/seq/10](http://localhost:8080/lab/seq/10).

(You can then change the number 10 to whatever positive integer.)

If you want to start the backend separately, you can navigate to the `backend` directory:

```shell script
  cd .\Backend\
```

And then run:

```shell script
  docker build -t lab_seq_be .
```

```shell script
  docker run -p 8080:8080 lab_seq_be
```

Alternatively, you can run the backend using Maven:

```shell script
  ./mvnw quarkus:dev
```

## Swagger UI

You can access the Swagger UI for the backend API
at: [http://localhost:8080/q/swagger-ui/](http://localhost:8080/q/swagger-ui/).

## Caching

The caching mechanism implemented in the backend uses a simple arrayList to store the results of the sequence
calculations. If the same number is requested again, the result is retrieved from the cache instead of recalculating it.

```java
private static ArrayList<BigInteger> cache = new ArrayList<>();
```

## Algorithm

For this algorithm I inspired myself in the Fibonacci sequence, where each number is the sum of the two preceding ones.

I inspired myself in this algorithm from this
article [https://www.geeksforgeeks.org/java-fibonacci-series/](https://www.geeksforgeeks.org/java-fibonacci-series/):

```java
class Geeks {

    // Function to find the fibonacci Series
    static int fib(int n) {
        // Declare an array to store
        // Fibonacci numbers.
        // 1 extra to handle case, n = 0
        int f[] = new int[n + 2];

        int i;

        // 0th and 1st number of
        // the series are 0 and 1
        f[0] = 0;
        f[1] = 1;

        for (i = 2; i <= n; i++) {

            // Add the previous 2 numbers
            // in the series and store it
            f[i] = f[i - 1] + f[i - 2];
        }

        // Nth Fibonacci Number
        return f[n];
    }

    public static void main(String args[]) {
        // Given Number N
        int N = 10;

        // Print first 10 term
        for (int i = 0; i < N; i++)
            System.out.print(fib(i) + " ");
    }
}
```

However, this algorithm does not use caching, and basically resets every time a function is called. This is not what we
want, so I modified it to use a cache to store the results of the sequence
calculations.

So a combination between the Memoization algorithm and the Fibonacci Series Using Dynamic Programming.

In the end, the algorithm calculates the sequence as follows:

```java
public class LabSequence {
    private static ArrayList<BigInteger> cache = new ArrayList<>();

    private void resetCache() {
        cache.clear();
        cache.add(BigInteger.ZERO);
        cache.add(BigInteger.ONE);
        cache.add(BigInteger.ZERO);
        cache.add(BigInteger.ONE);
    }


    public BigInteger labseq(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Number cannot be negative");
        }
        int cacheLength = cache.size();
        if (number < cacheLength) {
            return cache.get(number);
        }

        try {
            for (int i = cacheLength; i <= number; i++) {
                BigInteger calculation = cache.get(i - 4).add(cache.get(i - 3));
                cache.add(calculation);
            }
        } catch (OutOfMemoryError oom) {
            resetCache();
            throw new OutOfMemoryError("Please select a lower number: " + number);
        }
        return cache.get(number);
    }
}
```

Every element is stored in the cache. If the number is already in the cache, it is returned directly. If not, the
algorithm calculates from that number up to the requested number, storing each result in the cache.

Also, you may have noticed the BigInteger type. This is because the numbers can get very large, and we need to use a
type that can handle large integers without overflowing.

Another important point is that the algorithm uses a try-catch block to handle OutOfMemoryError. If the cache grows too large and the JVM runs out of memory, the cache is reset and an OutOfMemoryError is thrown with a message suggesting the user to select a lower number.


## Exception Handling

The backend application uses an exception handler to manage errors and provide meaningful responses to the client.
You can find the exception handler implementation in the `exceptionHandler` folder.

For this I inspired myself in this article: [https://howtodoinjava.com/resteasy/resteasy-exceptionmapper-example/](https://howtodoinjava.com/resteasy/resteasy-exceptionmapper-example/)