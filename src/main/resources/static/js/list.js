$(function () {
  $('.filter').each(function () {
    var filter = $('#filter').attr('value');
    if ($(this).val() == filter) {
      $(this).attr("selected", "selected");
    }
  });

  $('select').material_select();

  $('#searchBtn').click(function () {
    search();
  });

  $('#keyword').keydown(function (e) {
    if (e.keyCode == 13) {
      search();
    }
  });

  function search() {
    var filter = $('#filter').val();
    var keyword = $('#keyword').val();

    if (keyword.trim() === '') {
      Materialize.toast('검색어를 입력해주세요', 4000);
      return;
    }

    location.href = "/board?page=0&filter=" + filter + "&keyword=" + keyword;
  }

});