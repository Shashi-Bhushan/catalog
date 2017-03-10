Catalog
========

This Project is an AEM based Book Catalog System. It's meant to be a development exercise.

-   *Uses Separate UI and Content Package System.* 
    UI is for /apps components and /etc clientlibs and design pages.
    CONTENT is for /content pages.

Building
--------

Common commands:

From the root directory, run ``mvn -PautoInstallPackage clean install`` to build the bundle and ui package and install to a CQ instance.

From the root directory, run ``mvn -PautoInstallContent clean install`` to build the content package and install to a CQ instance.

From the bundle directory, run ``mvn -PautoInstallBundle clean install`` to build *just* the bundle and install to a CQ instance.

Using with VLT
--------------

To use vlt with this project, first build and install the package to your local CQ instance as described above. Then cd to `content/src/main/content/jcr_root` and run

    vlt --credentials admin:admin checkout -f ../META-INF/vault/filter.xml --force http://localhost:4502/crx

Once the working copy is created, you can use the normal ``vlt up`` and ``vlt ci`` commands.

