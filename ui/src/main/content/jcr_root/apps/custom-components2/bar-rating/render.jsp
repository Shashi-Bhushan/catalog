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
    attrsTextField.add("type", "hidden");
    attrsTextField.add("name", name);
%>

<input <%= attrsTextField.build() %>>

<select id="bar-rating">
  <option value="1">1</option>
  <option value="2">2</option>
  <option value="3">3</option>
  <option value="4">4</option>
  <option value="5">5</option>
  <option value="6">6</option>
  <option value="7">7</option>
  <option value="8">8</option>
  <option value="9">9</option>
  <option value="10">10</option>
</select>

<script type="text/javascript">
   $(function() {
      var $input = $('input[name="<%= name %>"]');
      if($input.val() === "") {
        $input.val("0");
      }

      var inputValue = $input.val();

      $('#bar-rating').barrating({
        theme: 'fontawesome-stars-o',
        initialRating : inputValue
      });

      $('#bar-rating').on('change', function(event){
        $input.val($(this).val());
      });
   });
</script>
