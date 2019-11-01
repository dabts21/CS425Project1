var Registration = ( function() {

    return {

        init: function() { 
            
            $("#paragraph").html( "jQuery Version: " + $().jquery );  
        },  
        confirmation: function(result) { 
            
            var congrats = "<p>Congratulations! You have successfully registered as: <strong>" + result["displayname"] + "</strong></p>";
            
            "<p>Your registration code is: <strong>" + result["rcode"] + "</strong></p>";
            $("#output1").html(congrats);  
        },        
        
        submit: function() {

            $.ajax({

                url: 'register',
                method: 'GET',
                data: $('#search').serialize(),
                success: function(response) {
                    $("#output1").html(response);
                }
            });

            return false;

        },  
        register: function() {
            
            var that = this;
            
            $.ajax({

                url: 'register',
                method: 'POST',
                data: $('#registration-form').serialize(),
                dataType: 'json',

                success: function(response) {
                    that.confirmation(response);
                }

            });          
        }
        

   
 
        
        
        

    };

}());
