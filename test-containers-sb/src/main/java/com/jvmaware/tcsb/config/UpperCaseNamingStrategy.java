package com.jvmaware.tcsb.config;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * Spring uses lower snake case by default, which means it uses only
 * lower case letters and separates words with underscores. The following
 * customization will change this to be upper case names.
 *
 * @author gaurs
 */
public class UpperCaseNamingStrategy extends CamelCaseToUnderscoresNamingStrategy {

    @Override
    protected Identifier getIdentifier(String name, boolean quoted, JdbcEnvironment jdbcEnvironment){
        return new Identifier(name.toUpperCase(), quoted);
    }
}