$(function () {

  $('#deleteArticle').click(function () {
    if (!confirm('게시물을 삭제 하시겠습니까?')) {
      return;
    }
    var data = {"id": $(this).val()};
    var URL = "/board" + csrf;
    $.ajax({
      type: "delete",
      contentType: "application/json",
      url: URL,
      data: JSON.stringify(data),
      success: function (resp, status) {
        location.href = resp;
      },
      error: function () {
        alert("삭제 실패!");
      }
    });
  });

  $('.replyBtn').on('click', function () {
    var id = $(this).attr('value')
    $('.' + id).addClass('hide');
    $('#re' + id).removeClass('hide');
  });

  $('.modBtn').on('click', function () {
    var id = $(this).attr('value')
    $('.' + id).addClass('hide');
    $('#mod' + id).removeClass('hide');
  });

  $('.deleteBtn').on('click', function (e) {
    e.preventDefault();
    if (!confirm('댓글을 삭제 하시겠습니까?')) {
      return;
    }
    $('#del' + $(this).attr('value')).submit();
  });

});