package com.poc.common.config.mongo;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.poc.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${mongodb.poc.host}")
    private String mongoHost;

    @Value("${mongodb.poc.port}")
    private String mongoPort;

    @Value("${mongodb.poc.mongoUri}")
    private String mongoUri;

    @Value("${mongodb.poc.dbname}")
    private String mongoDbname;

    /*@Value("${mongodb.poc.admin}")
    private String dbAdmin;

    @Value("${mongodb.poc.username}")
    private String userName;

    @Value("${mongodb.poc.password}")
    private String password;*/

    @Bean
    public MongoClient mongoClient() {

        // String mongoURI = MessageFormat.format( "mongodb://{0}:{1}@{2}:{3}/{4}", userName, password, mongoHost, mongoPort, dbAdmin);
        // String mongoURI = MessageFormat.format("mongodb://{0}:{1}/?retryWrites=false", mongoHost, mongoPort);
        ConnectionString s = new ConnectionString(mongoUri);
        // System.out.println(s);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(s).readConcern(ReadConcern.MAJORITY).writeConcern(WriteConcern.MAJORITY)
                .uuidRepresentation(UuidRepresentation.JAVA_LEGACY)
                .build();
        return MongoClients.create(settings);

    }

    @Override
    protected String getDatabaseName() {
        return mongoDbname;
    }

    @Primary
    @Bean
    public MongoDatabaseFactory mongoDbFactory() {
        return super.mongoDbFactory();
    }

    @Primary
    @Bean
    public MappingMongoConverter mongoConverter(MongoDatabaseFactory mongoFactory, MongoMappingContext mongoMappingContext) throws Exception {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoFactory);
        MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
        mongoConverter.setMapKeyDotReplacement("-DOT-");

        return mongoConverter;
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }

    @Bean(name = "mongoTransactionManager")
    public MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {

        // cf doc https://blog.clairvoyantsoft.com/mongodb-transaction-management-884f82f62767
        TransactionOptions transactionOptions = TransactionOptions.builder().readConcern(ReadConcern.MAJORITY).writeConcern(WriteConcern.MAJORITY).build();
        return new MongoTransactionManager(dbFactory, transactionOptions);
    }

    // @Bean  (name = "mongoTemplateSecondary")
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate template = new MongoTemplate(mongoClient(), mongoDbname);
        // template.setReadPreference(ReadPreference.secondaryPreferred(90000,TimeUnit.MILLISECONDS));
        // template.setReadPreference(ReadPreference.secondaryPreferred());
        return template;
    }

}
