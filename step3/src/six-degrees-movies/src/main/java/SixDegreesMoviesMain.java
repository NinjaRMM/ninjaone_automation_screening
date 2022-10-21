import sixDegreesMoviesServices.SixDegreesMoviesService;

public class SixDegreesMoviesMain {

    private static final SixDegreesMoviesService sixDegreesMoviesService = new SixDegreesMoviesService();

    public static void main(String[] args) {

        System.out.println(sixDegreesMoviesService.performanceExecution(args));

    }
}
