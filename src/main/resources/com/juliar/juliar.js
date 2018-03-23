// Initial Variables

var filename = "untitled.jrl";
var joutput = document.getElementById('output');
var jerrors = document.getElementById('errors');

// Auto-save every 5 seconds in Local Storage
/*window.setInterval(function(){
    localStorage.juliarCode = tabs[currentTab].codeIDE.getValue();
    localStorage.output = joutput.innerHTML;
}, 5000);*/

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


// Juliar Function Calls

//Today's date
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


function newfile(){

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
            var jsonResponse = JSON.parse(xmlhttp.responseText);
            joutput.innerHTML = jsonResponse["output"].replace(/(?:\r\n|\r|\n)/g, '<br />');
            jerrors.innerHTML = jsonResponse["errors"].replace(/(?:\r\n|\r|\n)/g, '<br />');
        }
    };

    var codeArea = tabs[currentTab].codeIDE.getValue();
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

    var reader = new FileReader();
    reader.onload = function(e) {
        add_tab(file.name, false, e.target.result);
    };
    reader.readAsText(file);
}


function docs(){
    window.open('https://juliar.org/documentation', '_blank');
}

function about(){
    popup("About Juliar","<div class='row'><div class='col-6'><img src='whitelogo.svg' style='background-color:rgb(51,52,71)'/></div><div class='col-6'>" +
        "<p>Juliar was created with love by Juliar Team. Visit us at <a target='_blank' href='https://juliar.org'>https://juliar.org</a>. " +
        "If you have any questions send an email to <a href='mailto:admin@juliar.org'>admin@juliar.org</a>.</p></div></div>");
}

function newfeatures(){
    popup("What's New?", "v0.1 Release - Initial Release. Major GUI updates.");
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
                download(tabs[currentTab].name,tabs[currentTab].codeIDE.getValue());
                break;
            case "download":
                download('output.txt',joutput.innerHTML);
                break;
            case "exit":
                EXIT();
                break;
            case "undo":
                tabs[currentTab].codeIDE.undo();
                break;
            case "redo":
                tabs[currentTab].codeIDE.redo();
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

/*if(localStorage.juliarCode){
    tabs[currentTab].codeIDE.getDoc().setValue(localStorage.juliarCode);
    if(localStorage.output) {
        joutput.innerHTML = localStorage.output;
    }
} else {
    newfile();
}*/


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

// Tabs


function File(id, name, path, modified, codeIDE) {
    this.id = id;
    this.name = name;
    this.path = path || null;
    this.modified = modified || true;
    this.codeIDE = codeIDE;
}

function unchecktabs(tabType){
    var tabs = document.getElementById(tabType).getElementsByClassName("tablinks");
    var content = document.getElementsByClassName("tabcontent " + tabType);

    for (i = 0; i < tabs.length; i++) {
        tabs[i].classList.remove("active");
    }

    for (i = 0; i < content.length; i++) {
        content[i].style.display = "none";
    }
}

function openTab(tabName, tabType) {
    tabType = tabType || "files_tab";
    var tabs = document.getElementById(tabType).getElementsByClassName("tablinks");
    var content = document.getElementsByClassName("tabcontent " + tabType);


    for (i = 0; i < tabs.length; i++) {
        tabs[i].classList.remove("active");
        if(tabs[i].name === tabName){
            tabs[i].classList.add("active");
        }
    }


    for (i = 0; i < content.length; i++) {
        content[i].style.display = "none";
        if(content[i].classList.contains(tabName)){
            content[i].style.display ="block";
        }
    }

    currentTab = tabName;
}

var allTabs = document.getElementsByClassName("tab");
for(var i=0; i<allTabs.length;i++) {
    allTabs[i].addEventListener('click', function (e) {
        e.preventDefault();
        if (e.target.name != null && e.target.classList.contains("tablinks")) {
            openTab(e.target.name, e.target.parentElement.id);
        }
    });
}


openTab("Help", "views_tab");
openTab("Output","output_tab");


//Generate Stack of Tabs
var tabs = {};
var currentTab = 0;

var lastTabID = 0;

document.getElementById("addtab").addEventListener('click', function(e){
    add_tab();
});


function add_tab(filename, modified, code) {
    unchecktabs("files_tab");
    var newTab = new File();
    newTab.id = lastTabID++;
    if(filename == null) {
        newTab.name = "Untitled_" + newTab.id + ".jrl";
    } else {
        newTab.name = filename;
    }
    newTab.modfied = modified || true;
    var newNode = document.createElement('button');
    newNode.className = "tablinks active";
    newNode.name = newTab.id;
    var mod = "";
    if(!newTab.modfied)  mod = "ok";
    newNode.innerHTML = '<span class="close_tab_btn">x</span><svg class="circle_status '+mod+'" viewBox="0 0 10 10" xmlns="http://www.w3.org/2000/svg\">' +
        '<circle cx="5" cy="5" r="5" />' +
        '</svg> ' + newTab.name;
    var filesTab = document.getElementById("files_tab");
    filesTab.insertBefore(newNode, document.getElementById("addtab"));

    var newTextArea = document.createElement('div');
    newTextArea.className = "tabcontent files_tab " + newTab.id;
    if(code == null) {
        newTextArea.innerHTML = "<textarea name=\"codeArea\" id=\"codeArea" + newTab.id + "\">" +
            "/*\n\tTitle: " + newTab.name + "\n\tAuthor: " + "Juliar" + "\n\tDate: " + today + "\n*/\n\n" +
            "function main() = {\n\tprintLine(\"Hello World\");\n}</textarea>";
    } else{
        newTextArea.innerHTML = "<textarea name=\"codeArea\" id=\"codeArea" + newTab.id + "\">" + code +"</textarea>";
    }
    document.getElementsByTagName("main")[0].appendChild(newTextArea);

    openTab(newTab.id.toString(), "files_tab");
    newTab.codeIDE = CodeMirror.fromTextArea(document.getElementById("codeArea" + newTab.id), {
        lineNumbers: true,
        mode: "simplemode",
        theme: "cobalt"
    });
    tabs[newTab.id]  = newTab;
    currentTab = newTab.id;
}

add_tab();


//Close
document.getElementById("files_tab").addEventListener('click', function(e){
    if(e.target.classList.contains("close_tab_btn")){
        var nameOfNode = e.target.parentElement.name;
        var fileTabs= document.getElementById("files_tab");
        var main = document.getElementsByTagName("main")[0];
        if(e.target.parentElement.classList.contains("active") && Object.keys(tabs).length > 1){
            var position = Object.keys(tabs).indexOf(nameOfNode) - 1;
            if(position < 0) position = 1;
            openTab(Object.keys(tabs)[position].toString(),"files_tab");
            currentTab = Object.keys(tabs)[position];
        }
        delete tabs[nameOfNode];

        var textarea =  main.getElementsByClassName("tabcontent files_tab "+nameOfNode)[0];
        main.removeChild(textarea);

        var el = fileTabs.querySelector("button[name='"+nameOfNode+"']");
        fileTabs.removeChild(el);
    }
});
