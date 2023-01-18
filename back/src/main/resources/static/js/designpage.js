const infonav = document.querySelectorAll(".info-nav-box a");

function changeSelect(e) {
  infonav.forEach((element) => {
    if (element == e) {
      element.className = "nav-select";
    } else {
      element.className = "nav-unselect";
    }
  });
}

infonav[0].addEventListener("click", () => changeSelect(infonav[0]));
infonav[1].addEventListener("click", () => changeSelect(infonav[1]));
infonav[2].addEventListener("click", () => changeSelect(infonav[2]));
