package in.shabhushan.catalog.services.impl

import in.shabhushan.catalog.services.PersonConfiguration
import org.apache.felix.scr.annotations.*
import org.apache.sling.commons.osgi.PropertiesUtil
import org.osgi.service.component.ComponentContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author Shashi Bhushan
 *
 * Change History:
 * Revision         Date            Dev             Comment
 * ----------------------------------------------------------------------------------------
 * 1.0              Nov 11, 2017    Shashi          Initial Person ConfigImpl
 *
 * TODO:
 * ----------------------------------------------------------------------------------------
 * - Configuration Factory
 * - osgiConfig Node
 * - ConfigAdmin class
 *
 * DONE:
 * ----------------------------------------------------------------------------------------
 * - Configuration Using {@link org.apache.felix.scr.annotations.Properties}
 *
 * This is a concrete Implementation of {@link PersonConfiguration}
 * The class is created as a way to demonstrate various different types of OSGi Configuration Types.
 */

/*
 * Notes:
 * ----------------------------------------------------------------------------------------
 * This is a Cleaner Way of Maintaining Properties. All the properties are grouped together.
 *
 * Making This an OSGi Service, in order to demonstrate how data can be retrieved via It's interface class, as opposed to
 * using Configuration Admin.
 */

@Component(
    label = "Person Configuration",
    immediate = true,
    metatype = true,
    enabled = true,
    description = "Sample OSGi Configuration having Some Properties"
)
@Properties([
    @Property(
        name = PersonConfigurationImplCleanerWay.STRING_CONFIG_KEY,
        label = "Person's Name",
        description = "Configuration Supporting String Values only",
        value = "Default Name"
    ),
    @Property(
        name = PersonConfigurationImplCleanerWay.PASSWORD_CONFIG_KEY,
        label = "Person's Password",
        description = "Configuration Supporting String Values only",
        value="Default Password",
        passwordValue = []
    ),
    @Property(
        name = PersonConfigurationImplCleanerWay.DROPDOWN_CONFIG_KEY,
        label = "Gender" ,
        description = "Configuration Supporting Dropdown Selection",
        options = [
            @PropertyOption(name = "Male", value = "MALE"),
            @PropertyOption(name = "Female", value = "FEMALE")
        ]
    ),
    @Property(
        name = PersonConfigurationImplCleanerWay.NUMERIC_CONFIG_KEY,
        label = "Phone Numbers",
        description = "Configuration Supporting Multiple Values",
        cardinality = Integer.MAX_VALUE
    ),
    @Property(
        name = PersonConfigurationImplCleanerWay.BOOLEAN_CONFIG_KEY,
        label = "Married",
        description = "Configuration Supporting Boolean Values only",
        boolValue = false
    )
])
class PersonConfigurationImplCleanerWay implements PersonConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(PersonConfigurationImplCleanerWay)

    /**
     * Static Final String Names for the Configurations
     */
    public static final String STRING_CONFIG_KEY = "config.name"

    public static final String PASSWORD_CONFIG_KEY = "config.password"

    public static final String DROPDOWN_CONFIG_KEY = "config.gender"

    public static final String MULTIFIELD_CONFIG_KEY = "config.phoneNumbers"

    public static final String NUMERIC_CONFIG_KEY = "config.numberOfChildren"

    public static final String BOOLEAN_CONFIG_KEY = "config.married"


    @Activate
    protected void activate(final ComponentContext context) {
        logger.info("${PersonConfigurationImplCleanerWay.class.name} : Activate Method.")
        this.modified(context)
        logger.info("${PersonConfigurationImplCleanerWay.class.name} : Activate Method End.")
    }

    @Modified
    protected void modified(final ComponentContext context) {
        logger.info("${PersonConfigurationImplCleanerWay.class.name} : Modified Method.")
        final Dictionary<?, ?> config = context.properties

        // Synthetic Accessors of Instance Variables
        // Name
        setStringConfig(PropertiesUtil.toString(config.get(STRING_CONFIG_KEY), ""))
        // Gender
        setDropDownConfig(PropertiesUtil.toString(config.get(DROPDOWN_CONFIG_KEY), null))
        // Phone Numbers
        setMultifieldConfig(PropertiesUtil.toStringArray(config.get(MULTIFIELD_CONFIG_KEY), null))
        // No of Children
        setNumericConfig(PropertiesUtil.toInteger(config.get(NUMERIC_CONFIG_KEY), 0))
        // Married
        setBooleanConfig(PropertiesUtil.toBoolean(config.get(BOOLEAN_CONFIG_KEY), false))

        logger.info("Password value is : ${PropertiesUtil.toString(config.get(PASSWORD_CONFIG_KEY), null)}.")

        logger.info("${PersonConfigurationImplCleanerWay.class.name} : Modified Method End.")
    }

    /**
     * This is a normal instance variable in this Class and is used to getProperty once from the map and set in this
     * instance variable so that you won't have to do map.get('PropertyName') each time you require a value.
     */
    // Name
    String stringConfig
    // password
    String passwordConfig
    // Gender
    String dropDownConfig
    // Phone Numbers
    String[] multifieldConfig
    // Number of Children
    Integer numericConfig
    // Married
    Boolean booleanConfig

    /**
     * Caveat:
     * - No UI Check for Property Values. Eg. intValue
     * - Value of Metatype Service Object not Linked to instance Variable. Eg. Password even though not saved as
     *   Instance Variable, still saved in system/config.
     */
}
