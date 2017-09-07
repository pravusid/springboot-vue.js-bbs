$(function() {
	$('#deleteBtn').click(function() {
		if (!confirm('게시물을 삭제 하시겠습니까?')) {
			return;
		}
		var id = $(this).val();
		$.ajax({
			type: "delete",
			url: "/board",
			data: {"id" : 3},
			dataType: "json",
			success: function(response) {
				location.href=response;
			},
			error: function(response) {
				alert(response.responseText);
			}
		});
	});
});