const sorting_list = document.querySelectorAll(".gallery-sorting li");

function changeSelect(e) {
  sorting_list.forEach((element) => {
    if (element == e) {
      element.className = "sorting-selected";
    } else {
      element.classList.remove("sorting-selected");
    }
  });
}

sorting_list[0].addEventListener("click", () => changeSelect(sorting_list[0]));
sorting_list[1].addEventListener("click", () => changeSelect(sorting_list[1]));
sorting_list[2].addEventListener("click", () => changeSelect(sorting_list[2]));
