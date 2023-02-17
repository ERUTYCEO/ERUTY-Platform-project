const sorting_list = document.querySelectorAll(".gallery-order");

function changeSelect(e) {
  sorting_list.forEach((element) => {
    if (element == e) {
      element.classList.add("order-selected");
    } else {
      element.classList.remove("order-selected");
    }
  });
}

sorting_list[0].addEventListener("click", () => changeSelect(sorting_list[0]));
sorting_list[1].addEventListener("click", () => changeSelect(sorting_list[1]));
sorting_list[2].addEventListener("click", () => changeSelect(sorting_list[2]));
