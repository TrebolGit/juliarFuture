
var juliarInterop = (function(){

    function juliarInterop( nameSpace, moduleName ) {
        this.nameSpace = nameSpace;
        this.moduleName = moduleName;

    }

    juliarInterop.prototype.Main = function(  ){

    };


    juliarInterop.prototype.serverModule = function( ) {
        debugger;
        callServerAsync( "compile?module=" + this.moduleName );
    };


    juliarInterop.prototype.jnf = function( funcName , parameterDictionary) {

    };

    function callServerAsync( uri ){
        var xmlhttp= window.XMLHttpRequest ?
            new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");

        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
                joutput.innerHTML = xmlhttp.responseText;
            }
        };

        xmlhttp.open("GET", encodeURIComponent( uri ), true);
        xmlhttp.send();
    }

    return juliarInterop;
}());
