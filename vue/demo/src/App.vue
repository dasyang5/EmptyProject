<template>
  <div id="app">
      <router-view id="router-view"/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style lang="scss">
    #app {
        width: 100%;
        height: 100%;
        margin: 0;
        padding: 0;
    }
    body, html {
        height: 100%;
        width: 100%;
        margin: 0;
        padding: 0;
    }
    .table-button{
        padding: 5px 8px;
    }
</style>
