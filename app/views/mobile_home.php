<!DOCTYPE html>
<html>
<head>
	<title>Vozni red uživo!</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="shortcut icon" href="resources/favicon.ico">
	<link rel="stylesheet" href="resources/jquery/jquery.mobile-1.3.0.min.css">
	<script type="text/javascript" src="resources/jquery/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="resources/jquery/jquery.mobile-1.3.0.min.js"></script>
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
</body>
</html>
