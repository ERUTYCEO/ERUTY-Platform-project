<div align="center">
    <a href="https://user-images.githubusercontent.com/64780539/213195321-60df76c6-6cc3-43f5-8be8-63c69b51cb18.png">
        <img src="https://user-images.githubusercontent.com/64780539/213195321-60df76c6-6cc3-43f5-8be8-63c69b51cb18.png" alt="Logo" width="300" height="250">
    </a>
    <h1 align="center">ERUTY_PLATFORM_PROJECT</h1>
    <p align="left">
    다양한 3D 그래픽 디자인들을 검색하고 확인해볼 수 있는 거래 플랫폼
    <br />
    직접 3D Model을 업로드할 수 있으며 마음에 드는 3D Model들을 구매할 수 있습니다.
    </p>
</div>

<br/>

### __Built With__

* [![HTML][HTML.io]][HTML-url]
* [![CSS][CSS.io]][CSS-url]
* [![Javascript][Javascript.io]][Javascript-url]
* [![Java][Java.io]][Java-url]
* [![Spring][Spring.io]][Spring-url]

<br/>

### __Function__

1. Create
    - 회원가입
    - 3D 디자인 업로드

2. Read
    - Home 화면에서 3D 디자인 클릭 시 해당 디자인의 정보 페이지로 이동
    - 3D 디자인 검색 기능
    - 다운로드한 3D 디자인 리스트 확인
    - 업로드한 3D 디자인 리스트 확인 (Uploader)

3. Update
    - 비밀번호 변경
    - 비밀번호 찾기 -> 임시 비밀번호 발급
    - 3D 디자인 정보 변경 (Uploader)

4. Delete
    - 회원 탈퇴
    - 3D 디자인 업로드 취소 (Uploader)

### __Object__

1. Member
    - _Id
    - 이름
    - 이메일
    - 비밀번호
    - 업로드 리스트
    - 다운로드 리스트

2. Item
    - _Id
    - 디자인 명칭
    - 창작자
    - 가격
    - 용량
    - 창작연월일
    - 작품 설명
    - 제작 툴
    - likes
    - 조회수

# Settings
> **Backend**
> --------------------------------

>   > __Spring__
>   >   > - Spring Boot 2.7.7
>   >   > - Oracle JDK 11
>   >   > - Gradle-Groovy
>   >   > - MongoDB-community@6.0

>   > __Dependencies__
>   >   > - Spring Web 
>   >   > - Spring Boot DevTools
>   >   > - Lombok
>   >   > - Validation
>   >   > - Spring Data MongoDB

> > [Build.gradle](https://github.com/ERUTYCEO/ERUTY-Platform-project/blob/master/back/build.gradle)

> > [application.yml](https://github.com/ERUTYCEO/ERUTY-Platform-project/blob/master/back/src/main/resources/application.yml)

[HTML.io]: https://img.shields.io/badge/html-orange?style=for-the-badge&logo=html&logoColor=white
[HTML-url]: https://html.spec.whatwg.org

[CSS.io]: https://img.shields.io/badge/css-blue?style=for-the-badge&logo=css&logoColor=white
[CSS-url]: https://developer.mozilla.org/ko/docs/Web/CSS/Reference

[Javascript.io]: https://img.shields.io/badge/javascript-black?style=for-the-badge&logo=javascript&logoColor=yellow
[Javascript-url]: https://developer.mozilla.org/ko/docs/Web/JavaScript

[Java.io]: https://img.shields.io/badge/java-chocolate?style=for-the-badge&logo=java&logoColor=orangebrown
[Java-url]: https://developer.mozilla.org/ko/docs/Web/JavaScript

[Spring.io]: https://img.shields.io/badge/spring-white?style=for-the-badge&logo=spring&logoColor=green
[Spring-url]: https://spring.io