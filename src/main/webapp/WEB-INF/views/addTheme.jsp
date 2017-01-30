<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Add theme form</title>
    <script type="text/javascript" src="js/jquery-3.1.1.js"></script>
    <script type="text/javascript" src="js/addNewTheme.js"></script>
</head>
<body><H3>Enter new theme data</H3>
<HR>
<table>
    <tr>
        <td align="right">Topic</td>
        <td><INPUT id="themeName" type="text" name="themeName" title="topic"></td>
    </tr>
    <tr>
        <td align="right">Option first</td>
        <td><INPUT id="option1" type="text" name="option1" title="option of the topic"></td>
    </tr>
    <tr>
        <td align="right">Option second</td>
        <td><INPUT id="option2" type="text" name="option2" title="option of the topic"></td>
    </tr>
    <tr>
        <td></td>
        <td><button id="addThemeButton" type="button" onclick="addTheme()">Add theme</button></td>
    </tr>
</table>
</body>
</html>
