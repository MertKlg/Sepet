<template>
    <NuxtLayout>
        <section class=" min-vh-100">
            <ToastMessages :messages="status" @close-toast="closeToast" />
            <div class="container">
                <div class="col-6 mt-5 mx-auto">
                    <Form @submit="signIn" :validation-schema="formSchema">
                        <div class="mb-3 text-center">
                            <h1 class="fw-bold" style="color: var(--primary-color);">Sign in</h1>
                        </div>

                        <div class="mb-3">
                            <label for="email-form-input" class="form-label">Email</label>
                            <Field name="email" type="email" class="form-control" id="email-form-input" />
                            <ErrorMessage name="email" class="text-danger fw-bold" />
                        </div>

                        <div class="mb-3">
                            <label for="password-form-input" class="form-label">Password</label>
                            <Field name="password" type="password" class="form-control" id="password-form-input" />
                            <ErrorMessage name="password" class="text-danger fw-bold" />
                        </div>
                        <input type="submit" value="Sign in" class="btn w-100 text-white"
                            style="background-color: var(--tertitary-color) !important;">
                    </Form>
                </div>
            </div>


        </section>
    </NuxtLayout>
</template>

<script lang="ts" setup>
import { authStorage } from '~/storage/AuthStorage';
import { reactive } from 'vue';
import type { IResponse } from '~/model/interface/IResponse';
import { Field, Form, ErrorMessage } from 'vee-validate';
import * as Yup from "yup"

const status = ref<IResponse[]>([])

const auth = authStorage()

const formSchema = Yup.object({
    email: Yup.string().email().required("Email required"),
    password: Yup.string().required("Password required").matches(RegExp("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$"), "Password must be have min 8, one lowercase, one uppercase and one special characters")
})

const signIn = async (values: string[]) => {
    await auth.signIn(values, (e) => {
        status.value.push(e)
    })
}

const closeToast = (response : IResponse) => {
    status.value.splice(response,1)
}

</script>