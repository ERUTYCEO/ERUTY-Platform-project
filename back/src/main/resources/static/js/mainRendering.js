const myCanvas = document.getElementById("main-3d");
const loader = new THREE.OBJLoader();
const mtlLoader = new THREE.MTLLoader();
const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(
  75,
  window.innerWidth / window.innerHeight,
  0.1,
  1000
);
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
mtlLoader.setPath("/img/");
mtlLoader.load(
  "ee.mtl",
  function (materials) {
    materials.preload();
    loader.setMaterials(materials);
    loader.setPath("/img/");
    loader.load(
      "ee.obj",
      function (objectload) {
        const { minX, maxX, minY, maxY, minZ, maxZ } =
          getBoundingBox(objectload);
        const width = maxX - minX;
        const height = maxY - minY;
        const depth = maxZ - minZ;
        const size = Math.max(width, height, depth);
        const center = new THREE.Vector3(
          (minX + maxX) / 1.75,
          (minY + maxY) / 1.75,
          (minZ + maxZ) / 1.75
        );
        camera.position.set(
          center.x + size / 1.5,
          center.y + size / 1.5,
          center.z + size / 1.5
        );
        camera.lookAt(center);
        camera.near = size / 100;
        camera.far = size * 100;
        camera.updateProjectionMatrix();

        //조명 추가
        shadowLight();

        //랜더링
        object.add(objectload);
        scene.add(object);
      },
      function (xhr) {
        console.log((xhr.loaded / xhr.total) * 100 + "% loaded");
      },
      function (error) {
        console.log(error);
        console.log("An error happened");
      }
    );
    animate();
  },
  function (xhr) {
    // 로드되는 동안 호출되는 함수
    console.log("MTLLoader: ", (xhr.loaded / xhr.total) * 100, "% loaded");
  },
  function (error) {
    // 로드가 실패했을때 호출하는 함수
    console.error("MTLLoader 로드 중 오류가 발생하였습니다.", error);
    alert("MTLLoader 로드 중 오류가 발생하였습니다.");
  }
);

function animate() {
  requestAnimationFrame(animate);
  object.rotation.x += 0.01;
  object.rotation.y += 0.01;
  renderer.render(scene, camera);
  controls.update();
}
function getBoundingBox(objectload) {
  var minX = Infinity,
    minY = Infinity,
    minZ = Infinity;
  var maxX = -Infinity,
    maxY = -Infinity,
    maxZ = -Infinity;

  for (let i = 0; i < objectload.children.length; i++) {
    objectload.children[i].geometry.computeBoundingSphere();
  }

  for (let i = 0; i < objectload.children.length; i++) {
    minX = Math.min(
      minX,
      objectload.children[i].geometry.boundingSphere.center.x
    );
    minY = Math.min(
      minY,
      objectload.children[i].geometry.boundingSphere.center.y
    );
    minZ = Math.min(
      minZ,
      objectload.children[i].geometry.boundingSphere.center.z
    );
    maxX = Math.max(
      maxX,
      objectload.children[i].geometry.boundingSphere.center.x
    );
    maxY = Math.max(
      maxY,
      objectload.children[i].geometry.boundingSphere.center.y
    );
    maxZ = Math.max(
      maxZ,
      objectload.children[i].geometry.boundingSphere.center.z
    );
  }
  return {
    minX,
    maxX,
    minY,
    maxY,
    minZ,
    maxZ,
  };
}

//조명 추가
function shadowLight() {
  const ambientLight = new THREE.AmbientLight(0xcccccc, 0.4);
  scene.add(ambientLight);

  const pointLight = new THREE.PointLight(0xffffff, 0.8);
  camera.add(pointLight);
  scene.add(camera);
}
