$(function () {
    hljs.configure({
        highlightSizeThreshold: 5000
    });

    // Pre load translate...
    if (window.SwaggerTranslator) {
        window.SwaggerTranslator.translate();
    }
    var settings = {
        url: "/v2/api-docs",
        validatorUrl: undefined,
        dom_id: "swagger-ui-container",
        onComplete: function (swaggerApi, swaggerUi) {
            if (typeof initOAuth == "function") {
                initOAuth({
                    clientId: "your-client-id",
                    clientSecret: "your-client-secret-if-required",
                    realm: "your-realms",
                    appName: "your-app-name",
                    scopeSeparator: ",",
                    additionalQueryStringParams: {}
                });
            }

            if (window.SwaggerTranslator) {
                window.SwaggerTranslator.translate();
            }
            addCsrfTokenHeaders();
            addApiKeyAuthorization();
        },
        onFailure: function (data) {
            log("Unable to Load SwaggerUI");
        },
    };
    $.extend(settings, JSON.parse($('#drs-settings').html()));

    window.swaggerUi = new SwaggerUi(settings);

    window.swaggerUi.load();

    function addCsrfTokenHeaders() {
        var token = $('[name="csrfmiddlewaretoken"]')[0];
        if (!token) {
            return;
        }
        swaggerUi.api.clientAuthorizations.add(
            'csrf_token',
            new SwaggerClient.ApiKeyAuthorization(
                'X-CSRFToken',
                token.value,
                'header'
            )
        );
    }

    function log() {
        if ('console' in window) {
            console.log.apply(console, arguments);
        }
    }

    /*window.authorizations.add("key", new ApiKeyAuthorization("Authorization", "header"));
     window.swaggerUi.load();*/
});

function addApiKeyAuthorization() {
    var token = $("#my_token").text();
    token = "Token " + token;
    var tokenHeader = new SwaggerClient.ApiKeyAuthorization("Authorization", token, "header");
    window.swaggerUi.api.clientAuthorizations.add("token", tokenHeader);
}
/*
 function addApiKeyAuthorization() {
 var key = encodeURIComponent($('#input_apiKey')[0].value);
 if (key && key.trim() != "") {
 var value = "auth-scheme api_key=123456,order_id=56789";
 var authKeyHeader = new SwaggerClient.ApiKeyAuthorization("Authorization", value, "header");
 window.swaggerUi.api.clientAuthorizations.add("Authorization", authKeyHeader);
 }
 }*/
