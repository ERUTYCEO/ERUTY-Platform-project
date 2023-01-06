const myCanvas = document.getElementById("main-3d");
const loader = new THREE.OBJLoader();
const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(
  35,
  window.innerWidth / window.innerHeight,
  0.1,
  1000
);
loader.load(
  "./cat.obj",
  function (object) {
    scene.add(object);
  },
  function (xhr) {
    console.log((xhr.loaded / xhr.total) * 100 + "% loaded");
  },
  function (error) {
    console.log("An error happened");
  }
);

const renderer = new THREE.WebGLRenderer({
  canvas: myCanvas,
  alpha: true,
});
renderer.setSize(2000, 2000);
//document.body.appendChild(renderer.domElement);

const geometry = new THREE.BoxGeometry(1, 1, 1);
const material = new THREE.MeshBasicMaterial({ color: 0x00ff00 });
const cube = new THREE.Mesh(geometry, material);
scene.add(cube);
camera.position.z = 5;

function animate() {
  requestAnimationFrame(animate);
  cube.rotation.z += 0.01;
  cube.rotation.y += 0.01;
  renderer.render(scene, camera);
}

animate();
