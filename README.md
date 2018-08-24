Catalog
========

This Project is an AEM based Book Catalog System. Hence the name Catalog. 
It's meant to be a development exercise. AEM version supported is AEM 6.4.

Features -
-   *Uses Separate UI and Content Package System.*  
    UI.apps is for /apps components and /etc folder deployment.  
    CONTENT is for /content pages.  
    Have different Maven Deployment profiles as well.
-   *AEM Scaffolding is used*  
    Used to create /etc/commerce/products nodes  
-   *AEM Commerce API is implemented*  
    Commerce API provider implementation for AbstractJcrProducts etc.  
    It's under in.shabhushan.catalog.commerce package.  
    Intention is to write Spock based test cases for these classes as well.
-   *Groovy & Spock*  
    Groovy - a JVM based Dynamic scripting language  
    Spock - a Unit testing framework with very Groovy like syntax  

Building
--------

Common commands:

From the root directory, run ``mvn -PautoInstallPackage clean install`` to build the core, config and ui.apps packages and install to a CQ instance.
This package will have all the Groovy code, OSGi configurations and apps package. 

From the root directory, run ``mvn -PautoInstallContent clean install`` to build the ui.content package and install to a CQ instance.
This package will have all the editable templates and their policies, Product scaffolding, Project Catalog specific tags, Catalog specific dam images, Catalog specific users, Catalog's Products, their blueprints and Catalog's content. 

From the bundle directory, run ``mvn -PautoInstallBundle clean install`` to build *just* the bundle and install to a CQ instance.

Using with VLT
--------------

To use vlt with this project, first build and install the package to your local CQ instance as described above. Then cd to `content/src/main/content/jcr_root` and run

    vlt --credentials admin:admin checkout -f ../META-INF/vault/filter.xml --force http://localhost:4502/crx

Once the working copy is created, you can use the normal ``vlt up`` and ``vlt ci`` commands.

