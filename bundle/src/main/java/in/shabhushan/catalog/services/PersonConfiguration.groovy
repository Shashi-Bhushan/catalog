package in.shabhushan.catalog.services

/**
 * @author Shashi Bhushan
 *
 * Change History:
 * Revision         Date            Dev             Comment
 * ----------------------------------------------------------------------------------------
 * 1.0              Oct 25, 2017    Shashi          Initial OSGi Config
 *
 */
interface PersonConfiguration {
    String getStringConfig()
    String getPasswordConfig()
    String getDropDownConfig()
    Integer getNumericConfig()
    Boolean getBooleanConfig()
    String[] getMultifieldConfig()
}
