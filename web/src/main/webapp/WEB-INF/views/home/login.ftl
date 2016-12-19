<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <title>乾康微贷管理系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Charisma, a fully featured, responsive, HTML5, Bootstrap admin template.">
    <meta name="author" content="Muhammad Usman">
    <link href="../static/css/login.css" rel="stylesheet">
    <style type="text/css">

    </style>
    <script type="text/javascript">

    </script>
</head>
<body>
<div class="login-box">
    <img src="../static/img/login-logo1.png"/>
    <form>
        <!--错误信息提示-->
			<span class="errorMessage">
				<img src="../static/img/error.png"/>&nbsp;用户名或密码错误！
			</span>
        <input type="text" class="login-input" placeholder="用户名" id="user">
        <input type="password" class="login-input" placeholder="密码" >

        <input type="button" class="login-button" value="登&nbsp;&nbsp;录" onclick="login()">
    </form>
</div>
<script type="text/javascript" src="../static/js/jquery-1.10.2.min.js"></script>

<script type="text/javascript">
    function login(){
        if($('#user').val()=="主管")
            window.location.href='index_zg.html'
        else
            window.location.href='index_khjl.html'
    }

</script>
</body>
</html>
