$(document).ready(function(){
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get('code');
    const redirect_uri = "http://localhost:8080/connector/import.html" // replace with your redirect_uri;
    const client_secret = "4inA-Vx8QrQoFaXsTE2cViNg"; // replace with your client secret
    const scope = "https://www.googleapis.com/auth/drive";
    var access_token= "";
    var client_id = "194383452071-84dleaqjqpgip40oq5k8r7m70nhr18eq.apps.googleusercontent.com";// replace it with your client id;
    $.ajax({
        type: 'POST',
        url: "https://www.googleapis.com/oauth2/v4/token",
        data: {code:code
            ,redirect_uri:redirect_uri,
            client_secret:client_secret,
        client_id:client_id,
        scope:scope,
        grant_type:"authorization_code"},
        dataType: "json",
        success: function(resultData) {
           console.log(resultData.access_token);
           localStorage.setItem("accessToken",resultData.access_token);
           localStorage.setItem("refreshToken",resultData.refreshToken);
           localStorage.setItem("expires_in",resultData.expires_in);
           $.ajax({
               type: "GET",
               beforeSend: function(request) {
                   request.setRequestHeader("Authorization", "Bearer" + " " + localStorage.getItem("accessToken"));

               },
               url: "https://www.googleapis.com/drive/v2/files",
               data:{
                   uploadType:"media"
               },
               xhr: function () {
                   var myXhr = $.ajaxSettings.xhr();
                   return myXhr;
               },
               success: function (data) {
                   console.log(data);
                   console.log(data.items[0].alternateLink);
                   $.each(data.items, function(key, value) {
                    //  $("ul").append("<li>"+value.alternateLink+"</li>");
                      //$("ul").append("<li class=\"list-group-item\">"+value.title+"</li>");
					const uri = value.alternateLink;
					var uri_dec = decodeURIComponent(uri);
					console.log(uri_dec);
					$('ul').append('<li class="list-group-item"> <input type="radio" name="recording" id="recording" value="' +uri_dec+ '">  '+value.title+' </li>');
                   
					
					
});
               },
               error: function (error) {
                   console.log(error);
               },
               async: true,
               cache: false,
               contentType: false,
               processData: false,
               timeout: 60000
           });
        }
  });

});
