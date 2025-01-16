<template>
    <NuxtLayout>
        <section class=" min-vh-100 mt-5">

            <ToastMessages :messages="status" @close-toast="closeToast" />

            <div class="container">
                <div class="col-6 mt-5 mx-auto">
                    <Form @submit="onSubmit" :validation-schema="formSchema">

                        <div class="mb-3 text-center">
                            <h1 class="fw-bold" style="color: var(--primary-color);">Sign up</h1>
                        </div>

                        <div class="mb-3">
                            <label for="username-form-input" class="form-label">Username</label>

                            <Field name="username" rules="required" type="text" class="form-control"
                                id="username-form-input" />

                            <ErrorMessage name="username" class="text-danger fw-bold" />
                        </div>

                        <div class="mb-3">
                            <label for="email-form-input" class="form-label">Email</label>
                            <Field name="email" type="email" class="form-control" id="email-form-input" />
                            <ErrorMessage name="email" class="text-danger fw-bold" />
                        </div>

                        <div class="mb-3">
                            <label for="phone-form-input" class="form-label">Phone</label>
                            <Field name="phone" type="tel" maxLength="11" class="form-control" id="phone-form-input" />
                            <ErrorMessage name="phone" class="text-danger fw-bold" />
                        </div>

                        <div class="mb-3">
                            <label for="password-form-input" class="form-label">Password</label>
                            <Field name="password" type="password" class="form-control" id="password-form-input" />
                            <ErrorMessage name="password" class="text-danger fw-bold" />
                        </div>

                        <div class="mb-3">
                            <label for="password-again-form-input" class="form-label">Password again</label>
                            <Field name="passwordAgain" type="password" class="form-control"
                                id="password-again-form-input" />
                            <ErrorMessage name="passwordAgain" class="text-danger fw-bold" />
                        </div>

                        <input type="submit" value="Sign up" class="btn w-100 text-white"
                            style="background-color: var(--tertitary-color) !important;">
                    </form>
                </div>
            </div>


        </section>
    </NuxtLayout>
</template>

<script setup lang="ts">
import type { ISignUp } from '~/model/interface/ISignUp';
import { reactive } from 'vue';

import { Field, Form, ErrorMessage } from 'vee-validate';
import * as Yup from 'yup';
import { API_URL } from '~/common/Api';
import type { IResponse } from '~/model/interface/IResponse';

const status = reactive<IResponse[]>([])


const formSchema = Yup.object({
    username: Yup.string().required("Username required"),
    email: Yup.string().email("Email is not validated").required("Email is required"),
    phone: Yup.string().min(11, "Phone must be have only min 11").max(11, "Phone must be have only max 11").required("Phone required").matches(RegExp("^[0-9]*$"), "Phone must be only numeric"),
    password: Yup.string().required("Password required").matches(RegExp("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$"),"Password must be have min 8, one lowercase, one uppercase and one special characters"),
    passwordAgain: Yup.string().required("Password again required").matches(RegExp("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$"), "Password must be have min 8, one lowercase, one uppercase and one special characters"),
})


const onSubmit = async (values: string[]) => {
    await signUp(JSON.stringify(values))
}



const signUp = async (form: Object) => {
    const {data,error} = await useAsyncData("signUp", () => $fetch((API_URL + "v1/auth/register"), {credentials : "include", method : "POST", body : form}))
    if(error.value){
        status.push(error.value.data as IResponse)
        return
    }

    status.push(data.value as IResponse)
}

const closeToast = (response : IResponse) => {
    status.splice(response,1)
}

</script>

<style scoped></style>