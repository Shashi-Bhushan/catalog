new hobs.TestSuite("Catalog - Product Page Tests", {
        path:"/apps/catalog/tests/js/ProductPageTests.js",
        register: true
    })
    .addTestCase(
        new hobs.TestCase("Navigate To Catalog Product")
            .navigateTo("/content/catalog/en/fiction/the-best-we-could-do.html")
            .asserts.location("/content/catalog/en/fiction/the-best-we-could-do.html", true)
    )
    .addTestCase(
        new hobs.TestCase("Open Model")
            .navigateTo("/content/catalog/en/fiction/the-best-we-could-do.html")
            .asserts.hasCssClass("div[id='myModal']:has(div[class='modal-content'])", "modal fade")
            .click("button[data-target='#myModal']")
            .asserts.hasCssClass("div[id='myModal']:has(div[class='modal-content'])", "modal fade in")
    )
    .addTestCase(
        new hobs.TestCase("Open Model and Close")
            .navigateTo("/content/catalog/en/fiction/the-best-we-could-do.html")
            .asserts.hasCssClass("div[id='myModal']:has(div[class='modal-content'])", "modal fade")
            .click("button[data-target='#myModal']")
            .asserts.hasCssClass("div[id='myModal']:has(div[class='modal-content'])", "modal fade in")
            .click("button[class='close'][data-dismiss='modal']")
            .asserts.hasCssClass("div[id='myModal']:has(div[class='modal-content'])", "modal fade")
    )
