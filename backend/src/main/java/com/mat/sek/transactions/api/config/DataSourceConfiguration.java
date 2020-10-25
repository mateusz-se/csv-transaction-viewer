package com.mat.sek.transactions.api.config;

import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.SQLDialect;
import org.jooq.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {
    private DataSource dataSource;

    @Autowired
    public DataSourceConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider
                (new TransactionAwareDataSourceProxy(dataSource));
    }

    @Bean
    public DefaultDSLContext dsl() {
        return new DefaultDSLContext(configuration());
    }

    public DefaultConfiguration configuration() {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.set(connectionProvider());
        jooqConfiguration
                .set(new DefaultExecuteListenerProvider(exceptionTransformer()));
        return jooqConfiguration;
    }

    private ExecuteListener exceptionTransformer() {
        return new DefaultExecuteListener() {
            @Override
            public void exception(ExecuteContext context) {
                SQLDialect dialect = context.configuration().dialect();
                SQLExceptionTranslator translator
                        = new SQLErrorCodeSQLExceptionTranslator(dialect.name());
                context.exception(translator
                        .translate("Access database using Jooq", context.sql(), context.sqlException()));
            }
        };
    }


}
