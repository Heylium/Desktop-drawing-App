import * as THREE from "three"

const scene = new THREE.Scene();

const geometry = new THREE.BoxGeometry(100, 100, 100);

const material = new THREE.MeshBasicMaterial({
    color: 0xff0000,
})

const mesh = new THREE.Mesh(geometry, material);

mesh.position.set(0,0,0)

scene.add(mesh);

console.log(mesh)