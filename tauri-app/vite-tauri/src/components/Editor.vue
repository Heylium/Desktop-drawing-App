<script setup lang="ts">
// @ts-ignore
import { onMounted } from 'vue'

// @ts-ignore
import {MoleculeEditor} from "butlerov"

let editor: MoleculeEditor;

onMounted(() => {
  const element: HTMLDivElement = document.getElementById("butlerov") as HTMLDivElement;
  editor = MoleculeEditor.from_html_element(element);
});

/**
 * clear the drawing on editor
 */
const clearDrawing = () => {
  editor.clear();
}

</script>

<template>
  <div class="page-wrapper">
    <div class="column-left">
      <button @click="clearDrawing">Clear</button>
    </div>
    <div class="column-right">
      <div id="butlerov" class="border" style="height: 600px; width: 600px;"></div>
    </div>
  </div>
</template>

<style scoped>
.border {
  border: dashed 2px black;
}

.page-wrapper {
  display: flex;
}

.column-left {
  flex: 70%;
  padding: 20px 5px 20px 15px;
}
.column-right {
  flex: 30%;
  padding: 20px 5px 20px 15px;
  border-left: solid 2px #555;
}


</style>