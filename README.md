# Word Count

This project takes a file with mime type of text/plain, counts the words in the file,
and displays some statistics that include the total number of words and the count of
each of the words found.

Word Count is divided into a front end Javascript React single page application and
a backend Scala Play Framework service.

### Backend
The backend consists of two controllers and a model:
1. FrontEndController was contributed by the seed project and it serves the React
applicatio.
2. WordCountController wraps the WordCount algorithm and handles marshalling and
unmarshalling of inputs and outputs. It has a single method that processes an HTTP POST
containing a multipart mime with a single FilePart. This FilePart is expected to be of
type text/plain. The controller calls into the WordCount object and then marshalls the
output into JSON which it sends back to the caller.
3. WordCount contains the core algorithm. The function count() takes a String,
and returns both the total number of words and the count of each word. This algorithm
has a worst case time complexity of O(n log n) and a worst case space complexity of
O(n). The algorithm is designed to count the words in a 10MB string in under 2 seconds.

There are two interesting tests:
1. WordCountControllerSpec which tests WordCountController
2. WordCountSpec which test which tests WordCount

### Frontend

The frontend consists of normal React plumbing plus the interesting parts which
contain the core of the display. The core components are WordCount and WordCountResults.
1. WordCount contains a Card for displaying the UI for uploading a file. The problem set
requests a separate step for uploading and then processing but I felt the UX was improved
by combining those two steps into a single action for the user. WordCount also
contains a state machine that shows and hides the WordCountResult component and
the busy indicator.
2. WordCountResults has no state and it simply turns props passed in from WordCount
into a table to display the results.

### Running the Project

This project can be completely controlled by the Simple Build Tool (sbt) but it has a
few other dependencies that need to be installed first.

1. JDK - It is suggested that you use either Oracle's or OpenJDK and it requires at least
version 8 of either one of those.
2. sbt - The most modern version should be used
3. npm - The most modern version should be used

Once these dependencies are installed you can run the entire project through sbt. The
two most interesting commands are:

* Use `sbt run` to run both the backend and frontend. When this is executed it will
open your browser automatically to the WordCount page. If that process fails for
some reason you can see the app at http://localhost:3000
* Use `sbt test` to run both the backend and frontend tests

### Seed

To look at the seed project used see: https://github.com/yohangz/scala-play-react-seed

## License

This software is licensed under the MIT license
