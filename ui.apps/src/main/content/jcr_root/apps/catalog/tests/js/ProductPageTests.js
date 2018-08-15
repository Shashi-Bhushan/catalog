"use strict";

(function(hobs){

    var productPageHref = "/content/catalog/en/fiction/the-best-we-could-do.html";

    var hobsTestSuite = new hobs.TestSuite("Catalog - Product Page Tests", {
            path:"/apps/catalog/tests/js/ProductPageTests.js",
            register: true
        });

    hobsTestSuite.addTestCase(
        new hobs.TestCase("Navigate To Catalog Product")
            .navigateTo(productPageHref)
            .asserts.location(productPageHref, true)
    );

    hobsTestSuite.addTestCase(
        new hobs.TestCase("Open Model")
            .navigateTo(productPageHref)
            .asserts.hasCssClass("div[id='myModal']:has(div[class='modal-content'])", "modal fade")
            .click("button[data-target='#myModal']")
            .asserts.hasCssClass("div[id='myModal']:has(div[class='modal-content'])", "modal fade in")
    );

    hobsTestSuite.addTestCase(
        new hobs.TestCase("Open Model and Close")
            .navigateTo(productPageHref)
            .asserts.hasCssClass("div[id='myModal']:has(div[class='modal-content'])", "modal fade")
            .click("button[data-target='#myModal']")
            .asserts.hasCssClass("div[id='myModal']:has(div[class='modal-content'])", "modal fade in")
            .click("button[class='close'][data-dismiss='modal']")
            .asserts.hasCssClass("div[id='myModal']:has(div[class='modal-content'])", "modal fade")
    )
})(hobs);
