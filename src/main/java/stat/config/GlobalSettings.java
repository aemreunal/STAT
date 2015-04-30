package stat.config;

/*
 * ******************************* *
 * Copyright (c) 2015              *
 *                                 *
 * Sales Tracking & Analytics Tool *
 *                                 *
 * @author Ahmet Emre Ünal         *
 * @author Uğur Özkan              *
 * @author Burcu Başak Sarıkaya    *
 * @author Eray Tunçer             *
 *                                 *
 * ******************************* *
 */

public class GlobalSettings {
    /**
     * These strings provide package names for annotation-based scanning.
     */
    public static final String BASE_PACKAGE_NAME       = "stat";
    // This must be the path of the package which holds the entity classes.
    // The Entity Manager Factory scans the package designated by this string
    // to map entities.
    public static final String ENTITY_PACKAGE_NAME     = BASE_PACKAGE_NAME + ".domain";
    public static final String REPOSITORY_PACKAGE_NAME = BASE_PACKAGE_NAME + ".service.repository";

    public static final String APP_DB_PROPERTY_SOURCE  = "file:db.properties";
    public static final String TEST_DB_PROPERTY_SOURCE = "file:test.properties";

    //-------------------------------------------------------------------------------------------
    // Property name: "hibernate.hbm2ddl.auto"
    //
    // hibernate.hbm2ddl.auto: Automatically validates or exports schema DDL to the
    // database when the SessionFactory is created. With create-drop, the database
    // schema will be dropped when the SessionFactory is closed explicitly.
    //
    // Values: "validate" | "update" | "create" | "create-drop"
    //
    // validate:    validate the schema, makes no changes to the database.
    // update:      update the schema.
    // create:      creates the schema, destroying previous data.
    // create-drop: drop the schema at the end of the session.
    //----------------------------------------
    public static final String HBM2DDL_KEY         = "hibernate.hbm2ddl.auto";
    public static final String HBM2DDL_PROPERTY    = "update";
    //-------------------------------------------------------------------------------------------


    //-------------------------------------------------------------------------------------------
    // Property name: "hibernate.show_sql"
    //----------------------------------------
    public static final String SHOW_SQL_KEY        = "hibernate.show_sql";
    public static final String SHOW_SQL_PROPERTY   = "false";
    //-------------------------------------------------------------------------------------------


    //-------------------------------------------------------------------------------------------
    // Property name: "hibernate.format_sql"
    //----------------------------------------
    public static final String FORMAT_SQL_KEY      = "hibernate.format_sql";
    public static final Object FORMAT_SQL_PROPERTY = "true";
    //-------------------------------------------------------------------------------------------


    //-------------------------------------------------------------------------------------------
    // Property name: "hibernate.dialect"
    //----------------------------------------
    public static final String DB_DIALECT_KEY      = "hibernate.dialect";
    public static final String DB_DIALECT_PROPERTY = "org.hibernate.dialect.MySQL5InnoDBDialect";
    //-------------------------------------------------------------------------------------------

    //-------------------------------------------------------------------------------------------
    // Default database properties
    //----------------------------------------
    public static final String DB_PROPERTY_USERNAME_KEY = "db.username";
    public static final String DB_DEFAULT_USERNAME      = "stat";

    public static final String DB_PROPERTY_PASSWORD_KEY = "db.password";
    public static final String DB_DEFAULT_PASSWORD      = "stat";

    public static final String DB_PROPERTY_IP_KEY       = "db.ip";
    public static final String DB_DEFAULT_IP            = "localhost";

    public static final String DB_PROPERTY_PORT_KEY     = "db.port";
    public static final String DB_DEFAULT_PORT          = "3306";

    public static final String DB_PROPERTY_NAME_KEY     = "db.name";
    public static final String DB_DEFAULT_NAME          = "stat";

    public static final String DB_PROPERTY_DDL_KEY      = "db.hbm2_ddl";

    public static final String DB_PROPERTY_SHOW_SQL_KEY = "db.show_sql";
    public static final int    PRICE_TOTAL_PRECISION    = 16;
    public static final int    PRICE_DECIMAL_PRECISION  = 4;
    //-------------------------------------------------------------------------------------------
}
