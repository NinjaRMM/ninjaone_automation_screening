package step3.src;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class KBSixDegreesImpl {
    private final HashMap<String, Actor> actors;

    public KBSixDegreesImpl() {
        actors = new HashMap<>();
    }

    public static void main(String[] args) {
        KBSixDegreesImpl kb = new KBSixDegreesImpl();
        kb.createFile();
        String fileName = "data/new-input.txt";
        Scanner f = null;

        try {
            f = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("File not found " + fileName);
            e.printStackTrace();
        }

        ArrayList<String> actorsList = kb.filterArguments(args);
        String name1 = kb.transformArgument(actorsList.get(0));
        String name2 = kb.transformArgument(actorsList.get(1));

        assert f != null;
        kb.readNewInput(f);
        kb.filterSecondActorMovies(name2);
        kb.printSteps(name1, name2);
    }

    public void filterSecondActorMovies(String name2) {
        Actor start = actors.get(name2);
        start.setNumber(0);
        start.setPrevActor(null);

        Queue<Actor> actorQueue = new LinkedList<>();
        actorQueue.add(start);
        HashSet<Actor> visited = new HashSet<>();

        while (!actorQueue.isEmpty()) {
            Actor curr = actorQueue.poll();
            visited.add(curr);
            Map<Actor, Movie> coStars = curr.coStars();

            for (Actor a : coStars.keySet()) {
                if (!visited.contains(a)) {
                    actorQueue.add(a);
                    a.setNumber(curr.getNumber() + 1);
                    a.setPrevActor(curr);
                    a.setSharedMovie(coStars.get(a));
                }
            }
        }
    }

    public void printSteps(String name, String name2) {
        Actor start = actors.get(name2);
        Actor dest = actors.get(name);

        if (dest == null) {
            System.out.println(transformNameBack(name) + " did not star in a movie in the data provided");
            return;
        }

        System.out.println("There are " + dest.getNumber() + " degrees of separation between " + transformNameBack(dest.getName()) + " and " + transformNameBack(start.getName()) + "");
        System.out.println();

        while (dest != start) {
            System.out.println(transformNameBack(dest.getName()) + " starred with " + transformNameBack(dest.getPrevActor().getName()) + " in " + dest.getSharedMovie().getName());
            dest = dest.getPrevActor();
        }
    }

    public void readNewInput(Scanner in) {
        while (in.hasNext()) {
            String line = in.nextLine();
            String[] elems = line.split("/");
            if (elems.length == 0) {
                System.out.println("BAD line in input!");
                continue;
            }

            Movie newMovie = new Movie(elems[0]);

            for (int k = 1; k < elems.length; k++) {
                Actor person = actors.get(elems[k]);

                if (person == null) {
                    person = new Actor(elems[k]);
                    actors.put(elems[k], person);
                }

                person.add(newMovie);
                newMovie.addActor(person);
            }
        }
    }

    public void createFile() {
        JSONParser jsonParser = new JSONParser();
        try {
            JSONArray allMovies = (JSONArray) jsonParser.parse(new FileReader("../../data/movies.json"));
            StringBuilder stringBuilder = new StringBuilder();

            for (Object movieObject : allMovies) {
                JSONObject movie = (JSONObject) movieObject;
                JSONArray cast = (JSONArray) movie.get("cast");

                if (cast.size() > 1) {
                    stringBuilder.append(movie.get("title")).append("/");

                    for (Object castParamObj : cast) {
                        String actorName = castParamObj.toString().replaceFirst("(.*) (.*)", "$2, $1");
                        stringBuilder.append(actorName).append("/");
                    }
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    stringBuilder.append("\n");
                }
            }

            FileWriter fileWriter = new FileWriter("data/new-input.txt");

            try {
                fileWriter.write(stringBuilder.toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> filterArguments(String[] args) {
        ArrayList<String> actorsList = new ArrayList<>();

        for (String str : args) {
            actorsList.add(str);
            if (actorsList.size() == 2) break;
        }

        if (actorsList.size() == 1) {
            actorsList.add("KevinBacon");
        }

        return actorsList;
    }

    public String transformArgument(String arg) {
        String[] splitArg = StringUtils.splitByCharacterTypeCamelCase(arg);

        StringBuilder nameStringBuilder = new StringBuilder();
        for (int i = 0; i < splitArg.length; i++) {
            nameStringBuilder.append(splitArg[i]);
            if (!(i == splitArg.length - 1)) {
                nameStringBuilder.append(" ");
            }
        }

        return transformName(nameStringBuilder.toString());
    }

    public String transformName(String name) {
        return name.replaceFirst("(.*) (.*)", "$2, $1");
    }

    public String transformNameBack(String name) {
        return transformName(name).replaceAll(",", "");
    }

}

class Actor {
    private final String name;
    private final ArrayList<Movie> movies;
    private int number;
    private HashMap<Actor, Movie> myCoStars;
    private Actor prevActor;
    private Movie sharedMovie;

    public Actor(String person) {
        name = person;
        movies = new ArrayList<>();
        number = Integer.MAX_VALUE;
        myCoStars = null;
        prevActor = null;
        sharedMovie = null;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public HashMap<Actor, Movie> coStars() {
        if (myCoStars != null)
            return myCoStars;
        myCoStars = new HashMap<>();
        for (Movie m : movies) {
            for (Actor a : m.getActors()) {
                if (a != this) {
                    myCoStars.put(a, m);
                }
            }
        }
        return myCoStars;
    }

    public void add(Movie m) {
        movies.add(m);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int n) {
        if (n < number)
            number = n;

    }

    public Actor getPrevActor() {
        return prevActor;
    }

    public void setPrevActor(Actor prevActor) {
        this.prevActor = prevActor;
    }

    public int hashCode() {
        return name.hashCode();
    }

    public Movie getSharedMovie() {
        return sharedMovie;
    }

    public void setSharedMovie(Movie sharedMovie) {
        this.sharedMovie = sharedMovie;
    }
}

class Movie {
    private final String name;
    private final ArrayList<Actor> actors;

    public Movie(String title, ArrayList<Actor> cast) {
        name = title;
        actors = cast;
    }

    public Movie(String title) {
        name = title;
        actors = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public void addActor(Actor person) {
        actors.add(person);
    }

}