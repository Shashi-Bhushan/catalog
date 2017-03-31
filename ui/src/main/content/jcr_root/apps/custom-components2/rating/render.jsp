<%@page session="false"
import="com.adobe.granite.ui.components.AttrBuilder, com.adobe.granite.ui.components.Config, com.adobe.granite.ui.components.Field,
com.adobe.granite.ui.components.Tag" %>
<%@include file="/libs/granite/ui/global.jsp" %>

<%
    Config cfg = cmp.getConfig();
    ValueMap vm = (ValueMap) request.getAttribute(Field.class.getName());

    String name = cfg.get("name", String.class);

    AttrBuilder attrsInput = new AttrBuilder(request, xssAPI);
    attrsInput.addClass("coral-InputGroup-input");
    attrsInput.add("name", name);
    attrsInput.add("type", cfg.get("type", "range"));
    attrsInput.add("value", vm.get("value", String.class));

    if (cfg.get("required", false)) {
        attrsInput.add("aria-required", true);
    }

    AttrBuilder attrsTextField = new AttrBuilder(request, xssAPI);
    attrsTextField.add("value", vm.get("value", String.class));
    attrsTextField.addDisabled(true);
%>

<!-- Ticked and Filled Vertical Slider -->
<div class="coral-InputGroup coral-InputGroup--block" style="text-align: center;">
    <div class="slider ticked filled tooltips" data-init="slider" style="float: left; width: 89%">
        <input <%= attrsInput.build() %> min="0" max="10" step="1" style="curson: pointer;">
    </div>
    <div style="float: right; width: 10%">
    <input class="coral-Form-field coral-Textfield" <%= attrsTextField.build() %> style="min-width: 5px;">
    </div>
</div>
