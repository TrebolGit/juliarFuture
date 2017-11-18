<%--
  Created by IntelliJ IDEA.
  User: dreamey
  Date: 11/15/17
  Time: 4:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet">
<link rel="stylesheet" href="assets/juliargrid.css">
<link rel="stylesheet" href="assets/codemirror.css">
<link rel="stylesheet" href="assets/juliarcobalt.css">
<script src="assets/codemirror.js"></script>
    <script src="assets/simple.js"></script>
  <script src="JuliarInterop.js"></script>
  <script type="text/javascript" language="javascript">
      window.addEventListener( "load", function () {
          var interOp = new juliarInterop("test");
          interOp.Main();
          interOp.jnf("printLine");
      })
  </script>
  <title></title>
</head>
<body>

<div class="section group">
    <div class="col span_1_of_2">
        <input type="button" name="open" value="&#9993; open" id="open_btn" onclick="openFileOption()"/>
        <input type="button" name="open" value="&#9997; save" id="save_btn" onclick="download(filename,myCodeMirror.getValue())"/>
        <input type="button" name="compile" value="&#9654; compile" id="compile_btn" onclick="JAJAX()" />
        <textarea name="codeArea" id="codeArea"></textarea>
	</div>
    <div class="col span_1_of_2">
        <div id="output_tab">Output</div><input type="button" id="save_output_tab" value="&#11096; Save" onclick="download('output.txt',joutput.innerHTML)" />
        <div id="output"></div>
    </div>
    <input type="file" id="file1" onchange="readSingleFile()" style="display:none" />

</div>

<script>
    var filename = "untitled.jrl";
    var joutput = document.getElementById('output');

    window.setInterval(function(){
        localStorage.juliarCode = myCodeMirror.getValue();
        localStorage.output = joutput.innerHTML;
    }, 5000);

    function openFileOption()
    {
        document.getElementById("file1").click();
    }

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

    if(localStorage.juliarCode){
        myCodeMirror.getDoc().setValue(localStorage.juliarCode);
        if(localStorage.output) {
            joutput.innerHTML = localStorage.output;
        }
    } else {
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


        myCodeMirror.getDoc().setValue("/*\n\tTitle: Untitled\n\tAuthor: " + navigator.appName + "\n\tDate: " + today + "\n*/\n\n" +
            "function main() = {\n\tprintLine(\"Hello World\");\n}");
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
        var interOp = new juliarInterop("test");
        interOp.Main();
        interOp.jnf("printLine");

        var xmlhttp= window.XMLHttpRequest ?
            new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");

        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
                joutput.innerHTML = xmlhttp.responseText;
            }
        };

        var codeArea = myCodeMirror.getValue();
        xmlhttp.open("GET","compile?codeArea=" + encodeURIComponent(codeArea), true);
        xmlhttp.send();

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


</script>
</body>
</html>

