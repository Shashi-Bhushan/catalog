"use strict"

;(function($){
    $.fn.initBarRating = function(options) {
        var $input = this;

        if($input.val() === "") {
            $input.val("0");
        }

        options.barRating.barrating({
            theme: 'fontawesome-stars-o',
            initialRating : $input.val()
        });

        options.barRating.on('change', function(event){
            $input.val($(this).val());
        });
    }
}(window.jQuery));
