// Initial Variables

var filename = "untitled.jrl";
var joutput = document.getElementById('output');

// Auto-save every 5 seconds in Local Storage
window.setInterval(function(){
    localStorage.juliarCode = myCodeMirror.getValue();
    localStorage.output = joutput.innerHTML;
}, 5000);

// Code Mirror Simple Styling rules for Juliar

CodeMirror.defineSimpleMode("simplemode", {
    // The start state contains the rules that are intially used
    start: [
        // The regex matches the token, the token property contains the type
        {regex: /"(?:[^\\]|\\.)*?(?:"|$)/, token: "string"},
        // You can match multiple tokens at once. Note that the captured
        // groups must span the whole string in this case
        {regex: /(function)(\s+)([a-z$][\w$]*)/,
            token: ["keyword", null, "variable-2"]},
        // Rules are matched in the order in which they appear, so there is
        // no ambiguity between this one and the one above
        {regex: /(?:function|var|return|if|for|while|else|do|this)\b/,
            token: "keyword"},
        {regex: /true|false|null|undefined/, token: "atom"},
        {regex: /0x[a-f\d]+|[-+]?(?:\.\d+|\d+\.?\d*)(?:e[-+]?\d+)?/i,
            token: "number"},
        {regex: /\/\/.*/, token: "comment"},
        {regex: /\/(?:[^\\]|\\.)*?\//, token: "variable-3"},
        // A next property will cause the mode to move to a different state
        {regex: /\/\*/, token: "comment", next: "comment"},
        {regex: /[-+\/*=<>!]+/, token: "operator"},
        // indent and dedent properties guide autoindentation
        {regex: /[\{\[\(]/, indent: true},
        {regex: /[\}\]\)]/, dedent: true},
        {regex: /[a-z$][\w$]*/, token: "variable"},
        // You can embed other modes with the mode property. This rule
        // causes all code between << and >> to be highlighted with the XML
        // mode.
        {regex: /<</, token: "meta", mode: {spec: "xml", end: />>/}}
    ],
    // The multi-line comment state.
    comment: [
        {regex: /.*?\*\//, token: "comment", next: "start"},
        {regex: /.*/, token: "comment"}
    ],
    // The meta property contains global information about the mode. It
    // can contain properties like lineComment, which are supported by
    // all modes, and also directives like dontIndentStates, which are
    // specific to simple modes.
    meta: {
        dontIndentStates: ["comment"],
        lineComment: "//"
    }
});

var myCodeMirror = CodeMirror.fromTextArea(codeArea, {
    lineNumbers: true,
    mode:  "simplemode",
    theme: "cobalt"
});

// Juliar Function Calls


function newfile(){
    //Get Today's Date
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //January is 0!
    var yyyy = today.getFullYear();

    if (dd < 10) {
        dd = '0' + dd
    }

    if (mm < 10) {
        mm = '0' + mm
    }

    today = mm + '/' + dd + '/' + yyyy;

    myCodeMirror.getDoc().setValue("/*\n\tTitle: Untitled\n\tAuthor: " + "Juliar" + "\n\tDate: " + today + "\n*/\n\n" +
        "function main() = {\n\tprintLine(\"Hello World\");\n}");
}

function openFileOption()
{
    document.getElementById("file1").click();
}

function download(filename, text) {
    var pom = document.createElement('a');
    pom.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
    pom.setAttribute('download', filename);

    if (document.createEvent) {
        var event = document.createEvent('MouseEvents');
        event.initEvent('click', true, true);
        pom.dispatchEvent(event);
    }
    else {
        pom.click();
    }
}

function JAJAX() {
    var xmlhttp= window.XMLHttpRequest ?
        new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            joutput.innerHTML = xmlhttp.responseText.replace(/(?:\r\n|\r|\n)/g, '<br />');
        }
    };

    var codeArea = myCodeMirror.getValue();
    xmlhttp.open("GET","/get?q=" + encodeURIComponent(codeArea), true);
    xmlhttp.send();

}

function EXIT() {
    var xmlhttp= window.XMLHttpRequest ?
        new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");

    xmlhttp.open("GET","/exit", true);
    xmlhttp.send();
    setInterval(function(){
        window.location.reload(1);
    }, 1000);
}

function readSingleFile() {
    var file = document.getElementById("file1").files[0];
    if (!file) {
        return;
    }
    filename = file.name;
    var reader = new FileReader();
    reader.onload = function(e) {
        myCodeMirror.getDoc().setValue(e.target.result);
    };
    reader.readAsText(file);
}


function docs(){
    window.open('https://juliar.org/documentation', '_blank');
}

function about(){
    popup("About Juliar","<div class='row'><div class='col-6'><img src='whitelogo.svg' style='background-color:rgb(0,224,188)'/></div><div class='col-6'><p>Juliar was created with love by Juliar Team</p></div></div>");
}

function newfeatures(){
    popup("What's New?", "v0.1 Release - Initial Release");
}

// Add Event Listeners

// Cache the lookup
document.getElementById("menu").addEventListener('click', function(e){
   e.preventDefault();
   var menu_call = e.target.getAttribute("href");
   if(menu_call !== null && menu_call.length > 1){
       menu_call = menu_call.slice(1);
        switch(menu_call){
            case "new":
                newfile();
                break;
            case "load":
                openFileOption();
                break;
            case "save":
                download(filename,myCodeMirror.getValue());
                break;
            case "download":
                download('output.txt',joutput.innerHTML);
                break;
            case "exit":
                EXIT();
                break;
            case "undo":
                myCodeMirror.undo();
                break;
            case "redo":
                myCodeMirror.redo();
                break;
            case "run":
                JAJAX();
                break;
            case "about":
                about();
                break;
            case "newfeatures":
                newfeatures();
                break;
            case "docs":
                docs();
                break;
            default:
                popup();
        }
       menu_unhover(e.target.parentNode.parentNode);
   }
});



function menu_unhover(el)
{
    var par = el.parentNode;
    var next = el.nextSibling;
    par.removeChild(el);
    setTimeout(function() {par.insertBefore(el, next);}, 0)
}


// Load Content from cache, else generate new page.

if(localStorage.juliarCode){
    myCodeMirror.getDoc().setValue(localStorage.juliarCode);
    if(localStorage.output) {
        joutput.innerHTML = localStorage.output;
    }
} else {
    newfile();
}


// Modal
var modal = document.getElementById('juliar_popup');

function popup(header,content){
    header = header || "Coming Soon to Juliar!";
    content = content || "Currently not available!";

    document.getElementsByClassName("modal-header")[0].children[1].innerHTML = header;
    document.getElementsByClassName("modal-body")[0].children[0].innerHTML = content;
    modal.style.display = "block";
}

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
};

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(e) {
    if (e.target === modal) {
        modal.style.display = "none";
    }
};
