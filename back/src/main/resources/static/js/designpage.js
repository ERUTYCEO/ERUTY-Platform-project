window.onload = function () {
  var httpRequest;
  /* button이 클릭되었을때 이벤트 */
  document.querySelector(".design-like-btn").addEventListener("click", () => {
    /* textBox에 작성된 name 데이터를 가져옴 */
    var itemId = document.querySelector(".design-like-id").innerText;
    /* 통신에 사용 될 XMLHttpRequest 객체 정의 */
    httpRequest = new XMLHttpRequest();
    /* httpRequest의 readyState가 변화했을때 함수 실행 */
    httpRequest.onreadystatechange = () => {
      if (httpRequest.readyState === XMLHttpRequest.DONE) {
        if (httpRequest.status === 200) {
          var result = httpRequest.response;
          console.log(result);
          if (result == true) {
            document.querySelector(".like-heart").classList.remove("bi-heart");
            document
              .querySelector(".like-heart")
              .classList.add("bi-heart-fill");
            document.querySelector(".like-heart").style.color = "red";
            document.querySelector(".like-heart").innerHTML = `<path
            fill-rule="evenodd"
            d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z"
          />`;
            console.log(document.querySelector(".design-like-num").innerText);
            let num =
              parseInt(document.querySelector(".design-like-num").innerText) +
              1;
            document.querySelector(".design-like-num").innerText = num;
          } else {
            document
              .querySelector(".like-heart")
              .classList.remove("bi-heart-fill");
            document.querySelector(".like-heart").classList.add("bi-heart");
            document.querySelector(".like-heart").style.color = "black";
            document.querySelector(".like-heart").innerHTML = `<path
            d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z"
          />`;
            let num =
              parseInt(document.querySelector(".design-like-num").innerText) -
              1;
            document.querySelector(".design-like-num").innerText = num;
          }
        } else {
          alert("Request Error!");
        }
      }
    };
    /* Get 방식으로 name 파라미터와 함께 요청 */
    httpRequest.open("GET", "/getLikeById?itemId=" + itemId);
    /* Response Type을 Json으로 사전 정의 */
    httpRequest.responseType = "json";
    /* 정의된 서버에 요청을 전송 */
    httpRequest.send();
  });
};
