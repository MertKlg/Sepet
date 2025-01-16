<template>
    <header class="position-relative z-1">
        <div class="container">
            <div class="row py-3">
                <NuxtLink to="/" class="fw-bold col m-0 fs-1 text-decoration-none text-white text-center">Sepet
                </NuxtLink>

                <nav class="nav col">
                    <ul class="h-100 w-100 m-0 list-unstyled d-flex  align-items-center justify-content-center">
                        <li>
                            <NuxtLink class="nav-link text-white" to="/">Home</NuxtLink>
                        </li>
                        <li>
                            <NuxtLink class="nav-link text-white" to="/shop">Shop</NuxtLink>
                        </li>
                        <li>
                            <NuxtLink class="nav-link text-white" to="/cart">Cart</NuxtLink>
                        </li>
                    </ul>
                </nav>

                <div class="col d-flex align-items-center justify-content-center">
                    <div class="w-100 h-100 d-flex align-items-center justify-content-center" v-if="!auth.isAuth">
                        <NuxtLink to="/sign-in" class="btn me-1 px-3 text-white">Sign in</NuxtLink>
                        <NuxtLink to="/sign-up" class="btn ms-1 px-3 text-white">Sign Up</NuxtLink>
                    </div>

                    <NuxtLink to="/profile" class="profile-link text-decoration-none position-relative h-100 d-flex align-items-center justify-content-center" v-else>
                       
                        <div class="d-flex align-items-center border rounded px-5 py-2">
                            <span class="material-symbols-outlined fs-3 text-white">
                                person
                            </span>
                            <h4 class="text-white m-0">{{ profile.userProfile?.username }}</h4>
                        </div>

                        <ul style="background-color: var(--tertitary-color)" class="d-none position-absolute m-0 top-100 list-unstyled w-100 px-3 text-white">
                            <li class="py-2">
                                <NuxtLink to="/address" class="text-decoration-none text-white">
                                    Delivery address
                                </NuxtLink>
                            </li>
                            <li class="py-2">
                                <NuxtLink to="/orders" class="text-decoration-none text-white">
                                    Orders
                                </NuxtLink>
                            </li>

                            <li class="py-2">
                                <RouterLink to="#" class="text-decoration-none text-white" @click="auth.logOut()">
                                    LogOut
                                </RouterLink>
                            </li>
                        </ul>

                    </NuxtLink>
                </div>
            </div>
        </div>

    </header>
</template>

<style scoped>
header {
    background: var(--primary-color);
}


.btn {
    background: var(--tertitary-color);
}

.profile-link:hover > ul{
    display: block !important;
}

</style>



<script lang="ts" setup>
import { authStorage } from '~/storage/AuthStorage';
import { profileStore } from '~/storage/ProfileStorage';

const profile = profileStore()
const auth = authStorage()

</script>