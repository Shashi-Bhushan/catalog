CQ.commerce = CQ.commerce || {};

/**
 * @class CQ.commerce.CatalogGoodreadsImporter
 * @extends CQ.Ext.Viewport
 * The importer enables the user to import product catalogs from a remote Hybris system.
 * @constructor
 * Creates a new importer.
 * @param {Object} config The config object
 */
CQ.commerce.CatalogGoodreadsImporter = CQ.Ext.extend(CQ.Ext.Viewport, {
    constructor : function(config) {
        this.results = document.createElement("iframe");
        this.results.id = "results_cq-CatalogGoodreadsImporter";
        this.results.name = "results_cq-CatalogGoodreadsImporter";
        this.results.height = "100%";
        this.results.width = "100%";
        this.results.onload = this.onResultsLoad;
        this.results.onreadystatechange = this.onResultsLoad;

        var importer = this;
        CQ.commerce.CatalogGoodreadsImporter.superclass.constructor.call(this, {
            "id" :"cq-CatalogGoodreadsImporter",
            "layout":"border",
            "renderTo":CQ.Util.getRoot(),
            "items" : [
                {
                    "id":"cq-CatalogGoodreadsImporter-wrapper",
                    "xtype":"panel",
                    "region":"center",
                    "layout":"border",
                    "border":false,
                    "items": [
                        {
                            "id":"cq-header",
                            "xtype":"container",
                            "autoEl":"div",
                            "region":"north",
                            "items": [
                                {
                                    "xtype":"panel",
                                    "border":false,
                                    "layout":"column",
                                    "cls": "cq-header-toolbar",
                                    "items": [
                                        new CQ.UserInfo({}),
                                        new CQ.HomeLink({})
                                    ]
                                }
                            ]
                        },{
                            "layout": "vbox",
                            "region": "center",
                            "items": [
                                {
                                    "xtype" :"form",
                                    "id" :"cq-CatalogGoodreadsImporter-form",
                                    "title":CQ.I18n.getMessage("GoodReads Product Catalog Importer"),
                                    "standardSubmit" : true,
                                    "autoScroll": true,
                                    "border":false,
                                    "margins":"5 5 5 5",
                                    "autoHeight": true,
                                    "defaults" : {
                                        "anchor" : "-54"
                                    },
                                    "style" : "background-color:white",
                                    "bodyStyle" : "padding:10px",
                                    "items" : [
                                        {
                                            "xtype" : "textfield",
                                            "fieldLabel" : CQ.I18n.getMessage("Base Path"),
                                            "fieldDescription" : CQ.I18n.getMessage("Base API Path for Goodreader Author API"),
                                            "name" : "baseApiPath",
                                            "allowBlank" : false,
                                            "value" : "https://www.goodreads.com/author/list.xml"
                                        },{
                                            "xtype" : "textfield",
                                            "fieldLabel" : CQ.I18n.getMessage("API Key"),
                                            "fieldDescription" : CQ.I18n.getMessage("Developer API Key for authorization"),
                                            "name" : "apiKey",
                                            "allowBlank" : false,
                                            "value" : "WbooinDS1zjuuoWD7lg"
                                        },{
                                            "xtype" : "textfield",
                                            "fieldLabel" : CQ.I18n.getMessage("Author ID"),
                                            "fieldDescription" : CQ.I18n.getMessage("Goodreads Author ID for which books are to be imported"),
                                            "name" : "authorId",
                                            "allowBlank" : false,
                                            "value" : "566.Paulo_Coelho"
                                        },{
                                            "xtype" : "pathfield",
                                            "fieldLabel" : CQ.I18n.getMessage("Products Base Path"),
                                            "fieldDescription" : CQ.I18n.getMessage("Base path to import products to"),
                                            "name" : "baseProductsPath",
                                            "allowBlank" : false,
                                            "value" : "/var/commerce/products/catalog"
                                        },{
                                            "xtype":"hidden",
                                            "name":"_charset_",
                                            "value":"utf-8"
                                        },{
                                            "xtype":"hidden",
                                            "name":":operation",
                                            "value":"import"
                                        },{
                                            "xtype":"hidden",
                                            "name":"fallbackProvider",
                                            "value":"hybris"
                                        }
                                    ],
                                    "buttonAlign":"left",
                                    "buttons":[
                                        {
                                            "id":"cq-CatalogGoodreadsImporter-btn-import",
                                            "text":CQ.I18n.getMessage("Import Catalog"),
                                            "handler":function() {
                                                var form = CQ.Ext.getCmp("cq-CatalogGoodreadsImporter-form").getForm();
                                                if (form.isValid()) {
                                                    var btn = CQ.Ext.getCmp("cq-CatalogGoodreadsImporter-btn-import");
                                                    btn.setDisabled(true);

                                                    var log = CQ.Ext.getCmp("cq-CatalogGoodreadsImporter-log");
                                                    log.expand();

                                                    // submit form
                                                    form.getEl().dom.action = CQ.HTTP.externalize(config.url);
                                                    form.getEl().dom.method = "POST";
                                                    form.getEl().dom.target = "results_cq-CatalogGoodreadsImporter";
                                                    form.submit();
                                                }
                                            }
                                        },
                                        new CQ.Ext.ProgressBar({
                                            "id":"cq-CatalogGoodreadsImporter-progress",
                                            "width":400,
                                            "hidden":true
                                        })
                                    ]
                                }
                            ]
                        },{
                            "xtype":"panel",
                            "id" :"cq-CatalogGoodreadsImporter-log",
                            "region":"south",
                            "title":CQ.I18n.getMessage("Import Log"),
                            "margins":"-5 5 5 5",
                            "height": 300,
                            "split":true,
                            "collapsible": true,
                            "collapsed": false,
                            "items":[
                                new CQ.Ext.BoxComponent({
                                    "autoEl": {
                                        "tag": "div"
                                    },
                                    "style": {
                                        "width": "100%",
                                        "height": "100%",
                                        "margin": "-2px"
                                    },
                                    "listeners":{
                                        render:function(wrapper) {
                                            new CQ.Ext.Element(importer.results).appendTo(wrapper.getEl());
                                        }
                                    }
                                })
                            ],
                            "plugins":[
                                {
                                    init: function(p) {
                                        if (p.collapsible) {
                                            var r = p.region;
                                            if ((r == "north") || (r == "south")) {
                                                p.on("collapse", function() {
                                                    var ct = p.ownerCt;
                                                    if (ct.layout[r].collapsedEl && !p.collapsedTitleEl) {
                                                        p.collapsedTitleEl = ct.layout[r].collapsedEl.createChild ({
                                                            tag:"span",
                                                            cls:"x-panel-collapsed-text",
                                                            html:p.title
                                                        });
                                                    }
                                                }, false, {single:true});
                                            }
                                        }
                                    }
                                }
                            ]
                        }
                    ]
                }
            ]
        })
    },

    onResultsLoad: function() {
        var btnCatalog = CQ.Ext.getCmp("cq-CatalogGoodreadsImporter-btn-import");
        btnCatalog.setDisabled(false);
    }
});
CQ.Ext.reg("cataloggoodreadsimporter", CQ.commerce.CatalogGoodreadsImporter);
