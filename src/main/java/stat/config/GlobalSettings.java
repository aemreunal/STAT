package stat.config;

/*
 **************************
 * Copyright (c) 2014     *
 *                        *
 * This code belongs to:  *
 *                        *
 * Ahmet Emre Ünal        *
 * S001974                *
 *                        *
 * aemreunal@gmail.com    *
 * emre.unal@ozu.edu.tr   *
 *                        *
 * aemreunal.com          *
 **************************
 */

public class GlobalSettings {
    public static final String DEBUGGING = "true";


    /**
     * This regex matches if the given string contains a non-ASCII character. It, however,
     * does not match punctuation, so while "Hellö" matches this regex, "Hello!" won't.
     */
    public static final String NON_ASCII_REGEX = ".*[^\\p{ASCII}].*";

    /**
     * These strings provide package names for annotation-based scanning.
     */
    public static final String BASE_PACKAGE_NAME       = "stat";
    // This must be the path of the package which holds the entity classes.
    // The Entity Manager Factory scans the package designated by this string
    // to map entities.
    public static final String ENTITY_PACKAGE_NAME     = BASE_PACKAGE_NAME + ".domain";
    public static final String REPOSITORY_PACKAGE_NAME = BASE_PACKAGE_NAME + ".repository";


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
    public static final String HBM2DDL_KEY      = "hibernate.hbm2ddl.auto";
    public static final String HBM2DDL_PROPERTY = "update";
    //-------------------------------------------------------------------------------------------


    //-------------------------------------------------------------------------------------------
    // Property name: "hibernate.show_sql"
    //----------------------------------------
    public static final String SHOW_SQL_KEY      = "hibernate.show_sql";
    public static final String SHOW_SQL_PROPERTY = DEBUGGING;
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
}
