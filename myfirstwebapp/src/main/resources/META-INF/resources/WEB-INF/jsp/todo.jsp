<!-- 양방향 바인딩... bean의 상태 <=> form 상태
변경 사항이 갱신 -->
<!-- command bean을 모델에 넣어서 넘겨주고 modelAttribute로
form backing object를 설정하면, input은 path로 넘어온 필드의 
setter 호출해서 값을 세팅함.
그런데, request에 값을 새로 담아서 보내기 때문에, 기존 값을
다시 전송 안해주면 null값이 넘어감.
이게 기본 자료형은 null 대입 불가능해서 에러날 수 있다.
해결법은 input태그로 해당 필드값을 넘겨주면 된다.
default value가 command bean이 넘어왔을 때의 값이기 때문!!
client가 수정 못하게 하고 싶으면 type을 hidden으로...  -->
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<main class="container">
	<h1>Enter Todo Details</h1>
	<form:form method="post" modelAttribute="todo">
		<fieldset class="mb-3">
			<form:label path="description">Description</form:label>
			<form:input type="text" path="description" required="required" />
			<form:errors path="description" cssClass="text-warning" />
		</fieldset>
		
		<fieldset class="mb-3">
			<form:label path="targetDate">Target Date</form:label>
			<form:input type="text" path="targetDate" required="required" />
			<form:errors path="targetDate" cssClass="text-warning" />
		</fieldset>
		
		<form:input type="hidden" path="id"/>
		
		<form:input type="hidden" path="done"/>
		
		<input type="submit" class="btn btn-success"/>
		<!-- spring taglib라서 cssClass로 class설정해야함 -->
	</form:form>
</main>

<%@ include file="common/footer.jspf" %>

<script type="text/javascript">
	$('#targetDate').datepicker({
	    format: 'yyyy-mm-dd'
	});
</script>