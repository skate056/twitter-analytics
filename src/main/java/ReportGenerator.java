import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;

import static org.springframework.data.mongodb.core.query.Query.query;

public class ReportGenerator {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

//        LocalDate startDate = new LocalDate(2015, 02, 06);
//        LocalDate endDate = new LocalDate(2015, 02,15);

        for(long date = 20150207; date <= 20150212; date++) {
            for(long time = 0; time <= 24; time++) {
                Criteria dateCriteria = Criteria.where("date").is(String.valueOf(date)).and("timeHour").is(String.valueOf(time));
                Criteria aap = Criteria.where("date").is(String.valueOf(date)).and("tag").is("aap").and("timeHour").is(String.valueOf(time));
                Criteria bjp = Criteria.where("date").is(String.valueOf(date)).and("tag").is("bjp").and("timeHour").is(String.valueOf(time));
                long count = mongoOperation.count(query(dateCriteria), Tweet.class);
                long aapCount = mongoOperation.count(query(aap), Tweet.class);
                long bjpCount = mongoOperation.count(query(bjp), Tweet.class);
                System.out.printf("date: %10d \t hour: %4d \t total: %5d \t aap: %5d \t bjp: %5d\n", date, time, count, aapCount, bjpCount);
            }
        }
    }
}
