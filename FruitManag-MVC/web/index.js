
window.onload=load;
function load(){
	//重置添加水果的表格
	resetAddTbl();
	//当页面加载完成需要绑定各种时事件
	//通过id获取表格
	var fruitTbl = $("tab_fruit");
	var rows = fruitTbl.rows;
	for(var i = 1; i<rows.length-1; i++){
		var tr = rows[i];
		tr.onmouseover = showBGColor;
		tr.onmouseout = recoverBGColor;
		//获取tr这一行的所有单元格
		var cells = tr.cells;
		//绑定鼠标悬浮在单价单元个变手势的事件
		cells[1].onmouseover = showHand;
		//绑定鼠标点击修改单价事件
		cells[1].onclick = editPrice;
	}
	var addFruitTbl = $("tab_add_fruit"); 
	rows = addFruitTbl.rows;

}
function $(id){
	return document.getElementById(id);
}
function showBGColor(){
	//event当前发生的事件
	//event.srcElement :事件源
	if(event && event.srcElement && event.srcElement.tagName == "TD"){
		var td = event.srcElement;
		var tr = td.parentElement;
		tr.style.backgroundColor = "RoyalBlue";
		var tds = tr.cells;
		for(var i = 0; i < tds.length; i++){
			tds[i].style.color = "white";
		}
	}
}
function recoverBGColor(){
	//event当前发生的事件
	//event.srcElement :事件源
	if(event && event.srcElement && event.srcElement.tagName == "TD"){
		var td = event.srcElement;
		var tr = td.parentElement;
		tr.style.backgroundColor = "Beige";
		var tds = tr.cells;
		for(var i = 0; i < tds.length; i++){
			tds[i].style.color = "black";
		}
	}
}
function showHand(){
	var td = event.srcElement;
	//光标：cursor
	td.style.cursor="hand";
}
//修改数据
function updatePrice(){
	if(event && event.srcElement && event.srcElement.tagName == "INPUT"){
		var input = event.srcElement;
		var newPrice = input.value;
		var priceID = input.parentElement;
		priceID.innerText = newPrice;
		//将priceID的父节点，即Tr传过去
		updateSubSum(priceID.parentElement);
	}
}

//修改小计
function updateSubSum(tr){
	if(tr && tr.tagName == "TR"){
		var tds = tr.cells;
		//获取单价
		var price = tr.cells[1].innerText;
		//获取数量
		var count = tr.cells[2].innerText;
		tr.cells[3].innerText = parseInt(price) * parseInt(count);
		//更新总价
		updateSum();
	}
}

//更新总价
function updateSum(){
	var fruitTbl = $("tab_fruit");
	var rows = fruitTbl.rows;
	var newSum = 0;
	for(var i = 1; i < rows.length-1; i++){
		var cells = rows[i].cells;
		newSum += parseInt(cells[3].innerText);
	}
	var lastCells = rows[rows.length-1].cells;
	lastCells[1].innerText = newSum;
}

//编辑数据
function editPrice(){
	if(event && event.srcElement && event.srcElement.tagName == "TD"){
		var priceID = event.srcElement;
		//表示获取当前节点的内部文本
		var oldPrice = priceID.innerText;
		//当点击一次后再次点击输入文本框外，单元格内时，会再次启动该方法，此时获取的oldPrice为null
		if(!oldPrice) return;
		//表示设置当前节点的内部HTML
		priceID.innerHTML="<input type = 'text' size = '4'/>"
		var input = priceID.firstChild;
		//实现点击价格时，不会直接清空原有数据，会全部选中
		if(input.tagName == "INPUT"){
			input.value = oldPrice;
			input.select();
			//绑定输入框失去焦点事件
			input.onblur = updatePrice;
			//绑定输入检查事件
			input.onkeydown = checkInput;
		}
	}
}
//删除水果
function delFruit(fid){
	if(event && event.srcElement && event.srcElement.tagName == "IMG"){
		if(window.confirm("否确定删除当前库存记录")){
			window.location.href ="fruit.do?operation=delete&fid="+fid;
		}
	}
}

function getPage(pageCount,pageNo){
	if(pageNo <=0){
		alert("已经是第一页了！");
		return;
	}
	if(pageCount < pageNo){
		alert("已经是最后一页了！");
		return;
	}
	window.location.href="fruit.do?pageNo="+pageNo;
}
function checkInput(){
	//获取用户输入
	/*
	backspace:8
	enter:13
	number: 48 ~ 57
	left:37
	top:38
	right：39
	bottom：40
	*/
	var kc = event.keyCode;
	if(!(kc == 8 || kc == 13 || (kc >= 48 && kc <=57) || kc == 37 || kc == 38 || kc == 39 || kc == 40)){
		event.returnValue = false;
	}
	if(kc == 13){
		event.srcElement.blur();
	}
}
function addFruit(){
	var addTable = event.srcElement.parentElement.parentElement.parentElement.parentElement;
	var rows = addTable.rows;
	var arr = new Array(3);
	for(var i = 0; i < rows.length-1; i++){
		//还有没有输入的数据
		if(!(rows[i].cells[1].children[0].value)){
			alert("无法提交有空的数据！");
			return;
		}
		arr[i] = rows[i].cells[1].children[0].value;
	}
}

function resetAddTbl(){
	var addFruitTbl = document.getElementById("tab_add_fruit");
	rows = addFruitTbl.rows;
	for(var i = 0; i<rows.length-1; i++){
		var input = rows[i].cells[1].children[0];
		input.value = "";
	}
}