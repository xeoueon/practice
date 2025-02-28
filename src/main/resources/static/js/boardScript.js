function checkBoardWrite() {
	var frm = document.form1;
	
	if (!frm.subject.value) {
		alert("제목을 입력하세요");
		frm.subject.focus();
		return false;
	}
	
	if (!frm.content.value) {
		alert("내용을 입력하세요");
		frm.content.focus();
		return false;
	}
	
	// 데이터 전송
	frm.submit();
}

function checkBoardModify() {
	var frm = document.form1;
	
	if (!frm.subject.value) {
		alert("제목을 입력하세요");
		frm.subject.focus();
		return false;
	}
	
	if (!frm.content.value) {
		alert("내용을 입력하세요");
		frm.content.focus();
		return false;
	}
	
	// 데이터 전송
	frm.submit();
}