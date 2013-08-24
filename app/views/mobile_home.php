<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Vozni red uživo!</title>
	<link rel="stylesheet" href="http://static.hocuinternet.com/jquery/jquery.mobile-1.3.2.min.css">
</head>
<body>
	<div data-role="page" class="type-interior">
		<div data-role="header">
			<h1>Vozni red uživo!</h1>
		</div>
		<div data-role="content">
			<form action="mobile/stations" method="get">
				<fieldset>
					<div data-role="fieldcontain">
						<input type="number" name="train_no" maxlength=4 placeholder="unesite broj vlaka..."/>
					</div>
					<button type="submit">Traži!</button>
				</fieldset>
			</form>
		</div>
	</div>
	<script type="text/javascript" src="http://static.hocuinternet.com/jquery/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="http://static.hocuinternet.com/jquery/jquery.mobile-1.3.2.min.js"></script>
</body>
</html>
