"use strict";

(function($){
    $(document).ready(function(){
        $('#signup input[type="submit"]').on('click', function(event){
            event.preventDefault();

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
        });
    });
})(jQuery);
