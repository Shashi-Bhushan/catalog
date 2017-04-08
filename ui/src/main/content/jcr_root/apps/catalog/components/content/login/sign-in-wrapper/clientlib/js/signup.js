"use strict";

(function($){
    $(document).ready(function(){
        $('#login input[type="submit"]').on('click', function(event){
            event.preventDefault();

            $.ajax({
                url: '/bin/signup',
                method: 'post',
                success: function(response) {
                    console.log(response);
                },
                error: function(response) {
                    console.log(response)
                }
            });
        });
    });
})(jQuery);
