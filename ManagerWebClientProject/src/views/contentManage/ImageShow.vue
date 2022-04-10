<template>
  <div>
    <div class="block">
      <el-carousel trigger="click" height="300px" >
        <el-carousel-item v-for="item in 4" :key="item">
          <h3 class="small">{{ item }}</h3>
        </el-carousel-item>
      </el-carousel>
      <el-divider direction="vertical"></el-divider>
    </div>
  </div>
</template>

<script>
export default {
  name: "ImageShow"
}
</script>

<style scoped>

</style>
