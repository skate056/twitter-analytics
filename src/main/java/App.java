import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class App {
    public static Set<String> tagsToTrack() {
        Set<String> tagsToTrack = new HashSet<>();
        tagsToTrack.add("aap");
        tagsToTrack.add("#aap");
        tagsToTrack.add("bjp");
        tagsToTrack.add("#bjp");
        tagsToTrack.add("congress");
        tagsToTrack.add("#congress");
        return tagsToTrack;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java twitter4j.examples.PrintFilterStream [follow(comma separated numerical user ids)] [track(comma separated filter terms)]");
            System.exit(-1);
        }
        Parameter param = parseParams(args);

        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");

        new TwitterListener(mongoOperation, param).run();

    }

    private static Parameter parseParams(String[] args) {
        List<Long> follow = new ArrayList<>();
        Set<String> track = tagsToTrack();
        for (String arg : args) {
            if (isNumericalArgument(arg)) {
                for (String id : arg.split(",")) {
                    follow.add(Long.parseLong(id));
                }
            } else {
                track.addAll(Arrays.asList(arg.split(",")));
            }
        }
        long[] followArray = new long[follow.size()];
        for (int i = 0; i < follow.size(); i++) {
            followArray[i] = follow.get(i);
        }
        String[] trackArray = track.toArray(new String[track.size()]);
        return new Parameter(followArray, trackArray);
    }

    private static boolean isNumericalArgument(String argument) {
        String args[] = argument.split(",");
        boolean isNumericalArgument = true;
        for (String arg : args) {
            try {
                Integer.parseInt(arg);
            } catch (NumberFormatException nfe) {
                isNumericalArgument = false;
                break;
            }
        }
        return isNumericalArgument;
    }

    static class Parameter {
        final long[] follow;
        final String[] track;

        Parameter(long[] follow, String[] track) {
            this.follow = follow;
            this.track = track;
        }
    }
}
