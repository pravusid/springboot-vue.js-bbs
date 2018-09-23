<template>
  <div id="app">
    <notifications group="noti" position="bottom center" />
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
@import url('https://fonts.googleapis.com/icon?family=Material+Icons');
@import url('https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css');
@import url('https://cdn.rawgit.com/hiun/NanumSquare/master/nanumsquare.css');

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
