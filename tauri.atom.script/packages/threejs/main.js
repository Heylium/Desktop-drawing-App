import * as THREE from "three"
import {OrbitControls} from "three/addons";

const scene = new THREE.Scene();

// add scene background
scene.background = new THREE.Color(0x666666);

// add picture as background
// const pic = new THREE.CubeTextureLoader.setPath('/').load()

// add scene fog
const fog = new THREE.Fog(0xcccccc, 10, 15);
scene.fog = fog;

const camera = new THREE.PerspectiveCamera();
camera.position.z = 10;
camera.position.y = 2;

const geometry = new THREE.BoxGeometry(2, 2, 2);

// scene material
const material = new THREE.MeshBasicMaterial({
    color: 0x00ff00,
});

// scene mesh
const mesh = new THREE.Mesh(geometry, material);
mesh.position.set(0, 3, 0)
scene.add(mesh);

console.log(mesh);

const moveMesh = () => {
    mesh.position.set(3,5,0);
    camera.lookAt(mesh.position)
}

// using webgl2 renderer
const renderer = new THREE.WebGLRenderer()
renderer.setSize(window.innerWidth, window.innerHeight)
document.body.appendChild(renderer.domElement);

const controls = new OrbitControls(camera, renderer.domElement);
controls.addEventListener('change', () => {
    console.log('changed')
})

// orbit damping added.
controls.enableDamping = true;
controls.dampingFactor = 0.01;

// added auto rotate
controls.autoRotate = true;
controls.autoRotateSpeed = 0.5;

// axis helper
const axesHelper = new THREE.AxesHelper(5);
axesHelper.position.y = 3;
scene.add(axesHelper);

// add grid
const grid = new THREE.GridHelper(10, 10)
scene.add(grid)


// add animation
const animate = () => {
    requestAnimationFrame(animate)
    mesh.rotation.x += 0.01;

    // update controller
    controls.update()

    renderer.render(scene, camera);
}
animate();