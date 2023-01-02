<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<h3>첫페이지</h3>

<button id="addBtn">등록</button>

<script>
const url =
    'https://api.odcloud.kr/api/15077586/v1/centers?page=1&perPage=284&returnType=JSON&serviceKey=QurnMhgJP589CbdgbsYZS0kmLNhjPqfRYxakDXd%2B8n8Q8Qa8%2BIQk5Cz1Z9T9Us5hoc7P30vB5tC4DJkRKwZ%2Btw%3D%3D';
//대용량의 데이터를 넣어서 처리해보고 싶어서


function testFnc(){
fetch(url)
.then(resolve => resolve.json())
.then(result => {
	console.log(result);
	console.log('start: ', new Date);
	result.data.forEach(center => {
		addCenterInfoFnc(center); // 284건 반복.
	})
	console.log('end: ', new Date);
})
.catch(err => console.log(err))
}    


document.getElementById('addBtn').addEventListener('click', addCenterInfoFnc2)

function addCenterInfoFnc2(){
	fetch(url)
	.then(resolve => resolve.json())
	.then(result => {
		console.log(result);
		console.log('start: ', new Date);
		let tData = result.data; //result.data=JSON object
		transferToCener(tData); //한번에 처리해주기 위해서
		console.log('end: ', new Date);
	})
	.catch(err => console.log(err))
	
}

function transferToCener(ars){
	//json => object : JSON.parse()
	//object => json : JSON.stringify()
	let jsonStr = JSON.stringify(args);
	fetch('addCenterJson.do',{
		method: 'post',
		headers: {'Content-Type': 'application/json'},
		body: jsonStr
	})
	.then(resolve => resolve.text())
	.then(result => console.log(result))
	.catch(err => console.log(err))
}



function addCenterInfoFnc(args = {}) {
	let param = 'id='+args.id+'&cn='+args.centerName+'&pn='+args.phoneNumber+'&add='+args.address
	fetch('addCenterInfo.do', {
		method: 'post',
		hearders: {'Content-Type': 'application/x-www-form-urlencoded'},
		body: param
	})
	.then(resolve => resolve.text())
	.then(result => console.log(result))
	.catch(err => console.log(err))
	
}
    
    
</script>