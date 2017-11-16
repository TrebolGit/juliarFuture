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
  <script src="JuliarInterop.js"></script>
  <script type="text/javascript" language="javascript">
      window.addEventListener( "load", function () {
          var interOp = new juliarInterop("test");
          interOp.Main();
      })
  </script>
  <title></title>
</head>
<body>
<form action="/compile">
  <label>Enter code to compile</label>
  <textarea name="codeArea"></textarea>
  <input type="submit" name="compile">
</form>
</body>
</html>

