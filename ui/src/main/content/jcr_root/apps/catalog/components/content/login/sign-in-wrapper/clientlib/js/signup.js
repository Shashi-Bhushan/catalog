"use strict";

(function($){
    $(document).ready(function(){
        $('#signup input[type="submit"]').on('click', function(event){
            event.preventDefault();

            var $signupModal = $('#loginModal #signup');

//             TODO : Show error on password and confirm password mismatch
            $.ajax({
                url: '/bin/signup',
                method: 'post',
                data: {
                    'j_username' : $signupModal.find('input[name="j_username"]').val(),
                    'j_password' : $signupModal.find('input[name="j_password"]').val()
                    'j_name' : $signupModal.find('input[name="j_name"]').val()
                    'j_email' : $signupModal.find('input[name="j_email"]').val()
                },
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
