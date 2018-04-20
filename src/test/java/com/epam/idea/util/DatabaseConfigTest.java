package com.epam.idea.util;

import com.epam.idea.core.repository.config.db.ProdDatabaseConfig;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Aliaksei_Liubetski on 2015-07-01.
 */
//ProdDatabaseConfig
//@RunWith(MockitoJUnitRunner.class)
public class DatabaseConfigTest {

    public void shouldCheckDataSource(){
        ProdDatabaseConfig config = new ProdDatabaseConfig();
        javax.sql.DataSource ds = config.dataSource();

        assertThat(ds).isNotNull();
    }//shouldCheckDataSource

    public void shouldGetJPAProperties(){
        ProdDatabaseConfig config = new ProdDatabaseConfig();
        Properties prop = config.jpaProperties();

        assertThat(config).isNotNull();
        assertThat(prop).isNotNull();
    }//shouldCheckDataSource
}
