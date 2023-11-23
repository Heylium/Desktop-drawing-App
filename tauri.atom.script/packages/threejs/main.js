import * as THREE from "three"

const scene = new THREE.Scene();

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
mesh.position.set(0, 3)
scene.add(mesh);

console.log(mesh);

// using webgl2 renderer
const renderer = new THREE.WebGLRenderer()
renderer.setSize(window.innerWidth, window.innerHeight)
document.body.appendChild(renderer.domElement);

const grid = new THREE.GridHelper(10, 10)
scene.add(grid)


// add animation with mousemove
const animate = () => {
    requestAnimationFrame(animate)
    mesh.rotation.x += 0.01;
    renderer.render(scene, camera);
}
animate();