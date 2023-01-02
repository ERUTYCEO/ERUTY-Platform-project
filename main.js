var nystories = document.querySelector("#main-page").offsetTop;
window.onscroll = function () {
  if (window.pageYOffset > 0) {
    var opac = window.pageYOffset / nystories;
    console.log(nystories);
    console.log(opac);
    document.getElementById("test1").style.background =
      "linear-gradient(rgba(255, 255, 255, " +
      opac +
      "), rgba(255, 255, 255, " +
      opac +
      ")), url(./background.png) no-repeat";
  }
};
