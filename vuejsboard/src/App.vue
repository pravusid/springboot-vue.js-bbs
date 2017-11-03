<template>
  <div id="app">
    <app-header></app-header>
    <router-view></router-view>
  </div>
</template>

<script>
import appHeader from './components/Header.vue';

export default {
  name: 'app',
  components: {
    appHeader,
  },
};
</script>

<style>
#app {
  font-size: 1em;
  font-family: 'Nanum Square';
}

.bold {
  font-weight: bold;
}

.inline {
  display: inline;
}
</style>
