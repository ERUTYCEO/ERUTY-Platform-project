window.addEventListener("onclick", function (e) {
  console.log(e.target);
});

// window.addEventListener("scroll", () => {
//   //스크롤을 할 때마다 로그로 현재 스크롤의 위치가 찍혀나온다.
//   console.log(window.scrollX, window.scrollY);
//   const bg = document.querySelector(".bg-ani");
//   if (window.scrollY > 1385) {
//     bg.classList.remove("first-bg");
//     bg.classList.add("second-bg");
//   } else {
//     bg.classList.remove("second-bg");
//     bg.classList.add("first-bg");
//   }
// });
