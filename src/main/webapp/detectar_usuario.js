$(document).ready(function(){
    $("#form").submit(function(event){
        event.preventDefault();
        var formData = {
            email: $("#email").val()
            cpf: $("#cpf").val()
        };
        $.ajax({
            url:''
        });

    });
});
