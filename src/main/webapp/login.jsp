<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
</head>
<body>
	<center>
		<div>
			<form id="form" name="form" method="post" action="/">
				<h1>Login</h1>
				<p>
					Please enter your login information <br />New User? <a
						href="register.jsp">Register</a>
				</p>
				<div>
					<label> 
						<span class="small">User ID: </span>
					</label> 
						<input type="text" name="userName" id="userName"/> 
				</div>
				<div>
					<label>
						<span class="small">Password (Minium 8 charactors): </span>
					</label> 
					<input type="password" name="password" id="password" />
				</div>
				<div>
					<button type="submit">Sign-in</button>
				</div>
				<div class="spacer"></div>
			</form>
		</div>
	</center>
</body>
</html>