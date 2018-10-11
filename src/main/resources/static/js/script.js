var logout = function () {
  document.getElementById("logout").submit();
};

window.onload = function () {
  var auth = document.getElementById("auth");
  if (auth != null && auth.value === "") {
    logout();
  }
};
