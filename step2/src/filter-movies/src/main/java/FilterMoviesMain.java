import filterMoviesServices.FilterMoviesService;
import org.apache.commons.lang3.math.NumberUtils;

public class FilterMoviesMain {

    private static FilterMoviesService filterMoviesService = new FilterMoviesService();

    public static void main(String[] args) {
        Integer decade;
        Boolean isStrictMode = Boolean.FALSE;
        if(args.length < 1){
            System.out.println("Missing arguments <decade> <strictMode>[Optional]");
            //Should throw exception instead of return;
            return;
        }

        try{
            decade = NumberUtils.createInteger(args[0]);
        } catch (NumberFormatException e){
            System.out.println("Bad type for argument <decade> should be a number");
            //Should throw exception instead of return;
            return;
        }

        if(args.length > 1){
            isStrictMode = Boolean.valueOf(args[1]);
        }

        filterMoviesService.filterMovies(decade, isStrictMode);
    }
}
