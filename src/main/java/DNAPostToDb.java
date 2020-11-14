import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class DNAPostToDb {

    private final Sql2o sql2o;

    public DNAPostToDb() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("C:\\Users\\olga1\\Desktop\\projects\\DataBase\\ParseDNA\\src\\main\\resources\\db.properties"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(properties.getProperty("db.url"));
        hikariConfig.setDriverClassName(properties.getProperty("db.driver.classname"));
        hikariConfig.setUsername(properties.getProperty("db.username"));
        hikariConfig.setPassword(properties.getProperty("db.password"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.hikari.max-pool-size")));
        sql2o = new Sql2o(new HikariDataSource(hikariConfig));
    }

    public void insertDNA(DNA dna) {
        insertDNA3(dna);
        insertDNA5(dna);
        insertDNA9(dna);
    }

    //language=SQL
    String INSERT_DNA2_INTO_TABLE = "insert into DNA3 (id, t) values(:id,:t)";

    public void insertDNA3(DNA dna) {
        List<String> dnaList = dna.getTaken2();
        for (String dnam: dnaList) {
            try (Connection connection = sql2o.open()) {
                connection.createQuery(INSERT_DNA2_INTO_TABLE)
                        .addParameter("id", dna.getId())
                        .addParameter("t", dnam).executeUpdate();
            }
        }
    }

    //language=SQL
    String INSERT_DNA5_INTO_TABLE = "insert into DNA5 (id, t) values (:id, :t)";
    public void insertDNA5(DNA dna) {
        List<String> dnaList = dna.getTaken5();
        for (String dnam: dnaList) {
            try (Connection connection = sql2o.open()) {
                connection.createQuery(INSERT_DNA5_INTO_TABLE)
                        .addParameter("id", dna.getId())
                        .addParameter("t", dnam).executeUpdate();
            }
        }
    }

    //language=SQL
    String INSERT_DNA9_INTO_TABLE = "insert into DNA9 (id, t) values (:id, :t)";
    public void insertDNA9(DNA dna) {
        List<String> dnaList = dna.getTaken9();
        for (String dnam: dnaList) {
            try (Connection connection = sql2o.open()) {
                connection.createQuery(INSERT_DNA9_INTO_TABLE)
                        .addParameter("id", dna.getId())
                        .addParameter("t", dnam).executeUpdate();
            }
        }
    }

    //language=SQL
    String SELECT3 = "select (select sum(min_count)\n" +
            "        from (\n" +
            "                 select t, 2*min(count) as min_count, count(id) as count_id\n" +
            "                 from (select id, t, count(*) as count from dna3 where id = :id1 or id = :id2 group by id, t)\n" +
            "                          as it2c\n" +
            "                group by t)\n" +
            "                 as t2m where count_id > 1) /\n" +
            "       (select count(*) from dna3 where id = :id1 or id = :id2)::float as coincidences;";

    public Float getPerCentOfCoincidencesFor3(Integer id1, Integer id2) {
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(SELECT3)
                    .addParameter("id1", id1)
                    .addParameter("id2", id2).executeScalar(Float.class);
        }
    }

    //language=SQL
    String SELECT5 = "select (select sum(min_count)\n" +
            "        from (\n" +
            "                 select t, 2*min(count) as min_count, count(id) as count_id\n" +
            "                 from (select id, t, count(*) as count from dna5 where id = :id1 or id = :id2 group by id, t)\n" +
            "                          as it2c\n" +
            "                group by t)\n" +
            "                 as t2m where count_id > 1) /\n" +
            "       (select count(*) from dna5 where id = :id1 or id = :id2)::float as coincidences;";

    public Float getPerCentOfCoincidencesFor5(Integer id1, Integer id2) {
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(SELECT5)
                    .addParameter("id1", id1)
                    .addParameter("id2", id2).executeScalar(Float.class);
        }
    }

    //language=SQL
    String SELECT9 = "select (select sum(min_count)\n" +
            "        from (\n" +
            "                 select t, 2*min(count) as min_count, count(id) as count_id\n" +
            "                 from (select id, t, count(*) as count from dna9 where id = :id1 or id = :id2 group by id, t)\n" +
            "                          as it2c\n" +
            "                group by t)\n" +
            "                 as t2m where count_id > 1) /\n" +
            "       (select count(*) from dna9 where id = :id1 or id = :id2)::float as coincidences;";

    public Float getPerCentOfCoincidencesFor9(Integer id1, Integer id2) {
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(SELECT9)
                    .addParameter("id1", id1)
                    .addParameter("id2", id2).executeScalar(Float.class);
        }
    }
}
