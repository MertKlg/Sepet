<template>
  <NuxtLayout>
    <section class="wh-100 vh-100 position-relative">
      <div
        class="w-100 h-100 position-absolute z-n1"
        :style="{
          background: `url('/images/home-page-bg-image.jpg')`,
          filter: 'blur(1px)',
        }"
      ></div>

      <div class="container h-100 position-relative d-flex align-items-center">
        <div class="col-12">
          <h1 class="fw-bold">Welcome to Sepet</h1>
          <p>We are away from you one step.</p>

          <NuxtLink
            to="/shop"
            class="btn text-white py-2 px-5"
            :style="{
              background: `var(--tertitary-color)`,
            }"
            >Shop</NuxtLink
          >
        </div>
      </div>
    </section>
  </NuxtLayout>
</template>

<style scoped>
.category-image {
  height: 60px;
  width: 70px;
}

.bg-image {
  filter: blur(2px);
}

.card {
  background: var(--tertitary-color) !important;
  border: none !important;
}
</style>