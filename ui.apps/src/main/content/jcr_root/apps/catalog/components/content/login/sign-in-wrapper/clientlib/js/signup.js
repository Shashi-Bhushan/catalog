"use strict";

(function($){
    $.fn.initiateLogin = function(opts) {
        var $loginForm = this;

        options = $.extend({
            path : '/content/catalog/en',
            securityToken: 'j_security_check',
        }, opts);

        console.log("options are : " + options);
        $loginForm.submit(function(event){

            $.ajax({
                url: options.path + '/j_security_check',
                method: 'post',
                data: $loginForm.serialize(),
                success: function(response){
                    console.log(response);

                    window.location.reload();
                }
            });
            return false;
        });
    };

    $(document).ready(function(){
        $('#signup input[type="submit"]').on('click', function(event){
            var $signupModal = $('#loginModal #signup form[name="loginForm"]');

//             TODO : Show error on password and confirm password mismatch
            $.ajax({
                url: '/bin/signup',
                method: 'post',
                data: $signupModal.serialize(),
                success: function(response) {
                    console.log(response);
                    if(response.status) {
                        $('#verificationMail').show();
                    }
                },
                error: function(response) {
                    console.log(response)
                }
            });

            // Equivalent of event.preventDefault();
            return false;
        });


    });
})(jQuery);
