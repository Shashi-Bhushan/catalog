package in.shabhushan.catalog.services.impl

import in.shabhushan.catalog.services.PersonConfiguration
import org.apache.felix.scr.annotations.Activate
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Modified
import org.apache.felix.scr.annotations.Property
import org.apache.felix.scr.annotations.PropertyOption
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
 * 1.0              Oct 25, 2017    Shashi          Initial OSGi ConfigImpl
 *
 * TODO:
 * ----------------------------------------------------------------------------------------
 * - Configuration Using {@link org.apache.felix.scr.annotations.Properties}
 * - Configuration Factory
 * - osgiConfig Node
 * - ConfigAdmin class
 *
 * This is a concrete Implementation of {@link PersonConfiguration}
 * The class is created as a way to demonstrate various different types of OSGi Configuration Types.
 */

/*
 * Notes:
 * ----------------------------------------------------------------------------------------
 * Declarative Services (DS) is a component model that simplifies the creation of components that publish and/or
 * reference OSGi Services.
 *
 * - Declarative means no need to write explicit code to publish or consume services.
 * - components that publish services are delayed, meaning that the service implementation class is not loaded or
 *   instantiated until the service is actually requested by a client.
 * - components have their own lifecycle (i.e. activation and deactivation), bounded by the lifecycle of the bundle
 *   in which they are defined.
 * - components can automatically receive configuration data from Configuration Admin.
 *
 * Order of precedence of configurations
 * felix > apps > libs is precedence order while looking for configurations at run time(Reason Below)
 * and apps > libs > felix is the order while startup
 * Reason: In CQ5. Any changes done in felix console modifies the config files with the highest priority(in apps).
 *
 * Tools have taken different approach to generate Service-Component's xml.
 * BND and SCR takes Annotations approach.
 * Note that the maven-bundle-plugin and maven-scr-plugin will generate the manifest(Manifest.mf)
 * and serviceComponents(serviceComponents.xml) files automatically
 */
@Component(
    label = "Person Configuration",
    immediate = true,
    metatype = true,
    enabled = true,
    description = "Sample OSGi Configuration having Some Properties"
)
class PersonConfigurationImpl implements PersonConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(PersonConfigurationImpl)

    /**
     * This property specifies with which name to save this property in ComponentContext Map.
     * You can directly do, componentContextMap.get('PropertyName') each time you require the value of the property
     * and you would get the latest property of that name.
     */
    @Property(
        name = "config.name",
        label = "Person's Name",
        description = "Configuration Supporting String Values only"
    )
    private static final String STRING_CONFIG_KEY = "Default Value"

    @Property(
        label = "Person's Password",
        description = "Configuration Supporting String Values only",
        passwordValue = []
    )
    private static final String PASSWORD_CONFIG_KEY = "config.password"

    @Property(label="Gender" , description = "Configuration Supporting Dropdown Selection",
        options = [
            @PropertyOption(name = "Male", value = "MALE"),
            @PropertyOption(name = "Female", value = "FEMALE")
        ])
    private static final String DROPDOWN_CONFIG_KEY = "config.gender"

    @Property(
        label = "Phone Numbers",
        description = "Configuration Supporting Multiple Values",
        cardinality = Integer.MAX_VALUE
    )
    private static final String MULTIFIELD_CONFIG_KEY = "config.phoneNumbers"

    @Property(
        label = "Number of Children",
        description = "Configuration Supporting Numeric Values only",
        intValue = 0
    )
    private static final String NUMERIC_CONFIG_KEY = "config.numberOfChildren"

    @Property(
        label = "Married",
        description = "Configuration Supporting Boolean Values only",
        boolValue = false
    )
    private static final String BOOLEAN_CONFIG_KEY = "config.married"


    @Activate
    protected void activate(final ComponentContext context) {
        logger.info("${PersonConfigurationImpl.class.name} : Activate Method.")
        this.modified(context)
        logger.info("${PersonConfigurationImpl.class.name} : Activate Method End.")
    }

    @Modified
    protected void modified(final ComponentContext context) {
        logger.info("${PersonConfigurationImpl.class.name} : Modified Method.")
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

        logger.info("${PersonConfigurationImpl.class.name} : Modified Method End.")
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
