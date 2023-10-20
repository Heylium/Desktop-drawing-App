<script setup lang="ts">
// @ts-ignore
import { onMounted } from 'vue'

// @ts-ignore
import {MoleculeEditor} from "butlerov"

onMounted(() => {
  const element: HTMLDivElement = document.getElementById("butlerov") as HTMLDivElement;
  MoleculeEditor.from_html_element(element);
});

</script>

<template>
  <div id="butlerov" class="border" style="height: 600px; width: 600px;"></div>
</template>

<style scoped>
.border {
  border: dashed 2px black;
}

</style>