const myCanvas = document.getElementById("main-3d");
const loader = new THREE.OBJLoader();
const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(
  35,
  window.innerWidth / window.innerHeight,
  0.1,
  1000
);
camera.position.z = 5;
const renderer = new THREE.WebGLRenderer({
  canvas: myCanvas,
  alpha: true,
});
renderer.setSize(2000, 2000);

const controls = new THREE.OrbitControls(camera, renderer.domElement);
controls.rotateSpeed = 1.0; // 마우스로 카메라를 회전시킬 속도입니다. 기본값(Float)은 1입니다.
controls.zoomSpeed = 1.2; // 마우스 휠로 카메라를 줌 시키는 속도 입니다. 기본값(Float)은 1입니다.
controls.panSpeed = 0.8; // 패닝 속도 입니다. 기본값(Float)은 1입니다.
controls.minDistance = 5; // 마우스 휠로 카메라 거리 조작시 최소 값. 기본값(Float)은 0 입니다.
controls.maxDistance = 100; // 마우스 휠로 카메라 거리 조작시 최대 값. 기본값(Float)은 무제한 입니다.

const object = new THREE.Object3D();
loader.load(
  "../img/cat.obj",
  function (objectload) {
    object.add(objectload);
    scene.add(object);
  },
  function (xhr) {
    console.log((xhr.loaded / xhr.total) * 100 + "% loaded");
  },
  function (error) {
    console.log("An error happened");
  }
);

const geometry = new THREE.BoxGeometry(1, 1, 1);
const material = new THREE.MeshBasicMaterial({ color: 0x00ff00 });
const cube = new THREE.Mesh(geometry, material);
object.add(cube);

function animate() {
  requestAnimationFrame(animate);
  object.rotation.z -= 0.01;
  object.rotation.y -= 0.01;
  renderer.render(scene, camera);
  controls.update();
}

animate();
