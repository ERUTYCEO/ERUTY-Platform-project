// 유효성 검사
function checkFindPassword() {
  if (findPasswordform.exampleInputEmail1.value == "") {
    $("#findPWCheckMessage").show();
    return false;
  }
  $("#findPWCheckMessage").hide();
  return true;
}
function checkLogin() {
  if (
    loginform.exampleInputEmail1.value == "" ||
    loginform.exampleInputPassword1.value == ""
  ) {
    $("#loginMessage").show();
    return false;
  }
  $("#loginMessage").hide();
  return true;
}
function checkSignUp() {
  if (signUpform.exampleInputEmail1.value == "") {
    $("#signUpEmailMessage").show();
    return false;
  }
  $("#signUpEmailMessage").hide();

  if (signUpform.exampleInputPassword1.value == "") {
    $("#signUpPWMessage").show();
    return false;
  }
  $("#signUpPWMessage").hide();

  if (
    signUpform.exampleInputPassword1.value !=
    signUpform.exampleInputPassword2.value
  ) {
    $("#signUpPWDMessage").show();
    return false;
  }
  $("#signUpPWDMessage").hide();

  if (
    signUpform.exampleCheck4.checked == false ||
    signUpform.exampleCheck3.checked == false ||
    signUpform.exampleCheck2.checked == false ||
    signUpform.exampleCheck5.checked == false
  ) {
    $("#signUpTermMessage").show();
    return false;
  }
  $("#signUpTermMessage").hide();
  return true;
}

// 전체선택

$("#allcheck").on("click", function () {
  var checked = $(this).is(":checked");
  if (checked) {
    $(this)
      .closest(".agreement-container")
      .find(".form-check-input")
      .prop("checked", true);
  } else {
    $(this)
      .closest(".agreement-container")
      .find(".form-check-input")
      .prop("checked", false);
  }
  console.log($(this).closest(".agreement-container"));
});
$(".agreement-container .form-check-input").on("click", function () {
  var chkGroup = $(this).closest(".agreement-container").find(".check-group");
  var chkGroup_cnt = chkGroup.length;
  checked_cnt = $(".check-group .form-check-input:checked").length;

  if (checked_cnt < chkGroup_cnt) {
    $("#allcheck").prop("checked", false);
  } else if (checked_cnt == chkGroup_cnt) {
    $("#allcheck").prop("checked", true);
  }
});
