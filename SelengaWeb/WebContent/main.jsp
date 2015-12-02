<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<head>
<title>Выбор данных</title>
<meta charset="UTF-8">
 <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js">
 </script> 
<link type="text/css" href="style.css" rel="stylesheet" >
</head>
<body>
 <!--  <div class="header">Выбрать данные по</div> -->

	<form method="post">
		<fieldset>
			<legend>
				<b>Выбрать данные по:</b>
			</legend>
			<div class="bord">
				<div class="col1">
					<input type="radio" name="data_type" value="by_year" id="by_year"> 
					<label for="by_year"> По году</label>
				</div>
				<div class="col2">
					<input type="radio" name="data_type" value="by_sp" id="by_sp"> 
					<label for="by_sp">По сельскому поселению</label>
				</div>
				<div class="col3">
					<input type="radio" name="data_type" value="by_district" id="by_district">
					<label for="by_district">По району в целом</label>
				</div>
			</div>
		</fieldset>
		<fieldset id="years">
			<legend>
				<b>Год</b>
			</legend>			
		</fieldset>
		<p></p>
		<fieldset id="sp">
			<legend>
				<b>Сельское поселение</b>
			</legend>			
		</fieldset>
		<p></p>
		<fieldset id = "gp">
			<legend>
				<b>Группы показателей</b>
			</legend>
			
		</fieldset>
		<fieldset id="pokaz">
			<legend>
				<b>Показатели</b>
			</legend>
		</fieldset>
		<div id="buttonpanel">
		   <div class="col2_1">
		   <input type="button" id="select_all" value="Выбрать все">
		   </div>
		   <div class="col2_2">
		   <input type="button" id="load_data" value="Загрузить выбранные показатели">
		   </div>
		</div>
	</form>	
	
 <script  type="text/javascript" src="src.js">
 </script>
    
</body>